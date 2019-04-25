package com.test.arc.polimer;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.arc.polimer.adapters.HomeAdapter;
import com.test.arc.polimer.adapters.SubAdapter;
import com.test.arc.polimer.model.HomeItem;
import com.test.arc.polimer.model.SubItem;

import java.util.ArrayList;
import java.util.List;

public class SubActivity extends AppCompatActivity {
    public static final String DATA_KEY = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        HomeItem homeItem = (HomeItem) getIntent().getSerializableExtra(DATA_KEY);
        setTitle(homeItem.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sub_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new SubAdapter(homeItem.getSubItems()));
    }
}
