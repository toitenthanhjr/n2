package com.example.computermanagement_330;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ComputerAdapter extends BaseAdapter {

    private Context context;
    private List<Computer> list;

    public ComputerAdapter(Context context, List<Computer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int iPosition) {
        return list.get(iPosition);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_computer, null);

        }
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvCategory = view.findViewById(R.id.tv_category);

        Computer cp = list.get(i);
        tvName.setText(cp.getName());
        tvCategory.setText(cp.getCategory());

        return view;
    }
}
