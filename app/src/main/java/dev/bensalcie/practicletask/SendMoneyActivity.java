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

public class SendMoneyActivity extends AppCompatActivity {
    private EditText etAccountTo,etAmount;
    private Button btnSend;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        etAccountTo = findViewById(R.id.editTextTextPersonName);
        etAmount = findViewById(R.id.editTextTextPersonName2);
        btnSend = findViewById(R.id.btnSend);
        user= SharedPrefManager.getInstance(this).getUser();


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountto =etAccountTo.getText().toString();
                String amount = etAmount.getText().toString();
                if (!TextUtils.isEmpty(accountto ) && !TextUtils.isEmpty(amount))
                {
                    btnSend.setText("Processing...");
                    sendMoney(accountto,amount);
                }
            }
        });
    }

    private void sendMoney(final String accountto, String amount) {
        JSONObject jsonWholeObject =new  JSONObject();

//        try {
//
//            jsonWholeObject.put("customerId",user.getCustomerid());
//            jsonWholeObject.put("accountFrom", user.getCutomeraccount());
//            jsonWholeObject.put("accountTo",accountto);
//            jsonWholeObject.put("amount", amount);
//            String requestBody = jsonWholeObject.toString();
//



            StringRequest stringRequest = new StringRequest(Request.Method.POST, MyConstants.BASE_URL+"customers/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", "Response: "+response);


                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            boolean statuscheck = obj.getBoolean("response_status");


                            if (statuscheck) {
                                String message = obj.getString("response_message");

                                Toast.makeText(SendMoneyActivity.this, message, Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();

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

                        Toast.makeText(getApplicationContext(), "Something unusual happened", Toast.LENGTH_SHORT).show();

                    }
                })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("customerId", ""+user.getCustomerid());
                params.put("accountFrom", user.getCutomeraccount());
                params.put("accountTo", accountto);
                params.put("amount", accountto);
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}