package com.example.mobileshop.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileshop.R;

public class AdminActivity extends AppCompatActivity {
    EditText edttenadmin, edtpassadmin;
    Button btndangnhap, btnthoat;
    String ten , mk;
    Toolbar toolbarlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Anhxa();
        ControlButton();
        ActionBar();
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edttenadmin.getText().length() != 0 && edtpassadmin.getText().length() !=0){
                    if (edttenadmin.getText().toString().equals(ten) && edtpassadmin.getText().toString().equals(mk)){
                        Toast.makeText(AdminActivity.this, "Bạn đã đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminActivity.this, AdminQuanTriActivity.class);
                        startActivity(intent);
                    }else if (edttenadmin.getText().toString().equals("Admin") && edtpassadmin.getText().toString().equals("Admin")){
                        Toast.makeText(AdminActivity.this, "Bạn đã đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminActivity.this,AdminQuanTriActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(AdminActivity.this, "Bạn đã đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AdminActivity.this, "Mời bạn nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // layout nút thoát dialog
    private void ControlButton() {
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog);
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
            }
        });
    }
    private void Anhxa() {
        edttenadmin  = findViewById(R.id.edttenadmin);
        edtpassadmin = findViewById(R.id.edtpassadmin);
        btndangnhap  = findViewById(R.id.btndangnhap);
        btnthoat     = findViewById(R.id.btnthoat);
        toolbarlog = findViewById(R.id.toolbarlog);
    }
    private void ActionBar() {
        setSupportActionBar(toolbarlog);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlog.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}