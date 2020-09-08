package com.example.businessapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name;
    EditText pwd;
    Button register;
    Button lostPwd;
    Button login;
    String flag="empty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText)findViewById(R.id.providerName);
        pwd=(EditText)findViewById(R.id .providerPwd);

        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(this);

        lostPwd=(Button)findViewById(R.id.register);
        lostPwd.setOnClickListener(this);

        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                Intent it=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(it);
                break;
            case R.id.lostPwd:

                break;
            case R.id.login:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        flag=postwebinfo();

                    }
                }).start();
                while (flag.equals("empty"));
                if(flag.equals("ERROR")){
                    Toast.makeText(MainActivity.this, "请检查用户名，密码是否有误",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Userdetails.getLoginUser(name.getText().toString());
                    Toast.makeText(MainActivity.this, "登录成功",
                            Toast.LENGTH_SHORT).show();
                    Intent toLoginMain = new Intent(MainActivity.this,MainOrderActivity.class);
                    startActivity(toLoginMain);
                }
                break;
            default:
                break;
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
    public String postwebinfo()
    {

        String str="";
        try {
            //1,找水源--创建URL
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/ProviderLoginServlet");//放网站
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
            /*
            conn.setUseCaches(false);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RegisterActivity.this, "id_cardans=\n"+id_cardans
                            +"\net_type=\n"+et_type.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                }
            });

             */
            //我们请求的数据


            String data = "providerid="+ URLEncoder.encode(name.getText().toString(),"UTF-8")
                    +"&providerPwd="+URLEncoder.encode(pwd.getText().toString(),"UTF-8")
                    ;
            //獲取輸出流
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            conn.connect();

            if (conn.getResponseCode() == 200||1==1) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                str = inputStream2String(is);
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                Message msg = new Message();
                msg.what = 1;
                msg.obj = message.toString();
                Log.e("WangJ", message.toString());

            }
            else
            {
                conn.setUseCaches(false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "网络错误",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return str;
        }
    }


}
