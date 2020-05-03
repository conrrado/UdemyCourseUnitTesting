package com.ccamacho.udemycourseunittesting.testDoublesFundamentals;

import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.authtoken.AuthTokenCache;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.eventbus.EventBusPoster;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.eventbus.LoggedInEvent;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.LoginHttpEndpointSync;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.NetworkErrorException;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LoginUseCaseSyncTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String AUTH_TOKEN = "authToken";

    private LoginHttpEndpointSyncTd mLoginHttpEndpointSyncTd;
    private AuthTokenCacheTd mAuthTokenCacheTd;
    private EventBusPosterTd mEventBusPosterTd;

    private LoginUseCaseSync loginTest;

    @Before
    public void setup() throws Exception {
        mLoginHttpEndpointSyncTd = new LoginHttpEndpointSyncTd();
        mAuthTokenCacheTd = new AuthTokenCacheTd();
        mEventBusPosterTd = new EventBusPosterTd();
        loginTest = new LoginUseCaseSync(mLoginHttpEndpointSyncTd, mAuthTokenCacheTd, mEventBusPosterTd);
    }

    @Test
    public void loginSync_success_usernameAndPasswordPassedToEndpoint() throws Exception {
        loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(mLoginHttpEndpointSyncTd.mUsername, is(USERNAME));
        assertThat(mLoginHttpEndpointSyncTd.mPassword, is(PASSWORD));
    }

    @Test
    public void loginSync_success_authTokenCached() throws Exception {
        loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(mAuthTokenCacheTd.getAuthToken(), is(AUTH_TOKEN));
    }

    @Test
    public void loginSync_generalError_authTokenNotCached() throws Exception {
        mLoginHttpEndpointSyncTd.mIsGeneralError = true;
        loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(mAuthTokenCacheTd.getAuthToken(), is(""));
    }

    @Test
    public void loginSync_authError_authTokenNotCached() throws Exception {
        mLoginHttpEndpointSyncTd.mIsAuthError = true;
        loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(mAuthTokenCacheTd.getAuthToken(), is(""));
    }

    @Test
    public void loginSync_serverError_authTokenNotCached() throws Exception {
        mLoginHttpEndpointSyncTd.mIsServerError = true;
        loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(mAuthTokenCacheTd.getAuthToken(), is(""));
    }

    @Test
    public void loginSync_success_loggedInEventPosted() throws Exception {
        loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(mEventBusPosterTd.mEvent, is(instanceOf(LoggedInEvent.class)));
    }

    @Test
    public void loginSync_generalError_noInteractionWithEventBusPoster() throws Exception {
        mLoginHttpEndpointSyncTd.mIsGeneralError = true;
        loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(mEventBusPosterTd.mInteractionCount, is(0));
    }

    @Test
    public void loginSync_authError_noInteractionWithEventBusPoster() throws Exception {
        mLoginHttpEndpointSyncTd.mIsAuthError = true;
        loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(mEventBusPosterTd.mInteractionCount, is(0));
    }

    @Test
    public void loginSync_serverError_noInteractionWithEventBusPoster() throws Exception {
        mLoginHttpEndpointSyncTd.mIsServerError = true;
        loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(mEventBusPosterTd.mInteractionCount, is(0));
    }

    @Test
    public void loginSync_success_successReturned() throws Exception {
        LoginUseCaseSync.UseCaseResult result = loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.SUCCESS));
    }

    @Test
    public void loginSync_generalError_failureReturned() throws Exception {
        mLoginHttpEndpointSyncTd.mIsGeneralError = true;
        LoginUseCaseSync.UseCaseResult result = loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void loginSync_authError_failureReturned() throws Exception {
        mLoginHttpEndpointSyncTd.mIsAuthError = true;
        LoginUseCaseSync.UseCaseResult result = loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void loginSync_serverError_failureReturned() throws Exception {
        mLoginHttpEndpointSyncTd.mIsServerError = true;
        LoginUseCaseSync.UseCaseResult result = loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void loginSync_networkError_networkErrorReturned() throws Exception {
        mLoginHttpEndpointSyncTd.mIsNetworkError = true;
        LoginUseCaseSync.UseCaseResult result = loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.NETWORK_ERROR));
    }

    private static class LoginHttpEndpointSyncTd implements LoginHttpEndpointSync {
        private String mUsername;
        private String mPassword;
        private boolean mIsGeneralError = false;
        private boolean mIsAuthError = false;
        private boolean mIsServerError = false;
        private boolean mIsNetworkError = false;

        @Override
        public EndpointResult loginSync(String username, String password) throws NetworkErrorException {
            mUsername = username;
            mPassword = password;
            if (mIsGeneralError) {
                return new EndpointResult(EndpointResultStatus.GENERAL_ERROR, "");
            }
            else if (mIsAuthError) {
                return new EndpointResult(EndpointResultStatus.AUTH_ERROR, "");
            }
            else if (mIsServerError) {
                return new EndpointResult(EndpointResultStatus.SERVER_ERROR, "");
            }
            else if (mIsNetworkError) {
                throw new NetworkErrorException();
            }
            return new EndpointResult(EndpointResultStatus.SUCCESS, AUTH_TOKEN);
        }
    }

    private static class AuthTokenCacheTd implements AuthTokenCache {
        private String mAuthToken;

        @Override
        public void cacheAuthToken(String authToken) {
            mAuthToken = authToken;
        }

        @Override
        public String getAuthToken() {
            return mAuthToken;
        }
    }

    private static class EventBusPosterTd implements EventBusPoster {
        private Object mEvent;
        private int mInteractionCount = 0;

        @Override
        public void postEvent(Object object) {
            mInteractionCount++;
            mEvent = object;
        }
    }
}
