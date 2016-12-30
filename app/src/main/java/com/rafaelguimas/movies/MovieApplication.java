package com.rafaelguimas.movies;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Rafael on 30/12/2016.
 */

public class MovieApplication extends Application {
    public void onCreate() {
        super.onCreate();

        // Inicializa o Stheto
        Stetho.initializeWithDefaults(this);
    }
}
