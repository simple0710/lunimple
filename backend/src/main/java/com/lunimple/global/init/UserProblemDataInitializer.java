package com.lunimple.global.init;

import com.lunimple.domain.problems.entity.Problem;
import com.lunimple.domain.problems.repository.ProblemRepository;
import com.lunimple.domain.user.entity.User;
import com.lunimple.domain.user.entity.UserProblem;
import com.lunimple.domain.user.enums.ProblemResult;
import com.lunimple.domain.user.repository.UserProblemRepository;
import com.lunimple.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Order(3)
@Component
@Transactional
@RequiredArgsConstructor
public class UserProblemDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final UserProblemRepository userProblemRepository;

    @Override
    public void run(String... args) {

        if (userProblemRepository.count() > 0) {
            return;
        }

        User simple = userRepository.findByHandle("simple710");
        User tourist = userRepository.findByHandle("tourist");
        User yutaka = userRepository.findByHandle("yutaka1999");

        Problem abcA = problemRepository.findByProblemId("abc463_a");
        Problem abcB = problemRepository.findByProblemId("abc463_b");
        Problem abcC = problemRepository.findByProblemId("abc463_c");
        Problem abcD = problemRepository.findByProblemId("abc463_d");
        Problem abcE = problemRepository.findByProblemId("abc463_e");

        Problem arcA = problemRepository.findByProblemId("arc211_a");
        Problem arcB = problemRepository.findByProblemId("arc211_b");
        Problem arcC = problemRepository.findByProblemId("arc211_c");

        LocalDateTime now = LocalDateTime.now();

        List<UserProblem> userProblems = List.of(

                // simple710

                UserProblem.builder()
                        .user(simple)
                        .problem(abcA)
                        .solved(true)
                        .attemptCount(1)
                        .lastResult(ProblemResult.AC)
                        .lastSubmittedAt(now.minusDays(20))
                        .solvedAt(now.minusDays(20))
                        .build(),

                UserProblem.builder()
                        .user(simple)
                        .problem(abcB)
                        .solved(true)
                        .attemptCount(3)
                        .lastResult(ProblemResult.AC)
                        .lastSubmittedAt(now.minusDays(17))
                        .solvedAt(now.minusDays(17))
                        .build(),

                UserProblem.builder()
                        .user(simple)
                        .problem(abcC)
                        .solved(false)
                        .attemptCount(3)
                        .lastResult(ProblemResult.WA)
                        .lastSubmittedAt(now.minusDays(14))
                        .solvedAt(null)
                        .build(),

                UserProblem.builder()
                        .user(simple)
                        .problem(arcA)
                        .solved(false)
                        .attemptCount(2)
                        .lastResult(ProblemResult.WA)
                        .lastSubmittedAt(now.minusDays(13))
                        .solvedAt(null)
                        .build(),

                // tourist

                UserProblem.builder()
                        .user(tourist)
                        .problem(abcA)
                        .solved(true)
                        .attemptCount(1)
                        .lastResult(ProblemResult.AC)
                        .lastSubmittedAt(now.minusDays(10))
                        .solvedAt(now.minusDays(10))
                        .build(),

                UserProblem.builder()
                        .user(tourist)
                        .problem(abcB)
                        .solved(true)
                        .attemptCount(1)
                        .lastResult(ProblemResult.AC)
                        .lastSubmittedAt(now.minusDays(10))
                        .solvedAt(now.minusDays(10))
                        .build(),

                UserProblem.builder()
                        .user(tourist)
                        .problem(abcC)
                        .solved(true)
                        .attemptCount(1)
                        .lastResult(ProblemResult.AC)
                        .lastSubmittedAt(now.minusDays(10))
                        .solvedAt(now.minusDays(10))
                        .build(),

                UserProblem.builder()
                        .user(tourist)
                        .problem(abcD)
                        .solved(true)
                        .attemptCount(1)
                        .lastResult(ProblemResult.AC)
                        .lastSubmittedAt(now.minusDays(10))
                        .solvedAt(now.minusDays(10))
                        .build(),

                UserProblem.builder()
                        .user(tourist)
                        .problem(abcE)
                        .solved(true)
                        .attemptCount(1)
                        .lastResult(ProblemResult.AC)
                        .lastSubmittedAt(now.minusDays(10))
                        .solvedAt(now.minusDays(10))
                        .build(),

                UserProblem.builder()
                        .user(tourist)
                        .problem(arcC)
                        .solved(true)
                        .attemptCount(2)
                        .lastResult(ProblemResult.AC)
                        .lastSubmittedAt(now.minusDays(8))
                        .solvedAt(now.minusDays(8))
                        .build(),

                // yutaka1999

                UserProblem.builder()
                        .user(yutaka)
                        .problem(abcA)
                        .solved(true)
                        .attemptCount(1)
                        .lastResult(ProblemResult.AC)
                        .lastSubmittedAt(now.minusDays(7))
                        .solvedAt(now.minusDays(7))
                        .build(),

                UserProblem.builder()
                        .user(yutaka)
                        .problem(abcB)
                        .solved(true)
                        .attemptCount(1)
                        .lastResult(ProblemResult.AC)
                        .lastSubmittedAt(now.minusDays(7))
                        .solvedAt(now.minusDays(7))
                        .build(),

                UserProblem.builder()
                        .user(yutaka)
                        .problem(abcC)
                        .solved(true)
                        .attemptCount(2)
                        .lastResult(ProblemResult.AC)
                        .lastSubmittedAt(now.minusDays(5))
                        .solvedAt(now.minusDays(5))
                        .build(),

                UserProblem.builder()
                        .user(yutaka)
                        .problem(arcB)
                        .solved(true)
                        .attemptCount(2)
                        .lastResult(ProblemResult.AC)
                        .lastSubmittedAt(now.minusDays(3))
                        .solvedAt(now.minusDays(3))
                        .build()
        );

        userProblemRepository.saveAll(userProblems);
    }
}