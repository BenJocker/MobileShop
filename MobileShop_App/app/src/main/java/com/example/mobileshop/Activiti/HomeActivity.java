package com.example.mobileshop.Activiti;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileshop.Admin.AdminActivity;
import com.example.mobileshop.R;
import com.example.mobileshop.adapter.Giohang;
import com.example.mobileshop.adapter.LoaispAdapter;
import com.example.mobileshop.adapter.SanphamAdapter;
import com.example.mobileshop.model.Loaisp;
import com.example.mobileshop.model.Sanpham;
import com.example.mobileshop.ultil.Checkconnection;
import com.example.mobileshop.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh, RecyclerView;
    NavigationView navigationView;
    ImageView imgSmartphone, imgLaptop, imgBill, imgAccount;
    ListView listViewmanhinhchinh;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    private DrawerLayout drawer;
    SanphamAdapter sanphamAdapter;
    ArrayList<Sanpham> mangsanpham;
    public static ArrayList<Giohang> manggiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AnhXa();
        if (Checkconnection.haveNetworkConnection(getApplicationContext())) {
            ActionViewFliper();
            GetDuLieuSPMoiNhat();
        } else {
            Checkconnection.Showtoast_Short(getApplicationContext(), "Bạn hãy kiễm tra lại kết nối");
            finish();
        }
        ActionViewFliper();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        navView.bringToFront();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), com.example.mobileshop.Activiti.Giohang.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
    private void GetDuLieuSPMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdansanphammoinhat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                    int ID = 0;
                    String Tensanpham = "";
                    Integer Giasanpham = 0;
                    String Hinhanhsanpham = "";
                    String Motasanpham = "";
                    int IDsanpham = 0;

                    Log.d("test1",response.toString());
                    try {
                    JSONArray jsonArr = new JSONArray(response);
                    for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObject = jsonArr.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tensanpham = jsonObject.getString("tensp");
                            Giasanpham = jsonObject.getInt("giasp");
                            Hinhanhsanpham = jsonObject.getString("hinhanhsp");
                            Motasanpham = jsonObject.getString("motasp");
                            IDsanpham = jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(ID, Tensanpham, Giasanpham, Hinhanhsanpham, Motasanpham, IDsanpham,false));
                        sanphamAdapter.notifyDataSetChanged();
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loi",error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }
    private void ActionViewFliper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn.tgdd.vn/2020/12/banner/Normal-Note20-800-300-800x300-3.png");
        mangquangcao.add("https://cdn.tgdd.vn/2020/11/banner/iphone-12-800-300-800x300-2.png");
        mangquangcao.add("https://cdn.tgdd.vn/2020/12/banner/C17-800-300-800x300.png");
        mangquangcao.add("https://cdn.tgdd.vn/2020/12/banner/reno4-800-300-800x300-2.png");
        mangquangcao.add("https://cdn.tgdd.vn/2020/12/banner/800-300-800x300-1.png");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }
    private void AnhXa() {
        imgSmartphone = findViewById(R.id.img_smartphone);
        imgSmartphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this,DienThoaiActivity.class);
                startActivity(i);
            }
        });

        imgLaptop = findViewById(R.id.img_laptop);
        imgLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this,LapTopActivity.class);
                startActivity(i);
            }
        });
        imgBill = findViewById(R.id.img_bill);
        imgBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(HomeActivity.this,LienHeActivity.class);
                startActivity(ii);
            }
        });
        imgAccount = findViewById(R.id.img_account);
        imgAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iii = new Intent(HomeActivity.this,ThongTinActivity.class);
                startActivity(iii);
            }
        });
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerViewmanhinhchinh = findViewById(R.id.recyclerView);

      //  navigationView = findViewById(R.id.navigationView);
        listViewmanhinhchinh = findViewById(R.id.listViewmanhinhchinh);
        mangloaisp = new ArrayList<>();
        mangsanpham= new ArrayList<>();
        loaispAdapter = new LoaispAdapter(mangloaisp, getApplicationContext());
        sanphamAdapter = new SanphamAdapter(this,mangsanpham);
        listViewmanhinhchinh.setAdapter(loaispAdapter);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewmanhinhchinh.setAdapter(sanphamAdapter);
        if (manggiohang != null) {

        } else {
            manggiohang = new ArrayList<>();
        }

    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        item.setChecked(true);

        switch (item.getItemId()){
            case R.id.nav_tentaikhoan:
                Intent intent = new Intent(HomeActivity.this, ThongTinTaikhoanActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_lichsu:
                Intent intent1 = new Intent(HomeActivity.this, LichSuMuaHangActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_doimatkhau:
                Intent intent2 = new Intent(HomeActivity.this, DoiMatKhauActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_lienketxahoi:
                Intent intent3 = new Intent(HomeActivity.this, LienKetMangXaHoiActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_admin:
                Intent intent4 = new Intent(HomeActivity.this, AdminActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_thoat:

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Bạn có chắc muốn thoát!");
                builder.setMessage("Hãy lựa chọn bên dưới để xác nhận");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
