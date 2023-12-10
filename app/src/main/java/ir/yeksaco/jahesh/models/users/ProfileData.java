package ir.yeksaco.jahesh.models.users;

import java.util.ArrayList;

import ir.yeksaco.jahesh.models.general.CityList;
import ir.yeksaco.jahesh.models.general.CountryList;
import ir.yeksaco.jahesh.models.general.FieldList;
import ir.yeksaco.jahesh.models.general.GradeList;
import ir.yeksaco.jahesh.models.general.StateList;

public class ProfileData {
    public ArrayList<CountryList> countryList;
    public ArrayList<StateList> stateList;
    public ArrayList<CityList> cityList;
    public ArrayList<GradeList> gradeList;
    public ArrayList<FieldList> fieldList;
    public UserProfile userProfile;
}
