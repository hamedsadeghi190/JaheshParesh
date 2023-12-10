package ir.yeksaco.jahesh.models.content;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class GetMainMenuResponse {
    @SerializedName("menu")
    public ArrayList<Menu> Menu;
    @SerializedName("slider")
    public ArrayList<SliderModel> Slider;

    @SerializedName("isConfirmed")
    public boolean IsConfirmed;
}
