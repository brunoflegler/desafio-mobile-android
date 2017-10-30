package br.com.desafio.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import br.com.desafio.adpter.GitHubPullAdapter;
import br.com.desafio.application.MyApplication;
import br.com.desafio.delegate.GithubDelegate;
import br.com.desafio.entity.GitHubPull;
import br.com.desafio.entity.Item;
import br.com.desafio.listener.ClickListener;
import br.com.desafio.listener.RecyclerTouchListener;
import br.com.desafio.task.GitHubPullTask;
import br.com.desafio.listener.RecyclerViewOnScrollListener;
import br.com.desafio.util.MyLog;


public class PullActivity extends Activity implements GithubDelegate {
    private ProgressDialog progressDialog;
    private RecyclerView recyclerViewPull;
    private RecyclerView.LayoutManager layoutManager;
    private GitHubPullAdapter adapter;
    private final String ESTADO_ATUAL_ADAPTER = "ESTADO_ATUAL_ADAPTER";
    private final String ESTADO_ATUAL_REPOSITORIO = "ESTADO_ATUAL_REPOSITORIO";
    private Item repositorio;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repositorio);
        getObjects();
        setObjects();
    }

    @Override
    public MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
                break;
            default:break;
        }
        return true;
    }

    public void getObjects() {
        recyclerViewPull = (RecyclerView) findViewById(R.id.recycler_repositorio);

        recyclerViewPull.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewPull.setLayoutManager(layoutManager);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);


        recyclerViewPull.addOnScrollListener(new RecyclerViewOnScrollListener() {
            @Override
            public void onLoadMore(Integer page) {
                new GitHubPullTask(PullActivity.this).execute(page.toString());
            }
        });

        recyclerViewPull.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerViewPull, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                GitHubPull gitHubPull = adapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(gitHubPull.getHtmlUrl()));
                startActivity(intent);
                finishAffinity();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setObjects() {

        if (getIntent().getSerializableExtra("Item") != null) {
            repositorio = (Item) getIntent().getSerializableExtra("Item");
        }
        setTitle(repositorio.getName());
        new GitHubPullTask(this).execute(repositorio.getOwner().getLogin(), repositorio.getName(), "1");

    }

    @Override
    public void retorno(List lista) {
        if( adapter == null){
            adapter = new GitHubPullAdapter(lista);
            recyclerViewPull.setAdapter(adapter);
        }else{
            adapter.addListAll(lista);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadingON() {
        progressDialog = ProgressDialog.show(this, "Aguarde...", "Consultando a API do GitHub...", false, false);
    }

    @Override
    public void loadingOFF() {
        progressDialog.dismiss();
    }


    @Override
    public void logErro(Exception e) {
        progressDialog.dismiss();
        MyLog.i(e.getMessage());
    }

}
