package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.presentation.comment.dto.CommentReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CommentDefService {
    private final CommentRepo commentRepo;

    public void createComment(CommentReqDto reqDto, Long postId) {
        Comment comment = new Comment(reqDto.detail(), Post);

        commentRepo.save(comment);
    }

    public void updateComment(CommentReqDto reqDto) {
        // TODO 작성한 유저와 업데이트한 유저가 같은지 확인
        Comment comment = commentRepo.findById(reqDto.id())
                .orElseThrow(() -> new EntityNotFoundException("No Updatable Comment"));

        comment.update(reqDto.detail());
    }
}
