package com.anilozmen.springbootmvcblog.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import java.util.Collection;
import java.util.List;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(name = "user_email_unique", columnNames = "email")
})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
  @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq")
  private Long id;

  private String firstName;
  private String lastName;

  @Email
  @Column(unique = true)
  private String email;

  private String password;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Post> posts;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Comment> comments;

  @Enumerated(EnumType.STRING)
  private List<Role> roles;

  private boolean isActive = Boolean.TRUE;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles
        .stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
        .toList();
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return isActive;
  }

  @Override
  public boolean isAccountNonLocked() {
    return isActive;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isActive;
  }

  @Override
  public boolean isEnabled() {
    return isActive;
  }

  public boolean isAdmin() {
    return this.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", roles=" + roles +
        ", isActive=" + isActive +
        '}';
  }
}
