package ir.yeksaco.jahesh.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.common.constants.Messages;
import ir.yeksaco.jahesh.common.enums.DeviceType;
import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.models.users.UserDevice;
import ir.yeksaco.jahesh.models.users.VerifyCodeRequest;
import ir.yeksaco.jahesh.models.users.VerifyCodeResponse;
import ir.yeksaco.jahesh.utility.SmsBroadcastReceiver;
import ir.yeksaco.jahesh.webService.iterfaces.iwebServicelistener;
import ir.yeksaco.jahesh.webService.services.UserService;

public class VerifyLoginActivity extends AppCompatActivity {
    MaterialButton btnVerify;
    ProgressBar Prg_remain_time;
    CountDownTimer timer;
    TextView txt_remain_time;
    TextView txt_change_numebr;
    TextView txt_resend_code;
    ImageView img_change_numebr;
    ConstraintLayout clyWating;
    UserService userService;
    String mobile;
    int verifyCode;
    final Handler handler = new Handler();
    SmsBroadcastReceiver smsBroadcastReceiver;
    PinEntryEditText pinEntry;

    private void startSMSRetrieverClient() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        Task<Void> task = client.startSmsRetriever();
        task.addOnSuccessListener(aVoid -> {
            // Successfully started retriever, expect broadcast intent
            // ...
        });
        task.addOnFailureListener(e -> {
            // Failed to start retriever, inspect Exception for more details
            // ...
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_verify_login);
        getSupportActionBar().hide();

        userService = new UserService();

        RemoveStausBar();
        bindControls();
        bindEvents();
        startTimer();

        Bundle bundle = getIntent().getExtras();
        mobile = bundle.getString("mobile");

        startSMSRetrieverClient(); // Already implemented above.
         smsBroadcastReceiver = new SmsBroadcastReceiver();
        this.registerReceiver(smsBroadcastReceiver, new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION));
        smsBroadcastReceiver.init(new SmsBroadcastReceiver.OTPReceiveListener() {
            @Override
            public void onOTPReceived(String otp) {
                // OTP Received
                verifyCode = Integer.parseInt(otp);
                pinEntry.setAnimateText(true);
                pinEntry.setText(otp);
                //Toast.makeText(getApplicationContext(), "On Success : "+otp, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onOTPTimeOut() {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clyWating.setVisibility(View.GONE);
        btnVerify.setCheckable(true);
    }

    private void RemoveStausBar() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.login_statusBar));
    }

    private void startTimer() {
        timer = new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {

                // logic to set the EditText could go here
                long diff = (millisUntilFinished);
                long Minutes = diff / (60 * 1000) % 120;
                long Seconds = diff / 1000 % 60;
                String sec = (Seconds < 10) ? "0" + Seconds : "" + Seconds;

                txt_remain_time.setText(Minutes + ":" + sec);

                long percent = ((millisUntilFinished) * 100) / (120000);
                Log.i("jahesh", "millisUntilFinished: " + millisUntilFinished + " percent : " + ((100) - (int) percent));


                Prg_remain_time.setProgress((100 - (int) percent));
            }

            public void onFinish() {

                handler.post(new Runnable() {
                    public void run() {
                        Prg_remain_time.setProgress(0);
                        txt_resend_code.setVisibility(View.VISIBLE);
                    }
                });
            }

        }.start();
    }

    private void bindControls() {
        clyWating = findViewById(R.id.cly_wating);
        btnVerify = findViewById(R.id.btn_verify);
        Prg_remain_time = findViewById(R.id.Prg_remain_time);
        txt_remain_time = findViewById(R.id.txt_remain_time);
        img_change_numebr = findViewById(R.id.img_change_numebr);
        txt_change_numebr = findViewById(R.id.txt_change_numebr);
        txt_resend_code = findViewById(R.id.txt_resend_code);

        pinEntry = (PinEntryEditText) findViewById(R.id.txt_pin_entry2);
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {

                    if (str.toString().length() == 5) {
                        btnVerify.setEnabled(true);
                        verifyCode = Integer.parseInt(str.toString());
                      //  verify();
                    } else {
                        btnVerify.setEnabled(false);
                    }
                }
            });
        }
    }

    private void bindEvents() {

        txt_resend_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
            }
        });
        img_change_numebr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        txt_change_numebr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verify();

            }
        });
    }

    private void verify() {
        clyWating.setVisibility(View.VISIBLE);
        clyWating.bringToFront();
        btnVerify.setCheckable(false);

        iwebServicelistener listener = new iwebServicelistener() {
            @Override
            public void OnSuccess(Object response) {

                ResponseBase<VerifyCodeResponse> apiRresponse = (ResponseBase<VerifyCodeResponse>) response;
                Gson gson = new Gson();
                String savedInfo2 = gson.toJson(apiRresponse);
                Log.e("jaheshTag", savedInfo2);

                if (apiRresponse.IsSuccess) {

                    VerifyCodeResponse data = (VerifyCodeResponse) apiRresponse.Data;
                    String savedInfo = gson.toJson(data);

                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("Token", savedInfo);
                    myEdit.apply();
                    myEdit.commit();

                    Intent myIntent = new Intent(VerifyLoginActivity.this, SplashScreenActivity.class);
                    finishAffinity();
                    startActivity(myIntent);
                } else {
                    Toast.makeText(getApplicationContext(), apiRresponse.Message, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void OnFailed(FailType type, String message) {
                Log.e("jaheshTag", message);
            }
        };

        VerifyCodeRequest model = new VerifyCodeRequest();
        model.setMobileNumber(mobile);
        model.setVerifyCode(verifyCode);
        model.setUserDevice(getSystemDetail());

        Gson gson = new Gson();
        String savedInfo3 = gson.toJson(model);
        Log.e("jaheshTag", savedInfo3);

        userService.VerifyCode(listener, model);
    }

    @SuppressLint("HardwareIds")
    private UserDevice getSystemDetail() {
        UserDevice result = new UserDevice();
        result.setDeviceType(DeviceType.Android);
        result.setDeviceID(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        result.setManufacture(Build.MANUFACTURER);
        result.setBrand(Build.BRAND);
        result.setModel(Build.MODEL);
        result.setSdkVersion("" + Build.VERSION.SDK_INT);
        result.setAppVersion(Messages.AppVersion);
        result.setCodeVersion(Messages.CodeVersion);
        result.setFireBaseToken("");
        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (smsBroadcastReceiver != null)
            this.unregisterReceiver(smsBroadcastReceiver);
    }
}
