package com.breckneck.washappca.broadcastreceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.breckneck.washapp.data.repository.TaskRepositoryImpl;
import com.breckneck.washapp.data.storage.database.DataBaseTaskStorageImpl;
import com.breckneck.washapp.domain.model.TaskApp;
import com.breckneck.washapp.domain.usecase.Task.GetTaskListFrequencyNullUseCase;
import com.breckneck.washapp.domain.usecase.Task.SubstractNotificationUseCase;
import com.breckneck.washappca.presentation.TaskDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SubstractionFrequencyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        List<TaskApp> taskList = new ArrayList<>();

        DataBaseTaskStorageImpl dataBaseTaskStorage = new DataBaseTaskStorageImpl(context);
        TaskRepositoryImpl taskRepository = new TaskRepositoryImpl(dataBaseTaskStorage);
        SubstractNotificationUseCase substractNotificationUseCase = new SubstractNotificationUseCase(taskRepository);
        GetTaskListFrequencyNullUseCase getTaskListFrequencyNullUseCase = new GetTaskListFrequencyNullUseCase(taskRepository);

        Disposable disposable = Single.just("1")
                .map(sub -> {
                    substractNotificationUseCase.execute();
                    return sub;
                })
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull String s) {
                        Log.e("TAG", "Frequences substracted");
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

        Disposable disposableGet = Single.just(taskList)
                .map(list -> {
                    list = getTaskListFrequencyNullUseCase.execute();
                    return list;
                })
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<List<TaskApp>>() {
                    @Override
                    public void onSuccess(@NonNull List<TaskApp> list) {
                        if (list.isEmpty()) {
                            Log.e("TAG", "No tasks with null frequency");
                        } else {
                            Log.e("TAG", "Create notification");
                            Intent intent = new Intent(context, NotificationReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                            long time = System.currentTimeMillis();
                            alarmManager.set(AlarmManager.RTC_WAKEUP, time + 3000, pendingIntent);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
