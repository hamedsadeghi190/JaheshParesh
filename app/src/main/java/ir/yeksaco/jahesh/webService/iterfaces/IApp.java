package ir.yeksaco.jahesh.webService.iterfaces;

import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.models.users.SendCodeRequest;
import ir.yeksaco.jahesh.models.users.SendCodeResponse;
import ir.yeksaco.jahesh.models.app.*;
import java.util.List;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.Header;
public interface IApp {

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @GET("AppVersion/CheckVersion")
    Call<ResponseBase<List<VersionHistoryResponse>>> CheckVersion(@Query("VersionCode") String VersionCode);
}
