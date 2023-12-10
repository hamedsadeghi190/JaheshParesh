package ir.yeksaco.jahesh.models.users;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("mobile")
    public String mobile;
    @SerializedName("email")
    public String email;
    @SerializedName("age")
    public int age;
    @SerializedName("gender")
    public String gender;
    @SerializedName("userKind")
    public String userKind;
    @SerializedName("countryId")
    public int countryId;
    @SerializedName("stateId")
    public int stateId;
    @SerializedName("cityId")
    public int cityId;
    @SerializedName("gradeId")
    public int gradeId;
    @SerializedName("fieldId")
    public int fieldId;
}
