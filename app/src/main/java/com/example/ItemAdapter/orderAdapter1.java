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

public class orderAdapter1 extends RecyclerView.Adapter<orderAdapter1.ViewHolder>{
    private List<Order> orderList;
    private ButtonInterface mButtonInterface;
    private ButtonInterface mButtonInterface2;
    private MainOrderActivity mContext;
   // private OnClickListener mOnButtonClickListener;

    public orderAdapter1(List<Order> OrderList)
    {
        orderList=OrderList;
    }
    public void buttonSetOnclick(ButtonInterface buttonInterface){
        this.mButtonInterface=buttonInterface;
    }

    public void buttonSetOnclick2(ButtonInterface buttonInterface){
        this.mButtonInterface2=buttonInterface;
    }

    public interface ButtonInterface{
        public void onclick( View view,int position);
    }

    public interface ButtonInterface2{
        public void onclick( View view,int position);
    }

    public void setmButtonInterface(ButtonInterface mButtonInterface) {
        this.mButtonInterface = mButtonInterface;
    }

   // public interface OnClickListener{
     //   void OnButtonClick
   // }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView userImage;
        TextView userName;
        TextView orderDetails;
        Button reject;
        Button accept;
        public ViewHolder(View view){
            super(view);
            userImage=(ImageView)view.findViewById(R.id.orderUserImage);
            userName=(TextView)view.findViewById(R.id.customerName);
            orderDetails=(TextView)view.findViewById(R.id.orderDetails);
            reject=(Button)view.findViewById(R.id.orderReject);
            accept=(Button)view.findViewById(R.id.orderAccept);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order1,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Order order=orderList.get(position);
        holder.userImage.setImageResource(R.drawable.ic_launcher_background);
        holder.userName.setText(order.getCustomerName());
        holder.orderDetails.setText(order.getOrderItemName());
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButtonInterface!=null){
                    mButtonInterface.onclick(v,position);
                }
            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
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
