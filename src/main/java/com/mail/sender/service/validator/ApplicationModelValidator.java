package com.mail.sender.service.validator;

public interface ApplicationModelValidator {
    <E> String validate(E model);
}
