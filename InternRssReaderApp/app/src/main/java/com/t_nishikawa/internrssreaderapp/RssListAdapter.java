package com.t_nishikawa.internrssreaderapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RssListAdapter extends RecyclerView.Adapter<RssListAdapter.ViewHolder> {

    private List<RssData> dataset;
    private OnItemClickListener onItemClickListener;

    public RssListAdapter(List<RssData> dataset) {
        this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rss_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.titleTextView.setText(dataset.get(position).title);
        holder.subTextView.setText(dataset.get(position).title);

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(view, dataset.get(holder.getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setOnClickListener(RssListAdapter.OnItemClickListener onClickListener) {
        this.onItemClickListener = onClickListener;
    }

    public void updateList(List<RssData> dataset) {
        this.dataset.clear();
        this.dataset.addAll(dataset);
        this.notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView titleTextView;
        TextView subTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.titleTextView = (TextView) itemView.findViewById(R.id.titleText);
            this.subTextView = (TextView) itemView.findViewById(R.id.subText);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, RssData rssData);
    }
}
