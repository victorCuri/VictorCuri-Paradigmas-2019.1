package com.cursoandroid.petinder.Cards;

import android.telephony.mbms.StreamingServiceInfo;

public class cards {

    private String userId;
    private String name;
    private  String age;
    private String profileImage;

    public cards(String userId, String name, String profileImage, String age) {
        this.userId = userId;
        this.name = name;
        this.profileImage = profileImage;
        this.age = age;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
