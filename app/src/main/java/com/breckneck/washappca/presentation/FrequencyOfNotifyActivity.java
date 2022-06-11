package com.breckneck.washappca.presentation;

import android.os.Bundle;
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

public class FrequencyOfNotifyActivity extends AppCompatActivity {

    boolean myvariant = false;
    int positionFrequency;
    String customFrequency;
    long id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequencyofnotify);

        DataBaseTaskStorageImpl dataBaseTaskStorage = new DataBaseTaskStorageImpl(getApplicationContext());
        TaskRepositoryImpl taskRepository = new TaskRepositoryImpl(dataBaseTaskStorage);
        SetFrequencyUseCase setFrequencyUseCase = new SetFrequencyUseCase(taskRepository);
//        CheckFrequencyUseCase checkFrequencyUseCase = new CheckFrequencyUseCase(taskRepository);

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
//        EditText taskName = findViewById(R.id.editNameEditText);

//        if (checkFrequencyUseCase.execute(id)) {
//            taskName.setVisibility(View.VISIBLE);
//        } else {
//            taskName.setVisibility(View.GONE);
//        }

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
//                if (checkFrequencyUseCase.execute(id)) {
//
//                } else {
                    if (myvariant) {
                        customFrequency = customFreq.getText().toString();
                    }
                    Toast.makeText(getApplicationContext(), "gello " + positionFrequency, Toast.LENGTH_SHORT).show();
                    setFrequencyUseCase.execute(id, positionFrequency, customFrequency, myvariant);
                    finish();
//                }
            }
        });
    }
}
