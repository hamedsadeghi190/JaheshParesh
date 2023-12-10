package ir.yeksaco.jahesh.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.adaptors.ContentAdaptor;
import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.content.GetMenuResponse;
import ir.yeksaco.jahesh.models.content.LoadedData;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.webService.iterfaces.iwebServicelistener;
import ir.yeksaco.jahesh.webService.services.ContentService;

public class ContentListActivity extends AppCompatActivity {

    private RecyclerView contentsRecyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayout ly_nodata;
    private LottieAnimationView anw_no_data,anw_loading;
    private TextView tv_parent;
    private ImageView btn_back_to_main,btn_up_level;

    private ContentService contentService;
    public String parentName;
    ArrayList<LoadedData> cached;
    ArrayList<GetMenuResponse> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list);
        getSupportActionBar().hide();
        RemoveStausBar();
        initial();

        cached = new ArrayList<LoadedData>();
        Bundle bundle = getIntent().getExtras();
        parentName = bundle.getString("parentName");
        LoadData(bundle.getInt("contentId"));
        bindControls();

    }

    private void bindAdaptor() {
        if (data.size() == 0) {
            ly_nodata.setVisibility(View.VISIBLE);
            contentsRecyclerView.setVisibility(View.GONE);
            anw_loading.setVisibility(View.GONE);
            anw_no_data.setVisibility(View.VISIBLE);

        } else {
            contentsRecyclerView.setVisibility(View.VISIBLE);
            ly_nodata.setVisibility(View.GONE);
            adapter = new ContentAdaptor(data);
            contentsRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void bindControls() {
        btn_back_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_up_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cached.size() <= 1) {
                    finish();
                } else {
                    cached.remove(cached.size() - 1);
                    data = cached.get(cached.size() - 1).Data;
                    bindAdaptor();
                }
            }
        });

        ir.yeksaco.jahesh.ui.activities.ItemClickSupport.addTo(contentsRecyclerView).setOnItemClickListener(
                new ir.yeksaco.jahesh.ui.activities.ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        GetMenuResponse content = data.get(position);

                        if (content.getLevelKind().equals("Menu")) {
                            parentName = content.getTitle();
                            LoadData(content.getId());
                        } else {
                            Intent myIntent = new Intent(ContentListActivity.this, ContentShowActivity.class);
                            myIntent.putExtra("title", content.getTitle());
                            myIntent.putExtra("contentId", content.getId());
                            startActivity(myIntent);
                        }
                    }
                }
        );
    }

    private void initial() {

        contentService = new ContentService();

        this.anw_no_data = findViewById(R.id.anw_no_data);
        this.anw_loading = findViewById(R.id.anw_loading);
        this.ly_nodata = findViewById(R.id.ly_nodata);

        this.btn_back_to_main = findViewById(R.id.btn_back_to_main);
        this.btn_up_level = findViewById(R.id.btn_up_level);
        this.contentsRecyclerView = findViewById(R.id.rcv_contents);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.contentsRecyclerView.setLayoutManager(mLayoutManager);

        this.tv_parent = findViewById(R.id.tv_parent);
        tv_parent.setText(parentName);
    }

    private void RemoveStausBar() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.main_color));
    }

    private void LoadData(int contentId) {
        contentsRecyclerView.setVisibility(View.GONE);
        ly_nodata.setVisibility(View.VISIBLE);
        anw_loading.setVisibility(View.VISIBLE);
        anw_no_data.setVisibility(View.GONE);
        iwebServicelistener listener = new iwebServicelistener() {
            @Override
            public void OnSuccess(Object response) {
                data = ((ResponseBase<ArrayList<GetMenuResponse>>) response).Data;
                LoadedData newData = new LoadedData();

                newData.ContentId = contentId;
                newData.Data.addAll(data);
                cached.add(newData);
                bindAdaptor();
                tv_parent.setText(parentName);
            }

            @Override
            public void OnFailed(FailType type, String message) {
                Log.e("jaheshTag", message);
            }
        };
        contentService.MenuByParentId(listener, contentId);
    }

    @Override
    public void onBackPressed() {

        if (cached.size() <= 1) {
            super.onBackPressed();
            finish();
        } else {
            cached.remove(cached.size() - 1);
            data = cached.get(cached.size() - 1).Data;

            bindAdaptor();
        }
    }
}