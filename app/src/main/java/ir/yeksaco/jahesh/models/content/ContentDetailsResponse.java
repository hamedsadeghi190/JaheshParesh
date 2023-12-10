package ir.yeksaco.jahesh.models.content;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class ContentDetailsResponse {
    @SerializedName("id")
    public int id;
    @SerializedName("treeId")
    public int treeId;
    @SerializedName("title")
    public String title;
    @SerializedName("imageUrl")
    public String imageUrl;
    @SerializedName("priority")
    public int priority;
    @SerializedName("fileUrlList")
    public ArrayList<FileUrlList> fileUrlList;
    
    @SerializedName("content")
    public String content;
    @SerializedName("price")
    public int price;
    @SerializedName("isActive")
    public boolean isActive;
    @SerializedName("description")
    public String description;
    @SerializedName("updateOn")
    public String updateOn;

    @SerializedName("purchased")
    public boolean Purchased;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTreeId() {
        return treeId;
    }

    public void setTreeId(int treeId) {
        this.treeId = treeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ArrayList<FileUrlList> getFileUrlList() {
        return fileUrlList;
    }

    public void setFileUrlList(ArrayList<FileUrlList> fileUrlList) {
        this.fileUrlList = fileUrlList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(String updateOn) {
        this.updateOn = updateOn;
    }

    public boolean getPurchased() {
        return Purchased;
    }

    public void setPurchased(boolean purchased) {
        Purchased = purchased;
    }
}
