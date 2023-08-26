package com.anilozmen.springbootmvcblog.service;

import com.anilozmen.springbootmvcblog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

  Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

  Page<Post> findAllByUserIdOrderByCreatedAtDesc(Long id, Pageable pageable);

  Post findBySlug(String slug);

  Post findById(Long id);

  Post save(Post post);

  void deleteById(Long id);

  boolean existsByIdAndUserId(Long id, Long userId);

}
