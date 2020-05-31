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
import com.bipul.groceryshope.model.RelatedProduct;
import com.bipul.groceryshope.model.SecondCategory;

import java.util.List;

public class RelatedProductAdapter extends RecyclerView.Adapter<RelatedProductAdapter.ViewHolder> {

    private Context context;
    private List<RelatedProduct>relatedProducts;

    public RelatedProductAdapter(Context context, List<RelatedProduct> relatedProducts) {
        this.context = context;
        this.relatedProducts = relatedProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.second_category_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RelatedProduct relatedProduct = relatedProducts.get(position);
        holder.productNameTV.setText(relatedProduct.getProductName());
        holder.productPriceTV.setText(relatedProduct.getProductPrice());
       // holder.productQuantityTV.setText(relatedProduct.getProductQuantity());
        holder.productImageIV.setImageResource(relatedProduct.getProductImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ProductDetailsActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return relatedProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageIV;
        TextView productNameTV, productQuantityTV,productPriceTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImageIV  = itemView.findViewById(R.id.imageProduct);
            productNameTV = itemView.findViewById(R.id.productNameTV);
            productPriceTV = itemView.findViewById(R.id.productPriceTV);
           // productQuantityTV = itemView.findViewById(R.id.productQuantityTV);
        }
    }
}
