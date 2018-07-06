package com.madonasyombua.bakingapp.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.madonasyombua.bakingapp.utils.Prefs;
import com.madonasyombua.bakingapp.adapters.RecipesAdapter;
import com.madonasyombua.bakingapp.helpers.ApiCallback;
import com.madonasyombua.bakingapp.helpers.RecipesApiManager;
import com.madonasyombua.bakingapp.models.Recipe;
import com.madonasyombua.bakingapp.utils.SpacingItemDecoration;
import com.madonasyombua.bakingapp.widget.AppWidgetService;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.madonasyombua.bakingapp.R;


public class RecipeFragment extends Fragment {
    @BindView(R.id.recipes_recycler_view)
    RecyclerView recipesRecyclerView;
    @BindView(R.id.pull_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.noDataContainer)
    ConstraintLayout constraintLayout;

    private static final String RECIPES_KEY = "recipes";
    private OnRecipeClickListener mListener;
    private Unbinder unbinder;
    private List<Recipe> mRecipes;


    private final BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mRecipes == null) {
                loadingRecipe();
            }
        }
    };

    public RecipeFragment() {
       
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment bind view to butter knife
        View viewRoot = inflater.inflate(R.layout.fragment_recipes, container, false);
        unbinder = ButterKnife.bind(this, viewRoot);

        swipeRefreshLayout.setOnRefreshListener(
                this::loadingRecipe);

        constraintLayout.setVisibility(View.VISIBLE);
        setupRecyclerView();

        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPES_KEY)) {
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPES_KEY);

            recipesRecyclerView.setAdapter(new RecipesAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(), mRecipes,
                    position -> mListener.onRecipeSelected(mRecipes.get(position))));
            dataLoadedTakeCareLayout();
        }
        return viewRoot;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeClickListener) {
            mListener = (OnRecipeClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Logger.d("onDestroyView");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();

        Objects.requireNonNull(getActivity()).registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPause() {
        super.onPause();

        Objects.requireNonNull(getActivity()).unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mRecipes != null && !mRecipes.isEmpty())
            outState.putParcelableArrayList(RECIPES_KEY, (ArrayList<? extends Parcelable>) mRecipes);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setupRecyclerView() {
        recipesRecyclerView.setVisibility(View.GONE);
        recipesRecyclerView.setHasFixedSize(true);

        boolean twoPaneMode = false;
        if (twoPaneMode) {
            recipesRecyclerView.setLayoutManager(new GridLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext(), 3));
        } else {
            recipesRecyclerView.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }

        recipesRecyclerView.addItemDecoration(new SpacingItemDecoration((int) getResources().getDimension(R.dimen.margin_medium)));
        recipesRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loadingRecipe() {
        if (isNetworkAvailable(Objects.requireNonNull(getActivity()).getApplicationContext())) {
            swipeRefreshLayout.setRefreshing(true);

            RecipesApiManager.getInstance().getRecipes(new ApiCallback<List<Recipe>>() {
                @Override
                public void onResponse(final List<Recipe> result) {
                    if (result != null) {
                        mRecipes = result;
                        recipesRecyclerView.setAdapter(new RecipesAdapter(getActivity().getApplicationContext(), mRecipes, position -> mListener.onRecipeSelected(mRecipes.get(position))));
                        // Set the default recipe for the widget
                        if (Prefs.loadRecipe(getActivity().getApplicationContext()) == null) {
                            AppWidgetService.updateWidget(getActivity(), mRecipes.get(0));
                        }

                    } else {
                        Toast.makeText(getContext(),"failed to load data",Toast.LENGTH_SHORT).show();

                    }

                    dataLoadedTakeCareLayout();
                }

                @Override
                public void onCancel() {
                    dataLoadedTakeCareLayout();
                }

            });
        } else {
            Toast.makeText(getContext(),"No Internet",Toast.LENGTH_SHORT).show();
        }
    }

    private void dataLoadedTakeCareLayout() {
        boolean loaded = mRecipes != null && mRecipes.size() > 0;
        swipeRefreshLayout.setRefreshing(false);

        recipesRecyclerView.setVisibility(loaded ? View.VISIBLE : View.GONE);
        constraintLayout.setVisibility(loaded ? View.GONE : View.VISIBLE);


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRecipeClickListener {
        void onRecipeSelected(Recipe recipe);
    }

    /**
     * check if the network is available
     * @param context context
     * @return connection
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
