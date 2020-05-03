package com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking;

import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.model.UserProfile;

public interface UserProfileHttpEndpointSync {

    EndpointResult fetchUserProfile() throws NetworkErrorException;

    enum EndpointResultStatus {
        SUCCESS,
        SERVER_ERROR,
        GENERAL_ERROR
    }

    class EndpointResult {
        private final EndpointResultStatus mStatus;
        private final UserProfile mUserProfile;

        public EndpointResult(EndpointResultStatus status, UserProfile userProfile) {
            mStatus = status;
            mUserProfile = userProfile;
        }

        public EndpointResultStatus getStatus() {
            return mStatus;
        }

        public UserProfile getUserProfile() {
            return mUserProfile;
        }
    }
}
