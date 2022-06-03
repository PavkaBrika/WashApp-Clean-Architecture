package com.breckneck.washappca.presentation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.breckneck.washapp.data.repository.TaskRepositoryImpl;
import com.breckneck.washapp.data.storage.database.DataBaseTaskStorageImpl;
import com.breckneck.washapp.domain.usecase.Task.DeleteTaskUseCase;
import com.breckneck.washappca.R;

public class TaskDetailsActivity extends AppCompatActivity {

    String zoneName;
    long id;
    long zoneId;
    String taskName;

    DataBaseTaskStorageImpl dataBaseTaskStorage;
    TaskRepositoryImpl taskRepository;
    DeleteTaskUseCase deleteTaskUseCase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskdetails);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            id = arguments.getLong("taskid");
            zoneId = arguments.getLong("zoneid");
            taskName = arguments.getString("taskname");
            zoneName = arguments.getString("zonename");
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(zoneName + " - " + taskName);


        Button button = findViewById(R.id.setDateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity_taskdetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        dataBaseTaskStorage = new DataBaseTaskStorageImpl(getApplicationContext());
        taskRepository = new TaskRepositoryImpl(dataBaseTaskStorage);
        deleteTaskUseCase = new DeleteTaskUseCase(taskRepository);

        switch (item.getItemId()) {
            case R.id.delete_task_button:
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskDetailsActivity.this);
                builder.setTitle(R.string.deletetask);
                builder.setMessage(R.string.alertdialogdeletetaskbutton);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTaskUseCase.execute(id);
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.no, null);
                builder.show();
                return true;
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
