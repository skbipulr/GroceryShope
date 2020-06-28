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
import com.bipul.groceryshope.modelForFeatureProduct.Category;
import com.bipul.groceryshope.modelForFeatureProduct.Data;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeatureProductAdapter extends RecyclerView.Adapter<FeatureProductAdapter.ViewHolder> {
    private Context context;
    private List<Category> featureProducts;

    public FeatureProductAdapter(Context context, List<Category> featureProducts) {
        this.context = context;
        this.featureProducts = featureProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feature_product_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category featureProduct = featureProducts.get(position);
        holder.groceriesTitleTV.setText(featureProduct.getName());

        Picasso.get().load("http://narsingdi.gobazaar.com.bd/public/upload/product/"+featureProduct.getPicture())
                .into(holder.groceriesImage);
    }

    @Override
    public int getItemCount() {
        return featureProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView groceriesImage;
        TextView groceriesTitleTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            groceriesImage = itemView.findViewById(R.id.groceriesImageIV);
            groceriesTitleTV = itemView.findViewById(R.id.titleTV);

        }
    }
}
