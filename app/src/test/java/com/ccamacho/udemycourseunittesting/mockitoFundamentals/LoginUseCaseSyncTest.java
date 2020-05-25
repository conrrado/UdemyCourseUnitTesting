package com.ccamacho.udemycourseunittesting.mockitoFundamentals;

import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.LoginUseCaseSync;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.authtoken.AuthTokenCache;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.eventbus.EventBusPoster;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.eventbus.LoggedInEvent;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.LoginHttpEndpointSync;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.NetworkErrorException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginUseCaseSyncTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String AUTH_TOKEN = "authToken";

    @Mock
    LoginHttpEndpointSync mLoginHttpEndpointSyncMock;

    @Mock
    AuthTokenCache mAuthTokenCacheMock;

    @Mock
    EventBusPoster mEventBusPosterMock;

    LoginUseCaseSync loginTest;


    @Before
    public void setup() throws Exception {
        loginTest = new LoginUseCaseSync(mLoginHttpEndpointSyncMock, mAuthTokenCacheMock, mEventBusPosterMock);
        success();
    }

    @Test
    public void loginSync_success_usernameAndPasswordPassedToEndpoint() throws Exception {
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        loginTest.loginSync(USERNAME, PASSWORD);
        verify(mLoginHttpEndpointSyncMock, times(1))
                .loginSync(argumentCaptor.capture(), argumentCaptor.capture());
        List<String> captures = argumentCaptor.getAllValues();
        assertThat(captures.get(0), is(USERNAME));
        assertThat(captures.get(1), is(PASSWORD));
    }

    @Test
    public void loginSync_success_authTokenCached() throws Exception {
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        loginTest.loginSync(USERNAME, PASSWORD);
        verify(mAuthTokenCacheMock).cacheAuthToken(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), is(AUTH_TOKEN));
    }

    @Test
    public void loginSync_generalError_authTokenNotCached() throws Exception {
        generalError();
        loginTest.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(mAuthTokenCacheMock);
    }

    @Test
    public void loginSync_authError_authTokenNotCached() throws Exception {
        authError();
        loginTest.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(mAuthTokenCacheMock);
    }

    @Test
    public void loginSync_serverError_authTokenNotCached() throws Exception {
        serverError();
        loginTest.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(mAuthTokenCacheMock);
    }

    @Test
    public void loginSync_success_loggedInEventPosted() throws Exception {
        ArgumentCaptor<Object> argumentCaptor = ArgumentCaptor.forClass(Object.class);
        loginTest.loginSync(USERNAME, PASSWORD);
        verify(mEventBusPosterMock).postEvent(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), is(instanceOf(LoggedInEvent.class)));
    }

    @Test
    public void loginSync_generalError_noInteractionWithEventBusPoster() throws Exception {
        generalError();
        loginTest.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(mEventBusPosterMock);
    }

    @Test
    public void loginSync_authError_noInteractionWithEventBusPoster() throws Exception {
        authError();
        loginTest.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(mEventBusPosterMock);
    }

    @Test
    public void loginSync_serverError_noInteractionWithEventBusPoster() throws Exception {
        serverError();
        loginTest.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(mEventBusPosterMock);
    }

    @Test
    public void loginSync_success_successReturned() throws Exception {
        LoginUseCaseSync.UseCaseResult result = loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.SUCCESS));
    }

    @Test
    public void loginSync_generalError_failureReturned() throws Exception {
        generalError();
        LoginUseCaseSync.UseCaseResult result = loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void loginSync_authError_failureReturned() throws Exception {
        authError();
        LoginUseCaseSync.UseCaseResult result = loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void loginSync_serverError_failureReturned() throws Exception {
        serverError();
        LoginUseCaseSync.UseCaseResult result = loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void loginSync_networkError_networkErrorReturned() throws Exception {
        networkError();
        LoginUseCaseSync.UseCaseResult result = loginTest.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.NETWORK_ERROR));
    }

    private void networkError() throws Exception {
        doThrow(new NetworkErrorException()).when(mLoginHttpEndpointSyncMock)
                .loginSync(any(String.class), any(String.class));
    }

    private void success() throws NetworkErrorException {
        when(mLoginHttpEndpointSyncMock.loginSync(any(String.class), any(String.class)))
                .thenReturn(new LoginHttpEndpointSync.EndpointResult(
                        LoginHttpEndpointSync.EndpointResultStatus.SUCCESS, AUTH_TOKEN));
    }

    private void generalError() throws NetworkErrorException {
        when(mLoginHttpEndpointSyncMock.loginSync(any(String.class), any(String.class)))
                .thenReturn(new LoginHttpEndpointSync.EndpointResult(
                        LoginHttpEndpointSync.EndpointResultStatus.GENERAL_ERROR, ""));
    }

    private void authError() throws NetworkErrorException {
        when(mLoginHttpEndpointSyncMock.loginSync(any(String.class), any(String.class)))
                .thenReturn(new LoginHttpEndpointSync.EndpointResult(
                        LoginHttpEndpointSync.EndpointResultStatus.AUTH_ERROR, ""));
    }

    private void serverError() throws NetworkErrorException {
        when(mLoginHttpEndpointSyncMock.loginSync(any(String.class), any(String.class)))
                .thenReturn(new LoginHttpEndpointSync.EndpointResult(
                        LoginHttpEndpointSync.EndpointResultStatus.SERVER_ERROR, ""));
    }
}
