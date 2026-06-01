package com.lunimple.domain.user.service;

import com.lunimple.domain.contest.enums.ContestType;
import com.lunimple.domain.problems.enums.ProblemCode;
import com.lunimple.domain.user.dto.response.*;
import com.lunimple.domain.user.entity.User;
import com.lunimple.domain.user.entity.UserProblem;
import com.lunimple.domain.user.enums.RankingSortType;
import com.lunimple.domain.user.repository.UserProblemRepository;
import com.lunimple.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProblemRepository userProblemRepository;

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
}
