package com.lunimple.global.init;

import com.lunimple.domain.user.entity.Country;
import com.lunimple.domain.user.entity.User;
import com.lunimple.domain.user.repository.CountryRepository;
import com.lunimple.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Order(1)
@Component
@Transactional
@RequiredArgsConstructor
public class UserDateInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CountryRepository countryRepository;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() > 0) {
            return;
        }

        Country kr = Country
                .builder()
                .code("KR")
                .name("KOREAN")
                .build();
        Country jp = Country
                .builder()
                .code("JP")
                .name("JAPAN")
                .build();
        Country us = Country
                .builder()
                .code("US")
                .name("UNITED STATES")
                .build();

        User user1 = User
                .builder()
                .handle("simple710")
                .country(kr)
                .rating(782)
                .highest(782)
                .matchCount(13)
                .win(24)
                .build();
        User user2 = User
                .builder()
                .handle("tourist")
                .country(us)
                .rating(3797)
                .highest(4229)
                .matchCount(71)
                .win(23)
                .build();
        User user3 = User
                .builder()
                .handle("yutaka1999")
                .country(jp)
                .rating(3538)
                .highest(3724)
                .matchCount(80)
                .win(1)
                .build();

        countryRepository.save(kr);
        countryRepository.save(jp);
        countryRepository.save(us);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }
}
