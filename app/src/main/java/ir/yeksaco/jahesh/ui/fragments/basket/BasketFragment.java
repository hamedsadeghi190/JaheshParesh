package ir.yeksaco.jahesh.ui.fragments.basket;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import ir.yeksaco.jahesh.MyApp;
import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.adaptors.BasketAdaptor;
import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.buy.CreateFactorModel;
import ir.yeksaco.jahesh.models.buy.CreateFactorResponse;
import ir.yeksaco.jahesh.models.buy.basket.BasketModel;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.webService.iterfaces.iwebServicelistener;
import ir.yeksaco.jahesh.webService.services.BuyService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasketFragment extends Fragment {
    public static final int MODE_PRIVATE = 0x0000;
    private RecyclerView contentsRecyclerView;
    private BuyService buyService;
    private TextView btn_payment;
    private RecyclerView.Adapter adapter;
    CreateFactorModel factor ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BasketFragment() {
        // Required empty public constructor
    }

    public static BasketFragment newInstance(String param1, String param2) {
        BasketFragment fragment = new BasketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        buyService = new BuyService();
        this.contentsRecyclerView = view.findViewById(R.id.rcv_basket_contnets);
        this.btn_payment = view.findViewById(R.id.btn_payment);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        this.contentsRecyclerView.setLayoutManager(mLayoutManager);

        SharedPreferences sharedPreferences =getContext().getSharedPreferences("UserData", MODE_PRIVATE);
        String basketString = sharedPreferences.getString("Basket", "");
        Log.i("jaheshtag",basketString);

        BasketModel basketData = new BasketModel();

        if (!basketString.isEmpty()) {
            Gson gson = new Gson();
            basketData = gson.fromJson(basketString, BasketModel.class);

            factor =new  CreateFactorModel();
            for (int i = 0; i < basketData.getContents().size(); i++) {
                factor.Ids.add( basketData.getContents().get(i).getContentId());
            }

            this.btn_payment.setText(getString(R.string.tasviye) +"("+  basketData.getTotal()+" ریال" +")");
        }

        adapter = new BasketAdaptor(basketData.Contents);
        contentsRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iwebServicelistener listener = new iwebServicelistener() {
                    @Override
                    public void OnSuccess(Object response) {
                        ResponseBase<CreateFactorResponse>     result = (ResponseBase<CreateFactorResponse>) response;

                        if(result.IsSuccess)
                        {
                            //Toast.makeText(MyApp.context,result.Message + "factor Id" + result.Data.FactorId, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.Data.PaymentUrl));
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(MyApp.context,result.Message, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void OnFailed(FailType type, String message) {
                        Log.e("jaheshTag", message);
                    }
                };

                Gson gson = new Gson();
                String factorss = gson.toJson(factor);
                Log.i("jaheshTag",factorss);
                buyService.CreateFactor(listener, factor);
            }
        });
    }
}