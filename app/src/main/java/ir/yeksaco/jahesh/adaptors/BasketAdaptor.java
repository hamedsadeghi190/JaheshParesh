package ir.yeksaco.jahesh.adaptors;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import ir.yeksaco.jahesh.MyApp;
import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.models.buy.basket.BasketContentModel;
import ir.yeksaco.jahesh.models.buy.basket.BasketModel;

public class BasketAdaptor extends RecyclerView.Adapter<BasketAdaptor.ViewHolder> {

    private ArrayList<BasketContentModel> contents;
    private iBasketItemClickListner listner;
    public static final int MODE_PRIVATE = 0x0000;
    public BasketAdaptor(ArrayList<BasketContentModel> contents,iBasketItemClickListner basketItemClickListner) {
        listner = basketItemClickListner;
        this.contents = contents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_list_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BasketContentModel item = contents.get(position);

        holder.tv_basket_content_title.setText(item.getName());
        holder.tv_basket_content_amount.setText(MyApp.context.getString(R.string.amount)+" : " +  item.getAmount() + " ریال");
        holder.btn_delete_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.OnDeleted(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (contents != null) {
            return contents.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView tv_basket_content_title,tv_basket_content_amount;
        public final ImageView btn_delete_basket;


        public ViewHolder(View view) {
            super(view);
            this.view =view;
            tv_basket_content_title = view.findViewById(R.id.tv_basket_content_title);
            tv_basket_content_amount = view.findViewById(R.id.tv_basket_content_amount);
            btn_delete_basket = view.findViewById(R.id.btn_delete_basket);
        }
    }
}
