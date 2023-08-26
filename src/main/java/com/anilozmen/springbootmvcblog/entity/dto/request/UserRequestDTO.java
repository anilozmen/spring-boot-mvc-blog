package com.anilozmen.springbootmvcblog.entity.dto.request;

import com.anilozmen.springbootmvcblog.annotation.PasswordMatches;
import com.anilozmen.springbootmvcblog.annotation.ValidPassword;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PasswordMatches
public class UserRequestDTO {

  @NotBlank
  @Size(min = 2)
  private String firstName;

  @NotBlank
  @Size(min = 2)
  private String lastName;

  @Email
  private String email;

  @ValidPassword
  private String password;

  @Transient
  private String passwordConfirm;

}
