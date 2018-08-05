package com.zebannikolay.capstone.presentation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zebannikolay.capstone.App;
import com.zebannikolay.capstone.R;
import com.zebannikolay.capstone.core.adapters.UniversalRecyclerAdapter;
import com.zebannikolay.capstone.databinding.FragmentGamesListBinding;
import com.zebannikolay.capstone.domain.BoardGamesInteractor;
import com.zebannikolay.capstone.domain.models.BoardGamePreview;

import java.util.ArrayList;

import javax.inject.Inject;

public final class GamesListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentGamesListBinding binding;

    @Inject BoardGamesInteractor interactor;
    private GamesListViewModel viewModel;
    private UniversalRecyclerAdapter<BoardGamePreview> adapter;

    public GamesListFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        App.getAppComponent().inject(this);
        viewModel = new GamesListViewModel(interactor);

        binding = FragmentGamesListBinding.inflate(inflater, container, false);
        binding.swipeRefresh.setOnRefreshListener(this);

        initRecyclerView();

        subscribeViewModel();

        return binding.getRoot();
    }

    private void subscribeViewModel() {
        viewModel.getGamesPreview().observe(this, adapter::setDataList);
        viewModel.getUiState().observe(this, uiState -> {
            if (uiState == UiState.CONTENT) {
                binding.swipeRefresh.setRefreshing(false);
            }
            if (uiState == UiState.FAIL) {
                binding.swipeRefresh.setRefreshing(false);
                Toast.makeText(requireActivity(), R.string.error_an_error_occurred, Toast.LENGTH_SHORT).show();
            }
            if (uiState == UiState.LOADING) {
                binding.swipeRefresh.setRefreshing(true);
            }
        });
    }

    private void initRecyclerView() {
        int columnCount = getResources().getInteger(R.integer.games_preview_list_column_count);
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        binding.list.setLayoutManager(sglm);

        adapter = new UniversalRecyclerAdapter<>(new ArrayList<>(), R.layout.item_board_game, game ->
            GameDetailsActivity.start(requireActivity(), game.getId())
        );
        binding.list.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        viewModel.fetchGamesPreviews();
    }
}
