package com.anilozmen.springbootmvcblog.validator;

import com.anilozmen.springbootmvcblog.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

public class CustomPasswordValidator implements ConstraintValidator<ValidPassword, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    PasswordValidator validator = new PasswordValidator(Arrays.asList(
        new LengthRule(8, 15),
        new CharacterRule(EnglishCharacterData.UpperCase, 1),
        new CharacterRule(EnglishCharacterData.LowerCase, 1),
        new CharacterRule(EnglishCharacterData.Digit, 1),
        new CharacterRule(EnglishCharacterData.Special, 1),
        new WhitespaceRule()
    ));

    RuleResult result = validator.validate(new PasswordData(value));

    if (result.isValid()) return true;

    List<String> messages = validator.getMessages(result);

    String messageTemp = String.join(",", messages);

    context.buildConstraintViolationWithTemplate(messageTemp)
        .addConstraintViolation()
        .disableDefaultConstraintViolation();

    return false;
  }
}
