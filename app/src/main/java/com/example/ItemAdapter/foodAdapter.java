package com.example.ItemAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ClassBean.Food;
import com.example.businessapplication.R;

import java.util.List;

public class foodAdapter extends ArrayAdapter<Food> {
    private int resourceId;

    public foodAdapter(Context context, int textViewResourceId, List<Food> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Food food = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView foodImage = view.findViewById(R.id.food_image);
        TextView foodName=view.findViewById(R.id.food_name);
        TextView foodPrice=view.findViewById(R.id.food_price);
        //foodAddr.setVisibility(View.GONE);
        foodImage.setImageResource(food.getImageId());
        foodName.setText(food.getFoodName());
        foodPrice.setText(String.valueOf(food.getFoodPrice()));

        return view;
    }
}
