package com.example.mobileshop.Activiti;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileshop.Admin.AdminQuanTriActivity;
import com.example.mobileshop.R;
import com.example.mobileshop.adapter.DonHangAdapter;
import com.example.mobileshop.model.DonHang;
import com.example.mobileshop.model.ShowSanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.os.Build.ID;

public class LichSuMuaHangActivity extends AppCompatActivity {
    Toolbar toolbardonhang;
    ListView lvdh;
    int idsanpham = 0;
    View footerview;
    ArrayList<DonHang> arrayListdh;
    DonHangAdapter adapter;
    String urlGetdata = "http://192.168.1.130:90/server/getdonhang.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_mua_hang);
        toolbardonhang = findViewById(R.id.toolbardonhang);
        lvdh = findViewById(R.id.lvdh);
        arrayListdh = new ArrayList<>();
        adapter = new DonHangAdapter(this,R.layout.dong_don_hang,arrayListdh);
        lvdh.setAdapter(adapter);
        Anhxa();
        ActionToolbar();
        GetData(urlGetdata);
    }
    private void ActionToolbar() {
        setSupportActionBar(toolbardonhang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardonhang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void Anhxa() {
    }
    private void GetData(String url){
         RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray response) {

                        if (response != null && response.length() !=0) {

                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    arrayListdh.add(new DonHang(
                                            jsonObject.getInt("ID"),
                                            jsonObject.getString("Tenkhachhang"),
                                            jsonObject.getInt("Sodienthoai"),
                                            jsonObject.getString("Email")
                                    ));
                                    Log.d("---", String.valueOf(response));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LichSuMuaHangActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return getParams();
            }
        };
        requestQueue.add(jsonArrayRequest);
    }
 }