package com.anilozmen.springbootmvcblog.entity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequestDTO {

  private Long id;

  @NotBlank
  @Size(min = 5, max = 100)
  private String title;

  @NotBlank
  @Size(min = 10)
  private String content;

  private boolean isVisible = Boolean.TRUE;

  public PostRequestDTO() {
  }

  public PostRequestDTO(String title, String content, boolean isVisible) {
    this.title = title;
    this.content = content;
    this.isVisible = isVisible;
  }

  public PostRequestDTO(Long id, String title, String content, boolean isVisible) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.isVisible = isVisible;
  }
}
