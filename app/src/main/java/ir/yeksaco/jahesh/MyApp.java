package ir.yeksaco.jahesh;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.view.LayoutInflater;

import java.text.DecimalFormat;

import ir.yeksaco.jahesh.models.content.GetMainMenuResponse;

public class MyApp extends Application {


    public static Context context;
    public static LayoutInflater Inflator;
    public static String ApiToken = null;
    public static GetMainMenuResponse MainPageData ;
    public static String BaseUrl = "http://jpapi.yeksaco.ir/JP/App/";
    //public static String BaseUrl = "http://192.168.1.102:366/api/";
    public static String Tag = "sfp";

    @Override
    public void onCreate() {
        super.onCreate();
//        MultiDex.install(getApplicationContext());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // Required initialization logic here!
        context = getApplicationContext();
        Inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApp.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String persianNumbers(String num) {
        try {
            char[] persianNumbers = {'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < num.length(); i++) {
                if (Character.isDigit(num.charAt(i))) {
                    builder.append(persianNumbers[(int) (num.charAt(i)) - 48]);
                } else {
                    builder.append(num.charAt(i));
                }
            }
            return builder.toString();
        } catch (Exception e) {
            return num;
        }
    }

    public static String formatPrice(String num) {
        if (num == null || num.isEmpty())
            return "";
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(Float.valueOf(num));
    }

    public static String getApiToken() {
        return "Bearer " + ApiToken;
    }


}