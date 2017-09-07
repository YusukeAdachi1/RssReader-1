package com.t_nishikawa.internrssreaderapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BookMarkListAdapter extends RecyclerView.Adapter<BookMarkListAdapter.ViewHolder> {

    private List<BookMarkDataManager.BookMarkData> dataset;
    private OnItemClickListener onClickListener;

    public BookMarkListAdapter(List<BookMarkDataManager.BookMarkData> dataset) {
        this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookmark_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textView.setText(dataset.get(position).title);
        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClick(view, dataset.get(holder.getAdapterPosition()));
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onClickListener.onLongClick(view, dataset.get(holder.getAdapterPosition()));
                    return true; //onLongClick時はonClickイベントを発生させないようにtrue
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setOnClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void updateList(List<BookMarkDataManager.BookMarkData> dataset) {
        this.dataset.clear();
        this.dataset.addAll(dataset);
        this.notifyDataSetChanged();
    }

    public BookMarkDataManager.BookMarkData getItem(int pos) {
        return this.dataset.get(pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.textView = (TextView) itemView.findViewById(R.id.titleText);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, BookMarkDataManager.BookMarkData bookMarkData);

        void onLongClick(View view, BookMarkDataManager.BookMarkData bookMarkData);
    }
}
