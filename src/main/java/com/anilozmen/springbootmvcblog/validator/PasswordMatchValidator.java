package com.anilozmen.springbootmvcblog.validator;

import com.anilozmen.springbootmvcblog.annotation.PasswordMatches;
import com.anilozmen.springbootmvcblog.entity.dto.request.UserRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatches, UserRequestDTO> {

  @Override
  public boolean isValid(UserRequestDTO request, ConstraintValidatorContext context) {
    boolean isValid = request.getPassword().equals(request.getPasswordConfirm());

    if (!isValid) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
          .addPropertyNode("passwordConfirm")
          .addConstraintViolation();
    }

    return isValid;
  }
}
