package com.ccamacho.udemycourseunittesting.testDoublesFundamentals;

import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.username.UsernameValidator;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserInputValidatorTest {

    private ServerUsernameValidatorTd mServerUsernameValidatorTd;

    private UserInputValidator userInputValidatorTest;

    @Before
    public void setup() throws Exception {
        mServerUsernameValidatorTd = new ServerUsernameValidatorTd();
        userInputValidatorTest = new UserInputValidator(mServerUsernameValidatorTd);
    }

    @Test
    public void isValidFullName_validFullName_trueReturned() throws Exception {
        boolean result = userInputValidatorTest.isValidFullName("validFullName");
        assertThat(result, is(true));
    }

    @Test
    public void isValidFullName_invalidFullName_falseReturned() throws Exception {
        boolean result = userInputValidatorTest.isValidFullName("");
        assertThat(result, is(false));
    }

    @Test
    public void isValidUsername_validUsername_trueReturned() throws Exception {
        boolean result = userInputValidatorTest.isValidUsername("validUsername");
        assertThat(result, is(true));
    }

    @Test
    public void isValidUsername_invalidUsername_falseReturned() throws Exception {
        boolean result = userInputValidatorTest.isValidUsername("");
        assertThat(result, is(false));
    }

    private static class ServerUsernameValidatorTd implements UsernameValidator {

        @Override
        public boolean isValidUsername(String username) {
            return !username.isEmpty();
        }
    }
}
