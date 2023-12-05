package ir.yeksaco.jahesh.ui.fragments.content;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.slider.Slider;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.yeksaco.jahesh.MyApp;
import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.adaptors.MainMenuAdaptor;
import ir.yeksaco.jahesh.adaptors.SliderAdapter;
import ir.yeksaco.jahesh.databinding.FragmentHomeBinding;
import ir.yeksaco.jahesh.models.MenuItem;
import ir.yeksaco.jahesh.models.general.SliderItem;
import ir.yeksaco.jahesh.ui.activities.ContentListActivity;

public class HomeFragment extends Fragment {

    View fview;
    private GridView gridView;
    private MainMenuAdaptor adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fview = view;
        gridView = (GridView) view.findViewById(R.id.grd_menu);
        adapter = new MainMenuAdaptor(getContext(), R.layout.main_menu_item_layout, getData());

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MyApp.context, ContentListActivity.class);
                myIntent.putExtra("contentId", MyApp.MainPageData.Menu.get(position).Id);
                myIntent.putExtra("parentName", MyApp.MainPageData.Menu.get(position).Title);
                startActivity(myIntent);
            }
        });

        if (MyApp.MainPageData.Slider.size() > 0) {
            SliderView sliderView = view.findViewById(R.id.imageSlider);

            SliderAdapter sAdapter = new SliderAdapter(getContext());
            sAdapter.renewItems(MyApp.MainPageData.Slider);

            sliderView.setSliderAdapter(sAdapter);
            sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            sliderView.setIndicatorSelectedColor(Color.parseColor("#cc0066"));
            sliderView.setIndicatorUnselectedColor(Color.GRAY);
            sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
            sliderView.startAutoCycle();
        }
    }

    private ArrayList getData() {
        final ArrayList imageItems = new ArrayList();
        for (int index = 0; index < MyApp.MainPageData.Menu.size(); index++) {
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