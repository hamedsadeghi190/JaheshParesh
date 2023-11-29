package ir.yeksaco.jahesh;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentCallBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_call_back);
        //Toast.makeText(getApplicationContext(),"callbackresid",Toast.LENGTH_LONG).show();
        Intent in = getIntent();
        Uri data = in.getData();
        // دو سطر بالا دیتاهای ارسال شده به اکتیویتی را دریافت مینماید و به data کپی میکند.
        if (data != null) {

        // در سطر زیر عبارت "varchar://" را از داده های دریافتی حذف مینماییم این عبارت همان بخش scheme موجود در منیفست می باشد.
            String rdata = data.toString().replace("varchar://", "");

        // حال rdata شامل داده هاییست که بعد intent:// در قسمت آموزش سمت وب فرستاده شده است می باشد.
        //برای این آموزش فقط عدد ارسال کرده ایم. و حالت های مختلف را با توست هندل میکنیم.
            if (rdata.equals("1")) {
                Toast.makeText(getBaseContext(), "موفقیت", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), "عدم موفقیت", Toast.LENGTH_LONG).show();
            }
        }


    }
}