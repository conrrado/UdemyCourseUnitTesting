package com.ccamacho.udemycourseunittesting.testDoublesFundamentals;

import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.authtoken.AuthTokenCache;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.eventbus.EventBusPoster;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.eventbus.LoggedInEvent;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.LoginHttpEndpointSync;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.NetworkErrorException;

public class LoginUseCaseSync {

    public enum UseCaseResult {
        SUCCESS,
        FAILURE,
        NETWORK_ERROR
    }

    private final LoginHttpEndpointSync mLoginHttpEndpointSync;
    private final AuthTokenCache mAuthTokenCache;
    private final EventBusPoster mEventBusPoster;

    public LoginUseCaseSync(LoginHttpEndpointSync loginHttpEndpointSync,
                            AuthTokenCache authTokenCache,
                            EventBusPoster eventBusPoster) {
        mLoginHttpEndpointSync = loginHttpEndpointSync;
        mAuthTokenCache = authTokenCache;
        mEventBusPoster = eventBusPoster;
    }

    public UseCaseResult loginSync(String username, String password) {
        LoginHttpEndpointSync.EndpointResult endpointEndpointResult;
        try {
            endpointEndpointResult = mLoginHttpEndpointSync.loginSync(username, password);
        } catch (NetworkErrorException e) {
            return UseCaseResult.NETWORK_ERROR;
        }

        mAuthTokenCache.cacheAuthToken(endpointEndpointResult.getAuthToken());

        if (isSuccessfulEndpointResult(endpointEndpointResult)) {
            mEventBusPoster.postEvent(new LoggedInEvent());
            return UseCaseResult.SUCCESS;
        }
        return UseCaseResult.FAILURE;
    }

    private boolean isSuccessfulEndpointResult(LoginHttpEndpointSync.EndpointResult endpointResult) {
        return endpointResult.getStatus() == LoginHttpEndpointSync.EndpointResultStatus.SUCCESS;
    }
}
