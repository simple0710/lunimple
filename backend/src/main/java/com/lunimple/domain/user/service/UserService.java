package com.lunimple.domain.user.service;

import com.lunimple.domain.user.dto.response.RankingResponse;
import com.lunimple.domain.user.enums.RankingSortType;
import com.lunimple.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
}
