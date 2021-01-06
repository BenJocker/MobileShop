package com.example.mobileshop.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.mobileshop.Activiti.HomeActivity;
import com.example.mobileshop.Activiti.MainActivity;
import com.example.mobileshop.R;

import java.util.HashMap;
import java.util.Map;

public class ThemActivity extends AppCompatActivity {
    EditText edttensanpham, edtgiasanpham, edthinhanhsp,edtmotasanpham,edtidsanpham;
    Button btnThem, btnHuythem;
    String URLINSERT ="http://192.168.1.130:90/server/insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them);
        Anhxa();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Tensp = edttensanpham.getText().toString().trim();
                String Giasp = edtgiasanpham.getText().toString().trim();
                String Hinhanhsp = edthinhanhsp.getText().toString().trim();
                String Motasp = edtmotasanpham.getText().toString().trim();
                String idsanpham = edtidsanpham.getText().toString().trim();
                if (Tensp.isEmpty() || Giasp.isEmpty() || Hinhanhsp.isEmpty() || Motasp.isEmpty() || idsanpham.isEmpty()){
                    Toast.makeText(ThemActivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    ThemSanPham(URLINSERT);
                }
            }
        });
        btnHuythem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void ThemSanPham(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("===",response.toString());
                        if (response.trim().equals("success")){
                            Toast.makeText(ThemActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ThemActivity.this, HomeActivity.class));
                            Log.d("AAA","Lỗi!\n"+ response.toString());
                        }else {
                            Toast.makeText(ThemActivity.this, "Lỗi Thêm", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ThemActivity.this, "Xãy ra lỗi!", Toast.LENGTH_SHORT).show();


                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("tensanpham",edttensanpham.getText().toString().trim());
                params.put("giasanpham",edtgiasanpham.getText().toString().trim());
                params.put("hinhanhsanpham",edthinhanhsp.getText().toString().trim());
                params.put("motasanpham",edtmotasanpham.getText().toString().trim());
                params.put("idsanpham",edtidsanpham.getText().toString().trim());
                params.put("isFavorite","0");
                Log.d("=-==",params.toString());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void Anhxa() {
        edttensanpham = findViewById(R.id.edttensanpham);
        edtgiasanpham = findViewById(R.id.edtgiasanpham);
        edthinhanhsp = findViewById(R.id.edthinhanhsp);
        edtmotasanpham = findViewById(R.id.edtmotasanpham);
        edtidsanpham = findViewById(R.id.edtidsanpham);
        btnThem = findViewById(R.id.btnthem);
        btnHuythem = findViewById(R.id.btnhuythem);
    }
}
