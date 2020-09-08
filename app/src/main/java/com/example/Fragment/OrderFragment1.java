package com.example.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ClassBean.Order;
import com.example.ClassBean.Userdetails;
import com.example.ItemAdapter.orderAdapter1;
import com.example.businessapplication.MainOrderActivity;
import com.example.businessapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public class OrderFragment1 extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private  boolean run=false;
    String flag="empty";
    int flag2=0;
    View view;
    private List<Order> orderList=new ArrayList<>();
     Handler handler;
     Handler handler2=new Handler();
    FragmentManager fm;
    public OrderFragment1(FragmentManager fm) {
        this.fm=fm;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OrderFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public OrderFragment1 newInstance(FragmentManager fm) {
        OrderFragment1 fragment = new OrderFragment1(fm);
        Bundle args = new Bundle();
        this.fm=fm;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_order1, container, false);
        RecyclerView orderRecycler1=view.findViewById(R.id.orderRecycler1);
        orderRecycler1.setLayoutManager(new LinearLayoutManager(getActivity()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                Order order=new Order();
                String a=postwebinfo(Userdetails.getname(),1);
                if(!a.equals("empty"))
                {
                    String[] spite1=a.split("#");
                    for(int i=0;i<spite1.length;i++){
                        String[] spite2=spite1[i].split(",");
                        int imageid=R.drawable.ic_launcher_background;
                        /*switch (i%5+1){
                            case 1:imageid=R.drawable.shop1;break;
                            case 2:imageid=R.drawable.shop2;break;
                            case 3:imageid=R.drawable.shop3;break;
                            case 4:imageid=R.drawable.shop4;break;
                            case 5:imageid=R.drawable.shop5;break;
                        }*/
                        order=new Order(spite2[0],spite2[1],spite2[2],
                                Integer.parseInt(spite2[3]),spite2[4],imageid,spite2[5]);
                        orderList.add(order);}
                }
                else{
                    flag2=1;
                }
            }
        }).start();
        while(orderList.isEmpty()&&flag2==0);

        orderAdapter1 orderAdapter1=new orderAdapter1(orderList);
        orderRecycler1.setAdapter(orderAdapter1);
        if(flag2==0){
            TextView empty=view.findViewById(R.id.empty1);
            empty.setVisibility(View.GONE);
        }
        else{
            TextView empty=view.findViewById(R.id.empty1);
            empty.setText("目前还没有收到订单哦");
        }
        flag2=0;
        orderAdapter1.buttonSetOnclick(new orderAdapter1.ButtonInterface() {
            @Override
            public void onclick(View view, final int position) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        flag=postwebinfo2(orderList.get(position).getOrderId(), 0);
                    }}).start();
                //while (flag.equals("empty"));
                initview();
            }
        });
        orderAdapter1.buttonSetOnclick2(new orderAdapter1.ButtonInterface() {
            @Override
            public void onclick(View view, final int position) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        flag=postwebinfo2(orderList.get(position).getOrderId(), 2);
                    }}).start();
                //while (flag.equals("empty"));
                initview();

            }
        });

        handler2.postDelayed(runnable,5000);

        return view;
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            initview();
            handler2.postDelayed(this, 5000);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        handler2.postDelayed(runnable,5000);
    }

    @Override
    public void onPause() {
        super.onPause();

        handler2.removeCallbacks(runnable);
    }

    public void initview(){

        final RecyclerView orderRecycler1=view.findViewById(R.id.orderRecycler1);
        orderRecycler1.setLayoutManager(new LinearLayoutManager(getActivity()));

        new Thread(new Runnable() {
            @Override
            public void run() {

                String a1=postwebinfo(Userdetails.getname(),1);
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = a1;
                handler.sendMessage(msg);
            }
        }).start();
        handler=new Handler(){
            Order order=new Order();

            @Override
            public void handleMessage(Message msg) {
                String a=msg.obj.toString();
                if (!a.equals("empty")) {
                    String[] spite1 = a.split("#");
                    for (int i = 0; i < spite1.length; i++) {
                        String[] spite2 = spite1[i].split(",");
                        int imageid = R.drawable.ic_launcher_background;
                        /*switch (i%5+1){
                            case 1:imageid=R.drawable.shop1;break;
                            case 2:imageid=R.drawable.shop2;break;
                            case 3:imageid=R.drawable.shop3;break;
                            case 4:imageid=R.drawable.shop4;break;
                            case 5:imageid=R.drawable.shop5;break;
                        }*/
                        order = new Order(spite2[0], spite2[1], spite2[2],
                                Integer.parseInt(spite2[3]), spite2[4], imageid, spite2[5]);
                        orderList.add(order);
                    }
                } else {
                    flag2 = 1;
                }

                while (orderList.isEmpty() && flag2 == 0) ;
                flag2 = 0;
                orderAdapter1 orderAdapter1 = new orderAdapter1(orderList);
                orderRecycler1.setAdapter(orderAdapter1);
                orderAdapter1.notifyDataSetChanged();
                MainOrderActivity.refreshF1();
            }};
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

    public String postwebinfo(String nickname,int state){
        String str="";String a = "";
        try {

            //1,找水源--创建URL
            URL url = new URL("http://62.234.119.213:8080/TomcatTest/PrGetOrderSerlvet");//放网站
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
            //Intent it2=getIntent();
            //String shopid=it2.getStringExtra("123");


            //我们请求的数据

            String data = "providername="+ URLEncoder.encode(nickname,"UTF-8")
                    +"&state1="+URLEncoder.encode(String.valueOf(state),"UTF-8")
                    +"&state2="+URLEncoder.encode(String.valueOf(state),"UTF-8");
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
