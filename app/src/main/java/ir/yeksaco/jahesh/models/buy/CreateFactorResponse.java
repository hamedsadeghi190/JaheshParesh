package ir.yeksaco.jahesh.models.buy;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
public class CreateFactorResponse {

    @SerializedName("factorId")
    public int FactorId;
    @SerializedName("totalAmount")
    public int TotalAmount;
    @SerializedName("paymentUrl")
    public String PaymentUrl;
    @SerializedName("createOn")
    public String CreateOn;
}
