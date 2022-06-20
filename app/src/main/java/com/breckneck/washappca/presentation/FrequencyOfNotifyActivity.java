package com.breckneck.washappca.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.breckneck.washapp.data.repository.TaskRepositoryImpl;
import com.breckneck.washapp.data.storage.database.DataBaseTaskStorageImpl;
import com.breckneck.washapp.domain.usecase.Task.CheckFrequencyUseCase;
import com.breckneck.washapp.domain.usecase.Task.SetFrequencyUseCase;
import com.breckneck.washappca.R;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FrequencyOfNotifyActivity extends AppCompatActivity {

    boolean myvariant = false;
    int positionFrequency;
    String customFrequency;
    long id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequencyofnotify);

        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            id = arguments.getLong("taskid");
        }

        String[] frequencyList = {getString(R.string.everyday), getString(R.string.twodays), getString(R.string.threedays),
                getString(R.string.fourdays), getString(R.string.fivedays), getString(R.string.sixdays),
                getString(R.string.oneweek), getString(R.string.twoweek), getString(R.string.threeweek),
                getString(R.string.onemonth), getString(R.string.twomonth), getString(R.string.threemonth),
                getString(R.string.Custom)};

        Spinner spinner = findViewById(R.id.spinnerFreq);
        EditText customFreq = findViewById(R.id.customFreq);
        LinearLayout layout = findViewById(R.id.editTextLayout);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, frequencyList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String item = (String) parent.getItemAtPosition(position);
                positionFrequency = position;
                if (item.equals(getString(R.string.Custom))) {
                    layout.setVisibility(View.VISIBLE);
                    myvariant = true;
                }
                else {
                    layout.setVisibility(View.GONE);
                    myvariant = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

        Button ok = findViewById(R.id.okFreq);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (myvariant) {
                        customFrequency = customFreq.getText().toString();
                    }
                    Toast.makeText(getApplicationContext(), "gello " + positionFrequency, Toast.LENGTH_SHORT).show();
                    setFrequency(id, positionFrequency, customFrequency, myvariant);
                    finish();
            }
        });
    }

    public void setFrequency(long id, int positionFrequency, String customFrequency, boolean myvariant) {
        DataBaseTaskStorageImpl dataBaseTaskStorage = new DataBaseTaskStorageImpl(getApplicationContext());
        TaskRepositoryImpl taskRepository = new TaskRepositoryImpl(dataBaseTaskStorage);
        SetFrequencyUseCase setFrequencyUseCase = new SetFrequencyUseCase(taskRepository);
        CompositeDisposable disposeBag = new CompositeDisposable();

        Disposable disposable = Single.just(id)
                .map(identificator -> {
                    setFrequencyUseCase.execute(identificator, positionFrequency, customFrequency, myvariant);
                    return identificator;
                })
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<Long>() {
                    @Override
                    public void onSuccess(@NonNull Long aLong) {
                        Log.e("TAG", id + " Frequency task set success");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
        disposeBag.add(disposable);
    }
}
