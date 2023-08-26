package com.anilozmen.springbootmvcblog.repository;

import com.anilozmen.springbootmvcblog.entity.Post;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  Optional<Post> findBySlug(String slug);

  Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

  Page<Post> findAllByUserIdOrderByCreatedAtDesc(Long id, Pageable pageable);

  boolean existsByIdAndUserId(Long id, Long userId);

}
