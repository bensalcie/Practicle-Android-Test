package dev.bensalcie.practicletask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import dev.bensalcie.practicletask.preferences.SharedPrefManager;

public class MainActivity extends AppCompatActivity {
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvName=findViewById(R.id.tvName);
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
        String username= SharedPrefManager.getInstance(MainActivity.this).getUser().getCustomername();
        tvName.setText(String.format("Welcome, %s", username));

    }

    public void logoutThisUser(View view) {
        SharedPrefManager.getInstance(MainActivity.this).logout();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}