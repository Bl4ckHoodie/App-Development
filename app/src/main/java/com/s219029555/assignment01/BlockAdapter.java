package com.s219029555.assignment01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class BlockAdapter extends BaseAdapter {
    Context context;
    String value;
    LayoutInflater inflater;
    public BlockAdapter(Context applicationContext, String value) {
        this.context = applicationContext;
        this.value = value;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return Block.getBlocks(5).size();
    }

    @Override
    public Object getItem(int i) {
        return value;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.reusableview_block,null);
        TextView tbBlock = (TextView) view.findViewById(R.id.tbLetter);
        tbBlock.setText(value);
        return view;
    }
}