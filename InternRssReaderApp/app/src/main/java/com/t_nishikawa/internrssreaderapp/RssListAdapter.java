package com.t_nishikawa.internrssreaderapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RssListAdapter extends ArrayAdapter<RssListItem> {

    private int mResource;
    private List<RssListItem> mItems;
    private LayoutInflater mInflater;

    public RssListAdapter(@NonNull final Context context, @LayoutRes final int resource , final List<RssListItem> items) {
        super(context, resource, items);

        mResource = resource;
        mItems = items;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = mInflater.inflate(mResource, null);
        }

        final RssListItem item = mItems.get(position);

        final TextView titleText = (TextView)view.findViewById(R.id.titleText);
        titleText.setText(item.getTitle());

        final TextView subText = (TextView)view.findViewById(R.id.subText);
        subText.setText(item.getSubText());

        return view;
    }
}
