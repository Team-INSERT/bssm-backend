package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.comment.ReComment;
import com.insert.ogbsm.domain.comment.repo.ReCommentRepo;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.presentation.comment.dto.ReCommentReqDto;
import com.insert.ogbsm.presentation.comment.dto.ReCommentResDto;
import com.insert.ogbsm.service.validation.UserValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReCommentDefService {
    private final ReCommentRepo reCommentRepo;
    private final UserRepo userRepo;
    private final UserValidation userValidation;

    public void create(ReCommentReqDto dto, Long userId) {
        ReComment reComment = new ReComment(dto.detail(), dto.commentId(), userId);
        reCommentRepo.save(reComment);
    }

    public void update(ReCommentReqDto dto, Long userId) {
        ReComment reComment = reCommentRepo.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Updatable ReComment Not Found"));

        userValidation.checkSameUser(userId, reComment.getUserId());

        reComment.update(dto.detail());
    }

    public void delete(Long reCommentId, Long useId) {
        ReComment reComment = reCommentRepo.findById(reCommentId)
                .orElseThrow(() -> new EntityNotFoundException("Deletable ReComment Not Found"));

        userValidation.checkSameUser(reComment.getUserId(), useId);

        reCommentRepo.delete(reComment);
    }

    public List<ReCommentResDto> read(Long postId, Pageable pageable) {

        return reCommentRepo.findByCommentIdOrderByCreatedAtAsc(postId, pageable)
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
