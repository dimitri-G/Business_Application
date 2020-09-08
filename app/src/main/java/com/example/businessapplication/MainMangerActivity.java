package com.example.businessapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ClassBean.Order;
import com.example.ClassBean.Shop;
import com.example.ClassBean.Userdetails;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainMangerActivity extends AppCompatActivity implements View.OnClickListener {
    String a="empty";
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manger);

        ImageView foodsManger=(ImageView) findViewById(R.id.foodsManager);
        ImageView foodsCommit=(ImageView) findViewById(R.id.foodsCommit);
        ImageView workState=(ImageView)findViewById(R.id.workState);
        ImageView shopInfo=(ImageView)findViewById(R.id.shopInfo);
        shopInfo.setOnClickListener(this);
        workState.setOnClickListener(this);
        foodsCommit.setOnClickListener(this);
        foodsManger.setOnClickListener(this);

        ImageView orders=(ImageView)findViewById(R.id.orders);
        orders.setOnClickListener(this);
        ImageView mangers=(ImageView)findViewById(R.id.mangers);
        mangers.setOnClickListener(this);
        ImageView my=(ImageView)findViewById(R.id.my);
        my.setOnClickListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String a1=postwebinfo();
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = a1;
                handler.sendMessage(msg);


            }
        }).start();

        handler=new Handler(){
           public void handleMessage(Message msg){

               String[] spite2=msg.obj.toString().split(",");
               TextView sumR=findViewById(R.id.sumRaise);
               TextView orderN=findViewById(R.id.orderNums);
               sumR.setText(spite2[0]);
               orderN.setText(spite2[1]);

           }
        };

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.foodsManager:
                Intent toFoodsManger=new Intent(MainMangerActivity.this,FoodsMangerActivity.class);
                startActivity(toFoodsManger);
                break;
            case R.id.foodsCommit:
                Intent toCommit=new Intent(MainMangerActivity.this,CommentActivity.class);
                startActivity(toCommit);
                break;

            case  R.id.workState:
                Intent toA=new Intent(MainMangerActivity.this,AdviceActivity.class);
                startActivity(toA);
                break;

            case R.id.shopInfo:
                Intent toD=new Intent(MainMangerActivity.this,ShopDetailActivity.class);
                startActivity(toD);
                break;

                case R.id.orders:
                        Intent tomanger=new Intent(MainMangerActivity.this,MainOrderActivity.class);
                        startActivity(tomanger);
                        break;

                  case R.id.my:
                        Intent tomy=new Intent(MainMangerActivity.this,MyActivity.class);
                        startActivity(tomy);
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
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/MainMangerServlet");//放网站
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


            String data = "providerid="+ URLEncoder.encode(Userdetails.getname().toString(),"UTF-8");
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


            }
            else
            {
                conn.setUseCaches(false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainMangerActivity.this, "网络错误",
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
