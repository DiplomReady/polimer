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

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        createData();
    }

    private void createData() {
        final ProgressBar progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        NetworkService.getInstance()
                .getJSONApi()
                .getData()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Data, ObservableSource<Data>>() {
                    @Override
                    public ObservableSource<Data> apply(Data data) throws Exception {
                        for (HomeItem item : data.getItems()) {
                            App.getInstance().getDatabase().dataDao().insert(item);
                        }
                        return Observable.just(data);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Data>() {
                    @Override
                    public void accept(Data data) throws Exception {
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

                        progressBar.setVisibility(View.GONE);
                        List<HomeItem> items = data.getItems();
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);
                        recyclerView.setHasFixedSize(true);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(new HomeAdapter(items));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("***", "Error: " + throwable.getMessage());
                        throwable.printStackTrace();
                        progressBar.setVisibility(View.GONE);


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
