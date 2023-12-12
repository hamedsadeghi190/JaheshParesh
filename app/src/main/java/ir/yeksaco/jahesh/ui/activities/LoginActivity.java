package ir.yeksaco.jahesh.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.users.SendCodeRequest;
import ir.yeksaco.jahesh.utility.AppSignatureHelper;
import ir.yeksaco.jahesh.webService.iterfaces.iwebServicelistener;
import ir.yeksaco.jahesh.webService.services.UserService;

public class LoginActivity extends AppCompatActivity {

    final Handler handler = new Handler();
    ConstraintLayout clyWating;
    TextInputEditText edtMobile;
    MaterialButton btnSendCode;
    UserService userService;
    String SingKey;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clyWating.setVisibility(View.GONE);
        btnSendCode.setCheckable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(getApplicationContext());
        SingKey = appSignatureHelper.getAppSignatures().get(0);
        Log.d("jaheshTag", "onCreate: " + appSignatureHelper.getAppSignatures().get(0));  // This will give you the key.
        userService = new UserService();
        RemoveStausBar();
        bindControls();
        bindEvents();
    }

    private void RemoveStausBar()
    {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
    }
    private void bindControls() {
        clyWating = findViewById(R.id.cly_wating);
        btnSendCode = findViewById(R.id.btn_send_code);
        edtMobile = findViewById(R.id.edt_mobile);
    }

    private void bindEvents() {
        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile =  edtMobile.getText().toString();
                if( mobile.length()!=11)
                {
                   // Toast.makeText(getApplicationContext(), Messages.MobileInvalid, Toast.LENGTH_LONG).show();
                    return;
                }


                clyWating.setVisibility(View.VISIBLE);
                clyWating.bringToFront();
                btnSendCode.setCheckable(false);

                iwebServicelistener listener = new iwebServicelistener() {
                    @Override
                    public void OnSuccess(Object response) {

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent myIntent = new Intent(LoginActivity.this, VerifyLoginActivity.class);
                                myIntent.putExtra("mobile",mobile);
                                startActivity(myIntent);

                                clyWating.setVisibility(View.GONE);
                                btnSendCode.setCheckable(true);
                            }
                        }, 1000);
                    }

                    @Override
                    public void OnFailed(FailType type, String message) {
                        Log.e("jaheshTag",message);
                    }
                };
                userService.SendCode(listener,new SendCodeRequest(mobile,SingKey));

            }
        });
    }
}