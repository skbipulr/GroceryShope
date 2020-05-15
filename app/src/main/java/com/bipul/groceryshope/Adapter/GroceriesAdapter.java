package com.bipul.groceryshope.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bipul.groceryshope.R;
import com.bipul.groceryshope.model.Groceries;

import java.util.List;

public class GroceriesAdapter  extends RecyclerView.Adapter<GroceriesAdapter.ViewHolder> {
    private Context context;
    private List<Groceries> groceriesList;

    public GroceriesAdapter(Context context, List<Groceries> groceriesList) {
        this.context = context;
        this.groceriesList = groceriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grocries_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Groceries groceries = groceriesList.get(position);
        holder.groceriesTitleTV.setText(groceries.getGroceriesTitle());
        holder.groceriesImage.setImageResource(groceries.getGroceriesImage());
    }

    @Override
    public int getItemCount() {
        return groceriesList.size();
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
