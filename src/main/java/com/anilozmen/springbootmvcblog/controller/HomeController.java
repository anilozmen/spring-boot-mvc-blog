package com.anilozmen.springbootmvcblog.controller;

import com.anilozmen.springbootmvcblog.entity.Post;
import com.anilozmen.springbootmvcblog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

  private final PostService postService;

  public HomeController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping("/")
  public String home(Pageable pageable, Model model) {

    int currentPage = pageable.getPageNumber() == 0 ? 1 : pageable.getPageNumber() + 1;

    Page<Post> posts = postService.findAllByOrderByCreatedAtDesc(pageable);

    if (currentPage > posts.getTotalPages()) {
      return "redirect:/";
    }

    // Pagination
    model.addAttribute("route", "/");
    model.addAttribute("currentPage", currentPage);
    model.addAttribute("lastPage", posts.getTotalPages());

    // Posts
    model.addAttribute("posts", posts);

    return "home";
  }
}
