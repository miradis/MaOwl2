package com.example.marowl.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.marowl.R;
import com.example.marowl.databinding.FragmentHomeBinding;
import com.example.marowl.model.ComicsModel;
import com.example.marowl.ui.comic.ComicActivity;
import com.example.marowl.ui.favorite.ComicsAdapter;
import com.example.marowl.ui.favorite.FavoriteViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    ComicsAdapter comicsAdapter;
    List<ComicsModel> comicsList;
    HomeViewModel homeViewModel;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        homeViewModel =
                new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.
                        getInstance(getActivity().getApplication())).
                        get(HomeViewModel.class);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText searchComicEditText=view.findViewById(R.id.search_container);
        MaterialButton button=view.findViewById(R.id.random_btn);
        SwipeRefreshLayout swipeRefreshLayout=view.findViewById(R.id.swipe_refresh);
        recyclerView=view.findViewById(R.id.home_grid_view);
        buildRecycleView();
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                homeViewModel.getComicsListModel().observe(getViewLifecycleOwner(), new Observer<List<ComicsModel>>() {
//                    @Override
//                    public void onChanged(List<ComicsModel> comicsModels) {
//                        comicsList= comicsModels;
//                        comicsAdapter.setData(comicsModels);
//                        comicsAdapter.notifyDataSetChanged();
//                    }
//                });
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random=new Random();
                ComicsModel comic=comicsList.get(random.nextInt(comicsList.size()));
                Intent intentToComicPage=new Intent(getActivity(), ComicActivity.class);
                intentToComicPage.putExtra("ComicImage",comic.getImg_url());
                intentToComicPage.putExtra("ComicTitle", comic.getTitle());
                intentToComicPage.putExtra("ComicDetails", comic.getDescription());
                intentToComicPage.putExtra("pdf_url",comic.getPdf_url());
                startActivity(intentToComicPage);
            }
        });
        searchComicEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());
            }
        });


    }

    private void buildRecycleView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeViewModel.getComicsListModel().observe(getViewLifecycleOwner(), new Observer<List<ComicsModel>>() {
            @Override
            public void onChanged(List<ComicsModel> comicsModels) {
                comicsList= comicsModels;
                comicsAdapter.setData(comicsModels);
                comicsAdapter.notifyDataSetChanged();
            }
        });
        comicsAdapter=new ComicsAdapter(getContext(),comicsList);
        recyclerView.setAdapter(comicsAdapter);
    }

    private void filter(String text){
        List<ComicsModel> filteredList=new ArrayList<>();

        for (ComicsModel item: comicsList){
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        comicsAdapter.filterList(filteredList);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}