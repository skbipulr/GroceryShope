package com.bipul.groceryshope.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bipul.groceryshope.R;

import com.bipul.groceryshope.modelFodSlider.SliderProduct;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private List<SliderProduct> sliderList;

    public SliderAdapterExample(Context context, List<SliderProduct> sliderList) {
        this.context = context;
        this.sliderList = sliderList;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

    /*    switch (position) {
            case 0:
               viewHolder.imageViewBackground.setImageResource(R.drawable.go_bazer_logo);
                break;
            case 1:
                viewHolder.imageViewBackground.setImageResource(R.drawable.slider2);
                break;
            case 2:
                viewHolder.imageViewBackground.setImageResource(R.drawable.slider1);
                break;
            case 3:
                viewHolder.imageViewBackground.setImageResource(R.drawable.slider5);
                break;

        }*/


        SliderProduct slider = sliderList.get(position);
        Picasso.get().load("http://gobazaar.com.bd/public/upload/product/"+slider.getPicture())
                .into(viewHolder.imageViewBackground);

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return sliderList.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView descriptionTV,titleTV;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            // textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
        }
    }
}