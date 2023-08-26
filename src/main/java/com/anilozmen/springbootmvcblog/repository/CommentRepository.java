package com.anilozmen.springbootmvcblog.repository;

import com.anilozmen.springbootmvcblog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  boolean existsCommentByIdAndUserId(Long id, Long userId);

  boolean existsByIdAndPostId(Long id, Long postId);
}
