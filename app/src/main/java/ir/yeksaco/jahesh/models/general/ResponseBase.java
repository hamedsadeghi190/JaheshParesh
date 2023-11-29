package ir.yeksaco.jahesh.models.general;

import com.google.gson.annotations.SerializedName;

public class ResponseBase <T>{
    @SerializedName("isSuccess")
    public boolean IsSuccess;
    @SerializedName("code")
    public int Code;
    @SerializedName("message")
    public String Message;
    @SerializedName("data")
    public T Data;

}
