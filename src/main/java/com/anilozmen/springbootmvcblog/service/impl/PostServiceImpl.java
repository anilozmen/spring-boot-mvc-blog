package com.anilozmen.springbootmvcblog.service.impl;

import com.anilozmen.springbootmvcblog.entity.Post;
import com.anilozmen.springbootmvcblog.exception.PostNotFoundException;
import com.anilozmen.springbootmvcblog.repository.PostRepository;
import com.anilozmen.springbootmvcblog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;

  public PostServiceImpl(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable) {
    return postRepository.findAllByOrderByCreatedAtDesc(pageable);
  }

  @Override
  public Page<Post> findAllByUserIdOrderByCreatedAtDesc(Long id, Pageable pageable) {
    return postRepository.findAllByUserIdOrderByCreatedAtDesc(id, pageable);
  }

  @Override
  public Post findBySlug(String slug) {
    return postRepository.findBySlug(slug).orElseThrow(PostNotFoundException::new);
  }

  @Override
  public Post findById(Long id) {
    return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
  }

  @Override
  public Post save(Post post) {
    return postRepository.save(post);
  }

  @Override
  public void deleteById(Long id) {
    postRepository.delete(findById(id));
  }

  @Override
  public boolean existsByIdAndUserId(Long id, Long userId) {
    return postRepository.existsByIdAndUserId(id, userId);
  }
}
