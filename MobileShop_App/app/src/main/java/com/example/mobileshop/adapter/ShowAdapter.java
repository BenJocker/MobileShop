package com.example.mobileshop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileshop.Admin.AdminQuanTriActivity;
import com.example.mobileshop.Admin.UpdateSanPhamActivity;
import com.example.mobileshop.R;
import com.example.mobileshop.model.ShowSanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ShowAdapter extends BaseAdapter {

    private AdminQuanTriActivity context;
    private int layout;
    private List<ShowSanPham> SanPhamList;

    public ShowAdapter(AdminQuanTriActivity context, int layout, List<ShowSanPham> sanPhamList) {
        this.context = context;
        this.layout = layout;
        SanPhamList = sanPhamList;
    }

    @Override
    public int getCount() {
        return SanPhamList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        ImageView imgsp, imgedit ,imgdelete;
        TextView txttensp, txtgiasp, txtmotasp;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_san_pham,null);
            holder.imgsp        = view.findViewById(R.id.imgsp);
            holder.txttensp     = view.findViewById(R.id.txttensp);
            holder.txtgiasp     = view.findViewById(R.id.txtgiasp);
            holder.txtmotasp    = view.findViewById(R.id.txtmotasp);
            holder.imgedit      = view.findViewById(R.id.imgedit);
            holder.imgdelete    = view.findViewById(R.id.imgdelete);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        final ShowSanPham showSanPham = SanPhamList.get(i);

        holder.txttensp.setText(showSanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasp.setText("Giá :"+ decimalFormat.format(showSanPham.getGiasanpham())+"Đ");
        holder.txtgiasp.setMaxLines(2);
        holder.txtgiasp.setEllipsize(TextUtils.TruncateAt.END);
//        holder.txtgiasp.setText(showSanPham.getGiasanpham());
        holder.txtmotasp.setText(showSanPham.getMotasanpham());
        Picasso.get().load(showSanPham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imgsp);

        //bắt sự kiện xóa và sửa
        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateSanPhamActivity.class);
                intent.putExtra("dataSanPham", showSanPham);
                context.startActivity(intent);

            }
        });
        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XacNhanXoa(showSanPham.getTensanpham(),showSanPham.getId());
            }
        });
        return view;
    }
    private void XacNhanXoa(String ten , final int id){
        AlertDialog.Builder dialogxoa = new AlertDialog.Builder(context);
        dialogxoa.setMessage("Bạn có muốn xóa Sản phẩm"+ ten + "không?");
        dialogxoa.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.DeleteSanPhan(id);

            }
        });
        dialogxoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogxoa.show();
     }
    }

