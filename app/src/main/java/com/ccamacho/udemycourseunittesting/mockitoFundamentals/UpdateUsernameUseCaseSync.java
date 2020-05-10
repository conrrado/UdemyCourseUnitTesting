package com.ccamacho.udemycourseunittesting.mockitoFundamentals;

import com.ccamacho.udemycourseunittesting.mockitoFundamentals.networking.UpdateUsernameHttpEndpointSync;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.eventbus.EventBusPoster;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.eventbus.LoggedInEvent;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.NetworkErrorException;
import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.users.UsersCache;

public class UpdateUsernameUseCaseSync {

    public enum UseCaseResult {
        SUCCESS,
        FAILURE,
        NETWORK_ERROR
    }

    private final UpdateUsernameHttpEndpointSync mUpdateUsernameHttpEndpointSync;
    private final UsersCache mUsersCache;
    private final EventBusPoster mEventBusPoster;

    public UpdateUsernameUseCaseSync(UpdateUsernameHttpEndpointSync updateUsernameHttpEndpointSync,
                                     UsersCache usersCache, EventBusPoster eventBusPoster) {
        mUpdateUsernameHttpEndpointSync = updateUsernameHttpEndpointSync;
        mUsersCache = usersCache;
        mEventBusPoster = eventBusPoster;
    }

    public UseCaseResult updateUsernameSync(String userId, String username) {
        UpdateUsernameHttpEndpointSync.EndpointResult endpointResult;
        try {
            endpointResult = mUpdateUsernameHttpEndpointSync.updateUsername(userId, username);
        } catch (NetworkErrorException e) {
            return UseCaseResult.NETWORK_ERROR;
        }

        if (isSuccessfulEndpointResult(endpointResult)) {
            mUsersCache.updateUsername(endpointResult.getUserId(), endpointResult.getUsername());
            mEventBusPoster.postEvent(new LoggedInEvent());
            return UseCaseResult.SUCCESS;
        }
        return UseCaseResult.FAILURE;
    }

    private boolean isSuccessfulEndpointResult(UpdateUsernameHttpEndpointSync.EndpointResult endpointResult) {
        return endpointResult.getStatus() == UpdateUsernameHttpEndpointSync.EndpointResultStatus.SUCCESS;
    }
}
