package com.example.pets4ever.user.dtos.Register.Validator;


import com.example.pets4ever.user.dtos.Register.RegisterDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, RegisterDTO> {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,64}$";

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(RegisterDTO registerDTO, ConstraintValidatorContext context) {
        if (registerDTO == null) {
            return true;
        }

        String password = registerDTO.getPassword();
        String email = registerDTO.getEmail();

        if (password == null || email == null) {
            return false;
        }

        // Validate password against the regex pattern
        if (!Pattern.matches(PASSWORD_PATTERN, password)) {
            return false;
        }

        // Check that the password does not contain the email
        return !password.contains(email);
    }
}
