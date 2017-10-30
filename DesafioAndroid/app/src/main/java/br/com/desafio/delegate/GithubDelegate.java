package br.com.desafio.delegate;


import java.io.Serializable;
import java.util.List;

import br.com.desafio.application.MyApplication;
import br.com.desafio.entity.GitHub;

public interface GithubDelegate extends Serializable {

    void retorno(List lista);
    void loadingON();
    void loadingOFF();
    void logErro ( Exception e);

    MyApplication getMyApplication();
}
