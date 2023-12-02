package ir.yeksaco.jahesh.models.content;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoadedData {

    public int ContentId;
    public ArrayList<GetMenuResponse> Data= new ArrayList<GetMenuResponse>();
}
