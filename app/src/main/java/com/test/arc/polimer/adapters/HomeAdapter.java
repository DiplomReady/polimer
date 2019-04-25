package com.test.arc.polimer.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.test.arc.polimer.R;
import com.test.arc.polimer.SubActivity;
import com.test.arc.polimer.model.HomeItem;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private List<HomeItem> mDataset;

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public HomeViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public HomeAdapter(List<HomeItem> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item_view, parent, false);

        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        final HomeItem homeItem = mDataset.get(position);
        holder.textView.setText(homeItem.getTitle());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SubActivity.class);
                intent.putExtra(SubActivity.DATA_KEY, homeItem);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
