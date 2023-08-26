package com.anilozmen.springbootmvcblog.service.impl;

import com.anilozmen.springbootmvcblog.entity.Comment;
import com.anilozmen.springbootmvcblog.repository.CommentRepository;
import com.anilozmen.springbootmvcblog.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;

  public CommentServiceImpl(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }


  @Override
  public Comment save(Comment comment) {
    return commentRepository.save(comment);
  }

  @Override
  public void deleteById(Long id) {
    commentRepository.deleteById(id);
  }

  @Override
  public boolean existsByIdAndUserId(Long id, Long userId) {
    return commentRepository.existsCommentByIdAndUserId(id, userId);
  }

  @Override
  public boolean existsByIdAndPostId(Long id, Long postId) {
    return commentRepository.existsByIdAndPostId(id, postId);
  }
}
