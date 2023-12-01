package ir.yeksaco.jahesh.ui.fragments.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ir.yeksaco.jahesh.MyApp;
import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.ui.activities.SplashScreenActivity;
import ir.yeksaco.jahesh.ui.activities.UpdateActivity;

import android.content.SharedPreferences;

public class ProfileFragment extends Fragment {

    Button btn_logout;
    public ProfileFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout();
                Activity activity = getActivity();
                Intent myIntent = new Intent(getContext(), SplashScreenActivity.class);
                activity.finish();
                startActivity(myIntent);




            }
        });
    }

    private  void logout()
    {
        SharedPreferences sharedPreferences =  MyApp.context.getSharedPreferences("UserData", 0x0000);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.remove("Token");
        myEdit.apply();
        myEdit.commit();
    }
}