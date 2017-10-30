package br.com.desafio.task;


import android.app.Application;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.List;

import br.com.desafio.application.MyApplication;
import br.com.desafio.delegate.GithubDelegate;
import br.com.desafio.entity.GitHub;
import br.com.desafio.entity.Item;
import br.com.desafio.request.WebClient;

public class GitHubTask extends AsyncTask<Integer, Void, List<Item>>{

    private GithubDelegate delegate;
    private Exception erro;

    public GitHubTask(GithubDelegate delegate) {
        this.delegate = delegate;
        this.delegate.loadingON();
        this.delegate.getMyApplication().registraTask(this);
    }

    @Override
    protected List<Item> doInBackground(Integer... strings) {
        try {
            Integer page = strings[0];
            String response = new WebClient("https://api.github.com/search/repositories?q=language:Java&sort=stars&page="+page).get();
            GitHub gitHub =  new Gson().fromJson(response, GitHub.class);
            return gitHub.getItems();
        }catch (Exception e){
            this.erro = e;
            return null;
        }
    }

    @Override
        protected void onPostExecute(List<Item> retorno) {
        this.delegate.getMyApplication().desregistraTask(this);
        this.delegate.loadingOFF();
        if(retorno != null){
            this.delegate.retorno(retorno);
        }else{
            this.delegate.logErro(this.erro);
        }

        delegate.getMyApplication().desregistraTask(this);
    }

}
