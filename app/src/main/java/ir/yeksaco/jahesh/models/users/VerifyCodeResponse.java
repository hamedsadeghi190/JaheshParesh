package ir.yeksaco.jahesh.models.users;


import com.google.gson.annotations.SerializedName;

public class VerifyCodeResponse  {
    @SerializedName("firstName")
    public String FirstName;
    @SerializedName("lastName")
    public String LastName;
    @SerializedName("token")
    public String Token;
    @SerializedName("refreshToken")
    public String RefreshToken;
    @SerializedName("isActive")
    public boolean IsActive;

    public VerifyCodeResponse() {
    }

    public VerifyCodeResponse(String firstName, String lastName, String token, String refreshToken, boolean isActive) {
        FirstName = firstName;
        LastName = lastName;
        Token = token;
        RefreshToken = refreshToken;
        IsActive = isActive;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getRefreshToken() {
        return RefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        RefreshToken = refreshToken;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }
}
