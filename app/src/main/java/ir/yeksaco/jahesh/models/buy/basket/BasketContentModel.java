package ir.yeksaco.jahesh.models.buy.basket;

public class BasketContentModel {
    public Integer ContentId;
    public  String Name;
    public  Integer Amount;

    public BasketContentModel() {
    }

    public BasketContentModel(Integer contentId, String name, Integer amount) {
        ContentId = contentId;
        Name = name;
        Amount = amount;
    }

    public Integer getContentId() {
        return ContentId;
    }

    public void setContentId(Integer contentId) {
        ContentId = contentId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }
}
