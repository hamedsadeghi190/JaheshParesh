package ir.yeksaco.jahesh.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.nex3z.notificationbadge.NotificationBadge;

import ir.yeksaco.jahesh.MainActivity;
import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.adaptors.ContentFilesAdaptor;
import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.buy.basket.BasketContentModel;
import ir.yeksaco.jahesh.models.buy.basket.BasketModel;
import ir.yeksaco.jahesh.models.content.ContentDetailsResponse;
import ir.yeksaco.jahesh.models.content.FileUrlList;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.webService.iterfaces.iwebServicelistener;
import ir.yeksaco.jahesh.webService.services.ContentService;

public class ContentShowActivity extends AppCompatActivity {
    private ContentService contentService;
    public String title;
    public int contentId;
    TextView tv_title, tv_amount, tv_content, btn_add_to_basket, tv_basket, tv_desc;
    LinearLayout panel_data, panel_loading;
    CardView card_buy_action, card_ghemat, card_files;
    NotificationBadge nbadge;
    WebView wbv_content;
    ImageView btnBack;
    ContentDetailsResponse data;
    boolean Exist = false;
    private RecyclerView fileRecyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_show);
        getSupportActionBar().hide();
        RemoveStausBar();
        initial();
        BindControls();
        setloadingMode();
        LoadData(contentId);

    }

    private void initial() {
        nbadge = findViewById(R.id.nbadge);
        panel_data = findViewById(R.id.panel_data);
        panel_loading = findViewById(R.id.panel_loading);
        card_buy_action = findViewById(R.id.card_buy_action);
        card_files = findViewById(R.id.card_files);
        card_ghemat = findViewById(R.id.card_ghemat);


        contentService = new ContentService();
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        contentId = bundle.getInt("contentId");
    }

    private void setloadingMode() {
        panel_loading.setVisibility(View.VISIBLE);
        panel_data.setVisibility(View.GONE);
        card_files.setVisibility(View.GONE);
        card_ghemat.setVisibility(View.GONE);
        card_buy_action.setVisibility(View.GONE);
    }

    private void setdataLoadedMode() {
        panel_loading.setVisibility(View.GONE);
        panel_data.setVisibility(View.VISIBLE);
        card_files.setVisibility(View.VISIBLE);
        card_ghemat.setVisibility(View.VISIBLE);
        card_buy_action.setVisibility(View.VISIBLE);
    }

    private void LoadData(int contentId) {
        iwebServicelistener listener = new iwebServicelistener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void OnSuccess(Object response) {
                setdataLoadedMode();
                data = ((ResponseBase<ContentDetailsResponse>) response).Data;

                adapter = new ContentFilesAdaptor(data.fileUrlList, data.getPurchased());
                fileRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                if (data.getPurchased()) {
                    card_buy_action.setVisibility(View.GONE);
                } else {
                    card_buy_action.setVisibility(View.VISIBLE);
                }

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                String basketString = sharedPreferences.getString("Basket", "");

                Log.d("jaheshTag", basketString);
                if (!basketString.isEmpty()) {

                    Gson gson = new Gson();
                    BasketModel basketData = gson.fromJson(basketString, BasketModel.class);
                    nbadge.setNumber(basketData.getContents().size());

                    if (data != null && basketData.Exist(data.id)) {
                        btn_add_to_basket.setText("حذف از سبد خرید");
                        Exist = true;
                    }
                }

                if (data.isFree()) {
                    tv_amount.setText("قیمت : " + " رايگان ");
                    card_buy_action.setVisibility(View.GONE);
                }
               else
                {
                    tv_amount.setText("قیمت : " + data.price + " ریال ");
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tv_content.setText(Html.fromHtml(data.content, Html.FROM_HTML_MODE_COMPACT));
                    tv_desc.setText(Html.fromHtml(data.getDescription(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tv_content.setText(Html.fromHtml(data.content));
                    tv_desc.setText(Html.fromHtml(data.getDescription()));
                }

                panel_loading.setVisibility(View.GONE);
                panel_data.setVisibility(View.VISIBLE);
            }

            @Override
            public void OnFailed(FailType type, String message) {

                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
        tv_desc = findViewById(R.id.tv_desc);
        btn_add_to_basket = findViewById(R.id.btn_add_to_basket);
        tv_title.setText(title);
        this.fileRecyclerView = findViewById(R.id.rcv_content_files);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.fileRecyclerView.setLayoutManager(mLayoutManager);

        ir.yeksaco.jahesh.ui.activities.ItemClickSupport.addTo(fileRecyclerView).setOnItemClickListener(
                new ir.yeksaco.jahesh.ui.activities.ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        if (data.getPurchased() || data.fileUrlList.get(position).isFree() ) {
                            FileUrlList content = data.fileUrlList.get(position);

                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(content.FileUrl));
                            startActivity(intent);
                        }
                    }
                }
        );

        tv_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ContentShowActivity.this, MainActivity.class);
                myIntent.putExtra("target", "basket");
                finishAffinity();
                startActivity(myIntent);
            }
        });
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
                    basketData.removeContentById(data.id);
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
