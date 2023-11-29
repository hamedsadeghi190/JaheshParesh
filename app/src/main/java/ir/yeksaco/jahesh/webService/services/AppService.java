package ir.yeksaco.jahesh.webService.services;

import java.util.List;
import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.app.VersionHistoryResponse;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.models.users.SendCodeRequest;
import ir.yeksaco.jahesh.models.users.SendCodeResponse;
import ir.yeksaco.jahesh.webService.ApiClient;
import ir.yeksaco.jahesh.webService.iterfaces.iwebServicelistener;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AppService {
    public void CheckVersion(final iwebServicelistener listener, int versionCode) {
        try {
            final Call<ResponseBase<List<VersionHistoryResponse>>> CallServer = ApiClient.AppClinet.CheckVersion(Integer.toString(versionCode));
            CallServer.enqueue(new Callback<ResponseBase<List<VersionHistoryResponse>>>() {
                @Override
                public void onResponse(Response<ResponseBase<List<VersionHistoryResponse>>> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        listener.OnSuccess(response.body());
                    } else {
                        listener.OnFailed(FailType.Api, response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    listener.OnFailed(FailType.Connection,t.getMessage());
                }
            });
        } catch (Exception ex) {
            listener.OnFailed(FailType.Exception, ex.getMessage());
        }
    }
}
