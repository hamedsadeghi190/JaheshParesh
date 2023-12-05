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
import ir.yeksaco.jahesh.models.content.FileUrlList;
import ir.yeksaco.jahesh.models.content.GetMenuResponse;

public class ContentFilesAdaptor extends RecyclerView.Adapter<ContentFilesAdaptor.ViewHolder> {

    private ArrayList<FileUrlList> contents;

    public ContentFilesAdaptor(ArrayList<FileUrlList> contents) {
        this.contents = contents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.content_files_list_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
        public final ImageView btn_download;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            btn_download = view.findViewById(R.id.btn_download);
        }
    }
}