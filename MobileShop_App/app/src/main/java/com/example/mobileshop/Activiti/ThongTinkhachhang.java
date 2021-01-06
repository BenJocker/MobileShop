package com.example.mobileshop.Activiti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileshop.R;
import com.example.mobileshop.ultil.Checkconnection;
import com.example.mobileshop.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinkhachhang extends AppCompatActivity {
    EditText edttenkhachnag,edtemail,edtsdt;
    Button btnxacnhan, btntrove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tinkhachhang);
        Anhxa();
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (Checkconnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        }else {
            Checkconnection.Showtoast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại internet");
        }
    }
    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ten = edttenkhachnag.getText().toString().trim();
                final String sdt = edtsdt.getText().toString().trim();
                final String email = edtemail.getText().toString().trim();
                if (ten.length() > 0 && sdt.length() > 0 && email.length() > 0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang",madonhang);
                            if (Integer.parseInt(madonhang) > 0 ){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")){
                                            HomeActivity.manggiohang.clear();
                                            Checkconnection.Showtoast_Short(getApplicationContext(),"Bạn đã thêm dữ liệu giỏ hàng thành công");
                                            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                            startActivity(intent);
                                            Checkconnection.Showtoast_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng");
                                        }else {
                                            Checkconnection.Showtoast_Short(getApplicationContext(),"Dữ liệu giỏ hàng của bạn đã bị lỗi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0 ; i < HomeActivity.manggiohang.size(); i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",HomeActivity.manggiohang.get(i).getIdsp());
                                                jsonObject.put("tensanpham",HomeActivity.manggiohang.get(i).getTensp());
                                                jsonObject.put("giasanpham",HomeActivity.manggiohang.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham",HomeActivity.manggiohang.get(i).getSoluongsp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);

                }else {
                    Checkconnection.Showtoast_Short(getApplicationContext(),"Hãy kiểm tra lại dữ liệu");
                }
            }
        });
    }
    private void Anhxa() {
        edttenkhachnag = findViewById(R.id.edittexttenkhachhang);
        edtemail = findViewById(R.id.edittextemail);
        edtsdt = findViewById(R.id.edittextsodienthoai);
        btnxacnhan = findViewById(R.id.buttonxacnhan);
        btntrove = findViewById(R.id.buttontrove);
    }
}