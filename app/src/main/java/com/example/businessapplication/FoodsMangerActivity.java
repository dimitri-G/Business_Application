package com.example.businessapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ClassBean.Food;
import com.example.ClassBean.Userdetails;
import com.example.ItemAdapter.foodAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FoodsMangerActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Food> foodList=new ArrayList<>();
    String flag="";
    int flag2=0;
    ListView flv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods_manger);
        Button createFood=(Button)findViewById(R.id.createFood);
        createFood.setOnClickListener(this);
        flv=(ListView)findViewById(R.id.foodListView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String a=postwebinfo();
                if(!a.equals("empty"))
                {
                    Food food=new Food();
                    String[] spite1=a.split("#");
                    for(int i=0;i<spite1.length;i++){
                        String[] spite2=spite1[i].split(",");
                        int imageid=R.drawable.ic_launcher_background;
                        food=new Food(spite2[0],spite2[1],Float.parseFloat(spite2[2]),imageid);
                        foodList.add(food);
                }
                }else{
                    flag2=1;
                }



            }
        }).start();
        while (foodList.isEmpty()&&flag2==0);

        foodAdapter foodAdapter=new foodAdapter(FoodsMangerActivity.this,R.layout.item_food,foodList);
        flv.setAdapter(foodAdapter);

        flv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food food=foodList.get(position);
                Intent intent = new Intent(FoodsMangerActivity.this, FoodDetailActivity.class);
                intent.putExtra("123",food.getFoodId());
                //finish();
                startActivity(intent);

            }
        });
        if(flag2==0){
            TextView t=findViewById(R.id.textView17);
            t.setVisibility(View.GONE);
        }else{
            TextView t=findViewById(R.id.textView17);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        foodList.clear();
                        flv = (ListView) findViewById(R.id.foodListView);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String a = postwebinfo();
                                if (!a.equals("empty")) {
                                    Food food = new Food();
                                    String[] spite1 = a.split("#");
                                    for (int i = 0; i < spite1.length; i++) {
                                        String[] spite2 = spite1[i].split(",");
                                        int imageid = R.drawable.ic_launcher_background;
                                        food = new Food(spite2[0], spite2[1], Float.parseFloat(spite2[2]), imageid);
                                        foodList.add(food);
                                    }
                                } else {
                                    flag2 = 1;
                                }


                            }
                        }).start();
                        while (foodList.isEmpty() && flag2 == 0) ;

                        final foodAdapter foodAdapter = new foodAdapter(FoodsMangerActivity.this, R.layout.item_food, foodList);
                        flv.setAdapter(foodAdapter);

                        flv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Food food = foodList.get(position);
                                Intent intent = new Intent(FoodsMangerActivity.this, FoodDetailActivity.class);
                                intent.putExtra("123", food.getFoodId());
                                //finish();
                                startActivity(intent);

                            }
                        });
                        if (flag2 == 0) {
                            TextView t = findViewById(R.id.textView17);
                            t.setVisibility(View.GONE);
                        } else {
                            TextView t = findViewById(R.id.textView17);
                        }
                        // do something
                        foodAdapter.notifyDataSetChanged();
                    }
                });
            }}).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createFood:
                Intent tocreate=new Intent(FoodsMangerActivity.this,CreateFoodActivity.class);
                //finish();

                startActivity(tocreate);

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

    /*public void changeActivity(String shopId){
        Intent intent=new Intent(this, ShoppingCartActivity.class);
        intent.putExtra("123",shopId);
        startActivity(intent);
    }
    public void OnItemClick(View v) {
      changeActivity(shopList.get((Integer) v.getTag()).getShopId());
    }*/

    public String postwebinfo(){
        String str="";String a = "";
        try {

            //1,找水源--创建URL
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/GetFoodServlet");//放网站
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
            //我们请求的数据

            String data = "ProviderId="+ URLEncoder.encode(Userdetails.getname(),"UTF-8");
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
