package com.breckneck.washappca.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.breckneck.washapp.data.repository.ZoneRepositoryImpl;
import com.breckneck.washapp.data.storage.database.DataBaseZoneStorageImpl;
import com.breckneck.washapp.domain.model.ZoneApp;
import com.breckneck.washapp.domain.usecase.Zone.GetZonesUseCase;
import com.breckneck.washappca.R;
import com.breckneck.washappca.adapter.ZoneAdapter;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<ZoneApp> zonesList = new ArrayList<>();
    DataBaseZoneStorageImpl dataBaseZoneStorage;
    ZoneRepositoryImpl zoneRepository;
    GetZonesUseCase getZonesUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dataBaseZoneStorage = new DataBaseZoneStorageImpl(getApplicationContext());
        zoneRepository = new ZoneRepositoryImpl(dataBaseZoneStorage);
        getZonesUseCase = new GetZonesUseCase(zoneRepository);

        Button button = findViewById(R.id.addNewTaskButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewZoneActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        RecyclerView recyclerView = findViewById(R.id.zonelist);

        ZoneAdapter.OnZoneClickListener zoneClickListener;
        zoneClickListener = new ZoneAdapter.OnZoneClickListener() {
            @Override
            public void onZoneClick(ZoneApp zoneApp, int position) {
                Intent intent = new Intent(MainActivity.this, ZoneDetailsActivity.class);
                intent.putExtra("zonename", zoneApp.getZoneName());
                intent.putExtra("zoneid", zoneApp.getId());
                Toast.makeText(getApplicationContext(), "эл-т " + zoneApp.getId() + "  " + zoneApp.getZoneName(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        };

        zonesList = getZonesUseCase.execute();
        ZoneAdapter adapter = new ZoneAdapter(getApplicationContext(), zonesList, zoneClickListener);
        recyclerView.setAdapter(adapter);
    }
}