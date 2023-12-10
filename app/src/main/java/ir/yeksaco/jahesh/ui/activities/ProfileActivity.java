package ir.yeksaco.jahesh.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;

import ir.yeksaco.jahesh.MainActivity;
import ir.yeksaco.jahesh.MyApp;
import ir.yeksaco.jahesh.R;
import ir.yeksaco.jahesh.common.enums.FailType;
import ir.yeksaco.jahesh.models.general.CityList;
import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.models.users.ProfileData;
import ir.yeksaco.jahesh.models.users.VerifyCodeResponse;
import ir.yeksaco.jahesh.webService.iterfaces.iwebServicelistener;
import ir.yeksaco.jahesh.webService.services.UserService;

public class ProfileActivity extends AppCompatActivity {
    UserService userService;
    ProfileData profileData;
    TextView btn_save_profile;
    TextInputLayout til_reshte;
    TextInputEditText edt_first_name, edt_last_name, edt_age, edt_email;

    String from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        RemoveStausBar();
        LoadFormData();
        Bundle bundle = getIntent().getExtras();
        from = bundle.getString("from");

        btn_save_profile = findViewById(R.id.btn_save_profile);
        til_reshte = findViewById(R.id.til_reshte);
        edt_first_name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_age = findViewById(R.id.edt_age);
        edt_email = findViewById(R.id.edt_email);

        btn_save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    profileData.userProfile.firstName = edt_first_name.getText().toString();
                    profileData.userProfile.lastName = edt_last_name.getText().toString();
                    profileData.userProfile.age = Integer.parseInt(edt_age.getText().toString());
                    profileData.userProfile.email = edt_email.getText().toString();
                    Gson gson = new Gson();
                    iwebServicelistener listener = new iwebServicelistener() {
                        @Override
                        public void OnSuccess(Object response) {
                            ResponseBase<String> responsed = (ResponseBase<String>) response;
                            Log.e("jaheshTag", "success");
                            Log.e("jaheshTag", gson.toJson(responsed));
                            if(from.equals("splash"))
                            {
                                Intent myIntent = new Intent(ProfileActivity.this, MainActivity.class);
                                startActivity(myIntent);
                                finish();
                            }
                            else {
                                finish();
                            }
                        }

                        @Override
                        public void OnFailed(FailType type, String message) {
                            Log.e("jaheshTag", message);
                            Toast.makeText(getApplicationContext(), "خطای ثبت اطلاعات" + message, Toast.LENGTH_SHORT).show();
                        }
                    };


                    Log.e("jaheshTag", gson.toJson(profileData.userProfile));
                    if (profileData.userProfile.countryId == 0) {
                        profileData.userProfile.countryId = 1;
                    }
                    userService.UpdateProfile(listener, profileData.userProfile);
                } else {
                    Toast.makeText(getApplicationContext(), "لطفا اطلاعات را کامل وارد نمایید", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateData() {
        if (checkNullOrEmpty(edt_first_name.getText().toString())) {
            return false;
        }

        if (checkNullOrEmpty(edt_last_name.getText().toString())) {
            return false;
        }

        if (checkNullOrEmpty(edt_age.getText().toString())) {
            return false;
        }
        if (!profileData.userProfile.gender.toLowerCase().equals("male") && !profileData.userProfile.gender.toLowerCase().equals("female")) {
            return false;
        }

        if (profileData.userProfile.gradeId == 0) {
            return false;
        }
        return true;
    }

    private boolean checkNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private void LoadFormData() {

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String info = sharedPreferences.getString("Token", "");

        if (info.isEmpty()) {

        } else {
            Log.i("jaheshTag", "info : " + info);

            Gson gson = new Gson();

            VerifyCodeResponse User = gson.fromJson(info, VerifyCodeResponse.class);
            MyApp.ApiToken = "Bearer " + User.Token;
            userService = new UserService();
            loadData();
        }
    }

    private void loadData() {

        iwebServicelistener listener = new iwebServicelistener() {
            @Override
            public void OnSuccess(Object response) {
                profileData = ((ResponseBase<ProfileData>) response).Data;
                Gson gson = new Gson();
                Log.i("jaheshTag", "info : " + gson.toJson(profileData.userProfile));



                BindFormData();
                BindFormValues();
            }

            @Override
            public void OnFailed(FailType type, String message) {
                Log.e("jaheshTag", "onfailed " + message);
            }
        };
        userService.GetProfile(listener);
    }

    private void RemoveStausBar() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
    }

    private void BindFormValues() {
        edt_first_name.setText(profileData.userProfile.firstName);
        edt_last_name.setText(profileData.userProfile.lastName);
        edt_age.setText("" + profileData.userProfile.age);
        edt_email.setText(profileData.userProfile.email);

        AutoCompleteTextView filled_dropdown_gender = findViewById(R.id.filled_dropdown_gender);
        if (profileData.userProfile.gender.equals("Male")) {
            filled_dropdown_gender.setText(filled_dropdown_gender.getAdapter().getItem(0).toString(), false);
        } else {
            filled_dropdown_gender.setText(filled_dropdown_gender.getAdapter().getItem(1).toString(), false);
        }

        AutoCompleteTextView filled_user_kind = findViewById(R.id.filled_user_kind);

        if (profileData.userProfile.userKind.equals("Student")) {
            filled_user_kind.setText(filled_user_kind.getAdapter().getItem(0).toString(), false);
        } else if (profileData.userProfile.userKind.equals("Teacher")) {
            filled_user_kind.setText(filled_user_kind.getAdapter().getItem(1).toString(), false);
        } else {
            filled_user_kind.setText(filled_user_kind.getAdapter().getItem(2).toString(), false);
        }

        AutoCompleteTextView filled_exposed_dropdown_grade = findViewById(R.id.filled_exposed_dropdown_grade);

        for (int i = 0; i < profileData.gradeList.size(); i++) {
            if (profileData.gradeList.get(i).id == profileData.userProfile.gradeId) {
                filled_exposed_dropdown_grade.setText(filled_exposed_dropdown_grade.getAdapter().getItem(i).toString(), false);
                break;
            }
        }

        if(profileData.userProfile.fieldId>0) {
            AutoCompleteTextView filled_exposed_dropdown_reshte = findViewById(R.id.filled_exposed_dropdown_reshte);
            til_reshte.setVisibility(View.VISIBLE);
            for (int i = 0; i < profileData.fieldList.size(); i++) {
                if (profileData.fieldList.get(i).id == profileData.userProfile.fieldId) {
                    filled_exposed_dropdown_reshte.setText(filled_exposed_dropdown_reshte.getAdapter().getItem(i).toString(), false);
                    break;
                }
            }
        }
        AutoCompleteTextView filled_exposed_dropdown_ostan = findViewById(R.id.filled_exposed_dropdown_ostan);

        for (int i = 0; i < profileData.stateList.size(); i++) {
            if (profileData.stateList.get(i).id == profileData.userProfile.stateId) {
                filled_exposed_dropdown_ostan.setText(filled_exposed_dropdown_ostan.getAdapter().getItem(i).toString(), false);
                break;
            }
        }
        ArrayList<CityList> filtered = new ArrayList<>();

        for (int index = 0; index < profileData.cityList.size(); index++) {
            if (profileData.cityList.get(index).stateId ==  profileData.userProfile.stateId) {
                filtered.add(profileData.cityList.get(index));
            };
        }
        String[] cities = new String[filtered.size()];
        for (int index = 0; index < filtered.size(); index++) {
            cities[index] = filtered.get(index).name;
        }

        AutoCompleteTextView filled_exposed_dropdown_shahr = findViewById(R.id.filled_exposed_dropdown_shahr);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, cities);

        filled_exposed_dropdown_shahr.setAdapter(adapter);

        for (int i = 0; i < filtered.size(); i++) {
            if (filtered.get(i).id == profileData.userProfile.cityId) {
                filled_exposed_dropdown_shahr.setText(filled_exposed_dropdown_shahr.getAdapter().getItem(i).toString(), false);
                break;
            }
        }
    }

    private void BindFormData() {
        BindGender();
        BindUserKind();
        BindGrade();
        BindOstan();
    }

    private void BindUserKind() {
        String[] Kindes = new String[]{"دانش آموز", "معلم", "والدین"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, Kindes);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.filled_user_kind);
        editTextFilledExposedDropdown.setAdapter(adapter);
        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kind = "";
                if (position == 0)
                    kind = "Student";
                else if (position == 1) {
                    kind = "Teacher";
                } else {
                    kind = "Parent";
                }

                profileData.userProfile.userKind = kind;
            }
        });
    }

    private void BindGender() {
        String[] COUNTRIES = new String[]{"آقا", "خانم"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, COUNTRIES);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.filled_dropdown_gender);
        editTextFilledExposedDropdown.setAdapter(adapter);
        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    profileData.userProfile.gender = "Male";
                } else {
                    profileData.userProfile.gender = "FeMale";
                }
            }
        });
    }

    private void BindGrade() {
        BindReshte();
        String[] grades = new String[profileData.gradeList.size()];
        for (int index = 0; index < profileData.gradeList.size(); index++) {
            grades[index] = profileData.gradeList.get(index).title;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, grades);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown_grade);
        editTextFilledExposedDropdown.setAdapter(adapter);
        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                profileData.userProfile.gradeId = profileData.gradeList.get(position).id;
                if (position > 8) {
                    til_reshte.setVisibility(View.VISIBLE);

                } else {
                    til_reshte.setVisibility(View.GONE);
                    profileData.userProfile.fieldId = 0;
                }
            }
        });
    }

    private void BindReshte() {
        String[] reshteha = new String[profileData.fieldList.size()];
        for (int index = 0; index < profileData.fieldList.size(); index++) {
            reshteha[index] = profileData.fieldList.get(index).title;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, reshteha);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown_reshte);
        editTextFilledExposedDropdown.setAdapter(adapter);
        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                profileData.userProfile.fieldId = profileData.fieldList.get(position).id;
            }
        });
    }

    private void BindOstan() {
        String[] states = new String[profileData.stateList.size()];
        for (int index = 0; index < profileData.stateList.size(); index++) {
            states[index] = profileData.stateList.get(index).name;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, states);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown_ostan);
        editTextFilledExposedDropdown.setAdapter(adapter);
        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                profileData.userProfile.stateId = profileData.stateList.get(position).id;
                BindCities(profileData.stateList.get(position).id);
            }
        });
    }

    private void BindCities(int stateId) {
        ArrayList<CityList> filtered = new ArrayList<>();

        for (int index = 0; index < profileData.cityList.size(); index++) {
            if (profileData.cityList.get(index).stateId == stateId) {
                filtered.add(profileData.cityList.get(index));
            }
            ;
        }
        String[] cities = new String[filtered.size()];
        for (int index = 0; index < filtered.size(); index++) {
            cities[index] = filtered.get(index).name;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, cities);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown_shahr);
        editTextFilledExposedDropdown.setAdapter(adapter);
        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                profileData.userProfile.cityId = filtered.get(position).id;

            }
        });
    }
}