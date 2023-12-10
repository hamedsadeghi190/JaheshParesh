package ir.yeksaco.jahesh.models.content;

import com.google.gson.annotations.SerializedName;
public class FileUrlList {

    @SerializedName("contentId")
    public int ContentId;
    @SerializedName("extension")
    public String Extension;
    @SerializedName("fileId")
    public int FileId;
    @SerializedName("fileUrl")
    public String FileUrl;
    @SerializedName("fileName")
    public String FileName;

    public int getContentId() {
        return ContentId;
    }

    public void setContentId(int contentId) {
        ContentId = contentId;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }

    public int getFileId() {
        return FileId;
    }

    public void setFileId(int fileId) {
        FileId = fileId;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileUrl) {
        FileUrl = fileUrl;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }
}
