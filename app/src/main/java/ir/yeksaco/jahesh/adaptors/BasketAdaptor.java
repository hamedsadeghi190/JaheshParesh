package ir.yeksaco.jahesh.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ir.yeksaco.jahesh.MyApp;
import ir.yeksaco.jahesh.models.buy.basket.BasketContentModel;
import ir.yeksaco.jahesh.models.buy.basket.BasketModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.models.content.GetMenuResponse;

public class BasketAdaptor extends RecyclerView.Adapter<BasketAdaptor.ViewHolder> {

    private ArrayList<BasketContentModel> contents;

    public BasketAdaptor(ArrayList<BasketContentModel> contents) {
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


        public ViewHolder(View view) {
            super(view);
            this.view =view;
            tv_basket_content_title = view.findViewById(R.id.tv_basket_content_title);
            tv_basket_content_amount = view.findViewById(R.id.tv_basket_content_amount);
        }
    }
}
