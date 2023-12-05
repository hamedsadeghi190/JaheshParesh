package ir.yeksaco.jahesh.ui.fragments.books;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;

import java.util.ArrayList;

import ir.yeksaco.jahesh.MyApp;
import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.adaptors.ContentBuyedAdaptor;
import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.content.GetMenuResponse;
import ir.yeksaco.jahesh.models.content.LoadedData;
import ir.yeksaco.jahesh.models.content.PurchasedContent;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.ui.activities.ContentListActivity;
import ir.yeksaco.jahesh.ui.activities.ContentShowActivity;
import ir.yeksaco.jahesh.webService.iterfaces.iwebServicelistener;
import ir.yeksaco.jahesh.webService.services.BuyService;


public class BooksFragment extends Fragment {

    private RecyclerView contentsRecyclerView;
    private LinearLayout ly_nodata;
    private BuyService buyService;
    private RecyclerView.Adapter adapter;
    private ArrayList<PurchasedContent> data;
    private LottieAnimationView anw_no_data, anw_loading;

    public BooksFragment() {
        buyService = new BuyService();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.anw_no_data = view.findViewById(R.id.anw_no_data);
        this.anw_loading = view.findViewById(R.id.anw_loading);
        this.ly_nodata = view.findViewById(R.id.ly_nodata);
        this.contentsRecyclerView = view.findViewById(R.id.rcv_buyed_content);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        this.contentsRecyclerView.setLayoutManager(mLayoutManager);
        ir.yeksaco.jahesh.ui.activities.ItemClickSupport.addTo(contentsRecyclerView).setOnItemClickListener(
                new ir.yeksaco.jahesh.ui.activities.ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        PurchasedContent content = data.get(position);

                        Intent myIntent = new Intent(getContext(), ContentShowActivity.class);
                        myIntent.putExtra("title", content.getTitle());
                        myIntent.putExtra("contentId", content.getTreeId());
                        startActivity(myIntent);
                    }
                }
        );
        LoadData();
    }

    private void LoadData() {
        contentsRecyclerView.setVisibility(View.GONE);
        ly_nodata.setVisibility(View.VISIBLE);
        anw_loading.setVisibility(View.VISIBLE);
        anw_no_data.setVisibility(View.GONE);
        iwebServicelistener listener = new iwebServicelistener() {
            @Override
            public void OnSuccess(Object response) {
                data = ((ResponseBase<ArrayList<PurchasedContent>>) response).Data;
                Gson gson = new Gson();
                Log.e("jaheshTag", MyApp.ApiToken);
                Log.e("jaheshTag", gson.toJson(response));

                bindAdaptor();
            }

            @Override
            public void OnFailed(FailType type, String message) {
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
                Log.e("jaheshTag", message);
            }
        };
        buyService.PurchasedContent(listener);
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
            adapter = new ContentBuyedAdaptor(data);
            contentsRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}