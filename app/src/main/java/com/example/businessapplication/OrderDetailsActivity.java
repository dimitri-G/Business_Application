package com.example.businessapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ClassBean.Order;
import com.example.ClassBean.OrderRelate;
import com.example.ClassBean.Shop;
import com.example.ClassBean.Userdetails;
import com.example.ItemAdapter.orderRelateAdapter;

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

public class OrderDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private List<OrderRelate> ORList = new ArrayList<>();
    private Order order;
    private TextView orderState;
    private TextView orderTip;
    private TextView orderShopName;
    private TextView userTelephone;
    private TextView order_sumprice;

    private Shop shop;
    private String flag="empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Intent it=getIntent();
        final String orderNum=it.getStringExtra("order");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String a=postwebinfo(orderNum);
                String[] spite2=a.split(",");
                order=new Order(spite2[0],spite2[1],spite2[2],
                        Integer.parseInt(spite2[3]),spite2[4],R.drawable.ic_launcher_background,spite2[8]);
                shop=new Shop(spite2[4],spite2[5],spite2[6],spite2[7]);
                ORList=order.getOR();
            }
        }).start();
        while (ORList.isEmpty());
        initView();
        ListView listView=(ListView)findViewById(R.id.order_detail_listview);
        orderRelateAdapter adapter=new orderRelateAdapter(OrderDetailsActivity.this,
                R.layout.item_orderrelate,ORList);
        listView.setAdapter(adapter);
    }
    public void initView(){
        orderState=(TextView)findViewById(R.id.order_state);
        orderTip=(TextView)findViewById(R.id.order_tip);
        orderShopName=(TextView)findViewById(R.id.order_shop_name);
        userTelephone=(TextView)findViewById(R.id.customer_telephone);
        order_sumprice=(TextView)findViewById(R.id.order_sumprice);

        String[] state={"已取消","已下单","已接受","制作完成","商家确认领取","已完成","投诉中","已评价"};
        orderState.setText(state[order.getState()]);
        orderTip.setText("订单号:"+order.getOrderId()+" 下单时间:"+order.getOrderTime());
        orderShopName.setText(order.getShopName());
        userTelephone.setText("顾客电话："+shop.getTelephone());
        order_sumprice.setText("总计：￥"+order.getSumprices());






    }

    public void onClick(View v){
        switch (v.getId()){


            default:
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

    public String postwebinfo(String keyword){
        String str="";String a = "";
        try {

            //1,找水源--创建URL
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/OrderDetailServlet");//放网站
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

            String data = "orderNum="+ URLEncoder.encode(keyword,"UTF-8");
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

    public String postwebinfo2(String orderNum,int state){
        String str="";String a = "";
        try {

            //1,找水源--创建URL
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/ChangeOrderStateServlet");//放网站
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

            String data = "orderNum="+ URLEncoder.encode(orderNum,"UTF-8")
                    +"&state="+URLEncoder.encode(String.valueOf(state),"UTF-8");

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
