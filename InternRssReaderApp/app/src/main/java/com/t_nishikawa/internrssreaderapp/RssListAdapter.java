package com.t_nishikawa.internrssreaderapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        View itemView;
        TextView titleTextView;
        TextView subTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.itemView.setOnLongClickListener(this);
            this.titleTextView = (TextView) itemView.findViewById(R.id.titleText);
            this.subTextView = (TextView) itemView.findViewById(R.id.subText);
        }

        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(itemView.getContext(), "長押し", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, RssData rssData);
    }
}
