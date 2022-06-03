package com.breckneck.washappca.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.breckneck.washapp.domain.model.TaskApp;
import com.breckneck.washappca.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.taskViewHolder> {

    private final LayoutInflater inflater;
    private final List<TaskApp> taskList;

    public interface OnTaskClickListener {
        void onTaskClick(TaskApp task, int position);
    }

    private final OnTaskClickListener onTaskClickListener;

    public TaskAdapter(Context context, List<TaskApp> taskList, OnTaskClickListener onTaskClickListener) {
        this.taskList = taskList;
        this.inflater = LayoutInflater.from(context);
        this.onTaskClickListener = onTaskClickListener;
    }

    @NonNull
    @Override
    public TaskAdapter.taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_list, parent, false);
        return new taskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.taskViewHolder holder, int position) {
        TaskApp task = taskList.get(position);
        holder.nameView.setText(task.getTaskName());
//        holder.picView.setImageResource(task.getPicture());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTaskClickListener.onTaskClick(task, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class taskViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        //        final ImageView picView;
        public taskViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.taskname);
//            picView = view.findViewById(R.id.taskpic);
        }
    }
}
