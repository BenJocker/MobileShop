package com.example.mobileshop.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mobileshop.R;
import com.example.mobileshop.model.Loaisp;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp> arraylistloaisp;
    Context context;
    public LoaispAdapter(ArrayList<Loaisp> arrayListloaisp, Context context) {
        this.arraylistloaisp = arrayListloaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylistloaisp.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylistloaisp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txttenloaisp;
        ImageView imgloaisp;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewHolder.txttenloaisp  = (TextView) view.findViewById(R.id.textViewloaisp);
            viewHolder.imgloaisp = (ImageView) view.findViewById(R.id.imageViewloaisp);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Loaisp loaisp = (Loaisp) getItem(i);
        viewHolder.txttenloaisp.setText(loaisp.getTenloaisp());
              Picasso.get().load(loaisp.getHinhanhloaisp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgloaisp);
        return view;
    }
}
