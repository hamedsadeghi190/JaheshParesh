package ir.yeksaco.jahesh.webService;

import java.util.concurrent.TimeUnit;

import ir.yeksaco.jahesh.MyApp;
import ir.yeksaco.jahesh.webService.iterfaces.*;
import retrofit.GsonConverterFactory;

public class ApiClient {
    private static com.squareup.okhttp.OkHttpClient okHttpClient = new com.squareup.okhttp.OkHttpClient();

    private static com.squareup.okhttp.OkHttpClient getOkHttpClient() {
        okHttpClient.setReadTimeout(1, TimeUnit.MINUTES);
        okHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);
        return okHttpClient;
    }

    public static retrofit.Retrofit retrofit = new retrofit.Retrofit.Builder()
            .baseUrl(MyApp.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build();

    public static IUser UserClinet = retrofit.create(IUser.class);
    public static IApp AppClinet = retrofit.create(IApp.class);
    public static IContents ContentClinet = retrofit.create(IContents.class);
    public static IBuy BuyClinet = retrofit.create(IBuy.class);

}
