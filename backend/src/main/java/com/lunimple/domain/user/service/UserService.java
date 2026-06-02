package com.lunimple.domain.user.service;

import com.lunimple.domain.contest.enums.ContestType;
import com.lunimple.domain.problems.entity.Submission;
import com.lunimple.domain.problems.enums.ProblemCode;
import com.lunimple.domain.user.dto.response.*;
import com.lunimple.domain.user.entity.User;
import com.lunimple.domain.user.entity.UserProblem;
import com.lunimple.domain.user.enums.ProblemResult;
import com.lunimple.domain.user.enums.RankingSortType;
import com.lunimple.domain.user.repository.SubmissionRepository;
import com.lunimple.domain.user.repository.UserProblemRepository;
import com.lunimple.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final int MIN_ANALYSIS_COUNT = 1;
    private static final int RECENT_DAYS = 360;

    private final UserRepository userRepository;
    private final UserProblemRepository userProblemRepository;
    private final SubmissionRepository submissionRepository;

    public UserProfileResponse getUserProfile(String handle) {
        Long userId = userRepository.findByHandle(handle).getId();
        List<UserProblem> userProblems = userProblemRepository.findAllByUserId(userId);

        int totalSolved = 0;
        int totalAttempt = 0;
        int solvedRatingSum = 0;
        int triedProblemCount = userProblems.size();

        for (UserProblem userProblem : userProblems) {
            totalAttempt += userProblem.getAttemptCount();

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
        // 전체 해결 문제 수

        // 해결한 문제들의 평균 레이팅
        // (해결한 문제 레이팅 총합 / 해결한 문제 수)

        /**
         * 문제당 평균 제출 횟수
         * (전체 제출 횟수 / 해결한 문제 수)
         */

        /**
         * 성공률
         * (해결한 문제 수 / 시도한 문제 수) × 100
         */
        return UserProfileResponse.of(
                totalSolved,
                averageSolvedRating,
                averageSubmissionCount,
                successRate
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
                .map(up -> up.getSolvedAt().toLocalDate())
                .collect(Collectors.toSet());

        LocalDate date = LocalDate.now();
        int streak = 0;
        if (!solvedDates.contains(date)) date = date.minusDays(1);

        while (solvedDates.contains(date)) {
            streak++;
            date = date.minusDays(1);
        }
        return UserStreakResponse.of(streak);
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
