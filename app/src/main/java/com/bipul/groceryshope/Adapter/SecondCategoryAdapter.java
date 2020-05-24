package com.bipul.groceryshope.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bipul.groceryshope.R;
import com.bipul.groceryshope.activity.ProductDetailsActivity;
import com.bipul.groceryshope.model.SecondCategory;

import java.util.List;

public class SecondCategoryAdapter extends RecyclerView.Adapter<SecondCategoryAdapter.ViewHolder> {

    private Context context;
    private List<SecondCategory>categoryList;
    int count=0;

    public SecondCategoryAdapter(Context context, List<SecondCategory> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.second_category_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SecondCategory secondCategory = categoryList.get(position);
        holder.productNameTV.setText(secondCategory.getProductName());
        holder.productPriceTV.setText(secondCategory.getProductPrice());
        //holder.productQuantityTV.setText(secondCategory.getProductQuantity());
        holder.productImageIV.setImageResource(secondCategory.getProductImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ProductDetailsActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageIV,increaseQuantity,reduceQuantity;
        TextView productNameTV, productQuantityTV,productPriceTV,itemQuantity,addtobag;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            productImageIV  = itemView.findViewById(R.id.imageProduct);
            productNameTV = itemView.findViewById(R.id.productNameTV);
            productPriceTV = itemView.findViewById(R.id.productPriceTV);
            //productQuantityTV = itemView.findViewById(R.id.productQuantityTV);
            increaseQuantity = itemView.findViewById(R.id.increaseQuantity);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            reduceQuantity = itemView.findViewById(R.id.reduceQuantity);
            addtobag = itemView.findViewById(R.id.addtobag);


            increaseQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    itemQuantity.setVisibility(View.VISIBLE);
                    reduceQuantity.setVisibility(View.VISIBLE);
                    addtobag.setVisibility(View.GONE);
                    itemQuantity.setText(String.valueOf(count));

                }
            });

            reduceQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count--;
                    itemQuantity.setText(String.valueOf(count));
                    if (count==0){
                        addtobag.setVisibility(View.VISIBLE);
                        itemQuantity.setVisibility(View.GONE);
                        reduceQuantity.setVisibility(View.GONE);
                    }


                }
            });

        }
    }
}
