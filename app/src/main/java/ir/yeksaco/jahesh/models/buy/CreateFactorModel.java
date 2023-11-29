package ir.yeksaco.jahesh.models.buy;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
public class CreateFactorModel {
    public ArrayList<Integer> Ids ;
    public String DicountCode;

    public CreateFactorModel() {
        Ids = new ArrayList<>();
    }
}

