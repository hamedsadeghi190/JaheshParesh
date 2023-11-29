package ir.yeksaco.jahesh.models.buy.basket;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

public class BasketModel {

    public ArrayList<BasketContentModel> Contents;

    public String Status;

    public boolean Exist(int id) {
        return (Contents.stream().filter(x -> x.ContentId == id).count() > 0);
    }

    public BasketModel() {
        Contents = new ArrayList<BasketContentModel>();
    }

    public ArrayList<BasketContentModel> getContents() {
        return Contents;
    }

    public void setContents(ArrayList<BasketContentModel> contents) {
        Contents = contents;
    }


    public int getTotal() {
        int total = 0;
        for (int i = 0; i < Contents.size(); i++) {
            total += Contents.get(i).getAmount();
        }
        return total;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void addContent(BasketContentModel value) {
        Contents.add(value);
    }

    public void removeContent(BasketContentModel value) {
        Contents.remove(value);
    }
}
