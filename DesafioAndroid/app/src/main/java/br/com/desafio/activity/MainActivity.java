package br.com.desafio.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import br.com.desafio.adpter.GitHubAdapter;
import br.com.desafio.adpter.GitHubPullAdapter;
import br.com.desafio.application.MyApplication;
import br.com.desafio.delegate.GithubDelegate;
import br.com.desafio.entity.Item;
import br.com.desafio.task.GitHubTask;
import br.com.desafio.listener.ClickListener;
import br.com.desafio.listener.RecyclerTouchListener;
import br.com.desafio.listener.RecyclerViewOnScrollListener;
import br.com.desafio.util.MyLog;


public class MainActivity extends Activity implements GithubDelegate {

    private RecyclerView recyclerView;
    private GitHubAdapter adapter;
    private ProgressDialog progressDialog;
    private RecyclerView.LayoutManager layoutManager;
    private final String ESTADO_ATUAL_ADAPTER = "ESTADO_ATUAL_ADAPTER";



    @Override
    public void loadingON() {
        progressDialog = ProgressDialog.show(this, "Aguarde...", "Consultando a API do GitHub...", false, false);
    }

    @Override
    public void loadingOFF() {
        progressDialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanteState){
        super.onCreate(savedInstanteState);
        setContentView(R.layout.activity_main);
        getObjects();
        setObjects();
    }

    @Override
    public MyApplication getMyApplication() {
       return (MyApplication) getApplication();
    }

    private void getObjects(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListener() {
            @Override
            public void onLoadMore(Integer page) {
                new GitHubTask(MainActivity.this).execute(page);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Item item = adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, PullActivity.class);
                intent.putExtra("Item", item);
                startActivity(intent);
                finishAffinity();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setObjects(){
        new GitHubTask(this).execute(1);
    }

    @Override
    public void retorno(List lista) {
        if(adapter == null){
            adapter = new GitHubAdapter(lista);
            recyclerView.setAdapter(adapter);
        }else{
            adapter.addListAll(lista);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void logErro(Exception e) {
        progressDialog.dismiss();
        MyLog.i(e.getMessage());
    }
}
