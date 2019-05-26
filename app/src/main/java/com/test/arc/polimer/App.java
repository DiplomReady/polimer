package com.test.arc.polimer;

import android.app.Application;

import androidx.room.Room;

import com.test.arc.polimer.dataBase.AppDatabase;

//основной класс приложеня, прописывается в манифесте, нужен что бы проинитить базуданных, синглтон
public class App extends Application {
    public static App instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //инитим БД
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
