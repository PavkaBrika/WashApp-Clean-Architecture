package com.breckneck.washappca.presentation;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.breckneck.washapp.data.repository.TaskRepositoryImpl;
import com.breckneck.washapp.data.storage.database.DataBaseTaskStorageImpl;
import com.breckneck.washapp.domain.usecase.Task.CheckFrequencyUseCase;
import com.breckneck.washapp.domain.usecase.Task.DeleteTaskUseCase;
import com.breckneck.washapp.domain.usecase.Task.GetTimeToNotificationUseCase;
import com.breckneck.washappca.R;
import com.breckneck.washappca.broadcastreceiver.NotificationReceiver;

import java.util.Calendar;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

    CompositeDisposable disposeBag = new CompositeDisposable();

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("test", "test", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar notifyme = Calendar.getInstance();
                notifyme.set(Calendar.HOUR_OF_DAY, 0);
                notifyme.set(Calendar.MINUTE, 33);
                notifyme.set(Calendar.SECOND, 0);

                Intent intent = new Intent(TaskDetailsActivity.this, NotificationReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(TaskDetailsActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

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

        checkFrequency(id);
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
//                        deleteTaskUseCase.execute(id);
                        deleteTask(id);
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.no, null);
                builder.show();
                return true;
//            case R.id.edit_task_button:
//                Intent intent = new Intent(TaskDetailsActivity.this, FrequencyOfNotifyActivity.class);
//                intent.putExtra("taskid", id);
//                startActivity(intent);
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkFrequency(long id) {
        Disposable disposableCheck = Single.just(id)
                .map(identificator -> {
                    return checkFrequencyUseCase.execute(identificator);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Boolean aBoolean) {
                        if (aBoolean) {
                            taskInfo.setVisibility(View.VISIBLE);
                            done.setVisibility(View.VISIBLE);
                            setDateButton.setVisibility(View.GONE);
                            infoLayout.setVisibility(View.GONE);
                        } else {
                            taskInfo.setVisibility(View.GONE);
                            done.setVisibility(View.GONE);
                            setDateButton.setVisibility(View.VISIBLE);
                            infoLayout.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

        Disposable disposableGetTime = Single.just(id)
                .map(indentificator -> {
                    return getTimeToNotificationUseCase.execute(id);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Integer integer) {
                        taskInfo.setText(getString(R.string.cleaningafter) + " " + integer);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

        disposeBag.add(disposableCheck);
        disposeBag.add(disposableGetTime);
    }

    public void deleteTask(long id) {
        Disposable disposable = Single.just(id)
                .map(identificator -> {
                    deleteTaskUseCase.execute(identificator);
                    return identificator;
                })
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<Long>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Long aLong) {
                        Log.e("TAG", "Task " + aLong + " delete success");
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
        disposeBag.add(disposable);
    }
}
