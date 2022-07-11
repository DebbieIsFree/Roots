package com.example.project2;

public class UserData {
    private String id;
    private String nickname;
    private String profileImage;
    private String record;

    public String getIdData() {
        return id;
    }
    public void setIdData(String id) {
        this.id = id;
    }

    public String getNicknameData() {
        return nickname;
    }
    public void setNicknameData(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImageData() {
        return profileImage;
    }
    public void setProfileImageData(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getRecordData() {
        return record;
    }
    public void setRecordData(String record) {
        this.record = record;
    }

    private static UserData instance = null;
    public static synchronized UserData getInstance(){
        if(null == instance){
            instance = new UserData();
        } return instance;
    }
}
