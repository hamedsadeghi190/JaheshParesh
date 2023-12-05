package ir.yeksaco.jahesh.adaptors;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.models.content.PurchasedContent;

public class ContentBuyedAdaptor  extends RecyclerView.Adapter<ContentBuyedAdaptor.ViewHolder> {

    private ArrayList<PurchasedContent> contents;

    public ContentBuyedAdaptor(ArrayList<PurchasedContent> contents) {
        this.contents = contents;
    }

    @NonNull
    @Override
    public ContentBuyedAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.buyed_content_list_item_layout, parent, false);
        return new ContentBuyedAdaptor.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentBuyedAdaptor.ViewHolder holder, int position) {
        PurchasedContent item = contents.get(position);
        holder.tv_content_title.setText(item.getTitle());
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
        public final TextView tv_content_title;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            tv_content_title = view.findViewById(R.id.tv_content_title);
        }
    }
}