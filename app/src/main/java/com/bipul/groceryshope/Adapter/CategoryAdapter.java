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
import com.bipul.groceryshope.activity.CategoryActivity;
import com.bipul.groceryshope.modelForProducts.Product;
import com.bipul.groceryshope.modelForProducts.ProductList;
import com.bipul.groceryshope.modelForcategories.Category;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<Product> categories;
    private List<ProductList> productLists;

    public CategoryAdapter(Context context, List<Product> categories, List<ProductList> productLists) {
        this.context = context;
        this.categories = categories;
        this.productLists = productLists;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_layout,
                        parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, final int position) {


        final Product category = categories.get(position);
        // final ProductList productList = productLists.get(position);
//       final ProductList productList =  productLists.get(position);
        holder.categoryName.setText(category.getCategoryName());
        if (category.getCategoryId() == null) {
            holder.itemView.setVisibility(View.GONE);
        }


        //holder.categoryImage.setImageResource(category.getCategoryImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, CategoryActivity.class);
              //  intent.putExtra("productName", productList.getProductName());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            // categoryImage = itemView.findViewById(R.id.categoryImage);
        }
    }
}
