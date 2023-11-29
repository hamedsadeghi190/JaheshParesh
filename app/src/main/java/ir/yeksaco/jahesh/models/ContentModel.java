package ir.yeksaco.jahesh.models;

public class ContentModel {
    public String Title;

    public ContentModel(String title) {
        Title = title;
    }

    public ContentModel() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
