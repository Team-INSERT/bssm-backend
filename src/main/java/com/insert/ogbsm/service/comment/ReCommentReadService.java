package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.comment.repo.ReCommentRepo;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.presentation.comment.dto.ReCommentResDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReCommentReadService {
    private final ReCommentRepo reCommentRepo;
    private final UserRepo userRepo;

    public List<ReCommentResDto> read(Long commentId, Pageable pageable) {

        return reCommentRepo.findByCommentIdOrderByCreatedAtAsc(commentId, pageable)
                .stream()
                .map(reComment -> new ReCommentResDto(
                                reComment,
                                userRepo.findById(reComment.getUserId())
                                        .orElseThrow(() -> new EntityNotFoundException("ReComment User Not Found"))
                        )
                )
                .collect(Collectors.toList());
    }
}
