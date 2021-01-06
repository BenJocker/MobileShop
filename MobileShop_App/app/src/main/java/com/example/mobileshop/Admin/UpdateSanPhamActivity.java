package com.example.mobileshop.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileshop.R;
import com.example.mobileshop.model.ShowSanPham;

import java.util.HashMap;
import java.util.Map;

public class UpdateSanPhamActivity extends AppCompatActivity {
    Toolbar toolbarupdate;
    EditText edttensanpham, edtgiasanpham , edthinhanhsp , edtmotasanpham ,edtidsanpham;
    Button btncapnhat , btnhuycapnhat;
    int id = 0;
    String urlUpdate = "http://192.168.1.130:90/server/update.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_san_pham);
        Intent intent = getIntent();
        ShowSanPham showSanPham = (ShowSanPham) intent.getSerializableExtra("dataSanPham");
        Toast.makeText(this,showSanPham.getTensanpham(), Toast.LENGTH_SHORT).show();
        Anhxa();
        ActionBar();
        id = showSanPham.getId();
        edttensanpham.setText(showSanPham.getTensanpham());
        edtgiasanpham.setText(showSanPham.getGiasanpham() +"");
        edthinhanhsp.setText(showSanPham.getHinhanhsanpham());
        edtmotasanpham.setText(showSanPham.getMotasanpham());
        edtidsanpham.setText(showSanPham.getIDSanpham()+"");

        btncapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensp        = edttensanpham.getText().toString().trim();
                String giasp        = edtgiasanpham.getText().toString().trim();
                String hinhanhsp    = edthinhanhsp.getText().toString().trim();
                String motasp       = edtmotasanpham.getText().toString().trim();
                String idsanpham    = edtidsanpham.getText().toString().trim();
                if (tensp.matches("") || giasp.equals("") || hinhanhsp.matches("") || motasp.matches("") || idsanpham.length() ==0){
                    Toast.makeText(UpdateSanPhamActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    CapNhatSinhVien(urlUpdate);
                }
            }
        });
        btnhuycapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void CapNhatSinhVien(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(UpdateSanPhamActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateSanPhamActivity.this,AdminQuanTriActivity.class));
                        }else {
                            Toast.makeText(UpdateSanPhamActivity.this,   "Lỗi cập nhật!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateSanPhamActivity.this, "Xãy ra lỗi, vui òng thử lại!", Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                params.put("tensanpham",edttensanpham.getText().toString().trim());
                params.put("giasanpham",edtgiasanpham.getText().toString().trim());
                params.put("hinhanhsanpham",edthinhanhsp.getText().toString().trim());
                params.put("motasanpham",edtmotasanpham.getText().toString().trim());
                params.put("idsanpham",edtidsanpham.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void ActionBar() {
        setSupportActionBar(toolbarupdate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarupdate.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void Anhxa() {
        edttensanpham  = findViewById(R.id.edttensanpham);
        edtgiasanpham  = findViewById(R.id.edtgiasanpham);
        edthinhanhsp   = findViewById(R.id.edthinhanhsp);
        edtmotasanpham = findViewById(R.id.edtmotasanpham);
        edtidsanpham   = findViewById(R.id.edtidsanpham);
        btncapnhat     = findViewById(R.id.btncapnhat);
        btnhuycapnhat  = findViewById(R.id.btnhuycapnhat);
        toolbarupdate  = findViewById(R.id.toolbarupdate);
    }

}