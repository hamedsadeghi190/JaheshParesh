package ir.yeksaco.jahesh.webService.iterfaces;

import java.util.List;

import ir.yeksaco.jahesh.models.app.VersionHistoryResponse;
import ir.yeksaco.jahesh.models.content.GetMainMenuResponse;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Query;
public interface IApp {

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @GET("AppVersion/CheckVersion")
    Call<ResponseBase<List<VersionHistoryResponse>>> CheckVersion(@Query("VersionCode") String VersionCode);

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @GET("AppVersion/NotFound")
    Call<ResponseBase<String>> NotFound();
}
