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
}
