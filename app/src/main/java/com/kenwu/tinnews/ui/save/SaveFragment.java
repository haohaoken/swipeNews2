package com.kenwu.tinnews.ui.save;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kenwu.tinnews.R;
import com.kenwu.tinnews.databinding.FragmentSaveBinding;
import com.kenwu.tinnews.databinding.SavedNewsItemBinding;
import com.kenwu.tinnews.model.Article;
import com.kenwu.tinnews.repository.NewsRepository;
import com.kenwu.tinnews.repository.NewsViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaveFragment extends Fragment {
    private SaveViewModel viewModel;
    private FragmentSaveBinding binding;

    public SaveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSaveBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SavedNewsAdapter savedNewsAdapter = new SavedNewsAdapter();
        binding.recyclerView.setAdapter(savedNewsAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        NewsRepository repository = new NewsRepository(getContext());
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(SaveViewModel.class);
        viewModel.getAllSavedArticles().observe(getViewLifecycleOwner(), savedArticles -> {
            if (savedArticles != null) {
                savedNewsAdapter.setArticles(savedArticles);
            }
        });
        savedNewsAdapter.setOnClickListener(new SavedNewsAdapter.OnClickListener() {
            @Override
            public void onClick(Article article) {

            }

            @Override
            public void unLike(Article article) {
                viewModel.deleteSavedArticle(article);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.onCancel();
    }
}
