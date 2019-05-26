package com.test.arc.polimer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.test.arc.polimer.model.SubItem;

//экран деталей категории которую выбрали на SubActivity
public class DetailsActivity extends AppCompatActivity {
    public static final String DATA_KEY = "det_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        SubItem subItem = (SubItem) getIntent().getSerializableExtra(DATA_KEY);
        setTitle(subItem.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((TextView) findViewById(R.id.description)).setText(subItem.getDescription());


        String subItemImage = subItem.getImage();
        //Проверяем есть ли картинка, загружаем с помощью Glide и показываем юзеру
        if (!TextUtils.isEmpty(subItemImage)) {

            ImageView imageView = (ImageView) findViewById(R.id.image);
            imageView.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .asBitmap()
                    .load(subItemImage)
                    .into(imageView);
        }
    }
}
