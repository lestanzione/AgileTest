package br.com.stanzione.agiletest.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class Owner extends RealmObject implements Serializable {

    @SerializedName("id")
    private long id;
    @SerializedName("login")
    private String login;
    @SerializedName("avatar_url")
    private String imageUrl;

    public Owner(){}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
