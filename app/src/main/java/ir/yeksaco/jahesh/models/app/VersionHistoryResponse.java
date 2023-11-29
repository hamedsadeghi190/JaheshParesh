package ir.yeksaco.jahesh.models.app;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class VersionHistoryResponse {

    @SerializedName("id")
    public int Id;
    @SerializedName("title")
    public String Title;
    @SerializedName("version")
    public String Version;
    @SerializedName("versionCode")
    public int VersionCode;
    @SerializedName("changes")
    public List<String> Changes;
    @SerializedName("releaseDate")
    public String ReleaseDate;
    @SerializedName("isActive")
    public boolean IsActive;
    @SerializedName("isForce")
    public boolean IsForce;
    @SerializedName("playUrl")
    public String PlayUrl;
    @SerializedName("directUrl")
    public String DirectUrl;
    @SerializedName("bazarUrl")
    public String BazarUrl;
    @SerializedName("myketUrl")
    public String MyketUrl;
    @SerializedName("updateOn")
    public String UpdateOn;
}
