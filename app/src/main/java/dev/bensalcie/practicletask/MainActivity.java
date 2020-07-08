package dev.bensalcie.practicletask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMoney(View view) {
        startActivity(new Intent(this,SendMoneyActivity.class));
        finish();
    }

    public void seeLastTransactions(View view) {
        startActivity(new Intent(this,StatementActivity.class));
        finish();
    }
}