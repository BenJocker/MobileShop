package com.example.mobileshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mobileshop.R;
import com.example.mobileshop.model.DonHang;

import java.util.List;

public class DonHangAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<DonHang> Listdonhang;

    public DonHangAdapter(Context context, int layout, List<DonHang> Listdonhang) {
        this.context = context;
        this.layout = layout;
        this.Listdonhang = Listdonhang;
    }

    @Override
    public int getCount() {
        return Listdonhang.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView txtid, txttenkh, txtsdtkh, txtemail;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtid = view.findViewById(R.id.iddonhang);
            holder.txttenkh = view.findViewById(R.id.tenkh);
            holder.txtsdtkh = view.findViewById(R.id.Sdtkh);
            holder.txtemail = view.findViewById(R.id.emailkh);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        DonHang donHang = Listdonhang.get(i);

        holder.txtid.setText(donHang.getId()+"");
        holder.txttenkh.setText(donHang.getTenkh());
        holder.txtsdtkh.setText(donHang.getSdtkh()+"");
        holder.txtemail.setText(donHang.getEmail());
        return view;
    }

}
