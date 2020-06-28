package com.bipul.groceryshope.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bipul.groceryshope.R;
import com.bipul.groceryshope.activity.ProductDetailsActivity;
import com.bipul.groceryshope.datebase.DatabaseOpenHelper;
import com.bipul.groceryshope.interfaces.OnCartListener;
import com.bipul.groceryshope.model.SecondCategory;
import com.bipul.groceryshope.modelForProducts.Product;
import com.bipul.groceryshope.modelForProducts.ProductList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ThirdCategoryAdapter extends RecyclerView.Adapter<ThirdCategoryAdapter.ViewHolder> {

    private Context context;
    private List<ProductList> categoryList;

    private List<ProductList> productLists;
    int count=0;
    private OnCartListener onCartListener;
    
    private DatabaseOpenHelper helper;

    public ThirdCategoryAdapter(Context context, List<ProductList> categoryList,OnCartListener onCartListener) {
        this.context = context;
        this.categoryList = categoryList;
        this.onCartListener = onCartListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.third_category_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ProductList productList;
        productList = categoryList.get(position);
        holder.productNameTV.setText(productList.getProductName());
        Picasso.get().load("http://narsingdi.gobazaar.com.bd/public/upload/product/" + productList.getPicture())
                .into(holder.productImageIV);
        int i = productList.getRate();
        holder.productPriceTV.setText(String.valueOf(i));
        holder.unitNameTV.setText(productList.getUnitName());
        holder.upozilaNameTV.setText(productList.getUpazilaName());
        holder.unionNameTV.setText(productList.getUnionName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ProductDetailsActivity.class);
                intent.putExtra("productId",String.valueOf(productList.getProductId()));
                context.startActivity(intent);
            }
        });



        holder.increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemQuantity.setVisibility(View.VISIBLE);
                holder.reduceQuantity.setVisibility(View.VISIBLE);
                holder.addtobag.setVisibility(View.GONE);
                productList.setCountForCart(productList.getCountForCart()+1);
                //Common.getCount = productList.getCountForCart();
                holder.itemQuantity.setText(String.valueOf(productList.getCountForCart()));
                onCartListener.OnCartAdded(productList);
/*
                    helper = new DatabaseOpenHelper(context);
                    helper.insert(productList.getProductId(),
                            productList.getProductName(),
                            productList.getPicture(),
                            String.valueOf(productList.getRate()),
                            productList.getUnitName(),
                            productList.getCount());*/
                Toast.makeText(context, "Add to cart ", Toast.LENGTH_SHORT).show();
            }
        });


        holder.reduceQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCartListener.onCartRemoved(productList);
                productList.setCountForCart(productList.getCountForCart()-1);
                holder.itemQuantity.setText(String.valueOf(productList.getCountForCart()));
                if (productList.getCountForCart() == 0) {
                    holder.addtobag.setVisibility(View.VISIBLE);
                    holder.itemQuantity.setVisibility(View.GONE);
                    holder.reduceQuantity.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
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




        }
    }
}
