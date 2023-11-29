package ir.yeksaco.jahesh.webService.iterfaces;

import java.util.ArrayList;

import ir.yeksaco.jahesh.models.content.GetMainMenuResponse;
import ir.yeksaco.jahesh.models.content.GetMenuResponse;
import ir.yeksaco.jahesh.models.content.ContentsPriceModel;
import ir.yeksaco.jahesh.models.content.ContentsPriceResponse;
import ir.yeksaco.jahesh.models.content.ContentDetailsResponse;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Query;

public interface IContents {
    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @GET("Home/MainMenu")
    Call<ResponseBase<GetMainMenuResponse>> GetMainMenu(@Header("Authorization") String AuthKey);

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @GET("Home/MenuByParentId")
    Call<ResponseBase<ArrayList<GetMenuResponse>>> MenuByParentId(@Header("Authorization") String AuthKey, @Query("ParentId") int ParentId);

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @GET("Home/ContentById")
    Call<ResponseBase<ContentDetailsResponse>> ContentById(@Header("Authorization") String AuthKey, @Query("Id") int Id);


    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @POST("Home/ContentPriceById")
    Call<ResponseBase<ArrayList<ContentsPriceResponse>>> ContentsPriceById(@Header("Authorization") String AuthKey, @Body ContentsPriceModel body);
}
