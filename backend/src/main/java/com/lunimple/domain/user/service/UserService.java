package com.lunimple.domain.user.service;

import com.lunimple.domain.contest.enums.ContestType;
import com.lunimple.domain.problems.entity.Problem;
import com.lunimple.domain.problems.entity.Submission;
import com.lunimple.domain.problems.enums.ProblemCode;
import com.lunimple.domain.problems.repository.ProblemRepository;
import com.lunimple.domain.user.enums.RecommendationTier;
import com.lunimple.domain.user.dto.response.*;
import com.lunimple.domain.user.entity.User;
import com.lunimple.domain.user.entity.UserProblem;
import com.lunimple.domain.user.enums.ProblemResult;
import com.lunimple.domain.user.enums.RankingSortType;
import com.lunimple.domain.user.repository.SubmissionRepository;
import com.lunimple.domain.user.repository.UserProblemRepository;
import com.lunimple.domain.user.repository.UserRepository;
import com.lunimple.global.util.RankLabelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final int MIN_ANALYSIS_COUNT = 1;
    private static final int RECENT_DAYS = 360;
    private static final int STREAK_CALENDAR_DAYS = 365;
    private static final int MAX_RECOMMENDATIONS = 7;

    private final UserRepository userRepository;
    private final UserProblemRepository userProblemRepository;
    private final SubmissionRepository submissionRepository;
    private final ProblemRepository problemRepository;

    public UserProfileResponse getUserProfile(String handle) {
        User user = userRepository.findByHandle(handle);
        Long userId = user.getId();
        List<UserProblem> userProblems = userProblemRepository.findAllByUserId(userId);

        int totalSolved = 0;
        int totalAttempt = 0;
        int solvedRatingSum = 0;
        int triedProblemCount = userProblems.size();
        long waCount = 0;

        for (UserProblem userProblem : userProblems) {
            totalAttempt += userProblem.getAttemptCount();

            if (userProblem.getLastResult() == ProblemResult.WA) {
                waCount++;
            }

            if (userProblem.getSolved()) {
                totalSolved++;
                solvedRatingSum += userProblem.getProblem().getDifficulty();
            }
        }

        double averageSolvedRating =
                totalSolved == 0 ? 0 :
                        Math.round((double) solvedRatingSum / totalSolved * 100) / 100.0;

        double averageSubmissionCount =
                totalSolved == 0 ? 0 :
                        Math.round((double) totalAttempt / totalSolved * 100) / 100.0;

        double successRate =
                triedProblemCount == 0 ? 0 :
                        Math.round((double) totalSolved * 100 / triedProblemCount * 100) / 100.0;

        double waRatio =
                triedProblemCount == 0 ? 0 :
                        Math.round((double) waCount * 100 / triedProblemCount * 100) / 100.0;

        return UserProfileResponse.of(
                user.getHandle(),
                user.getRating(),
                RankLabelUtil.toRankLabel(user.getRating()),
                totalSolved,
                averageSolvedRating,
                averageSubmissionCount,
                successRate,
                waRatio
        );
    }

    public Page<RankingResponse> getRanking(
            RankingSortType rankingSortType,
            Pageable pageable
    ) {
        Sort sort = switch (
                rankingSortType == null
                ? RankingSortType.RATING
                : rankingSortType
                ) {
            case RATING -> Sort.by(Sort.Direction.DESC, "rating");
            case WIN -> Sort.by(Sort.Direction.DESC, "win");
            case MATCH -> Sort.by(Sort.Direction.DESC, "match");
        };
        pageable = PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        sort
                );

        return userRepository.findAll(
                pageable
                )
                .map(RankingResponse::from);
    }

    public UserStreakResponse getUserStreak(String handle) {
        User user = userRepository.findByHandle(handle);
        List<UserProblem> userProblems = userProblemRepository.findAllByUserId(user.getId());
        Set<LocalDate> solvedDates = userProblems.stream()
                .filter(UserProblem::getSolved)
                .filter(up -> up.getSolvedAt() != null)
                .map(up -> up.getSolvedAt().toLocalDate())
                .collect(Collectors.toSet());

        LocalDate today = LocalDate.now();
        LocalDate rangeStart = today.minusDays(STREAK_CALENDAR_DAYS - 1L);

        List<String> activeDates = solvedDates.stream()
                .filter(d -> !d.isBefore(rangeStart) && !d.isAfter(today))
                .sorted()
                .map(LocalDate::toString)
                .toList();

        LocalDate date = today;
        int streak = 0;
        if (!solvedDates.contains(date)) {
            date = date.minusDays(1);
        }

        while (solvedDates.contains(date)) {
            streak++;
            date = date.minusDays(1);
        }
        return UserStreakResponse.of(streak, activeDates);
    }

    public UserRecommendationResponse getUserRecommendations(String handle) {
        Long userId = userRepository.findByHandle(handle).getId();
        List<UserProblem> userProblems = userProblemRepository.findAllByUserId(userId);

        Set<Long> solvedProblemIds = userProblems.stream()
                .filter(UserProblem::getSolved)
                .map(up -> up.getProblem().getId())
                .collect(Collectors.toSet());

        UserProfileResponse profile = getUserProfile(handle);
        UserWeaknessResponse weaknessResponse = getUserWeakness(handle);

        double avgDifficulty = profile.averageSolvedRating() > 0
                ? profile.averageSolvedRating()
                : 400;

        List<Problem> unsolved = problemRepository.findAll().stream()
                .filter(p -> !solvedProblemIds.contains(p.getId()))
                .toList();

        List<ProblemRecommendationResponse> recommendations = new ArrayList<>();
        Set<Long> picked = new HashSet<>();

        for (WeaknessItemResponse weakness : weaknessResponse.weaknesses().stream().limit(3).toList()) {
            unsolved.stream()
                    .filter(p -> p.getContest().getContestType() == weakness.contestType())
                    .filter(p -> p.getCode() == weakness.problemCode())
                    .findFirst()
                    .ifPresent(problem -> addRecommendation(
                            recommendations,
                            picked,
                            problem,
                            RecommendationTier.ESSENTIAL,
                            weakness.contestType() + " " + weakness.problemCode()
                                    + " 약점 보완"
                    ));
        }

        unsolved.stream()
                .filter(p -> !picked.contains(p.getId()))
                .filter(p -> p.getDifficulty() <= avgDifficulty * 1.1)
                .sorted(Comparator.comparingInt(Problem::getDifficulty))
                .limit(2)
                .forEach(problem -> addRecommendation(
                        recommendations,
                        picked,
                        problem,
                        RecommendationTier.ESSENTIAL,
                        "기초 실력 강화"
                ));

        unsolved.stream()
                .filter(p -> !picked.contains(p.getId()))
                .filter(p ->
                        p.getDifficulty() > avgDifficulty * 0.9
                                && p.getDifficulty() <= avgDifficulty * 1.3
                )
                .sorted(Comparator.comparingInt(
                        (Problem p) -> Math.abs(p.getDifficulty() - (int) avgDifficulty)
                ))
                .limit(2)
                .forEach(problem -> addRecommendation(
                        recommendations,
                        picked,
                        problem,
                        RecommendationTier.ADDITIONAL,
                        "현재 실력 유지"
                ));

        unsolved.stream()
                .filter(p -> !picked.contains(p.getId()))
                .filter(p -> p.getDifficulty() >= avgDifficulty * 1.2)
                .sorted(Comparator.comparingInt(Problem::getDifficulty))
                .limit(2)
                .forEach(problem -> addRecommendation(
                        recommendations,
                        picked,
                        problem,
                        RecommendationTier.CHALLENGE,
                        "난이도 상승 도전"
                ));

        List<ProblemRecommendationResponse> limited = recommendations.stream()
                .limit(MAX_RECOMMENDATIONS)
                .toList();

        Set<String> solvedUrls = userProblems.stream()
                .filter(UserProblem::getSolved)
                .map(up -> up.getProblem().getUrl())
                .collect(Collectors.toSet());

        int solvedCount = (int) limited.stream()
                .filter(rec -> solvedUrls.contains(rec.url()))
                .count();

        return UserRecommendationResponse.of(limited, solvedCount);
    }

    private void addRecommendation(
            List<ProblemRecommendationResponse> recommendations,
            Set<Long> picked,
            Problem problem,
            RecommendationTier tier,
            String hint
    ) {
        if (picked.contains(problem.getId())) {
            return;
        }
        picked.add(problem.getId());
        recommendations.add(
                ProblemRecommendationResponse.of(
                        tier,
                        problem.getTitle(),
                        problem.getDifficulty(),
                        problem.getUrl(),
                        hint
                )
        );
    }

    public UserStatisticsResponse getUserStatistics(String handle) {
        Long userId = userRepository.findByHandle(handle).getId();
        List<UserProblem> userProblems = userProblemRepository.findAllByUserId(userId);
        Map<ContestType, Map<ProblemCode, List<UserProblem>>> grouped =
                userProblems.stream()
                        .collect(Collectors.groupingBy(
                                up -> up.getProblem()
                                        .getContest()
                                        .getContestType(),
                                Collectors.groupingBy(
                                        up -> up.getProblem().getCode()
                                )
                        ));

        List<ContestStatisticsResponse> statistics = grouped.entrySet()
                .stream()
                .map(contestEntry -> {

                    List<ProblemStatisticsResponse> problems =
                            contestEntry.getValue()
                                    .entrySet()
                                    .stream()
                                    .map(problemEntry -> {

                                        List<UserProblem> list =
                                                problemEntry.getValue();

                                        int total = list.size();

                                        int solved = (int) list.stream()
                                                .filter(UserProblem::getSolved)
                                                .count();

                                        return new ProblemStatisticsResponse(
                                                problemEntry.getKey(),
                                                total,
                                                solved
                                        );
                                    })
                                    .toList();

                    return new ContestStatisticsResponse(
                            contestEntry.getKey(),
                            problems
                    );
                })
                .toList();
        return UserStatisticsResponse.of(
                statistics
        );
    }

    public UserWeaknessResponse getUserWeakness(String handle) {

        User user = userRepository.findByHandle(handle);

        List<UserProblem> userProblems =
                userProblemRepository.findAllByUserId(user.getId());

        List<Submission> recentSubmissions =
                submissionRepository.findAllByUserIdAndSubmittedAtAfter(
                        user.getId(),
                        LocalDateTime.now().minusDays(RECENT_DAYS)
                );

        /*
         * ContestType + ProblemCode 기준 최근 실패 수
         */
        Map<WeaknessKey, Long> recentFailMap =
                recentSubmissions.stream()
                        .filter(submission ->
                                submission.getResult() != ProblemResult.AC
                        )
                        .collect(Collectors.groupingBy(
                                submission -> new WeaknessKey(
                                        submission.getProblem()
                                                .getContest()
                                                .getContestType(),
                                        submission.getProblem()
                                                .getCode()
                                ),
                                Collectors.counting()
                        ));

        /*
         * ContestType + ProblemCode 기준 그룹화
         */
        Map<WeaknessKey, List<UserProblem>> grouped =
                userProblems.stream()
                        .collect(Collectors.groupingBy(
                                userProblem -> new WeaknessKey(
                                        userProblem.getProblem()
                                                .getContest()
                                                .getContestType(),
                                        userProblem.getProblem()
                                                .getCode()
                                )
                        ));

        List<WeaknessItemResponse> weaknesses =
                grouped.entrySet()
                        .stream()
                        .filter(entry ->
                                entry.getValue().size() >= MIN_ANALYSIS_COUNT
                        )
                        .map(entry -> {

                            WeaknessKey key = entry.getKey();

                            ContestType contestType =
                                    key.contestType();

                            ProblemCode problemCode =
                                    key.problemCode();

                            List<UserProblem> problems =
                                    entry.getValue();

                            int totalCount =
                                    problems.size();

                            long solvedCount =
                                    problems.stream()
                                            .filter(UserProblem::getSolved)
                                            .count();

                            double successRate =
                                    totalCount == 0
                                            ? 0
                                            : (double) solvedCount / totalCount;

                            double averageAttemptCount =
                                    problems.stream()
                                            .mapToInt(
                                                    UserProblem::getAttemptCount
                                            )
                                            .average()
                                            .orElse(0);

                            int recentFailCount =
                                    recentFailMap
                                            .getOrDefault(key, 0L)
                                            .intValue();

                            double weaknessScore =
                                    calculateWeaknessScore(
                                            successRate,
                                            averageAttemptCount,
                                            recentFailCount
                                    );

                            return WeaknessItemResponse.of(
                                    contestType,
                                    problemCode,
                                    round(successRate * 100),
                                    round(averageAttemptCount),
                                    recentFailCount,
                                    round(weaknessScore)
                            );
                        })
                        .filter(item ->
                                item.successRate() < 80
                                        || (
                                        item.successRate() < 100
                                                && item.weaknessScore() >= 0.7
                                )
                        )
                        .sorted(
                                Comparator.comparingDouble(
                                        WeaknessItemResponse::weaknessScore
                                ).reversed()
                        )
                        .toList();

        return UserWeaknessResponse.of(
                weaknesses
        );
    }

    private double calculateWeaknessScore(
            double successRate,
            double averageAttemptCount,
            int recentFailCount
    ) {

        return (1 - successRate) * 0.5
                + Math.log(averageAttemptCount + 1) * 0.2
                + recentFailCount * 0.3;
    }

    private double round(double value) {
        return Math.round(value * 100) / 100.0;
    }
}
