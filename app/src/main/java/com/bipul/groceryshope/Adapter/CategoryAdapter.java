package com.bipul.groceryshope.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<Product> categories;
    private List<ProductList> productLists = new ArrayList<>();

    public CategoryAdapter(Context context, List<Product> categories) {
        this.context = context;
        this.categories = categories;
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

        holder.categoryName.setText(category.getCategoryName());

            Picasso.get().load("http://narsingdi.gobazaar.com.bd/public/upload/category/" + category.getCategoryIcon()).placeholder(R.drawable.ic_image_gray_24dp)
                    .into(holder.categoryImageIV);


       /* if (category.getProductList().size() == 0) {
            holder.itemView.setVisibility(View.INVISIBLE);
        }*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryActivity.class);
                intent.putExtra("category", category.getCategoryName());
                intent.putParcelableArrayListExtra("productList", (ArrayList<? extends Parcelable>) categories.get(position).getProductList());
                context.startActivity(intent);
            }
        });

    }

    //holder.categoryImage.setImageResource(category.getCategoryImage());


    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImageIV;
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryImageIV = itemView.findViewById(R.id.categoryImageIV);
        }
    }
}
