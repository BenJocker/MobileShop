package com.example.mobileshop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.mobileshop.Activiti.ChiTietSanPham;
import com.example.mobileshop.R;
import com.example.mobileshop.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.graphics.Color.RED;

public class DienthoaiAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sanpham> arraydienthoai;

    public DienthoaiAdapter(Context context, ArrayList<Sanpham> arraydienthoai) {
        this.context = context;
        this.arraydienthoai = arraydienthoai;
    }

    @Override
    public int getCount() {
        return arraydienthoai.size();
    }

    @Override
    public Object getItem(int i) {
        return arraydienthoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        public TextView txttendienthoai, txtgiadienthoai, txtmotadienthoai;
        public ImageView imgdienthoai;
        private ImageButton btn_tym;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_dienthoai, null);
            viewHolder.txttendienthoai = view.findViewById(R.id.textViewdienthoai);
            viewHolder.txtgiadienthoai = view.findViewById(R.id.textViewgiadienthoai);
            viewHolder.txtmotadienthoai = view.findViewById(R.id.textViewmotadienthoai);
            viewHolder.imgdienthoai = view.findViewById(R.id.imageViewdienthoai);
            viewHolder.btn_tym = view.findViewById(R.id.btn_favorite);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttendienthoai.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiadienthoai.setText("Giá :" + decimalFormat.format(sanpham.getGiasanpham()) + "Đ");
        viewHolder.txtmotadienthoai.setMaxLines(2);
        viewHolder.txtmotadienthoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotadienthoai.setText(sanpham.getMotasanpham());

        viewHolder.btn_tym.setSelected(false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietSanPham.class);
                intent.putExtra("Thông tin sản phẩm", arraydienthoai.get(i));
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });

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
                .into(viewHolder.imgdienthoai);
        return view;
    }
}
