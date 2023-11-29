package ir.yeksaco.jahesh.webService.iterfaces;

import ir.yeksaco.jahesh.models.buy.CreateFactorModel;
import ir.yeksaco.jahesh.models.buy.CreateFactorResponse;
import ir.yeksaco.jahesh.models.buy.FactorStatusResponse;
import ir.yeksaco.jahesh.models.buy.CheckDiscountRequest;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

public interface IBuy {
    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @POST("Buy/CreateFactor")
    Call<ResponseBase<CreateFactorResponse>> CreateFactor(@Header("Authorization") String AuthKey, @Body CreateFactorModel data);

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @GET("Buy/FactorStatus")
    Call<ResponseBase<FactorStatusResponse>> FactorStatus(@Header("Authorization") String AuthKey,@Query("FactorId") String FactorId);


    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @POST("Buy/CheckDiscount")
    Call<ResponseBase<CreateFactorResponse>> CheckDiscount(@Header("Authorization") String AuthKey, @Body CheckDiscountRequest data);

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @GET("Buy/Payment")
    Call<ResponseBase<String>> CheckPayment(@Header("Authorization") String AuthKey,@Query("FID") String FactorId);
}
