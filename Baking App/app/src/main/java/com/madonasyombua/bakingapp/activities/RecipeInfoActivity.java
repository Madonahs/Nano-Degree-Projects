package com.madonasyombua.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.madonasyombua.bakingapp.adapters.RecipeListAdapter;
import com.madonasyombua.bakingapp.fragments.RecipeStepFragment;
import com.madonasyombua.bakingapp.models.Recipe;
import com.madonasyombua.bakingapp.utils.SpacingItemDecoration;
import com.madonasyombua.bakingapp.widget.AppWidgetService;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import com.madonasyombua.bakingapp.R;

public class RecipeInfoActivity extends AppCompatActivity {
    public static final String RECIPE_KEY = "recipe_k";

    @BindView(R.id.recipe_step_list)
    RecyclerView mRecyclerView;

    @BindView(android.R.id.content)
    View mParentLayout;
    @BindView(R.id.menuFab)
    FloatingActionMenu mFabMenul;
    private boolean mTwoPane;

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(RECIPE_KEY)) {
            mRecipe = bundle.getParcelable(RECIPE_KEY);
        } else {
            Toast.makeText(this,"failed to load", Toast.LENGTH_SHORT).show();

            finish();
        }

 

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar and set recipes name as title.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mRecipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mTwoPane = false;
        if (mTwoPane) {
            if (savedInstanceState == null && !mRecipe.getSteps().isEmpty()) {
                showStep(0);
            }
        }


        setupRecyclerView();
    }
    @OnClick(R.id.addToWidgetFab)
  public void startwidget(){
      AppWidgetService.updateWidget(this, mRecipe);
      Toast.makeText(this,"added to widget", Toast.LENGTH_SHORT).show();

  }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("onDestroy");
    }

    private void setupRecyclerView() {
        mRecyclerView.addItemDecoration(new SpacingItemDecoration((int) getResources().getDimension(R.dimen.margin_medium)));
        mRecyclerView.setAdapter(new RecipeListAdapter(mRecipe, this::showStep));
    }

    private void showStep(int position) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeStepFragment.STEP_KEY, mRecipe.getSteps().get(position));
            RecipeStepFragment fragment = new RecipeStepFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_step_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailActivity.RECIPE_KEY, mRecipe);
            intent.putExtra(RecipeDetailActivity.STEP_SELECTED_KEY, position);
            startActivity(intent);
        }
    }


}
