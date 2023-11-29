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
import ir.yeksaco.jahesh.models.content.GetMenuResponse;

public class ContentAdaptor extends RecyclerView.Adapter<ContentAdaptor.ViewHolder> {

    private ArrayList<GetMenuResponse> contents;

    public ContentAdaptor(ArrayList<GetMenuResponse> contents) {
        this.contents = contents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetMenuResponse item = contents.get(position);

        switch (item.getLevelKind()) {
            case "Menu":
                holder.img_menuType.setImageResource(R.drawable.ic_category);
                break;
            case "Content":
                holder.img_menuType.setImageResource(R.drawable.ic_content);
                break;
        }
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
        public final ImageView img_menuType;
        public final TextView tv_content_title;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            img_menuType = view.findViewById(R.id.img_menu_type);
            tv_content_title = view.findViewById(R.id.tv_content_title);
        }
    }
}