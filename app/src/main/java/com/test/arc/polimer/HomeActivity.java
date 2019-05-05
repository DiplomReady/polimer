package com.test.arc.polimer;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.arc.polimer.adapters.HomeAdapter;
import com.test.arc.polimer.api.NetworkService;
import com.test.arc.polimer.model.Data;
import com.test.arc.polimer.model.HomeItem;
import com.test.arc.polimer.model.SubItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                .enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                        progressBar.setVisibility(View.GONE);
                        List<HomeItem> items = response.body().getItems();
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);
                        recyclerView.setHasFixedSize(true);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(new HomeAdapter(items));
                    }

                    @Override
                    public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        t.printStackTrace();
                    }
                });

    }
}
