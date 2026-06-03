package com.lunimple.domain.recommendation.service;

import com.lunimple.domain.problems.entity.Problem;
import com.lunimple.domain.problems.repository.ProblemRepository;
import com.lunimple.domain.recommendation.dto.response.RecommendationResponse;
import com.lunimple.domain.recommendation.dto.response.RecommendedProblemResponse;
import com.lunimple.domain.user.entity.User;
import com.lunimple.domain.user.repository.UserProblemRepository;
import com.lunimple.domain.user.repository.UserRepository;
import com.lunimple.global.exception.CustomException;
import com.lunimple.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendationService {

    private static final int RECENT_SOLVED_LIMIT = 50;

    private final UserRepository userRepository;
    private final UserProblemRepository userProblemRepository;
    private final ProblemRepository problemRepository;

    public RecommendationResponse getRecommendations(
            String handle
    ) {

        User user = userRepository.findByHandle(handle);

        Integer averageDifficulty =
                userProblemRepository.findAverageSolvedDifficulty(
                        user.getId()
                );

        if (averageDifficulty == null) {
            averageDifficulty = 400;
        }

        List<Long> recentSolvedIds =
                userProblemRepository.findRecentSolvedProblemIds(
                        user.getId(),
                        PageRequest.of(0, RECENT_SOLVED_LIMIT)
                );

        List<RecommendedProblemResponse> required =
                problemRepository
                        .findRecommendations(
                                averageDifficulty - 100,
                                averageDifficulty,
                                recentSolvedIds,
                                PageRequest.of(0, 2)
                        )
                        .stream()
                        .map(problem -> toResponse(
                                problem,
                                "현재 실력 보완"
                        ))
                        .toList();

        List<RecommendedProblemResponse> additional =
                problemRepository
                        .findRecommendations(
                                averageDifficulty - 50,
                                averageDifficulty + 50,
                                recentSolvedIds,
                                PageRequest.of(0, 3)
                        )
                        .stream()
                        .map(problem -> toResponse(
                                problem,
                                "풀이 경험 확장"
                        ))
                        .toList();

        List<RecommendedProblemResponse> challenge =
                problemRepository
                        .findRecommendations(
                                averageDifficulty + 100,
                                averageDifficulty + 200,
                                recentSolvedIds,
                                PageRequest.of(0, 2)
                        )
                        .stream()
                        .map(problem -> toResponse(
                                problem,
                                "도전 문제"
                        ))
                        .toList();

        return new RecommendationResponse(
                7,
                0,
                required,
                additional,
                challenge
        );
    }

    private RecommendedProblemResponse toResponse(
            Problem problem,
            String reason
    ) {
        return new RecommendedProblemResponse(
                problem.getId(),
                problem.getProblemId(),
                problem.getTitle(),
                problem.getDifficulty(),
                reason
        );
    }
}