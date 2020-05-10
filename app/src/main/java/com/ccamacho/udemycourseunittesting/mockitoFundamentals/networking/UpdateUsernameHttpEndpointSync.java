package com.ccamacho.udemycourseunittesting.mockitoFundamentals.networking;

import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.NetworkErrorException;

public interface UpdateUsernameHttpEndpointSync {

    EndpointResult updateUsername(String userId, String username) throws NetworkErrorException;

    enum EndpointResultStatus {
        SUCCESS,
        SERVER_ERROR,
        GENERAL_ERROR
    }

    class EndpointResult {
        private final EndpointResultStatus mStatus;
        private final String mUserId;
        private final String mUsername;

        public EndpointResult(EndpointResultStatus status, String userId, String username) {
            mStatus = status;
            mUserId = userId;
            mUsername = username;
        }

        public EndpointResultStatus getStatus() {
            return mStatus;
        }

        public String getUserId() {
            return mUserId;
        }

        public String getUsername() {
            return mUsername;
        }
    }


}
