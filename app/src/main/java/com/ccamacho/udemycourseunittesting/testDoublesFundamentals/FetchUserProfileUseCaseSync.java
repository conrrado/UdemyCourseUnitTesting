package com.ccamacho.udemycourseunittesting.testDoublesFundamentals;

import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.model.UserProfile;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.NetworkErrorException;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.UserProfileHttpEndpointSync;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.users.UsersCache;

import java.util.ArrayList;
import java.util.List;

public class FetchUserProfileUseCaseSync {

    public enum UseCaseResult {
        SUCCESS,
        FAILURE,
        NETWORK_ERROR
    }

    private final UserProfileHttpEndpointSync mUserProfileHttpEndpointSync;
    private final UsersCache mUsersCache;

    public FetchUserProfileUseCaseSync(UserProfileHttpEndpointSync userProfileHttpEndpointSync,
                                       UsersCache usersCache) {
        mUserProfileHttpEndpointSync = userProfileHttpEndpointSync;
        mUsersCache = usersCache;
    }

    public UseCaseResult fetchUserProfile() {
        UserProfileHttpEndpointSync.EndpointResult endpointResult;
        try {
            endpointResult = mUserProfileHttpEndpointSync.fetchUserProfile();
        } catch (NetworkErrorException e) {
            return UseCaseResult.NETWORK_ERROR;
        }

        mUsersCache.setUserProfile(endpointResult.getUserProfile());

        if (isSuccessfulEndpointResult(endpointResult)) {
            return UseCaseResult.SUCCESS;
        }
        return UseCaseResult.FAILURE;
    }

    private boolean isSuccessfulEndpointResult(UserProfileHttpEndpointSync.EndpointResult endpointResult) {
        return endpointResult.getStatus() == UserProfileHttpEndpointSync.EndpointResultStatus.SUCCESS;
    }

}
