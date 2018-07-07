package com.zebannikolay.capstone.core.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zebannikolay.capstone.databinding.ItemBoardGameBinding;
import com.zebannikolay.capstone.domain.models.BoardGamePreview;

import java.util.List;

public class BoardGamesPreviewRecyclerViewAdapter extends RecyclerView.Adapter<BoardGamesPreviewRecyclerViewAdapter.BoardGamesViewHolder> {

    private final List<BoardGamePreview> items;
    private final OnBoardGameClickListener listener;

    public BoardGamesPreviewRecyclerViewAdapter(List<BoardGamePreview> items, OnBoardGameClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BoardGamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBoardGameBinding binding = ItemBoardGameBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new BoardGamesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final BoardGamesViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class BoardGamesViewHolder extends RecyclerView.ViewHolder {

        private ItemBoardGameBinding binding;

        public BoardGamesViewHolder(@NonNull final ItemBoardGameBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull final BoardGamePreview game){

        }
    }

    public interface OnBoardGameClickListener {
        void onClick(@NonNull final BoardGamePreview game);
    }
}
