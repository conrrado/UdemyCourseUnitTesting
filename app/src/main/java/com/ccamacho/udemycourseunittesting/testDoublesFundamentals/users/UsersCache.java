package com.ccamacho.udemycourseunittesting.testDoublesFundamentals.users;

import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.model.UserProfile;

public interface UsersCache {

    void setUserProfile(UserProfile userProfile);

    UserProfile getUserProfile();
}
