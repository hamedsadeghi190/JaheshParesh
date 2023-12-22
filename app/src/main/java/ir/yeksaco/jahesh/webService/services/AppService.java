package ir.yeksaco.jahesh.webService.services;

import android.util.Log;

import java.util.List;

import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.app.VersionHistoryResponse;
import ir.yeksaco.jahesh.models.general.ResponseBase;
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
                    listener.OnFailed(FailType.Connection, t.getMessage());
                }
            });
        } catch (Exception ex) {
            listener.OnFailed(FailType.Exception, ex.getMessage());
        }

    }

    public void NotFound(final iwebServicelistener listener) {
        try {
            final Call<ResponseBase<String>> CallServer = ApiClient.AppClinet.NotFound();
            CallServer.enqueue(new Callback<ResponseBase<String>>() {
                @Override
                public void onResponse(Response<ResponseBase<String>> response, Retrofit retrofit) {

                    if (response.isSuccess()) {
                        if (response.code() == 404) {
                            Log.e("jaheshTag", "404 success");
                        } else {
                            listener.OnSuccess(response.body());
                            Log.e("jaheshTag", response.code() +" success");
                        }
                    } else {
                        Log.e("jaheshTag", "not success");
                        listener.OnFailed(FailType.Api, response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("jaheshTag", "onFailure");
                    listener.OnFailed(FailType.Connection, t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.e("jaheshTag", "onException");
            listener.OnFailed(FailType.Exception, ex.getMessage());
        }

    }
}
