package com.breckneck.washappca.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.breckneck.washapp.data.repository.TaskRepositoryImpl;
import com.breckneck.washapp.data.repository.ZoneRepositoryImpl;
import com.breckneck.washapp.data.storage.database.DataBaseTaskStorageImpl;
import com.breckneck.washapp.data.storage.database.DataBaseZoneStorageImpl;
import com.breckneck.washapp.domain.model.TaskApp;
import com.breckneck.washapp.domain.usecase.Zone.DeleteZoneUseCase;
import com.breckneck.washapp.domain.usecase.Task.GetTasksUseCase;
import com.breckneck.washappca.R;
import com.breckneck.washappca.adapter.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ZoneDetailsActivity extends AppCompatActivity {

    long id;
    String name;
    List<TaskApp> tasksList = new ArrayList<>();

    DataBaseZoneStorageImpl dataBaseZoneStorage;
    ZoneRepositoryImpl zoneRepository;
    DeleteZoneUseCase deleteZoneUseCase;

    DataBaseTaskStorageImpl dataBaseTaskStorage;
    TaskRepositoryImpl taskRepository;
    GetTasksUseCase getTasksUseCase;

    TaskAdapter.OnTaskClickListener taskClickListener;

    CompositeDisposable disposeBag = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zonedetails);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            id = arguments.getLong("zoneid");
            name = arguments.getString("zonename");
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name + " " + getString(R.string.tasks));

        Button button = findViewById(R.id.addNewTaskButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ZoneDetailsActivity.this, NewTaskActivity.class);
                intent.putExtra("zoneid", id);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        dataBaseTaskStorage = new DataBaseTaskStorageImpl(getApplicationContext());
        taskRepository = new TaskRepositoryImpl(dataBaseTaskStorage);
        getTasksUseCase = new GetTasksUseCase(taskRepository);

        RecyclerView recyclerView = findViewById(R.id.tasklist);
        taskClickListener = new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(TaskApp task, int position) {
                Toast.makeText(getApplicationContext(), "эл-т " + task.getId() + "  " + task.getTaskName() + " зона=" + task.getZoneId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ZoneDetailsActivity.this, TaskDetailsActivity.class);
                intent.putExtra("taskname", task.getTaskName());
                intent.putExtra("zoneid", task.getZoneId());
                intent.putExtra("taskid", task.getId());
                intent.putExtra("zonename", name);
                startActivity(intent);
            }
        };

        Disposable disposableGet = Single.just(tasksList)
                .map( list -> {
                    list = getTasksUseCase.execute(id);
                    return list;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<TaskApp>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<TaskApp> taskApps) {
                        TaskAdapter adapter = new TaskAdapter(getApplicationContext(), taskApps, taskClickListener);
                        recyclerView.setAdapter(adapter);
                        Log.e("TAG", "Tasks load success");
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
        disposeBag.add(disposableGet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity_zonedetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        dataBaseZoneStorage = new DataBaseZoneStorageImpl(getApplicationContext());
        zoneRepository = new ZoneRepositoryImpl(dataBaseZoneStorage);
        deleteZoneUseCase = new DeleteZoneUseCase(zoneRepository);

        switch (item.getItemId()) {
            case R.id.delete_button:
                AlertDialog.Builder builder = new AlertDialog.Builder(ZoneDetailsActivity.this);
                builder.setTitle(R.string.deletezone);
                builder.setMessage(R.string.alertdialogdeletezonebutton);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Disposable disposableDelete = Single.just(id)
                                .map(id -> {
                                    deleteZoneUseCase.execute(id);
                                    return id;
                                })
                                .subscribeOn(Schedulers.io())
                                .subscribeWith(new DisposableSingleObserver<Long>() {
                                    @Override
                                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Long aLong) {
                                        Log.e("TAG", "Zone " + aLong + " delete success");
                                    }

                                    @Override
                                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                                    }
                                });
                        disposeBag.add(disposableDelete);
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
