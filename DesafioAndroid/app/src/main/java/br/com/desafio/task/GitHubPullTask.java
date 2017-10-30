package br.com.desafio.task;


import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.desafio.delegate.GithubDelegate;
import br.com.desafio.entity.GitHub;
import br.com.desafio.entity.GitHubPull;
import br.com.desafio.entity.Item;
import br.com.desafio.request.WebClient;

public class GitHubPullTask extends AsyncTask<String, Void, List<GitHubPull>>{

    private GithubDelegate delegate;
    private Exception erro;

    public GitHubPullTask(GithubDelegate delegate) {
        this.delegate = delegate;
        this.delegate.loadingON();
        this.delegate.getMyApplication().registraTask(this);
    }

    @Override
    protected List<GitHubPull> doInBackground(String... strings) {
        try {
            String criador = strings[0];
            String repo = strings[1];
            String page = strings[2];

            String response = new WebClient("https://api.github.com/repos/"+criador+"/" + repo+ "/pulls?page="+page).get();
            Type listType = new TypeToken<List<GitHubPull>>() {}.getType();

            List<GitHubPull> pulls =  new Gson().fromJson(response, listType);

            return pulls;

        }catch (Exception e){
            this.erro = e;
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<GitHubPull> retorno) {
        this.delegate.loadingOFF();
        if(retorno != null){
            this.delegate.retorno(retorno);
        }else{
            this.delegate.logErro(this.erro);
        }

        delegate.getMyApplication().desregistraTask(this);
    }
}


