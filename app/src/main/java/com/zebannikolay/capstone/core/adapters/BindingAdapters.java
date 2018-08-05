package com.zebannikolay.capstone.core.adapters;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.zebannikolay.capstone.R;

import timber.log.Timber;

public class BindingAdapters {

    @BindingAdapter({"imageUrl"})
    public static void setImageUrl(@NonNull final ImageView view, @NonNull final String imageUrl) {
        final StorageReference loadRef = FirebaseStorage.getInstance().getReference().child("images/" + imageUrl);
        loadRef.getDownloadUrl()
                .addOnSuccessListener(uri -> Picasso.get().load(uri.toString()).into(view))
                .addOnFailureListener(Timber::e);
    }

    @BindingAdapter({"youtubeImage"})
    public static void setYoutubeImage(@NonNull final ImageView view, @NonNull final String videoId) {
        Picasso.get().load("https://img.youtube.com/vi/" + videoId + "/default.jpg").into(view);
    }

    @BindingAdapter({"visible"})
    public static void setVisible(@NonNull final View view, final Boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}
