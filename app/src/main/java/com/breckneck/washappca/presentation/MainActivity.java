package com.breckneck.washappca.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

        CompositeDisposable disposeBag = new CompositeDisposable();
        Disposable disposable = Single.just(zonesList)
                .map(list -> {
                    list = getZonesUseCase.execute();
                    return list;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<ZoneApp>>() {
                    @Override
                    public void onSuccess(@NonNull List<ZoneApp> zoneApps) {
                        ZoneAdapter adapter = new ZoneAdapter(getApplicationContext(), zoneApps, zoneClickListener);
                        recyclerView.setAdapter(adapter);
                        Log.e("TAG", "Zones load success");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
        disposeBag.add(disposable);
    }
}