package br.com.desafio.application;


import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private List<AsyncTask<?,?,?>> tasks = new ArrayList<>();

    public void registraTask (AsyncTask<?,?,?> task){
        this.tasks.add(task);
    }

    public void desregistraTask (AsyncTask<?,?,?> task){
        tasks.remove(task);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for(AsyncTask task : this.tasks){
            task.cancel(true);
        }
    }
}
