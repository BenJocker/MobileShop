package com.example.mobileshop.Activiti;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileshop.R;
import com.example.mobileshop.adapter.LoaispAdapter;
import com.example.mobileshop.model.Loaisp;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ProgressBar loading;
    EditText email, Password;
    Button btnLogin, btnRegister;
    private static final String URL_LOGIN = "http://192.168.1.130:90/server/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActiviti.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = email.getText().toString().trim();
                String mPass = Password.getText().toString().trim();
                if (!mEmail.isEmpty() || !mPass.isEmpty()) {
                    Login(mEmail, mPass);
                } else {
                    email.setError("Please insert email");
                    Password.setError("Please insert password");
                }
            }

            private void Login(final String email, final String password) {
                loading.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");
                                    String message = jsonObject.getString("message");
                                    Log.d("success", success);
                                    if (success.equals("1")) {
                                        JSONObject jsonArray = jsonObject.getJSONObject("login");
                                        String email = jsonArray.getString("email").trim();
                                        String password = jsonArray.getString("password").trim();


                                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                        intent.putExtra("email", email);
                                        intent.putExtra("password", password);
                                        startActivity(intent);

                                        loading.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    loading.setVisibility(View.GONE);
                                    btnLogin.setVisibility(View.VISIBLE);
                                    Log.e("err", e.toString());
                                    Toast.makeText(MainActivity.this, "erro" + e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loading.setVisibility(View.GONE);
                                btnLogin.setVisibility(View.VISIBLE);
                                Toast.makeText(MainActivity.this, "erro" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("password", password);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);
            }
        });
    }

    private void AnhXa() {
        email = findViewById(R.id.email);
        Password = findViewById(R.id.Password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.link_regist);
        loading = findViewById(R.id.loading);


    }
}