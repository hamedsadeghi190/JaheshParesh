package ir.yeksaco.jahesh.models.users.token;

import com.google.gson.annotations.SerializedName;

public class RefreshTokenResponse {

    @SerializedName("access_token")
    public String AccessToken;
    @SerializedName("refresh_token")
    public String RefreshToken;
    @SerializedName("token_type")
    public String TokenType;
    @SerializedName("expires_in")
    public int ExpireDate;
    @SerializedName("userid")
    public int UserId;
    @SerializedName("username")
    public String Username;
    @SerializedName("firstname")
    public String Firstname;
    @SerializedName("lastname")
    public String Lastname;

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public String getRefreshToken() {
        return RefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        RefreshToken = refreshToken;
    }

    public String getTokenType() {
        return TokenType;
    }

    public void setTokenType(String tokenType) {
        TokenType = tokenType;
    }

    public int getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(int expireDate) {
        ExpireDate = expireDate;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }
}
