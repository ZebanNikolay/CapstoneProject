package com.zebannikolay.capstone;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zebannikolay.capstone.databinding.FragmentGamesListBinding;
import com.zebannikolay.capstone.domain.models.BoardGamePreview;

import java.util.ArrayList;
import java.util.List;

public final class GamesListFragment extends Fragment {

    private FragmentGamesListBinding binding;

    public GamesListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGamesListBinding.inflate(inflater, container, false);

        initRecyclerView();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        int columnCount = getResources().getInteger(R.integer.games_preview_list_column_count);
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        binding.list.setLayoutManager(sglm);

        List<BoardGamePreview> gamePreviews = new ArrayList<>();
        binding.list.setAdapter(new BoardGamesPreviewRecyclerViewAdapter(gamePreviews, game -> {

        }));
    }

}
