package com.zebannikolay.capstone.core.adapters;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class BindingAdapters {
    @BindingAdapter({"imageUrl"})
    public static void setImageUrl(ImageView view, String imageUrl){
        Picasso.get().load(imageUrl).into(view);
    }
}
