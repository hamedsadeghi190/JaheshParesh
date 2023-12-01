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

    public int getId() {

        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public String getVersion() {
        return Version;
    }

    public int getVersionCode() {
        return VersionCode;
    }

    public List<String> getChanges() {
        return Changes;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public boolean isActive() {
        return IsActive;
    }

    public boolean isForce() {
        return IsForce;
    }

    public String getPlayUrl() {
        return PlayUrl;
    }

    public String getDirectUrl() {
        return DirectUrl;
    }

    public String getBazarUrl() {
        return BazarUrl;
    }

    public String getMyketUrl() {
        return MyketUrl;
    }

    public String getUpdateOn() {
        return UpdateOn;
    }
}
