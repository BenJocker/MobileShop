package com.example.mobileshop.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.core.content.ContextCompat;

import com.example.mobileshop.R;
import com.example.mobileshop.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sanpham> arraylaptop;

    public LaptopAdapter(Context context, ArrayList<Sanpham> arraylaptop) {
        this.context = context;
        this.arraylaptop = arraylaptop;
    }

    @Override
    public int getCount() {
        return arraylaptop.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylaptop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txttenlaptop,txtgialaptop,txtmotalaptop;
        public ImageView imglaptop;
        private ImageButton btn_tym;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_laptop ,null);
            viewHolder.txttenlaptop = view.findViewById(R.id.textViewtenlaptop);
            viewHolder.txtgialaptop = view.findViewById(R.id.textViewgialaptop);
            viewHolder.txtmotalaptop = view.findViewById(R.id.textViewmotalaptop);
            viewHolder.imglaptop = view.findViewById(R.id.imageViewlaptop);
            viewHolder.btn_tym = view.findViewById(R.id.btn_favorite);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttenlaptop.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgialaptop.setText("Giá :"+ decimalFormat.format(sanpham.getGiasanpham())+"Đ");
        viewHolder.txtmotalaptop.setMaxLines(2);
        viewHolder.txtmotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotalaptop.setText(sanpham.getMotasanpham());
        viewHolder.btn_tym.setSelected(false);
        if (sanpham.isFavorite) {
            view.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.favorite));
        } else {
            view.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorAccent5));
        }

        viewHolder.btn_tym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!view.isSelected()) {
                    sanpham.isFavorite = true;
                    view.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.favorite));
                    view.setSelected(true);
                } else {
                    sanpham.isFavorite = false;
                    view.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorAccent5));
                    view.setSelected(false);
                }
            }
        });

        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imglaptop);
        return view;
    }
}
