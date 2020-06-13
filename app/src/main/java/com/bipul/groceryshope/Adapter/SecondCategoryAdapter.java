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
import com.bipul.groceryshope.modelForProducts.ProductList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SecondCategoryAdapter extends RecyclerView.Adapter<SecondCategoryAdapter.ViewHolder> {

    private Context context;
    private List<ProductList>productLists;
    int count=0;

    public SecondCategoryAdapter(Context context, List<ProductList> productLists) {
        this.context = context;
        this.productLists = productLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.second_category_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductList productList = productLists.get(position);
        holder.productNameTV.setText(productList.getProductName());
        Picasso.get().load("http://gobazaar.com.bd/public/upload/product/" + productList.getPicture())
                .into(holder.productImageIV);
        int i = productList.getRate();
        holder.productPriceTV.setText(String.valueOf(i));
        holder.unitNameTV.setText(productList.getUnitName());
        holder.upozilaNameTV.setText(productList.getUpazilaName());
        holder.unionNameTV.setText(productList.getUnionName());

    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageIV, increaseQuantity, reduceQuantity;
        TextView productNameTV, productQuantityTV, productPriceTV, itemQuantity, addtobag, upozilaNameTV, unionNameTV, unitNameTV;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            productImageIV = itemView.findViewById(R.id.imageProduct);
            productNameTV = itemView.findViewById(R.id.productNameTV);
            productPriceTV = itemView.findViewById(R.id.rateOfProductTV);
            unionNameTV = itemView.findViewById(R.id.unionName);
            unitNameTV = itemView.findViewById(R.id.unitName);
            upozilaNameTV = itemView.findViewById(R.id.upazilaName);
            productQuantityTV = itemView.findViewById(R.id.productQuantityTV);
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
