package ir.yeksaco.jahesh.models.content;

import com.google.gson.annotations.SerializedName;

public class GetMenuResponse {
    @SerializedName("id")
    public int Id;
    @SerializedName("levelKind")
    public String LevelKind;
    @SerializedName("menuType")
    public String MenuType;
    @SerializedName("title")
    public String Title;
    @SerializedName("imageUrl")
    public String ImageUrl;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLevelKind() {
        return LevelKind;
    }

    public void setLevelKind(String levelKind) {
        LevelKind = levelKind;
    }

    public String getMenuType() {
        return MenuType;
    }

    public void setMenuType(String menuType) {
        MenuType = menuType;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
