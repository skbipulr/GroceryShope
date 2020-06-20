package com.bipul.groceryshope.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bipul.groceryshope.R;
import com.bipul.groceryshope.interfaces.OnCartListener;
import com.bipul.groceryshope.modelForProducts.ProductList;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<ProductList> listData;
    private OnCartListener onCartListener;

    public CartAdapter(Context context, List<ProductList> listData, OnCartListener onCartListener) {
        this.context = context;
        this.listData = listData;
        this.onCartListener = onCartListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
        final ProductList productList = listData.get(position);


        Picasso.get().load("http://gobazaar.com.bd/public/upload/product/" + productList.getPicture())
                .into(holder.productImageIV);

        holder.productNameTV.setText(productList.getProductName());
        holder.productPriceTV.setText(String.valueOf(productList.getRate()));
        holder.unitNameTV.setText(productList.getUnitName());
        holder.itemQuantity.setText(String.valueOf(productList.getCountForCart()));


        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCartListener.onDeleteFromCart(productList);
            }
        });

        holder.increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCartListener.OnCartAdded(productList);
            }
        });

        holder.reduceQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCartListener.onCartRemoved(productList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView productImageIV, increaseQuantity, reduceQuantity;
        TextView productNameTV, productQuantityTV, productPriceTV, itemQuantity, addtobag, upozilaNameTV, unionNameTV, unitNameTV;
        ImageView deleteIV;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            productImageIV = itemView.findViewById(R.id.productImageIV);
            productNameTV = itemView.findViewById(R.id.productNameTV);
            productPriceTV = itemView.findViewById(R.id.productPriceTV);
            unitNameTV = itemView.findViewById(R.id.productUnitTV);

            unionNameTV = itemView.findViewById(R.id.unionName);
            upozilaNameTV = itemView.findViewById(R.id.upazilaName);
            productQuantityTV = itemView.findViewById(R.id.productQuantityTV);
            increaseQuantity = itemView.findViewById(R.id.increaseQuantity);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            reduceQuantity = itemView.findViewById(R.id.reduceQuantity);
            addtobag = itemView.findViewById(R.id.addtobag);

            deleteIV = itemView.findViewById(R.id.deleteIV);

        }
    }
}
