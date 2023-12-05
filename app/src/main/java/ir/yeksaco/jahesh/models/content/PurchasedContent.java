package ir.yeksaco.jahesh.models.content;

import com.google.gson.annotations.SerializedName;

public class PurchasedContent {
    @SerializedName("id")
    public int Id;
    @SerializedName("treeId")
    public int treeId;
    @SerializedName("title")
    public String Title;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getTreeId() {
        return treeId;
    }

    public void setTreeId(int treeId) {
        this.treeId = treeId;
    }
}
