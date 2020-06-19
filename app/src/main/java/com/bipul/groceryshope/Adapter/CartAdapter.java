package com.bipul.groceryshope.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bipul.groceryshope.R;
import com.bipul.groceryshope.Utils.Common;
import com.bipul.groceryshope.activity.AddToCartActivity;
import com.bipul.groceryshope.datebase.DatabaseOpenHelper;
import com.bipul.groceryshope.model.Order;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<Order> listData = new ArrayList<>();
    private DatabaseOpenHelper helper;

    int count=0;

    public CartAdapter(Context context, List<Order> listData) {
        this.context = context;
        this.listData = listData;
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
        final Order order = listData.get(position);

        Common.orderId = listData.get(position).getId();
        Common.po = position;

        Picasso.get().load("http://gobazaar.com.bd/public/upload/product/" + order.getProductImage())
                .into(holder.productImageIV);

        holder.productNameTV.setText(order.getProductName());
        holder.productPriceTV.setText(order.getProductPrice());
        holder.unitNameTV.setText(order.getProductunit());


        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper = new DatabaseOpenHelper(context);
                helper.deleteData(order.getId());
                listData.remove(position);

                notifyDataSetChanged();
            }
        });

        holder.increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;



                helper = new DatabaseOpenHelper(context);
                holder.itemQuantity.setText(order.getProductQuantity());
                    long id = helper.insert(order.getProductId(),
                            order.getProductName(),
                            order.getProductImage(),
                            order.getProductPrice(),
                            order.getProductunit(),
                            order.getProductQuantity());
                    Toast.makeText(context, "Add to cart ", Toast.LENGTH_SHORT).show();



            }
        });

        holder.reduceQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                holder.itemQuantity.setText(String.valueOf(count));
                if (count==-1){
                   /* holder.addtobag.setVisibility(View.VISIBLE);
                    holder.itemQuantity.setVisibility(View.GONE);
                    holder.reduceQuantity.setVisibility(View.GONE);*/
                    helper = new DatabaseOpenHelper(context);
                    helper.deleteData(order.getId());
                    listData.remove(position);
                    notifyDataSetChanged();

                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
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
