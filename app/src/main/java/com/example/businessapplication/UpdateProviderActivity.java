package com.example.businessapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ClassBean.Userdetails;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class UpdateProviderActivity extends AppCompatActivity implements View.OnClickListener {
    EditText updateName;
    EditText updatePwd;
    EditText updateAdr;
    EditText updateRemark;
    EditText updateTelephone;
    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_provider);

        updateName=findViewById(R.id.updatePName);
        updatePwd=findViewById(R.id.updatePwd);
        updateAdr=findViewById(R.id.updateAdr);
        updateRemark=findViewById(R.id.updateRemark);
        updateTelephone=findViewById(R.id.updatePhone);
        Button updateSub=findViewById(R.id.updateSub);
        updateSub.setOnClickListener(this);

        new Thread(new Runnable(){
            @Override
            public void run() {
                a=postwebinfo2();
                String[] spite=a.split(",");
                updateName.setText(spite[0]);
                updatePwd.setText(spite[1]);
                updateAdr.setText(spite[2]);
                updateRemark.setText(spite[3]);
                updateTelephone.setText(spite[4]);
            }
        }
        ).start();
        Toast.makeText(UpdateProviderActivity.this, Userdetails.getname(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.updateSub:
                if(updateName.getText().toString().equals("")) {
                   Toast.makeText(UpdateProviderActivity.this, "菜品名不可为空", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            postwebinfo();
                        }
                    }
                    ).start();
                    //while (flag.equals(""));
                    Toast.makeText(UpdateProviderActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                    Intent toOrderDetail =new Intent(UpdateProviderActivity.this,MyActivity.class);
                    toOrderDetail.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    toOrderDetail.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toOrderDetail);
                }
                finish();

        }
    }


    public static String inputStream2String (InputStream in) throws IOException {

        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        int n;
        while ((n = in.read(b)) != -1) {
            out.append(new String(b, 0, n));
        }
        Log.i("String的长度", new Integer(out.length()).toString());
        return out.toString();
    }

    public String postwebinfo(){
        String str="";String a = "";
        try {

            //1,找水源--创建URL
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/UpdateProviderServlet");//放网站
            //URL url = new URL("http://192.168.91.1:8080/dyl/register");//放网站手机
            //URL url = new URL("http://"+MainActivity.URL_DOMAIN+":8080/dyl/buyticket");//放网站电脑
            //2,开水闸--openConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式
            conn.setRequestMethod("POST");
            //设置超时信息
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            //设置允许输入
            conn.setDoInput(true);
            //设置允许输出
            conn.setDoOutput(true);
            //post方式不能设置缓存，需手动设置为false
            //子线程中显示Toast
            Intent it2=getIntent();
            String shopid=it2.getStringExtra("123");


            //我们请求的数据

            String data = "providerId="+ URLEncoder.encode(Userdetails.getname().toString(),"UTF-8")+
                    "&providerName="+URLEncoder.encode(updateName.getText().toString(),"UTF-8")
                    +"&providerPwd="+URLEncoder.encode(updatePwd.getText().toString(),"UTF-8")
                    +"&providerAdr="+URLEncoder.encode(updateAdr.getText().toString(),"UTF-8")
                    +"&providerRemark="+URLEncoder.encode(updateRemark.getText().toString(),"UTF-8")
                    +"&providerPhone="+URLEncoder.encode(updateTelephone.getText().toString(),"UTF-8");
            //獲取輸出流
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();

            if (conn.getResponseCode() == 200 || 1 == 1) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                a = inputStream2String(is);
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }

                // 释放资源
                /*Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = a;
                handler.sendMessage(msg);
                Log.e("WangJ", message.toString());*/
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return a;
        }
    }
    public String postwebinfo2(){
        String str="";String a = "";
        try {

            //1,找水源--创建URL
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/GetProviderServlet");//放网站
            //URL url = new URL("http://192.168.91.1:8080/dyl/register");//放网站手机
            //URL url = new URL("http://"+MainActivity.URL_DOMAIN+":8080/dyl/buyticket");//放网站电脑
            //2,开水闸--openConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式
            conn.setRequestMethod("POST");
            //设置超时信息
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            //设置允许输入
            conn.setDoInput(true);
            //设置允许输出
            conn.setDoOutput(true);
            //post方式不能设置缓存，需手动设置为false
            //子线程中显示Toast
            Intent it2=getIntent();
            String shopid=it2.getStringExtra("123");


            //我们请求的数据

            String data = "providerId="+ URLEncoder.encode(Userdetails.getname().toString(),"UTF-8");
            //獲取輸出流
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();

            if (conn.getResponseCode() == 200 || 1 == 1) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                a = inputStream2String(is);
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }

                // 释放资源
                /*Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = a;
                handler.sendMessage(msg);
                Log.e("WangJ", message.toString());*/
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return a;
        }
    }
}
