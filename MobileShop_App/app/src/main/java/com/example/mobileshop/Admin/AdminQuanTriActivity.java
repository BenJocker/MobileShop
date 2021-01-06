package com.example.mobileshop.Admin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileshop.R;
import com.example.mobileshop.adapter.ShowAdapter;
import com.example.mobileshop.model.ShowSanPham;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class AdminQuanTriActivity extends AppCompatActivity {
   Toolbar toolbarsanpham;
    ListView lvsp;
    ArrayList<ShowSanPham> arraySp;
    ShowAdapter adapter;
    View footerview;
    int idsanpham = 0;
    boolean limitadata = false;
    String urlGetData = "http://192.168.1.130:90/server/getdata.php";
    String urlDelete ="http://192.168.1.130:90/server/delete.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_quan_tri);
        lvsp = findViewById(R.id.lvsp);
        toolbarsanpham = findViewById(R.id.toolbarsanpham);
        arraySp = new ArrayList<>();
        GetData(urlGetData);
        adapter = new ShowAdapter(this,R.layout.dong_san_pham,arraySp);
        lvsp.setAdapter(adapter);
        GetData(urlGetData);
        Anhxa();
      ActionToolbar();
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
                        arraySp.clear();
                        int id = 0;
                        String Tendt = "";
                        int Giadt = 0;
                        String Hinhanhdt = "";
                        String Mota = "";
                        int Idspdt = 0;
                        if (response != null && response.length() !=0) {
                            lvsp.removeFooterView(footerview);
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    id = jsonObject.getInt("ID");
                                    Tendt = jsonObject.getString("Tensp");
                                    Giadt = jsonObject.getInt("Giasp");
                                    Hinhanhdt = jsonObject.getString("Hinhanhsp");
                                    Mota = jsonObject.getString("Mota");
                                    Idspdt = jsonObject.getInt("idsanpham");
                                    arraySp.add(new ShowSanPham(id, Tendt, Giadt, Hinhanhdt, Mota, Idspdt));
                                    adapter.notifyDataSetChanged();// cập nhật lại
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdminQuanTriActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("idsanpham",String.valueOf(idsanpham));
                return param;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }
    public void DeleteSanPhan(final int idsp){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(AdminQuanTriActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            GetData(urlGetData);
                        }else {
                            Toast.makeText(AdminQuanTriActivity.this,   "Lỗi xóa!", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdminQuanTriActivity.this, "Xáy ra lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idsanpham", String.valueOf(idsp));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void ActionToolbar() {
        setSupportActionBar(toolbarsanpham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarsanpham.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thong_tin,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuadd:
                Intent i = new Intent(AdminQuanTriActivity.this, ThemActivity.class);
                startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }
}
