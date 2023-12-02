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
import com.nex3z.notificationbadge.NotificationBadge;

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
    public String title;
    public int contentId;
    TextView tv_title, tv_amount, tv_content, btn_add_to_basket,tv_basket;
    NotificationBadge nbadge;
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
        nbadge = findViewById(R.id.nbadge);
        contentService = new ContentService();
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        contentId = bundle.getInt("contentId");

        BindControls();
        LoadData(contentId);
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String basketString = sharedPreferences.getString("Basket", "");

        Log.d("jaheshTag",basketString);
        if (!basketString.isEmpty()) {
            Gson gson = new Gson();
            BasketModel basketData = gson.fromJson(basketString, BasketModel.class);
            nbadge.setNumber(basketData.getContents().size());
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
        tv_basket = findViewById(R.id.tv_basket);
        tv_amount = findViewById(R.id.tv_amount);
        // wbv_content = findViewById(R.id.wbv_content);
        tv_content = findViewById(R.id.tv_content);
        btn_add_to_basket = findViewById(R.id.btn_add_to_basket);
        tv_title.setText(title);

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
                nbadge.setNumber(basketData.getContents().size());
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
        window.setStatusBarColor(this.getResources().getColor(R.color.main_color));
    }
}
