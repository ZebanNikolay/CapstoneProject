package com.zebannikolay.capstone.core.adapters;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class BindingAdapters {
    @BindingAdapter({"imageUrl"})
    public static void setImageUrl(@NonNull final ImageView view, @NonNull final String imageUrl) {
        final StorageReference loadRef = FirebaseStorage.getInstance().getReference().child("images/" + imageUrl);

        loadRef.getDownloadUrl()
                .addOnSuccessListener(uri -> Picasso.get().load(uri.toString()).into(view))
                .addOnFailureListener(Timber::e);
    }
}
