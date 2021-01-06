package com.example.mobileshop.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileshop.Activiti.ChiTietSanPham;
import com.example.mobileshop.R;
import com.example.mobileshop.model.Sanpham;
import com.example.mobileshop.ultil.Checkconnection;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.ArrayList;
public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        ItemHolder  itemHolder = new ItemHolder(v);
        return itemHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Sanpham sanpham = arraysanpham.get(position);
        holder.txttensanpham.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Giá :"+ decimalFormat.format(sanpham.getGiasanpham())+"Đ");
        Picasso.get()
                .load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imghinhsanpham);

    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhsanpham;
        public TextView txttensanpham,txtgiasanpham;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imghinhsanpham = itemView.findViewById(R.id.imageViewsanpham);
            txtgiasanpham = itemView.findViewById(R.id.textViewgiasanpham);
            txttensanpham = itemView.findViewById(R.id.textViewtensanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("Thông tin sản phẩm",arraysanpham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Checkconnection.Showtoast_Short(context,arraysanpham.get(getPosition()).getTensanpham());
                    context.startActivity(intent);
                }
            });

        }
    }
}
