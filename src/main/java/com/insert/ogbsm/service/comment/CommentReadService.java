package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.presentation.comment.dto.CommentResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReadService {
    private final CommentRepo commentRepo;
    private final UserRepo userRepo;

    public List<CommentResDto> readComments(Long postId, Pageable pageable) {
        return commentRepo.findAllByPostId(postId, pageable)
                .stream()
                .map(comment -> new CommentResDto(
                        comment,
                        userRepo.findById(comment.getUserId())
                                .orElseThrow(() -> new EntityNotFoundException("comment user not found"))))
                .collect(Collectors.toList());
    }
}
