package dev.bensalcie.practicletask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {
    private TextView tvName;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvName=findViewById(R.id.tvName);
        user= SharedPrefManager.getInstance(this).getUser();
    }

    public void sendMoney(View view) {
        startActivity(new Intent(this,SendMoneyActivity.class));
        finish();
    }

    public void seeLastTransactions(View view) {
        startActivity(new Intent(this,StatementActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String username= user.getCustomername();
        tvName.setText(String.format("Welcome, %s", username));

    }

    public void logoutThisUser(View view) {
        SharedPrefManager.getInstance(MainActivity.this).logout();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    public void showBalance(View view) {

        Button btn =view.findViewById(R.id.btnBalance);
        btn.setText("Checking balance...");
        String balance= getAccountBalance(user.getCustomerid());
        btn.setText("Check balance");


        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Account Balance");
         builder.setMessage("Account Balance :"+balance);
         builder.setPositiveButton("OKEY",null);
         builder.show();

    }

    private String getAccountBalance(long customerid) {
        final String[] balance = {""};

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MyConstants.BASE_URL+"accounts/balance",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", "Response: "+response);


                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            balance[0] = obj.getString("balance");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response", "Response: "+error);
                        Toast.makeText(getApplicationContext(), "Something unusual happened", Toast.LENGTH_SHORT).show();

                    }
                })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("customerId", ""+user.getCustomerid());
                params.put("accountNo", user.getCutomeraccount());
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        return balance[0];
    }
}