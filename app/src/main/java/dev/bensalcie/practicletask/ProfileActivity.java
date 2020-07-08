package dev.bensalcie.practicletask;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import dev.bensalcie.practicletask.preferences.SharedPrefManager;
import dev.bensalcie.practicletask.preferences.User;

public class ProfileActivity extends AppCompatActivity {
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView tvName = findViewById(R.id.tvName);
        TextView tvAccount = findViewById(R.id.tvCustomerAccount);
        TextView tvEmail = findViewById(R.id.tvAccountEmail);
        TextView tvCustomerID = findViewById(R.id.tvCustomerId);
        User user = SharedPrefManager.getInstance(this).getUser();

        tvName.setText(user.getCustomername());
        tvAccount.setText(user.hashCode());
        tvCustomerID.setText(String.format("Customer Id :%d", user.getCustomerid()));
        tvEmail.setText(user.getCustomeremail());


    }
}