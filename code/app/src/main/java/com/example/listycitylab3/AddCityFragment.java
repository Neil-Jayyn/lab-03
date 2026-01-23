package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener{
        void addCity(City city);
        void editCity(City city,City newCity);
    }

    City currentCity;

    public AddCityFragment(){
        this.currentCity=null;
    }

    public AddCityFragment(City city){
        this.currentCity=city;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    //  Reusing the dialog fragment to make it -> instead of creating it to changing it
    //use the same dialog but just change the code for fragment in main activity??

    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof AddCityDialogListener){
            listener=(AddCityDialogListener) context;
        }else{
            throw new RuntimeException(context+"must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city,null);
        EditText editCityName=view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName=view.findViewById(R.id.edit_text_province_text);
// MAKE IF STATEMENT - to check if edittext has value in -> if no value on instance addCity, if yes value editCity
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        if(currentCity==null){
            return builder
                    .setView(view)
                    .setTitle("Add/Edit a city")
                    .setNegativeButton("Cancel",null)
                    .setPositiveButton("Add",(dialog, which)->{
                        String cityName=editCityName.getText().toString();
                        String provinceName=editProvinceName.getText().toString();
                        listener.addCity(new City(cityName,provinceName));
                    }).create();
        }else{
            String oldCityName= currentCity.getName();
            String oldProvinceName= currentCity.getProvince();
            editCityName.setText(oldCityName);
            editProvinceName.setText(oldProvinceName);

            return builder
                    .setView(view)
                    .setTitle("Add/Edit a city")
                    .setNegativeButton("Cancel",null)
                    .setPositiveButton("Edit",(dialog, which)->{
                        String cityName=editCityName.getText().toString();
                        String provinceName=editProvinceName.getText().toString();
                        listener.editCity(currentCity, new City(cityName,provinceName));
                    }).create();
        }
    }
}
