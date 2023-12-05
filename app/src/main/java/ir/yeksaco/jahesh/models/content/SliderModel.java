package ir.yeksaco.jahesh.models.content;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
public class SliderModel {
    @SerializedName("id")
    public int Id;
    @SerializedName("title")
    public String Title;
    @SerializedName("imageUrl")
    public String ImageUrl;
    @SerializedName("sliderType")
    public String SliderType;
    @SerializedName("isActive")
    public boolean IsActive;
    @SerializedName("data")
    public String Data;
    @SerializedName("UpdateOn")
    public Date UpdateOn;

    public int getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getSliderType() {
        return SliderType;
    }

    public boolean isActive() {
        return IsActive;
    }

    public String getData() {
        return Data;
    }

    public Date getUpdateOn() {
        return UpdateOn;
    }
}
