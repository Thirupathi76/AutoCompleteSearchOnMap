package com.vmax.searchmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SearchResultAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<SearchItemBean> items;

    public SearchResultAdapter(Context context, List<SearchItemBean> items) {
        this.context = context;
        this.items = items;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return this.items.size();
    }

    public Object getItem(int position) {
        return this.items.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.auto_complete_list_item, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.place_auto_complete_desc)).setText(((SearchItemBean) this.items.get(position)).getName());
        return convertView;
    }
}
