package dev.bensalcie.practicletask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dev.bensalcie.practicletask.preferences.SharedPrefManager;
import dev.bensalcie.practicletask.preferences.User;
import dev.bensalcie.practicletask.utils.MyConstants;
import dev.bensalcie.practicletask.volley.VolleySingleton;

public class LoginActivity extends AppCompatActivity {
    private EditText etCustomerID,etPin;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCustomerID = findViewById(R.id.editTextTextPersonName);
        etPin = findViewById(R.id.editTextTextPersonName2);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customerId =etCustomerID.getText().toString();
                String customerPin = etPin.getText().toString();
                if (!TextUtils.isEmpty(customerId) && !TextUtils.isEmpty(customerPin)){
                    loginUser(customerId,customerPin);

                }
            }
        });


    }

    private void loginUser(final String customerId, final String customerPin) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MyConstants.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", "Response: "+response);


                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            String statuscheck = obj.getString("status");


                            if (statuscheck.equals("success")) {

                                User user = new User(
                                        obj.getInt("Customer Name"),
                                        obj.getString("Customer ID"),
                                        obj.getString("Customer Email"),
                                        obj.getString("Customer Account")
                                );




                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("category",1));

                            } else {
                                Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response", "Response: "+error);
                        btnLogin.setText("Error");

                        Toast.makeText(getApplicationContext(), "Something unusual happened", Toast.LENGTH_SHORT).show();

                    }
                })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("customerId", customerId);
                params.put("pin", customerPin);
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkIfLoggedIn();

    }
    private void checkIfLoggedIn() {
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}