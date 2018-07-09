package com.zebannikolay.capstone.core.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.zebannikolay.capstone.R;

import java.util.List;

public class UniversalRecyclerAdapter<E> extends RecyclerView.Adapter<UniversalRecyclerAdapter.UniversalViewHolder> {

    private List<E> dataList;
    private OnItemClickListener<E> listener;

    public UniversalRecyclerAdapter(@NonNull final List<E> dataList, @NonNull final OnItemClickListener<E> listener){
        this.dataList = dataList;
        this.listener = listener;
    }

    public void setDataList(@NonNull final List<E> data){
        this.dataList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UniversalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding bindingView = DataBindingUtil.inflate(inflater, R.layout.item_board_game, parent, false);

        return new UniversalViewHolder(bindingView);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversalRecyclerAdapter.UniversalViewHolder holder, int position) {
        holder.bindView(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class UniversalViewHolder extends RecyclerView.ViewHolder{

        private ViewDataBinding binding;

        UniversalViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bindView(@NonNull final E item){
            binding.setVariable(BR.item, item);
            binding.setVariable(BR.handler, listener);
        }
    }

    public interface OnItemClickListener<E> {
        void onClick(@NonNull final E item);
    }
}