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

import com.breckneck.washapp.data.repository.TaskRepositoryImpl;
import com.breckneck.washapp.data.storage.database.DataBaseTaskStorageImpl;
import com.breckneck.washapp.domain.usecase.Task.AddTasksUseCase;
import com.breckneck.washappca.R;

public class NewTaskActivity extends AppCompatActivity {

    String name;
    boolean myvariant = false;
    long id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewtask);

        DataBaseTaskStorageImpl dataBaseTaskStorage = new DataBaseTaskStorageImpl(getApplicationContext());
        TaskRepositoryImpl taskRepository = new TaskRepositoryImpl(dataBaseTaskStorage);
        AddTasksUseCase addTasksUseCase = new AddTasksUseCase(taskRepository);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            id = arguments.getLong("zoneid");
        }

        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        EditText newTaskName = findViewById(R.id.addNewTaskEditText);
        Spinner spinner = findViewById(R.id.spinnerTask);

        String[] taskNames = {getString(R.string.livingroom), getString(R.string.Hallway), getString(R.string.Kitchen), getString(R.string.Bedroom), getString(R.string.Bathroom),
                getString(R.string.Toilet), getString(R.string.Childrensroom), getString(R.string.Bathroom),getString(R.string.car), getString(R.string.Custom)};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, taskNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String item = (String) parent.getItemAtPosition(position);
                if (item.equals(getString(R.string.Custom))) {
                    newTaskName.setVisibility(View.VISIBLE);
                    myvariant = true;
                }
                else {
                    newTaskName.setVisibility(View.GONE);
                    name = item;
                    myvariant = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinner.setOnItemSelectedListener(onItemSelectedListener);

        Button button = findViewById(R.id.okAddNewTaskButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myvariant) {
                    name = newTaskName.getText().toString();
                }
                addTasksUseCase.execute(id, name);
                finish();
            }
        });
    }
}
