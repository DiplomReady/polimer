package com.test.arc.polimer;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.arc.polimer.adapters.HomeAdapter;
import com.test.arc.polimer.model.HomeItem;
import com.test.arc.polimer.model.SubItem;

import java.util.ArrayList;
import java.util.List;

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
        List<HomeItem> homeItems = new ArrayList<>();

        List<SubItem> pipeItems = new ArrayList<>();
        SubItem subItem = new SubItem("Муфты соединительные", "Муфты соединительные description");
        subItem.setImage(R.drawable.mufta_prev);
        pipeItems.add(subItem);
        pipeItems.add(new SubItem("Трубы для парников", "Трубы для парников description"));
        pipeItems.add(new SubItem("Трубы для поручней", "Трубы для поручней description"));
        pipeItems.add(new SubItem("Элементы детских игровых площадок", "Элементы детских игровых площадок description"));
        SubItem subItem1 = new SubItem("Трубы для карданных валов", "Трубы для карданных валов description");
        subItem1.setImage(R.drawable.mufta_prev);
        pipeItems.add(subItem1);
        pipeItems.add(new SubItem("Трубы полиэтиленновые технические", "Трубы полиэтиленновые технические description"));

        homeItems.add(new HomeItem("Трубы", pipeItems));
        homeItems.add(new HomeItem("Пленка", pipeItems));
        homeItems.add(new HomeItem("Строительные изделия", pipeItems));
        homeItems.add(new HomeItem("Для сада и огорода", pipeItems));
        homeItems.add(new HomeItem("Упаковочные изделия", pipeItems));
        homeItems.add(new HomeItem("Продукты переработки", pipeItems));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new HomeAdapter(homeItems));
    }
}
