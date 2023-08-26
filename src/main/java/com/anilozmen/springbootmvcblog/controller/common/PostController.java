package com.anilozmen.springbootmvcblog.controller.common;

import com.anilozmen.springbootmvcblog.entity.Comment;
import com.anilozmen.springbootmvcblog.entity.Post;
import com.anilozmen.springbootmvcblog.entity.User;
import com.anilozmen.springbootmvcblog.entity.dto.request.CommentRequestDTO;
import com.anilozmen.springbootmvcblog.entity.dto.request.PostRequestDTO;
import com.anilozmen.springbootmvcblog.service.CommentService;
import com.anilozmen.springbootmvcblog.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class PostController {

  private static final String COMMON_PATH = "/user/posts";
  private final PostService postService;
  private final CommentService commentService;

  @Autowired
  public PostController(PostService postService, CommentService commentService) {
    this.postService = postService;
    this.commentService = commentService;
  }


  @GetMapping("/posts/{slug}")
  public String findPostBySlug(@PathVariable String slug, Model model) {
    model.addAttribute("post", postService.findBySlug(slug));

    if (!model.containsAttribute("commentRequest")) {
      model.addAttribute("commentRequest", new CommentRequestDTO());
    }

    return "post";
  }

  @GetMapping({COMMON_PATH, COMMON_PATH + "/all"})
  @PreAuthorize("(!#request.getRequestURI().contains('/all') && hasAnyRole('USER', 'ADMIN')) || "
      + "(#request.getRequestURI().contains('/all') && hasRole('ADMIN'))"
  )
  public String findAllPostsByUser(
      Pageable pageable,
      HttpServletRequest request,
      Model model,
      @AuthenticationPrincipal User user
  ) {

    int currentPage = pageable.getPageNumber() == 0 ? 1 : pageable.getPageNumber() + 1;

    Page<Post> posts;

    if (request.getRequestURI().equals(COMMON_PATH + "/all")) {
      posts = postService.findAllByOrderByCreatedAtDesc(pageable);
    } else {
      posts = postService.findAllByUserIdOrderByCreatedAtDesc(user.getId(), pageable);
    }

    if (currentPage > posts.getTotalPages()) {
      return "redirect:" + request.getRequestURI();
    }

    // Pagination
    model.addAttribute("route", request.getRequestURI());
    model.addAttribute("currentPage", currentPage);
    model.addAttribute("lastPage", posts.getTotalPages());

    // Posts
    model.addAttribute("posts", posts);

    return "post/list-posts";
  }

  @GetMapping(COMMON_PATH + "/create")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public String newPost(@ModelAttribute("data") PostRequestDTO postRequest) {
    return "user/new-post";
  }

  @PostMapping(COMMON_PATH)
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public String savePost(
      @ModelAttribute("data") @Valid PostRequestDTO postRequest,
      BindingResult result,
      @AuthenticationPrincipal User user
  ) {

    if (result.hasErrors()) {
      return "user/new-post";
    }

    Post post = new Post(
        postRequest.getTitle(),
        postRequest.getContent(),
        user,
        postRequest.isVisible()
    );

    postService.save(post);

    return "redirect:" + COMMON_PATH;
  }

  @GetMapping(COMMON_PATH + "/{postId}")
  @PreAuthorize("@postServiceImpl.existsByIdAndUserId(#postId, #user.id) || hasRole('ADMIN')")
  public String editPost(
      @PathVariable Long postId,
      Model model,
      @AuthenticationPrincipal User user
  ) {

    if (!model.containsAttribute("post")) {
      model.addAttribute("post", postService.findById(postId));
    }

    return "user/edit-post";
  }

  @PutMapping(COMMON_PATH + "/{postId}")
  @PreAuthorize("(@postServiceImpl.existsByIdAndUserId(#postId, #user.id) && #postId == #postRequest.id) || hasRole('ADMIN')")
  public String updatePost(
      @PathVariable Long postId,
      @ModelAttribute("post") @Valid PostRequestDTO postRequest,
      BindingResult result,
      RedirectAttributes attributes,
      @AuthenticationPrincipal User user
  ) {

    Post post = postService.findById(postId);

    if (result.hasErrors()) {

      attributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "post", result);
      attributes.addFlashAttribute("post", postRequest);

      return "redirect:" + COMMON_PATH + "/{postId}";
    }

    if (!postRequest.getTitle().equals(post.getTitle())) {
      post.setTitle(postRequest.getTitle());
    }

    if (!postRequest.getContent().equals(post.getContent())) {
      post.setContent(postRequest.getContent());
    }

    if (postRequest.isVisible() != post.isVisible()) {
      post.setVisible(postRequest.isVisible());
    }

    postService.save(post);

    return "redirect:%s/%s".formatted(COMMON_PATH, postId);
  }

  @DeleteMapping(COMMON_PATH + "/{postId}")
  @PreAuthorize("@postServiceImpl.existsByIdAndUserId(#postId, #user.id) || hasRole('ADMIN')")
  public String deletePostById(
      @PathVariable Long postId,
      @AuthenticationPrincipal User user,
      HttpServletRequest request
  ) {
    postService.deleteById(postId);
    return "redirect:" + request.getHeader("Referer");
  }


  @PostMapping(COMMON_PATH + "/{postId}/comments")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public String addComment(
      @PathVariable Long postId,
      @ModelAttribute @Valid CommentRequestDTO commentRequest,
      BindingResult result,
      RedirectAttributes attributes,
      @AuthenticationPrincipal User user
  ) {

    Post post = postService.findById(postId);

    if (result.hasErrors()) {
      attributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "commentRequest", result);
      attributes.addFlashAttribute("commentRequest", commentRequest);
      return "redirect:/posts/" + post.getSlug();
    }

    Comment comment = new Comment(commentRequest.getComment(), user, post);

    Comment saved = commentService.save(comment);

    post.getComments().add(saved);

    postService.save(post);

    return "redirect:/posts/" + post.getSlug();
  }


  @DeleteMapping("/user/posts/{postId}/comments/{commentId}")
  @PreAuthorize("hasRole('ADMIN') ||"
      + " (@commentServiceImpl.existsByIdAndUserId(#commentId, authentication.principal.id) && "
      + "@commentServiceImpl.existsByIdAndPostId(#commentId, #postId))"
  )
  public String deleteComment(
      @PathVariable Long postId,
      @PathVariable Long commentId,
      HttpServletRequest request
  ) {
    commentService.deleteById(commentId);
    return "redirect:" + request.getHeader("Referer");
  }

}
