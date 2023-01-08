package com.mail.sender.service.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationModelValidatorImpl implements ApplicationModelValidator {
    private final Validator validator;

    @Override
    public <E> String validate(E model) {
        var validationViolations = validator.validate(model);
        if (validationViolations.isEmpty()) {
            return "";
        }
        return validationViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));
    }
}
