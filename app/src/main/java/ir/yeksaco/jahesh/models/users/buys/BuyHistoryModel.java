package ir.yeksaco.jahesh.models.users.buys;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
public class BuyHistoryModel {

    public BuyHistoryModel() {
        Carts = new ArrayList<>();
    }

    @SerializedName("id")
    public int Id;
    @SerializedName("userId")
    public int UserId;
    @SerializedName("factorStatus")
    public String FactorStatus;
    @SerializedName("totalAmount")
    public int TotalAmount;
    @SerializedName("carts")
    public ArrayList<Cart> Carts;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getFactorStatus() {
        return FactorStatus;
    }

    public void setFactorStatus(String factorStatus) {
        FactorStatus = factorStatus;
    }

    public int getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        TotalAmount = totalAmount;
    }

    public ArrayList<Cart> getCarts() {
        return Carts;
    }

    public void setCarts(ArrayList<Cart> carts) {
        Carts = carts;
    }
}
