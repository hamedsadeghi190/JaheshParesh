package ir.yeksaco.jahesh.webService.services;

import java.util.ArrayList;

import ir.yeksaco.jahesh.MyApp;
import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.models.users.SendCodeRequest;
import ir.yeksaco.jahesh.models.users.SendCodeResponse;
import ir.yeksaco.jahesh.models.users.VerifyCodeRequest;
import ir.yeksaco.jahesh.models.users.VerifyCodeResponse;
import ir.yeksaco.jahesh.models.users.buys.BuyHistoryModel;
import ir.yeksaco.jahesh.models.users.token.RefreshTokenResponse;
import ir.yeksaco.jahesh.models.users.token.ResetPasswordRequest;
import ir.yeksaco.jahesh.models.users.token.UpdateFcmTokenRequest;
import ir.yeksaco.jahesh.webService.ApiClient;
import ir.yeksaco.jahesh.webService.iterfaces.iwebServicelistener;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserService {

    public void SendCode(final iwebServicelistener listener, SendCodeRequest requestModel) {
        try {
            final Call<SendCodeResponse> CallServer = ApiClient.UserClinet.SendCode(requestModel);
            CallServer.enqueue(new Callback<SendCodeResponse>() {
                @Override
                public void onResponse(Response<SendCodeResponse> response, Retrofit retrofit) {
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

    public void VerifyCode(final iwebServicelistener listener, VerifyCodeRequest requestModel) {
        try {
            final Call<ResponseBase<VerifyCodeResponse>> CallServer = ApiClient.UserClinet.VerifyCode(requestModel);
            CallServer.enqueue(new Callback<ResponseBase<VerifyCodeResponse>>() {
                @Override
                public void onResponse(Response<ResponseBase<VerifyCodeResponse>> response, Retrofit retrofit) {
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

    public void CheckUserAuthenticated(final iwebServicelistener listener ) {
        try {
            final Call<ResponseBase<RefreshTokenResponse>> CallServer = ApiClient.UserClinet.CheckUserAuthenticated(MyApp.ApiToken);
            CallServer.enqueue(new Callback<ResponseBase<RefreshTokenResponse>>() {
                @Override
                public void onResponse(Response<ResponseBase<RefreshTokenResponse>> response, Retrofit retrofit) {
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
    public void RefreshToken(final iwebServicelistener listener ,String refreshToken) {
        try {
            final Call<ResponseBase<RefreshTokenResponse>> CallServer = ApiClient.UserClinet.RefreshToken(MyApp.ApiToken,refreshToken);
            CallServer.enqueue(new Callback<ResponseBase<RefreshTokenResponse>>() {
                @Override
                public void onResponse(Response<ResponseBase<RefreshTokenResponse>> response, Retrofit retrofit) {
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

    public void ResetPassword(final iwebServicelistener listener, ResetPasswordRequest requestModel) {
        try {
            final Call<ResponseBase<String>> CallServer = ApiClient.UserClinet.ResetPassword(MyApp.ApiToken,requestModel);
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
                    listener.OnFailed(FailType.Connection,t.getMessage());
                }
            });
        } catch (Exception ex) {
            listener.OnFailed(FailType.Exception, ex.getMessage());
        }
    }

    public void UpdateFCM (final iwebServicelistener listener, UpdateFcmTokenRequest requestModel) {
        try {
            final Call<ResponseBase<String>> CallServer = ApiClient.UserClinet.UpdateFCM(MyApp.ApiToken,requestModel);
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
                    listener.OnFailed(FailType.Connection,t.getMessage());
                }
            });
        } catch (Exception ex) {
            listener.OnFailed(FailType.Exception, ex.getMessage());
        }
    }

    public void BuyHistory (final iwebServicelistener listener ) {
        try {
            final Call<ResponseBase<ArrayList<BuyHistoryModel>>> CallServer = ApiClient.UserClinet.BuyHistory(MyApp.ApiToken);
            CallServer.enqueue(new Callback<ResponseBase<ArrayList<BuyHistoryModel>>>() {
                @Override
                public void onResponse(Response<ResponseBase<ArrayList<BuyHistoryModel>>> response, Retrofit retrofit) {
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
