package ir.yeksaco.jahesh.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ir.yeksaco.jahesh.MainActivity;
import ir.yeksaco.jahesh.Messages;
import ir.yeksaco.jahesh.MyApp;
import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.app.VersionHistoryResponse;
import ir.yeksaco.jahesh.models.content.GetMainMenuResponse;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.models.users.VerifyCodeResponse;
import ir.yeksaco.jahesh.webService.iterfaces.iwebServicelistener;
import ir.yeksaco.jahesh.webService.services.AppService;
import ir.yeksaco.jahesh.webService.services.ContentService;



public class SplashScreenActivity extends AppCompatActivity {
    final Handler handler = new Handler();
    AppService appService;
    ContentService contentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Initial();
        RemoveStausBar();
        CheckVersion();
    }

    private void Initial() {
        appService = new AppService();
        contentService = new ContentService();
    }
    private void RemoveStausBar() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.login_statusBar));
    }

    private void CheckVersion() {
        iwebServicelistener listener = new iwebServicelistener() {
            @Override
            public void OnSuccess(Object response) {
                List<VersionHistoryResponse> historyResponse = ((ResponseBase<List<VersionHistoryResponse>>) response).Data;
                ArrayList<VersionHistoryResponse> historyResponses ;

                if (historyResponse.size() == 0) {
                    LoadToken();
                    Toast.makeText(getApplicationContext()," update nemikhay",Toast.LENGTH_LONG).show();
                } else if (historyResponse.stream().filter(x -> x.IsActive == true).count() > 0) {
                    Log.i("jaheshTag", "bayad update beshi");
                    Toast.makeText(getApplicationContext(),"bayad update beshi",Toast.LENGTH_LONG).show();
                    LoadToken();
                }
            }

            @Override
            public void OnFailed(FailType type, String message) {
                Log.e("jaheshTag", message);
            }
        };
        appService.CheckVersion(listener, Messages.CodeVersion);
    }

    private void LoadToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String info = sharedPreferences.getString("Token", "");

        if (info.isEmpty()) {
            Intent myIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(myIntent);
            finish();
        } else {
            Gson gson = new Gson();
            VerifyCodeResponse User = gson.fromJson(info, VerifyCodeResponse.class);
            MyApp.ApiToken = User.Token;
            Toast.makeText(getApplicationContext(),"LoadMainMenu",Toast.LENGTH_LONG).show();
            LoadMainMenu();
        }
    }
    private void LoadMainMenu() {
        iwebServicelistener listener = new iwebServicelistener() {
            @Override
            public void OnSuccess(Object response) {
                GetMainMenuResponse historyResponse = ((ResponseBase<GetMainMenuResponse>) response).Data;
                MyApp.MainPageData = historyResponse;
                Intent myIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }

            @Override
            public void OnFailed(FailType type, String message) {
                Log.e("jaheshTag", message);
                Toast.makeText(getApplicationContext(),"LoadMainMenu Error :" + message,Toast.LENGTH_LONG).show();
            }
        };
        contentService.GetMainMenu(listener);
    }

}