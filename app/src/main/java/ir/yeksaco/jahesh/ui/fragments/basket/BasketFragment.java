package ir.yeksaco.jahesh.ui.fragments.basket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import ir.yeksaco.jahesh.MyApp;
import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.adaptors.BasketAdaptor;
import ir.yeksaco.jahesh.adaptors.iBasketItemClickListner;
import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.buy.CreateFactorModel;
import ir.yeksaco.jahesh.models.buy.CreateFactorResponse;
import ir.yeksaco.jahesh.models.buy.basket.BasketModel;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.webService.iterfaces.iwebServicelistener;
import ir.yeksaco.jahesh.webService.services.BuyService;


public class BasketFragment extends Fragment {
    public static final int MODE_PRIVATE = 0x0000;
    private RecyclerView contentsRecyclerView;
    public  CardView card_buy_action;
    private BuyService buyService;
    private TextView btn_payment;
    private RecyclerView.Adapter adapter;
    CreateFactorModel factor;
    private BasketModel basketData ;
    private iBasketItemClickListner listner;
    public BasketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.card_buy_action = view.findViewById(R.id.card_buy_action);

        basketData = new BasketModel();
        buyService = new BuyService();
        this.contentsRecyclerView = view.findViewById(R.id.rcv_basket_contnets);
        this.btn_payment = view.findViewById(R.id.btn_payment);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        this.contentsRecyclerView.setLayoutManager(mLayoutManager);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String basketString = sharedPreferences.getString("Basket", "");


        if (!basketString.isEmpty()) {
            Gson gson = new Gson();
            basketData = gson.fromJson(basketString, BasketModel.class);

            factor = new CreateFactorModel();
            for (int i = 0; i < basketData.getContents().size(); i++) {
                factor.Ids.add(basketData.getContents().get(i).getContentId());
            }

            this.btn_payment.setText(getString(R.string.tasviye) + "(" + basketData.getTotal() + " ریال" + ")");
        }
        listner = new iBasketItemClickListner() {
            @Override
            public void OnDeleted(int position) {
               basketData.Contents.remove(position);

                Gson gson = new Gson();
                String savedInfo = gson.toJson(basketData);
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("Basket", savedInfo);
                myEdit.apply();
                myEdit.commit();
                adapter.notifyDataSetChanged();
                btn_payment.setText(getString(R.string.tasviye) + "(" + basketData.getTotal() + " ریال" + ")");
                if(basketData.Contents.size()==0)
                {
                    card_buy_action.setVisibility(View.GONE);
                }
            }
        };
        adapter = new BasketAdaptor(basketData.Contents,listner);
        contentsRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if(basketData.Contents.size()==0)
        {
            card_buy_action.setVisibility(View.GONE);
        }


        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iwebServicelistener listener = new iwebServicelistener() {
                    @Override
                    public void OnSuccess(Object response) {
                        ResponseBase<CreateFactorResponse> result = (ResponseBase<CreateFactorResponse>) response;

                        if (result.IsSuccess) {
                            //Toast.makeText(MyApp.context,result.Message + "factor Id" + result.Data.FactorId, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.Data.PaymentUrl));
                            startActivity(intent);
                        } else {
                            Toast.makeText(MyApp.context, result.Message, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void OnFailed(FailType type, String message) {
                        Log.e("jaheshTag", message);
                    }
                };

                Gson gson = new Gson();
                String factorss = gson.toJson(factor);
                Log.i("jaheshTag", factorss);
                buyService.CreateFactor(listener, factor);
            }
        });
    }
}