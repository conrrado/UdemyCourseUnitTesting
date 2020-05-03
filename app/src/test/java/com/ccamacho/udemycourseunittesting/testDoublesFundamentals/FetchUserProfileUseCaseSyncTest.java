package com.ccamacho.udemycourseunittesting.testDoublesFundamentals;

import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.model.UserProfile;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.NetworkErrorException;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.UserProfileHttpEndpointSync;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.users.UsersCache;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FetchUserProfileUseCaseSyncTest {

    private static final UserProfile USER_PROFILE = new UserProfile(1, "name");
    private static final UserProfile USER_PROFILE_ERROR = new UserProfile();

    private UserProfileHttpEndpointSyncTd mUserProfileHttpEndpointSyncTd;
    private UsersCacheTd mUsersCacheTd;

    private FetchUserProfileUseCaseSync fetchUserProfileTest;

    @Before
    public void setup() throws Exception {
        mUserProfileHttpEndpointSyncTd = new UserProfileHttpEndpointSyncTd();
        mUsersCacheTd = new UsersCacheTd();
        fetchUserProfileTest = new FetchUserProfileUseCaseSync(mUserProfileHttpEndpointSyncTd, mUsersCacheTd);
    }

    @Test
    public void fetchUserProfile_success_usersCached() throws Exception {
        fetchUserProfileTest.fetchUserProfile();
        assertThat(mUsersCacheTd.getUserProfile(), is(USER_PROFILE));
    }

    @Test
    public void fetchUserProfile_generalError_usersCached() throws Exception {
        mUserProfileHttpEndpointSyncTd.mIsGeneralError = true;
        fetchUserProfileTest.fetchUserProfile();
        assertThat(mUsersCacheTd.getUserProfile(), is(USER_PROFILE_ERROR));
    }

    @Test
    public void fetchUserProfile_serverError_usersCached() throws Exception {
        mUserProfileHttpEndpointSyncTd.mIsServerError = true;
        fetchUserProfileTest.fetchUserProfile();
        assertThat(mUsersCacheTd.getUserProfile(), is(USER_PROFILE_ERROR));
    }

    @Test
    public void fetchUserProfile_success_successReturned() throws Exception {
        FetchUserProfileUseCaseSync.UseCaseResult result = fetchUserProfileTest.fetchUserProfile();
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.SUCCESS));
    }

    @Test
    public void fetchUserProfile_generalError_failureReturned() throws Exception {
        mUserProfileHttpEndpointSyncTd.mIsGeneralError = true;
        FetchUserProfileUseCaseSync.UseCaseResult result = fetchUserProfileTest.fetchUserProfile();
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void fetchUserProfile_serverError_failureReturned() throws Exception {
        mUserProfileHttpEndpointSyncTd.mIsServerError = true;
        FetchUserProfileUseCaseSync.UseCaseResult result = fetchUserProfileTest.fetchUserProfile();
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void fetchUserProfile_networkError_networkErrorReturned() throws Exception {
        mUserProfileHttpEndpointSyncTd.mIsNetworkError = true;
        FetchUserProfileUseCaseSync.UseCaseResult result = fetchUserProfileTest.fetchUserProfile();
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.NETWORK_ERROR));
    }

    private static class UserProfileHttpEndpointSyncTd implements UserProfileHttpEndpointSync {
        private boolean mIsGeneralError = false;
        private boolean mIsServerError = false;
        private boolean mIsNetworkError = false;

        @Override
        public EndpointResult fetchUserProfile() throws NetworkErrorException {
            if(mIsGeneralError) {
                return new EndpointResult(EndpointResultStatus.GENERAL_ERROR, USER_PROFILE_ERROR);
            }
            else if (mIsServerError) {
                return new EndpointResult(EndpointResultStatus.SERVER_ERROR, USER_PROFILE_ERROR);
            }
            else if (mIsNetworkError) {
                throw  new NetworkErrorException();
            }
            return new EndpointResult(EndpointResultStatus.SUCCESS, USER_PROFILE);
        }
    }

    private static class UsersCacheTd implements UsersCache {
        private UserProfile mUserProfile;

        @Override
        public void setUserProfile(UserProfile userProfile) {
            mUserProfile = userProfile;
        }

        @Override
        public UserProfile getUserProfile() {
            return mUserProfile;
        }
    }
}
