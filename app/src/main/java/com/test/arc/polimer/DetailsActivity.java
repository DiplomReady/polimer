package com.test.arc.polimer;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.arc.polimer.adapters.SubAdapter;
import com.test.arc.polimer.model.HomeItem;
import com.test.arc.polimer.model.SubItem;

public class DetailsActivity extends AppCompatActivity {
    public static final String DATA_KEY = "det_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        SubItem subItem = (SubItem) getIntent().getSerializableExtra(DATA_KEY);
        setTitle(subItem.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((TextView)findViewById(R.id.description)).setText(subItem.getDescription());

        int subItemImage = subItem.getImage();
        if(subItemImage != 0){
            ((ImageView)findViewById(R.id.image)).setImageDrawable(getDrawable(subItemImage));
        }
    }
}
