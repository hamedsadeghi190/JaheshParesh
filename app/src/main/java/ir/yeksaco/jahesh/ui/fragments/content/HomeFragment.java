package ir.yeksaco.jahesh.ui.fragments.content;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import ir.yeksaco.jahesh.MyApp;
import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.adaptors.MainMenuAdaptor;
import ir.yeksaco.jahesh.databinding.FragmentHomeBinding;
import ir.yeksaco.jahesh.models.MenuItem;
import ir.yeksaco.jahesh.ui.activities.ContentListActivity;

public class HomeFragment extends Fragment {

    private GridView gridView;
    private MainMenuAdaptor adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = (GridView) view.findViewById(R.id.grd_menu);
        adapter = new MainMenuAdaptor(getContext(), R.layout.main_menu_item_layout, getData());

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MyApp.context, ContentListActivity.class);
                myIntent.putExtra("contentId",MyApp.MainPageData.Menu.get(position).Id);
                myIntent.putExtra("parentName",MyApp.MainPageData.Menu.get(position).Title);
                startActivity(myIntent);
            }
        });
    }

    private ArrayList getData() {

        final ArrayList imageItems = new ArrayList();
        for(int index=0;index< MyApp.MainPageData.Menu.size();index++)
        {
            imageItems.add(new MenuItem(MyApp.MainPageData.Menu.get(index).Title));
        }
        return imageItems;

    }
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}