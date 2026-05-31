package com.lunimple.global.init;

import com.lunimple.domain.problems.entity.Problem;
import com.lunimple.domain.problems.entity.Submission;
import com.lunimple.domain.problems.enums.ProgrammingLanguageType;
import com.lunimple.domain.problems.repository.ProblemRepository;
import com.lunimple.domain.user.entity.User;
import com.lunimple.domain.user.enums.ProblemResult;
import com.lunimple.domain.user.repository.SubmissionRepository;
import com.lunimple.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Order(3)
@Component
@Transactional
@RequiredArgsConstructor
public class SubmissionDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final SubmissionRepository submissionRepository;

    @Override
    public void run(String... args) throws Exception {

        if (submissionRepository.count() > 0) {
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

        List<Submission> submissions = List.of(

                // simple710

                Submission.builder()
                        .user(simple)
                        .problem(abcA)
                        .result(ProblemResult.AC)
                        .programmingLanguage(ProgrammingLanguageType.JAVA)
                        .submittedAt(now.minusDays(20))
                        .build(),

                Submission.builder()
                        .user(simple)
                        .problem(abcB)
                        .result(ProblemResult.WA)
                        .programmingLanguage(ProgrammingLanguageType.JAVA)
                        .submittedAt(now.minusDays(19))
                        .build(),

                Submission.builder()
                        .user(simple)
                        .problem(abcB)
                        .result(ProblemResult.WA)
                        .programmingLanguage(ProgrammingLanguageType.JAVA)
                        .submittedAt(now.minusDays(18))
                        .build(),

                Submission.builder()
                        .user(simple)
                        .problem(abcB)
                        .result(ProblemResult.AC)
                        .programmingLanguage(ProgrammingLanguageType.JAVA)
                        .submittedAt(now.minusDays(17))
                        .build(),

                Submission.builder()
                        .user(simple)
                        .problem(abcC)
                        .result(ProblemResult.WA)
                        .programmingLanguage(ProgrammingLanguageType.JAVA)
                        .submittedAt(now.minusDays(16))
                        .build(),

                Submission.builder()
                        .user(simple)
                        .problem(abcC)
                        .result(ProblemResult.WA)
                        .programmingLanguage(ProgrammingLanguageType.JAVA)
                        .submittedAt(now.minusDays(15))
                        .build(),

                Submission.builder()
                        .user(simple)
                        .problem(abcC)
                        .result(ProblemResult.WA)
                        .programmingLanguage(ProgrammingLanguageType.JAVA)
                        .submittedAt(now.minusDays(14))
                        .build(),

                // tourist

                Submission.builder()
                        .user(tourist)
                        .problem(abcA)
                        .result(ProblemResult.AC)
                        .programmingLanguage(ProgrammingLanguageType.CPP)
                        .submittedAt(now.minusDays(10))
                        .build(),

                Submission.builder()
                        .user(tourist)
                        .problem(abcB)
                        .result(ProblemResult.AC)
                        .programmingLanguage(ProgrammingLanguageType.CPP)
                        .submittedAt(now.minusDays(10))
                        .build(),

                Submission.builder()
                        .user(tourist)
                        .problem(abcC)
                        .result(ProblemResult.AC)
                        .programmingLanguage(ProgrammingLanguageType.CPP)
                        .submittedAt(now.minusDays(10))
                        .build(),

                Submission.builder()
                        .user(tourist)
                        .problem(abcD)
                        .result(ProblemResult.AC)
                        .programmingLanguage(ProgrammingLanguageType.CPP)
                        .submittedAt(now.minusDays(10))
                        .build(),

                Submission.builder()
                        .user(tourist)
                        .problem(abcE)
                        .result(ProblemResult.AC)
                        .programmingLanguage(ProgrammingLanguageType.CPP)
                        .submittedAt(now.minusDays(10))
                        .build(),

                Submission.builder()
                        .user(tourist)
                        .problem(arcC)
                        .result(ProblemResult.WA)
                        .programmingLanguage(ProgrammingLanguageType.CPP)
                        .submittedAt(now.minusDays(9))
                        .build(),

                Submission.builder()
                        .user(tourist)
                        .problem(arcC)
                        .result(ProblemResult.AC)
                        .programmingLanguage(ProgrammingLanguageType.CPP)
                        .submittedAt(now.minusDays(8))
                        .build(),

                // yutaka

                Submission.builder()
                        .user(yutaka)
                        .problem(abcA)
                        .result(ProblemResult.AC)
                        .programmingLanguage(ProgrammingLanguageType.CPP)
                        .submittedAt(now.minusDays(7))
                        .build(),

                Submission.builder()
                        .user(yutaka)
                        .problem(abcB)
                        .result(ProblemResult.AC)
                        .programmingLanguage(ProgrammingLanguageType.CPP)
                        .submittedAt(now.minusDays(7))
                        .build(),

                Submission.builder()
                        .user(yutaka)
                        .problem(abcC)
                        .result(ProblemResult.WA)
                        .programmingLanguage(ProgrammingLanguageType.CPP)
                        .submittedAt(now.minusDays(6))
                        .build(),

                Submission.builder()
                        .user(yutaka)
                        .problem(abcC)
                        .result(ProblemResult.AC)
                        .programmingLanguage(ProgrammingLanguageType.CPP)
                        .submittedAt(now.minusDays(5))
                        .build(),

                Submission.builder()
                        .user(yutaka)
                        .problem(arcB)
                        .result(ProblemResult.WA)
                        .programmingLanguage(ProgrammingLanguageType.CPP)
                        .submittedAt(now.minusDays(4))
                        .build(),

                Submission.builder()
                        .user(yutaka)
                        .problem(arcB)
                        .result(ProblemResult.AC)
                        .programmingLanguage(ProgrammingLanguageType.CPP)
                        .submittedAt(now.minusDays(3))
                        .build()
        );

        submissionRepository.saveAll(submissions);
    }
}
