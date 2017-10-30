package br.com.desafio.util;


import android.util.Log;

public class MyLog {

    public static void i(Object o) {
        Log.i("Desafio", o.toString());
    }

    public static void e(Object o) {
        Log.e("Desafio", o.toString());
    }
}
