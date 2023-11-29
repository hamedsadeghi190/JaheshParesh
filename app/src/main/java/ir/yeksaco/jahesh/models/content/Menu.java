package ir.yeksaco.jahesh.models.content;

import com.google.gson.annotations.SerializedName;

public class Menu {
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

    public String getLevelKind() {
        return LevelKind;
    }

    public String getMenuType() {
        return MenuType;
    }

    public String getTitle() {
        return Title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }
}
