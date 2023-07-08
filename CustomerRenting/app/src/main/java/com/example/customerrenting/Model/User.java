package com.example.customerrenting.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String email;
    private String userID;
    private String gender;
    public String fullName;
    private String avatarURL;
    private String birthday;
    private String phoneNumber;
    private String address;
    private String city;
    private String frontCard;
    private String behindCard;
    private int haveStore;

    public User() {
        address = "";
        email = "";
        userID = "";
        fullName = "";
        avatarURL = "";
        birthday = "";
        phoneNumber = "";
        city = "";
        gender = "";
        frontCard = "";
        behindCard = "";
        haveStore = 0;
    }

    protected User(Parcel in) {
        email = in.readString();
        userID = in.readString();
        fullName = in.readString();
        avatarURL = in.readString();
        birthday = in.readString();
        phoneNumber = in.readString();
        address = in.readString();
        city = in.readString();
        gender = in.readString();
        frontCard = in.readString();
        behindCard = in.readString();
        haveStore = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getHaveStore() {
        return haveStore;
    }

    public void setHaveStore(int haveStore) {
        this.haveStore = haveStore;
    }

    public String getFrontCard() {
        return frontCard;
    }

    public void setFrontCard(String frontCard) {
        this.frontCard = frontCard;
    }

    public String getBehindCard() {
        return behindCard;
    }

    public void setBehindCard(String behindCard) {
        this.behindCard = behindCard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public User(String email, String user_id, String username, String avatarURL, String dateOfBirth, String phoneNumber, String address, String city, String gender, String frontCard, String behindCard, int haveStore) {
        this.email = email;
        this.userID = user_id;
        this.fullName = username;
        this.avatarURL = avatarURL;
        this.birthday = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.gender = gender;
        this.frontCard = frontCard;
        this.behindCard = behindCard;
        this.haveStore = haveStore;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", user_id='" + userID + '\'' +
                ", username='" + fullName + '\'' +
                ", avatar='" + avatarURL + '\'' +

                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(userID);
        dest.writeString(fullName);
        dest.writeString(avatarURL);
        dest.writeString(birthday);
        dest.writeString(phoneNumber);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(gender);
        dest.writeString(frontCard);
        dest.writeString(behindCard);
        dest.writeInt(haveStore);
    }
}
