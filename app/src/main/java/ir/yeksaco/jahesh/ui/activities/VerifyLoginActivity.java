package ir.yeksaco.jahesh.ui.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;

import ir.yeksaco.jahesh.Messages;
import ir.yeksaco.jahesh.common.enums.DeviceType;
import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.models.users.*;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ir.yeksaco.jahesh.MainActivity;
import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.webService.services.UserService;
import ir.yeksaco.jahesh.webService.iterfaces.iwebServicelistener;

// dependencies
import android.text.Editable;
import android.text.TextWatcher;

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
    TextInputEditText edt_pin_I, edt_pin_II, edt_pin_III, edt_pin_IV, edt_pin_V;
    String mobile;
    int verifyCode;
    final Handler handler = new Handler();

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
        timer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {

                // logic to set the EditText could go here
                long diff = (millisUntilFinished);
                long Minutes = diff / (60 * 1000) % 120;
                long Seconds = diff / 1000 % 60;
                String sec = (Seconds < 10) ? "0" + Seconds : "" + Seconds;

                txt_remain_time.setText(Minutes + ":" + sec);

                long percent = ((millisUntilFinished) * 100) / (30000);
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

        edt_pin_I = findViewById(R.id.edt_pin_I);
        edt_pin_II = findViewById(R.id.edt_pin_II);
        edt_pin_III = findViewById(R.id.edt_pin_III);
        edt_pin_IV = findViewById(R.id.edt_pin_IV);
        edt_pin_V = findViewById(R.id.edt_pin_V);
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


                clyWating.setVisibility(View.VISIBLE);
                clyWating.bringToFront();
                btnVerify.setCheckable(false);

                iwebServicelistener listener = new iwebServicelistener() {
                    @Override
                    public void OnSuccess(Object response) {

                        ResponseBase<VerifyCodeResponse> apiRresponse = (ResponseBase<VerifyCodeResponse>) response;

                        if (apiRresponse.IsSuccess) {
                            Gson gson = new Gson();
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
                           // Toast.makeText(getApplicationContext(), Messages.MobileInvalid, Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void OnFailed(FailType type, String message) {
                        Log.e("jaheshTag", message);
                    }
                };
                verifyCode = Integer.parseInt(edt_pin_I.getText().toString() + edt_pin_II.getText().toString() + edt_pin_III.getText().toString() + edt_pin_IV.getText().toString() + edt_pin_V.getText().toString());

                VerifyCodeRequest model = new VerifyCodeRequest();
                model.setMobileNumber(mobile);
                model.setVerifyCode(verifyCode);
                model.setUserDevice(getSystemDetail());
                userService.VerifyCode(listener, model);

            }
        });

        edt_pin_I.requestFocus();
        edt_pin_II.setCursorVisible(true);
        edt_pin_III.setCursorVisible(true);
        edt_pin_IV.setCursorVisible(true);
        edt_pin_V.setCursorVisible(true);

        edt_pin_I.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    edt_pin_I.clearFocus();
                    edt_pin_II.requestFocus();
                }
            }
        });


        edt_pin_II.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    edt_pin_II.clearFocus();
                    edt_pin_III.requestFocus();
                } else {
                    edt_pin_II.clearFocus();
                    edt_pin_I.requestFocus();
                }
            }
        });


        edt_pin_III.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    edt_pin_III.clearFocus();
                    edt_pin_IV.requestFocus();
                } else {
                    edt_pin_II.clearFocus();
                    edt_pin_III.requestFocus();
                }
            }
        });

        edt_pin_IV.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    edt_pin_IV.clearFocus();
                    edt_pin_V.requestFocus();
                } else {
                    edt_pin_IV.clearFocus();
                    edt_pin_V.requestFocus();
                }
            }
        });

        edt_pin_I.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {

                } else {
                    edt_pin_V.clearFocus();
                    edt_pin_IV.requestFocus();
                }
            }
        });


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

}
