package com.breckneck.washappca.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.breckneck.washapp.data.repository.TaskRepositoryImpl;
import com.breckneck.washapp.data.storage.database.DataBaseTaskStorageImpl;
import com.breckneck.washapp.domain.usecase.Task.CheckFrequencyUseCase;
import com.breckneck.washapp.domain.usecase.Task.DeleteTaskUseCase;
import com.breckneck.washapp.domain.usecase.Task.GetTimeToNotificationUseCase;
import com.breckneck.washappca.R;

public class TaskDetailsActivity extends AppCompatActivity {

    String zoneName;
    long id;
    long zoneId;
    String taskName;

    Button setDateButton;
    Button done;
    TextView taskInfo;
    LinearLayout infoLayout;

    DataBaseTaskStorageImpl dataBaseTaskStorage;
    TaskRepositoryImpl taskRepository;
    DeleteTaskUseCase deleteTaskUseCase;
    CheckFrequencyUseCase checkFrequencyUseCase;
    GetTimeToNotificationUseCase getTimeToNotificationUseCase;

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


        setDateButton = findViewById(R.id.setDateButton);
        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskDetailsActivity.this, FrequencyOfNotifyActivity.class);
                intent.putExtra("taskid", id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        dataBaseTaskStorage = new DataBaseTaskStorageImpl(getApplicationContext());
        taskRepository = new TaskRepositoryImpl(dataBaseTaskStorage);
        checkFrequencyUseCase = new CheckFrequencyUseCase(taskRepository);
        getTimeToNotificationUseCase = new GetTimeToNotificationUseCase(taskRepository);

        setDateButton = findViewById(R.id.setDateButton);
        taskInfo = findViewById(R.id.taskInfo);
        done = findViewById(R.id.done);
        infoLayout = findViewById(R.id.infolayout);

        if (checkFrequencyUseCase.execute(id)) {
            taskInfo.setVisibility(View.VISIBLE);
            done.setVisibility(View.VISIBLE);
            setDateButton.setVisibility(View.GONE);
            infoLayout.setVisibility(View.GONE);
            taskInfo.setText(getString(R.string.cleaningafter) + " " + getTimeToNotificationUseCase.execute(id));
        } else {
            taskInfo.setVisibility(View.GONE);
            done.setVisibility(View.GONE);
            setDateButton.setVisibility(View.VISIBLE);
            infoLayout.setVisibility(View.VISIBLE);
        }
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
