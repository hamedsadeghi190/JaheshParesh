package ir.yeksaco.jahesh.webService.services;

import android.util.Log;

import java.util.ArrayList;

import ir.yeksaco.jahesh.MyApp;
import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.content.ContentDetailsResponse;
import ir.yeksaco.jahesh.models.content.ContentsPriceModel;
import ir.yeksaco.jahesh.models.content.ContentsPriceResponse;
import ir.yeksaco.jahesh.models.content.GetMainMenuResponse;
import ir.yeksaco.jahesh.models.content.GetMenuResponse;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.webService.ApiClient;
import ir.yeksaco.jahesh.webService.iterfaces.iwebServicelistener;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ContentService {
    public void GetMainMenu(final iwebServicelistener listener) {
        try {
            final Call<ResponseBase<GetMainMenuResponse>> CallServer = ApiClient.ContentClinet.GetMainMenu(MyApp.ApiToken);
            CallServer.enqueue(new Callback<ResponseBase<GetMainMenuResponse>>() {
                @Override
                public void onResponse(Response<ResponseBase<GetMainMenuResponse>> response, Retrofit retrofit) {
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
    public void MenuByParentId(final iwebServicelistener listener,int ParentId) {
        try {
            final Call<ResponseBase<ArrayList<GetMenuResponse>>> CallServer = ApiClient.ContentClinet.MenuByParentId(MyApp.ApiToken,ParentId);
            CallServer.enqueue(new Callback<ResponseBase<ArrayList<GetMenuResponse>>>() {
                @Override
                public void onResponse(Response<ResponseBase<ArrayList<GetMenuResponse>>> response, Retrofit retrofit) {
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
    public void ContentById(final iwebServicelistener listener,int Id) {
        try {
            final Call<ResponseBase<ContentDetailsResponse>> CallServer = ApiClient.ContentClinet.ContentById(MyApp.ApiToken,Id);
            CallServer.enqueue(new Callback<ResponseBase<ContentDetailsResponse>>() {
                @Override
                public void onResponse(Response<ResponseBase<ContentDetailsResponse>> response, Retrofit retrofit) {

                    if (response.isSuccess() ) {
                        Log.e("jaheshTag","isSuccess : " + Id);
                        if(response.body().IsSuccess) {
                            listener.OnSuccess(response.body());
                        }
                        else
                        {
                            listener.OnFailed(FailType.Api, response.body().Message);
                        }
                    } else {

                        listener.OnFailed(FailType.Api, response.errorBody().toString());

                        Log.e("jaheshTag","OnSuccess faile : "+ Id + "  " + response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("jaheshTag","onFailure faile : "+ Id + "  "  + t.toString());
                    listener.OnFailed(FailType.Connection, t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.e("jaheshTag","onFailure faile : " +  ex.getMessage());
            listener.OnFailed(FailType.Exception, ex.getMessage());
        }
    }
    public void ContentsPriceById(final iwebServicelistener listener, ContentsPriceModel model) {
        try {
            final Call<ResponseBase<ArrayList<ContentsPriceResponse>>> CallServer = ApiClient.ContentClinet.ContentsPriceById(MyApp.ApiToken,model);
            CallServer.enqueue(new Callback<ResponseBase<ArrayList<ContentsPriceResponse>>>() {
                @Override
                public void onResponse(Response<ResponseBase<ArrayList<ContentsPriceResponse>>> response, Retrofit retrofit) {
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

}
