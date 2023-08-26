package com.anilozmen.springbootmvcblog.service;

import com.anilozmen.springbootmvcblog.entity.Comment;

public interface CommentService {

  Comment save(Comment comment);

  void deleteById(Long id);

  boolean existsByIdAndUserId(Long id, Long userId);

  boolean existsByIdAndPostId(Long id, Long postId);

}
