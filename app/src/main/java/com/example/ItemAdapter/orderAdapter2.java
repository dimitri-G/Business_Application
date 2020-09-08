package com.example.ItemAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ClassBean.Order;
import com.example.businessapplication.MainOrderActivity;
import com.example.businessapplication.R;

import java.util.List;

public class orderAdapter2 extends RecyclerView.Adapter<orderAdapter2.ViewHolder>{
    private List<Order> orderList;
    private ButtonInterface mButtonInterface;
    private ButtonInterface mButtonInterface2;
    private MainOrderActivity mContext;
    // private OnClickListener mOnButtonClickListener;

    public orderAdapter2(List<Order> OrderList)
    {
        orderList=OrderList;
    }
    public void buttonSetOnclick(orderAdapter2.ButtonInterface buttonInterface){
        this.mButtonInterface=buttonInterface;
    }

    public void buttonSetOnclick2(orderAdapter2.ButtonInterface buttonInterface){
        this.mButtonInterface2=buttonInterface;
    }

    public interface ButtonInterface{
        public void onclick( View view,int position);
    }

    public interface ButtonInterface2{
        public void onclick( View view,int position);
    }

    public void setmButtonInterface(orderAdapter2.ButtonInterface mButtonInterface) {
        this.mButtonInterface = mButtonInterface;
    }

    // public interface OnClickListener{
    //   void OnButtonClick
    // }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView userImage;
        TextView userName;
        TextView orderDetails;
        Button finish;
        Button arrive;
        public ViewHolder(View view){
            super(view);
            userImage=(ImageView)view.findViewById(R.id.orderUserImage2);
            userName=(TextView)view.findViewById(R.id.customerName2);
            orderDetails=(TextView)view.findViewById(R.id.orderDetails2);
            finish=(Button)view.findViewById(R.id.orderFinish);
            arrive=(Button)view.findViewById(R.id.orderArrive);

        }
    }

    @NonNull
    @Override
    public orderAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order2,parent,false);
        orderAdapter2.ViewHolder holder=new orderAdapter2.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull orderAdapter2.ViewHolder holder, final int position) {
        Order order=orderList.get(position);
        holder.userImage.setImageResource(R.drawable.ic_launcher_background);
        holder.userName.setText(order.getCustomerName());
        holder.orderDetails.setText(order.getOrderItemName());

        if(order.getState()==2)
            holder.arrive.setVisibility(View.GONE);
        if(order.getState()==3)
            holder.finish.setVisibility(View.GONE);
        holder.finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButtonInterface!=null){
                    mButtonInterface.onclick(v,position);
                }
            }
        });

        holder.arrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButtonInterface2!=null){
                    mButtonInterface2.onclick(v,position);
                }
            }
        });

    }

    public int getItemCount(){
        return orderList.size();
    }
}
