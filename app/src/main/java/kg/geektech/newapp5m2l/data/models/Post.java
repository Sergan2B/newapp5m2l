package kg.geektech.newapp5m2l.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Post implements Serializable {
    String content, title;
    @SerializedName("group")
    Integer groupId;
    Integer id;
    @SerializedName("user")
    Integer userId;


    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }




    public Post(String content, String title, Integer groupId, Integer userId) {
        this.content = content;
        this.title = title;
        this.groupId = groupId;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }
}
