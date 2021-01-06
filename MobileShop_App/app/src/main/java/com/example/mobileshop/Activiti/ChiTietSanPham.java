package com.example.mobileshop.Activiti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mobileshop.R;
import com.example.mobileshop.adapter.Giohang;
import com.example.mobileshop.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarchitiet;
    ImageView imgchitiet;
    TextView txtten, txtgia, txtmota;
    Spinner spinner;
    Button btndamua;
    int id = 0;
    String Tenchitiet = "";
    int Giachitiet = 0;
    String Hinhanhchitiet = "";
    String Motachitiet = "";
    int Idsanpham = 0;
    int id1 = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
    }

    private void EventButton() {
        btndamua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HomeActivity.manggiohang.size() > 0) {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i < HomeActivity.manggiohang.size(); i++) {
                        if (HomeActivity.manggiohang.get(i).getIdsp() == id) {
                            HomeActivity.manggiohang.get(i).setSoluongsp(HomeActivity.manggiohang.get(i).getSoluongsp() + sl);
                            if (HomeActivity.manggiohang.get(i).getSoluongsp() >= 10) {
                                HomeActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            HomeActivity.manggiohang.get(i).setGiasp(Giachitiet * HomeActivity.manggiohang.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if (exists == false) {
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong * Giachitiet;
                        HomeActivity.manggiohang.add(new Giohang(id, Tenchitiet, Giamoi, Hinhanhchitiet, soluong));
                    }
                } else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong * Giachitiet;
                    HomeActivity.manggiohang.add(new Giohang(id, Tenchitiet, Giamoi, Hinhanhchitiet, soluong));
                }
                Intent intent = new Intent(getApplicationContext(), com.example.mobileshop.Activiti.Giohang.class);
                startActivity(intent);

            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setGravity(Gravity.CENTER);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("Thông tin sản phẩm");
        id = sanpham.getID();
        Tenchitiet = sanpham.getTensanpham();
        Giachitiet = sanpham.getGiasanpham();
        Hinhanhchitiet = sanpham.getHinhanhsanpham();
        Motachitiet = sanpham.getMotasanpham();
        Idsanpham = sanpham.getIDSanpham();
        txtten.setText(Tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá :" + decimalFormat.format(Giachitiet) + "Đ");
        txtmota.setText(Motachitiet);
        Picasso.get().load(Hinhanhchitiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgchitiet);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarchitiet = findViewById(R.id.toolbarchitietsanpham);
        imgchitiet = findViewById(R.id.imageViewchitietsanpham);
        txtten = findViewById(R.id.textviewtenchitietsanpham);
        txtgia = findViewById(R.id.textviewgiachitietsanpham);
        txtmota = findViewById(R.id.textviewmotachitietsanpham);
        spinner = findViewById(R.id.spinner);
        btndamua = findViewById(R.id.buttondatmua);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), com.example.mobileshop.Activiti.Giohang.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}