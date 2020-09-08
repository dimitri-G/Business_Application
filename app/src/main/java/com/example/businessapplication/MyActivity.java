package com.example.businessapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ClassBean.Userdetails;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        TextView myName=findViewById(R.id.myName);
        ImageView imageView=findViewById(R.id.imageView);
        Button update=findViewById(R.id.updateProvider);
        Button logout=findViewById(R.id.logout);

        myName.setText(Userdetails.getname());

        update.setOnClickListener(this);
        logout.setOnClickListener(this);
        Toast.makeText(MyActivity.this, Userdetails.getname(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.updateProvider:
                //Userdetails.logout();
                Intent it2=new Intent(MyActivity.this,UpdateProviderActivity.class);
                startActivity(it2);
                //finish();


                break;
            case R.id.logout:
                Userdetails.logout();
                Intent it=new Intent(MyActivity.this,MainActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(it);
                finish();
                break;

            case R.id.orders:
                Intent toorder=new Intent(MyActivity.this,MainOrderActivity.class);
                startActivity(toorder);
                break;


            case R.id.mangers:
                Intent tomanger=new Intent(MyActivity.this,MainMangerActivity.class);
                startActivity(tomanger);
                break;
        }
    }
}
