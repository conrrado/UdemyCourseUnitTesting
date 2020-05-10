package com.ccamacho.udemycourseunittesting.mockitoFundamentals;

import com.ccamacho.udemycourseunittesting.mockitoFundamentals.networking.UpdateUsernameHttpEndpointSync;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.eventbus.EventBusPoster;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.eventbus.LoggedInEvent;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.NetworkErrorException;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.users.UsersCache;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class UpdateUsernameUseCaseSyncTest {

    private static final String USER_ID = "userId";
    private static final String USERNAME = "username";

    UpdateUsernameHttpEndpointSync mUpdateUsernameHttpEndpointSyncMock;
    UsersCache mUsersCacheMock;
    EventBusPoster mEventBusPosterMock;

    UpdateUsernameUseCaseSync updateTest;

    @Before
    public void setup() throws Exception {
        mUpdateUsernameHttpEndpointSyncMock = mock(UpdateUsernameHttpEndpointSync.class);
        mUsersCacheMock = mock(UsersCache.class);
        mEventBusPosterMock = mock(EventBusPoster.class);
        updateTest = new UpdateUsernameUseCaseSync(
                mUpdateUsernameHttpEndpointSyncMock, mUsersCacheMock, mEventBusPosterMock);
        success();
    }

    @Test
    public void updateUsername_success_userIdAndUsernameToEndpoint() throws Exception {
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        updateTest.updateUsernameSync(USER_ID, USERNAME);
        verify(mUpdateUsernameHttpEndpointSyncMock, times(1))
                .updateUsername(argumentCaptor.capture(), argumentCaptor.capture());
        List<String> captures = argumentCaptor.getAllValues();
        assertThat(captures.get(0), is(USER_ID));
        assertThat(captures.get(1), is(USERNAME));
    }

    @Test
    public void updateUsername_success_usersCache() throws Exception {
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        updateTest.updateUsernameSync(USER_ID, USERNAME);
        verify(mUsersCacheMock).updateUsername(argumentCaptor.capture(), argumentCaptor.capture());
        List<String> captures = argumentCaptor.getAllValues();
        assertThat(captures.get(0), is(USER_ID));
        assertThat(captures.get(1), is(USERNAME));
    }

    @Test
    public void updateUsername_generalError_usersNotCached() throws Exception {
        generalError();
        updateTest.updateUsernameSync(USER_ID, USERNAME);
        verifyNoMoreInteractions(mUsersCacheMock);
    }

    @Test
    public void updateUsername_serverError_usersNotCached() throws Exception {
        serverError();
        updateTest.updateUsernameSync(USER_ID, USERNAME);
        verifyNoMoreInteractions(mUsersCacheMock);
    }

    @Test
    public void updateUsername_success_loggedInEventPosted() throws Exception {
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        updateTest.updateUsernameSync(USER_ID, USERNAME);
        verify(mEventBusPosterMock).postEvent(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), is(CoreMatchers.<String>instanceOf(LoggedInEvent.class)));
    }

    @Test
    public void updateUsername_generalError_noInteractionWithEventPosted() throws Exception {
        generalError();
        updateTest.updateUsernameSync(USER_ID, USERNAME);
        verifyNoMoreInteractions(mEventBusPosterMock);
    }

    @Test
    public void updateUsername_serverError_noInteractionWithEventPosted() throws Exception {
        serverError();
        updateTest.updateUsernameSync(USER_ID, USERNAME);
        verifyNoMoreInteractions(mEventBusPosterMock);
    }

    @Test
    public void updateUsername_success_successReturned() throws Exception {
        UpdateUsernameUseCaseSync.UseCaseResult result = updateTest.updateUsernameSync(USER_ID, USERNAME);
        assertThat(result, is(UpdateUsernameUseCaseSync.UseCaseResult.SUCCESS));
    }

    @Test
    public void updateUsername_generalError_failureReturned() throws Exception {
        generalError();
        UpdateUsernameUseCaseSync.UseCaseResult result = updateTest.updateUsernameSync(USER_ID, USERNAME);
        assertThat(result, is(UpdateUsernameUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void updateUsername_serverError_failureReturned() throws Exception {
        serverError();
        UpdateUsernameUseCaseSync.UseCaseResult result = updateTest.updateUsernameSync(USER_ID, USERNAME);
        assertThat(result, is(UpdateUsernameUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void updateUsername_networkError_networkErrorReturned() throws Exception {
        networkError();
        UpdateUsernameUseCaseSync.UseCaseResult result = updateTest.updateUsernameSync(USER_ID, USERNAME);
        assertThat(result, is(UpdateUsernameUseCaseSync.UseCaseResult.NETWORK_ERROR));
    }

    private void networkError() throws Exception {
        doThrow(new NetworkErrorException()).when(mUpdateUsernameHttpEndpointSyncMock)
                .updateUsername(any(String.class), any(String.class));
    }

    private void success() throws NetworkErrorException {
        when(mUpdateUsernameHttpEndpointSyncMock.updateUsername(any(String.class), any(String.class)))
                .thenReturn(new UpdateUsernameHttpEndpointSync.EndpointResult(
                        UpdateUsernameHttpEndpointSync.EndpointResultStatus.SUCCESS, USER_ID, USERNAME));
    }

    private void generalError() throws Exception {
        when(mUpdateUsernameHttpEndpointSyncMock.updateUsername(any(String.class), any(String.class)))
                .thenReturn(new UpdateUsernameHttpEndpointSync.EndpointResult(
                        UpdateUsernameHttpEndpointSync.EndpointResultStatus.GENERAL_ERROR, "", ""
                    )
                );
    }

    private void serverError() throws Exception {
        when(mUpdateUsernameHttpEndpointSyncMock.updateUsername(any(String.class), any(String.class)))
                .thenReturn(new UpdateUsernameHttpEndpointSync.EndpointResult(
                                UpdateUsernameHttpEndpointSync.EndpointResultStatus.SERVER_ERROR, "", ""
                        )
                );
    }
}
