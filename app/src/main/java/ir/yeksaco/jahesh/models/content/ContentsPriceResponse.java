package ir.yeksaco.jahesh.models.content;

import com.google.gson.annotations.SerializedName;

public class ContentsPriceResponse {

    @SerializedName("Id")
    public int id;
    @SerializedName("Price")
    public int price;
    @SerializedName("OffPrice")
    public int offPrice;
    @SerializedName("IsActive")
    public boolean isActive;
}
