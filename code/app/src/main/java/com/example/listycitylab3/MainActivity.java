package com.example.listycitylab3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;
    City clickedCity=null;
    @Override
    public void addCity(City city){
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void editCity(City city, City newCity) {
        //Change values of the item in city list
        int p = cityAdapter.getPosition(city);
        dataList.set(p,newCity);
        cityAdapter.notifyDataSetChanged();
        //notifyItemChanged();
        //cityAdapter.

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {
                "Edmonton", "Vancouver", "Toronto"
        };
        String[] provinces = {
                "AB","BC","ON"
        };

        dataList=new ArrayList<City>();
        for(int i=0;i<cities.length;i++){
            dataList.add(new City(cities[i],provinces[i]));
        }

        cityAdapter=new CityArrayAdapter(this,dataList);

        cityList = findViewById(R.id.city_list);
        cityList.setAdapter(cityAdapter);
        
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickedCity = dataList.get(i);
                //AddCityFragment addCityFragment = new AddCityFragment(clickedCity);
            }
        });



        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            if(clickedCity==null){
            new AddCityFragment().show(getSupportFragmentManager(),"Add City");
            }else{
                new AddCityFragment(clickedCity).show(getSupportFragmentManager(),"Edit City");
            }

        });
    }
}