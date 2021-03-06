package com.breckneck.washappca.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.breckneck.washapp.data.repository.ZoneRepositoryImpl;
import com.breckneck.washapp.data.storage.database.DataBaseZoneStorageImpl;
import com.breckneck.washapp.domain.usecase.Zone.AddZoneUseCase;
import com.breckneck.washappca.R;

public class NewZoneActivity extends AppCompatActivity {

    String name;
    long idzone;
    boolean myvariant = false;

    DataBaseZoneStorageImpl dataBaseZoneStorage;
    ZoneRepositoryImpl zoneRepository;
    AddZoneUseCase addZoneUseCase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewzone);

        dataBaseZoneStorage = new DataBaseZoneStorageImpl(getApplicationContext());
        zoneRepository = new ZoneRepositoryImpl(dataBaseZoneStorage);
        addZoneUseCase = new AddZoneUseCase(zoneRepository);

        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        EditText newZoneName = findViewById(R.id.addNewZoneEditText);
        Spinner spinner = findViewById(R.id.spinner);

        String[] zoneNames = {getString(R.string.livingroom), getString(R.string.Hallway), getString(R.string.Kitchen), getString(R.string.Bedroom), getString(R.string.Bathroom),
                getString(R.string.Toilet), getString(R.string.Childrensroom), getString(R.string.Bathroom),getString(R.string.car), getString(R.string.Myversion)};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, zoneNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String item = (String) parent.getItemAtPosition(position);
                if (item.equals(getString(R.string.Myversion))) {
                    newZoneName.setVisibility(View.VISIBLE);
                    myvariant = true;
                }
                else {
                    newZoneName.setVisibility(View.GONE);
                    name = item;
                    myvariant = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

        Button ok = findViewById(R.id.okAddNewZoneButton);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myvariant) {
                    name = newZoneName.getText().toString();
                }
                addZoneUseCase.execute(name);
                finish();
            }
        });

    }
}
