package com.mybid.models;

import android.os.Parcel;
import android.os.Parcelable;

//we made our user model implement parcelable b/c we
// want to pass it's object b/n Activities
//we could also use serializable but it is slower.
public class User implements Parcelable {
    private String uid ;
    private String uname;
    private String uemail;
    private String uphone;
    private String ugender;
    private Double udeposit;
    private String upasswd;
    private byte[] uimgByte;

    public User (){
    }
    public User(String uid, String uname, String uemail, String uphone,
                String ugender, Double udeposit, String upasswd, byte[] uimgByte) {
        this.uid = uid;
        this.uname = uname;
        this.uemail = uemail;
        this.uphone = uphone;
        this.ugender = ugender;
        this.udeposit = udeposit;
        this.upasswd = upasswd;
        this.uimgByte = uimgByte;
    }

    protected User(Parcel in) {
        uid = in.readString();
        uname = in.readString();
        uemail = in.readString();
        uphone = in.readString();
        ugender = in.readString();
        if (in.readByte() == 0) {
            udeposit = null;
        } else {
            udeposit = in.readDouble();
        }
        upasswd = in.readString();
        uimgByte = in.createByteArray();
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

    public void clearUser(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUgender() {
        return ugender;
    }

    public void setUgender(String ugender) {
        this.ugender = ugender;
    }

    public Double getUdeposit() {
        return udeposit;
    }

    public void setUdeposit(Double udeposit) {
        this.udeposit = udeposit;
    }

    public String getUpasswd() {
        return upasswd;
    }

    public void setUpasswd(String upasswd) {
        this.upasswd = upasswd;
    }

    public byte[] getUimgByte() {
        return uimgByte;
    }

    public void setUimgByte(byte[] uimgByte) {
        this.uimgByte = uimgByte;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(uname);
        dest.writeString(uemail);
        dest.writeString(uphone);
        dest.writeString(ugender);
        if (udeposit == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(udeposit);
        }
        dest.writeString(upasswd);
        dest.writeByteArray(uimgByte);
    }
}
