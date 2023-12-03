package ir.yeksaco.jahesh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class PaymentCallBackActivity extends AppCompatActivity {

    private Button btnContinue, btnContinue1;
    private ConstraintLayout ctl_failed, ctl_success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_call_back);
        RemoveStausBar();
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue1 = findViewById(R.id.btnContinue1);
        ctl_failed = findViewById(R.id.ctl_failed);
        ctl_success = findViewById(R.id.ctl_success);


        //Toast.makeText(getApplicationContext(),"callbackresid",Toast.LENGTH_LONG).show();
        Intent in = getIntent();
        Uri data = in.getData();
        // دو سطر بالا دیتاهای ارسال شده به اکتیویتی را دریافت مینماید و به data کپی میکند.
        if (data != null) {


            // در سطر زیر عبارت "varchar://" را از داده های دریافتی حذف مینماییم این عبارت همان بخش scheme موجود در منیفست می باشد.
            String rdata = data.toString().replace("varchar://", "");

            Log.i("jahesh tag", rdata);

            rdata = rdata.replace("http://jpapp.yeksaco.ir/payment?", "");
            String[] parts = rdata.split("&");

            // حال rdata شامل داده هاییست که بعد intent:// در قسمت آموزش سمت وب فرستاده شده است می باشد.
            //برای این آموزش فقط عدد ارسال کرده ایم. و حالت های مختلف را با توست هندل میکنیم.
            if (parts[0].split("=")[1].equals("2")) {
                ctl_success.setVisibility(View.VISIBLE);
                ctl_failed.setVisibility(View.GONE);

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.remove("Basket");
                myEdit.apply();
                myEdit.commit();

            } else {
                ctl_success.setVisibility(View.GONE);
                ctl_failed.setVisibility(View.VISIBLE);
            }
        }

        btnContinue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void RemoveStausBar() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
    }
}