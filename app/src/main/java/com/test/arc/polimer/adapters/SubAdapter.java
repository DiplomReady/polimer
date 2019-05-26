package com.test.arc.polimer.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.test.arc.polimer.DetailsActivity;
import com.test.arc.polimer.R;
import com.test.arc.polimer.SubActivity;
import com.test.arc.polimer.model.HomeItem;
import com.test.arc.polimer.model.SubItem;

import java.util.List;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.HomeViewHolder> {
    private List<SubItem> mDataset;

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public HomeViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public SubAdapter(List<SubItem> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_item_view, parent, false);

        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        final SubItem subItem = mDataset.get(position);
        holder.textView.setText(subItem.getTitle());
        holder.textView.setAllCaps(true);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //открытие деталей категории
                Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.DATA_KEY, subItem);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
