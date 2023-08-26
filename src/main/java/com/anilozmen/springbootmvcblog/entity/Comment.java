package com.anilozmen.springbootmvcblog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.data.annotation.CreatedDate;

@Table(name = "comments")
@Entity
@SQLDelete(sql = "UPDATE comments SET is_deleted = true where id = ?")
@Where(clause = "is_deleted=false")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_seq")
  @SequenceGenerator(name = "comment_id_seq", sequenceName = "comment_id_seq")
  private Long id;

  @NotBlank
  @Size(min = 3)
  @Column(name = "comment", nullable = false)
  private String comment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @Column(name = "is_deleted")
  private boolean isDeleted = Boolean.FALSE;

  @CreatedDate
  @Column(name = "created_at")
  private Date createdAt;

  @Transient
  private String formattedDate;

  public Comment(String comment, User user, Post post) {
    this.comment = comment;
    this.user = user;
    this.post = post;
  }

  @PrePersist
  protected void prePersist() {
    createdAt = new Date();
  }

  public String getFormattedDate() {
    PrettyTime prettyTime = new PrettyTime();
    return prettyTime.format(createdAt);
  }

  @Override
  public String toString() {
    return "Comment{" +
        "id=" + id +
        ", comment='" + comment + '\'' +
        ", isDeleted=" + isDeleted +
        ", createdAt=" + createdAt +
        '}';
  }
}
