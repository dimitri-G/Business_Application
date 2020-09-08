package com.example.ItemAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ClassBean.Order;
import com.example.Fragment.OrderFragment3;
import com.example.businessapplication.MainOrderActivity;
import com.example.businessapplication.R;

import java.util.List;

public class orderAdapter3 extends RecyclerView.Adapter<orderAdapter3.ViewHolder> implements View.OnClickListener {
    private List<Order> orderList;
    private OrderFragment3 mContext;
    private OnItemClickListener mOnItemClickListener=null;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void OnItemClick(View v,int position);
    }



    static class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView UserImage;
        TextView UserName;
        TextView OrderState;
        TextView ShopRelate;
        TextView OrderSumprice;

        public ViewHolder(View view){
            super(view);
            UserImage = (ImageView) view.findViewById(R.id.orderUserImage3);
            UserName = (TextView) view.findViewById(R.id.customerName3);
            OrderState = (TextView) view.findViewById(R.id.orderState);
            ShopRelate = (TextView) view.findViewById(R.id.orderDetails3);
            OrderSumprice=(TextView)view.findViewById(R.id.orderSumprice);
        }
    }

    public orderAdapter3(List<Order> OrderList, OrderFragment3 _mContext){
        this.orderList=OrderList;
        mContext=_mContext;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order3,parent,false);
        ViewHolder holder =new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        String[] state={"已取消","已下单","已接受","制作完成","商家确认领取","已完成","投诉中","已评价"};
        final OrderFragment3 activity =mContext;
        final Order order=orderList.get(position);
        holder.UserImage.setImageResource(order.getShopImage());
        holder.UserName.setText(order.getShopName());
        holder.OrderState.setText(state[order.getState()]);
        holder.ShopRelate.setText(order.getOrderItemName());
        holder.OrderSumprice.setText(order.getSumprices());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
    public void onClick(View v) {
        int position = (int)v.getTag();
        //int position = v.getChildAdapterPosition(v);
        if(mOnItemClickListener!=null){
            //Toast.makeText(mContext, "Click " + orderList.get((int)v.getTag()), Toast.LENGTH_SHORT).show();
            mOnItemClickListener.OnItemClick(v,position);
        }
    }
}
