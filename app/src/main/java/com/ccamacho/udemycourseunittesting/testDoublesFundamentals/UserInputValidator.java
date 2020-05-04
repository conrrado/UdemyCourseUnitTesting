package com.ccamacho.udemycourseunittesting.testDoublesFundamentals;

import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.username.UsernameValidator;

public class UserInputValidator {

    private UsernameValidator mUsernameValidator;

    public UserInputValidator(UsernameValidator usernameValidator) {
        mUsernameValidator = usernameValidator;
    }

    public boolean isValidFullName(String fullName) {
        return FullNameValidator.isValidFullName(fullName);
    }

    public boolean isValidUsername(String username) {
        return mUsernameValidator.isValidUsername(username);
    }
}
