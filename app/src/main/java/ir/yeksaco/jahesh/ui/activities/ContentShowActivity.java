package ir.yeksaco.jahesh.ui.activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.buy.basket.BasketContentModel;
import ir.yeksaco.jahesh.models.content.ContentDetailsResponse;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.webService.iterfaces.iwebServicelistener;
import ir.yeksaco.jahesh.webService.services.ContentService;
import ir.yeksaco.jahesh.models.buy.basket.*;

public class ContentShowActivity extends AppCompatActivity {
    private ContentService contentService;
    public String parentName;
    public int contentId;
    TextView tv_title, tv_content_title, tv_amount, tv_content, btn_add_to_basket;
    WebView wbv_content;
    ImageView btnBack;
    ContentDetailsResponse data;
    boolean Exist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_show);
        getSupportActionBar().hide();
        RemoveStausBar();
        contentService = new ContentService();
        Bundle bundle = getIntent().getExtras();
        parentName = bundle.getString("parentName");
        contentId = bundle.getInt("contentId");

        BindControls();
        LoadData(contentId);
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String basketString = sharedPreferences.getString("Basket", "");

        Log.d("jaheshTag",basketString);
        if (!basketString.isEmpty()) {
            Gson gson = new Gson();
            BasketModel basketData = gson.fromJson(basketString, BasketModel.class);
            if (basketData.Exist(contentId)) {
                btn_add_to_basket.setText("حذف از سبد خرید");
                Exist = true;
            }
        }
    }

    private void LoadData(int contentId) {
        iwebServicelistener listener = new iwebServicelistener() {
            @Override
            public void OnSuccess(Object response) {
                data = ((ResponseBase<ContentDetailsResponse>) response).Data;
                tv_content_title.setText(data.title);
                tv_content.setText(data.content);
                tv_amount.setText("قیمت : " + data.price + " تومان ");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tv_content.setText(Html.fromHtml(data.content, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tv_content.setText(Html.fromHtml(data.content));
                }



            }

            @Override
            public void OnFailed(FailType type, String message) {
                Log.e("jaheshTag", message);
            }
        };
        contentService.ContentById(listener, contentId);
    }

    private void BindControls() {
        btnBack = findViewById(R.id.btn_back_to_list);
        tv_title = findViewById(R.id.tv_title);
        tv_amount = findViewById(R.id.tv_amount);
        tv_content_title = findViewById(R.id.tv_content_title);
        // wbv_content = findViewById(R.id.wbv_content);
        tv_content = findViewById(R.id.tv_content);
        btn_add_to_basket = findViewById(R.id.btn_add_to_basket);
        tv_title.setText(parentName);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_add_to_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                String basketString = sharedPreferences.getString("Basket", "");

                BasketModel basketData;
                Gson gson = new Gson();
                if (basketString.isEmpty()) {
                    basketData = new BasketModel();
                } else {
                    basketData = gson.fromJson(basketString, BasketModel.class);
                }
                if (!Exist) {
                    basketData.addContent(new BasketContentModel(data.id, data.title, data.price));
                    btn_add_to_basket.setText("حذف از سبد خرید");
                    Exist = true;
                } else {
                    basketData.removeContent(new BasketContentModel(data.id, data.title, data.price));
                    btn_add_to_basket.setText("افزودن از سبد خرید");
                    Exist = false;
                }

                String savedInfo = gson.toJson(basketData);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("Basket", savedInfo);
                myEdit.apply();
                myEdit.commit();

            }
        });

    }

    private void RemoveStausBar() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.purple_700));
    }
}
