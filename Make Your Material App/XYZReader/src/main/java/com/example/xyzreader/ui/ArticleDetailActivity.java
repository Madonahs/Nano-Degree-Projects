package com.example.xyzreader.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleDetailActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private Cursor mCursor;
    private long mStartId;
    @BindView(R.id.article_detail_photo)
    ImageView mArticleImage;
    private static String mTitleFab;
    private static String mAuthorFab;
    private static final String EXTRA_ID = "article selected extra id";

    private ViewPager viewPager;
    private ArticleDetailActivity.MyPagerAdapter pagerAdapter;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        FloatingActionButton fab = findViewById(R.id.fab_share);
        fab.setOnClickListener(v -> shareArticle());
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportLoaderManager().initLoader(0, null, this);

        pagerAdapter = new ArticleDetailActivity.MyPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                if (mCursor != null) {
                    mCursor.moveToPosition(position);
                }
            }
        });


        if (savedInstanceState == null) {
            if (getIntent() != null) {
                mStartId = getIntent().getLongExtra(EXTRA_ID, 0);
                }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.setting){
            openSnackBar();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openSnackBar() {

    }

    private void shareArticle(){
        if(mCursor != null && mAuthorFab != null && mTitleFab != null){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_article));
            String message = String.format(getString(R.string.share_message), mTitleFab, mAuthorFab);
            intent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(intent, getString(R.string.share_title)));
        }
    }



    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return ArticleLoader.newAllArticlesInstance(this);
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> cursorLoader, Cursor cursor) {
        this.mCursor = cursor;
        pagerAdapter.notifyDataSetChanged();

        // Select the start ID
        if (mStartId > 0) {
            while (cursor.moveToNext()) {
                if (this.mCursor.getLong(ArticleLoader.Query._ID) == mStartId) {
                    final int position = this.mCursor.getPosition();
                    viewPager.setCurrentItem(position, false);
                    break;
                }
            }
            mStartId = 0;
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> cursorLoader) {
        mCursor = null;
        pagerAdapter.notifyDataSetChanged();
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            mCursor.moveToPosition(position);
            String photoUrl = mCursor.getString(ArticleLoader.Query.PHOTO_URL);
            mTitleFab = mCursor.getString(ArticleLoader.Query.TITLE);
            mAuthorFab = mCursor.getString(ArticleLoader.Query.AUTHOR);
            Picasso.get().load(photoUrl).placeholder(R.drawable.ic_library_books_black_24dp)
                    .error(R.drawable.ic_library_books_black_24dp).into(mArticleImage);

        }


        @Override
        public Fragment getItem(int position) {
            mCursor.moveToPosition(position);
            return ArticleDetailFragment.newInstance(mCursor.getLong(ArticleLoader.Query._ID));
        }

        @Override
        public int getCount() {
            return (mCursor != null) ? mCursor.getCount() : 0;
        }
    }
}
