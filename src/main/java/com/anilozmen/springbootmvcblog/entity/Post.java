package com.anilozmen.springbootmvcblog.entity;

import com.github.slugify.Slugify;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Table(name = "posts")
@Entity
@SQLDelete(sql = "UPDATE posts SET is_deleted = true, is_visible=false where id = ?")
@Where(clause = "is_deleted=false")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Post {

  @Id
  @SequenceGenerator(name = "post_id_seq", sequenceName = "post_id_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq")
  private Long id;

  @NotBlank
  @Size(min = 5, max = 100)
  @Column(name = "title")
  private String title;

  @NotBlank
  @Column(name = "slug", unique = true)
  private String slug;

  @NotBlank
  @Size(min = 10)
  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @OrderBy("createdAt DESC ")
  private List<Comment> comments;

  @Column(name = "is_visible")
  private boolean isVisible = Boolean.TRUE;

  @Column(name = "is_deleted")
  private boolean isDeleted = Boolean.FALSE;

  @CreatedDate
  @Column(name = "created_at")
  private Date createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private Date updatedAt;

  @PrePersist
  protected void prePersist() {
    createdAt = new Date();
    updatedAt = new Date();
  }

  @PreUpdate
  protected void preUpdate() {
    this.updatedAt = new Date();
  }

  public Post(
      String title,
      String content,
      User user,
      boolean isVisible
  ) {
    setTitle(title);
    this.content = content;
    this.user = user;
    this.isVisible = isVisible;
  }

  public void setTitle(String title) {
    this.title = title;

    Slugify slugify = Slugify.builder().build();
    String result = slugify.slugify(title);

    if (this.slug == null || !this.slug.equals(result)) {
      this.slug = result;
    }
  }

  @Override
  public String toString() {
    return "Post{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", slug='" + slug + '\'' +
        ", content='" + content + '\'' +
        ", isVisible=" + isVisible +
        ", isDeleted=" + isDeleted +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        '}';
  }

}
