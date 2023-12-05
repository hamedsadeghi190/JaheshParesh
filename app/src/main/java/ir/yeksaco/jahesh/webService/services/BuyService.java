package ir.yeksaco.jahesh.webService.services;

import android.util.Log;

import java.util.ArrayList;

import ir.yeksaco.jahesh.MyApp;
import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.buy.CheckDiscountRequest;
import ir.yeksaco.jahesh.models.buy.CreateFactorModel;
import ir.yeksaco.jahesh.models.buy.CreateFactorResponse;
import ir.yeksaco.jahesh.models.buy.FactorStatusResponse;
import ir.yeksaco.jahesh.models.content.PurchasedContent;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.webService.ApiClient;
import ir.yeksaco.jahesh.webService.iterfaces.iwebServicelistener;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class BuyService {
    public void CreateFactor(final iwebServicelistener listener, CreateFactorModel data) {
        try {
            final Call<ResponseBase<CreateFactorResponse>> CallServer = ApiClient.BuyClinet.CreateFactor(MyApp.ApiToken, data);
            CallServer.enqueue(new Callback<ResponseBase<CreateFactorResponse>>() {
                @Override
                public void onResponse(Response<ResponseBase<CreateFactorResponse>> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        listener.OnSuccess(response.body());
                    } else {
                        listener.OnFailed(FailType.Api, response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("jaheshTag", "onFailure faile : " + t.toString());
                    listener.OnFailed(FailType.Connection, t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.e("jaheshTag", "onFailure faile : " + ex.getMessage());
            listener.OnFailed(FailType.Exception, ex.getMessage());
        }
    }

    public void PurchasedContent(final iwebServicelistener listener) {
        try {
            final Call<ResponseBase<ArrayList<PurchasedContent>>> CallServer = ApiClient.BuyClinet.PurchasedContent( MyApp.ApiToken);
            CallServer.enqueue(new Callback<ResponseBase<ArrayList<PurchasedContent>>>() {
                @Override
                public void onResponse(Response<ResponseBase<ArrayList<PurchasedContent>>> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        if (response.body().IsSuccess) {
                            listener.OnSuccess(response.body());
                        } else {
                            listener.OnFailed(FailType.Api, response.body().Message);
                        }
                    } else {
                        listener.OnFailed(FailType.Api, response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("jaheshTag", "onFailure faile : " + t.toString());
                    listener.OnFailed(FailType.Connection, t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.e("jaheshTag", "onFailure faile : " + ex.getMessage());
            listener.OnFailed(FailType.Exception, ex.getMessage());
        }
    }

    public void FactorStatus(final iwebServicelistener listener, String factorId) {
        try {
            final Call<ResponseBase<FactorStatusResponse>> CallServer = ApiClient.BuyClinet.FactorStatus(MyApp.ApiToken, factorId);
            CallServer.enqueue(new Callback<ResponseBase<FactorStatusResponse>>() {
                @Override
                public void onResponse(Response<ResponseBase<FactorStatusResponse>> response, Retrofit retrofit) {
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

    public void CheckDiscount(final iwebServicelistener listener, CheckDiscountRequest data) {
        try {
            final Call<ResponseBase<CreateFactorResponse>> CallServer = ApiClient.BuyClinet.CheckDiscount(MyApp.ApiToken, data);
            CallServer.enqueue(new Callback<ResponseBase<CreateFactorResponse>>() {
                @Override
                public void onResponse(Response<ResponseBase<CreateFactorResponse>> response, Retrofit retrofit) {
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

    public void CheckPayment(final iwebServicelistener listener, String factorId) {
        try {
            final Call<ResponseBase<String>> CallServer = ApiClient.BuyClinet.CheckPayment(MyApp.ApiToken, factorId);
            CallServer.enqueue(new Callback<ResponseBase<String>>() {
                @Override
                public void onResponse(Response<ResponseBase<String>> response, Retrofit retrofit) {
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
