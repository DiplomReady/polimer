package com.test.arc.polimer;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.arc.polimer.adapters.HomeAdapter;
import com.test.arc.polimer.api.NetworkService;
import com.test.arc.polimer.model.Data;
import com.test.arc.polimer.model.HomeItem;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

//главный экран приложения
public class HomeActivity extends AppCompatActivity {

    //этот метод вызывается при создании этого экрана
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //указываем лэяут этого экрана
        setContentView(R.layout.activity_main);

        // прячем меню вверху экрана
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //вызываем метод для полцчения данных
        createData();
    }

    private void createData() {

        //показываем прогресс
        final ProgressBar progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        //вызов для получения данных, используется рхджава
        NetworkService.getInstance()
                .getJSONApi()
                .getData()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Data, ObservableSource<Data>>() {
                    @Override
                    public ObservableSource<Data> apply(Data data) throws Exception {
                        for (HomeItem item : data.getItems()) {
                            //вставляем в базу данных, происходит в бэкграунде
                            App.getInstance().getDatabase().dataDao().insert(item);
                        }
                        return Observable.just(data);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Data>() {
                    @Override
                    public void accept(Data data) throws Exception {
                        //получаем данные в основном потоке приложеня и проверяем не изменилась ли версия приложения, если изменилась показывваем диалог для апдейта
                        if (Float.valueOf(BuildConfig.VERSION_NAME).compareTo(data.version) < 0) {
                            AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("App update required");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/")));
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                        //прячем прогресс тк данные получили
                        progressBar.setVisibility(View.GONE);
                        List<HomeItem> items = data.getItems();
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);
                        recyclerView.setHasFixedSize(true);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        //это указывается для того чтобы айтемы подчеркивались
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        //показываем данные юзеру
                        recyclerView.setAdapter(new HomeAdapter(items));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //этот код вызывается в случаи получения ошибки
                        Log.d("***", "Error: " + throwable.getMessage());
                        throwable.printStackTrace();
                        progressBar.setVisibility(View.GONE);


                        //создаем новый поток что бы достать данные из базы
                        Single.fromCallable(new Callable<List<HomeItem>>() {
                            @Override
                            public List<HomeItem> call() throws Exception {
                                List<HomeItem> all = App.getInstance().getDatabase().dataDao().getAll();
                                return all;
                            }
                        })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<List<HomeItem>>() {
                                    @Override
                                    public void accept(List<HomeItem> all) throws Exception {
                                        //показываем данные юзеру
                                        if (all != null && !all.isEmpty()) {
                                            List<HomeItem> items = all;
                                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);
                                            recyclerView.setHasFixedSize(true);
                                            LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
                                            recyclerView.setLayoutManager(layoutManager);
                                            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                                            recyclerView.setAdapter(new HomeAdapter(items));
                                        }
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Log.d("***", "Error: " + throwable.getMessage());
                                        throwable.printStackTrace();
                                    }
                                });


                    }
                });

    }
}
