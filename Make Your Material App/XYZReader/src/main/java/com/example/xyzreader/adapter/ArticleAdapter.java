package com.example.xyzreader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import android.support.constraint.ConstraintLayout;

import com.example.xyzreader.R;

import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;

import butterknife.BindView;
import butterknife.ButterKnife;
//adapter class
public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TITLE_TYPE = 1;
    private static final int ARTICLE_BODY_TYPE = 2;
    private static final int PALETTE_IMAGE_SIDE = 200;

    private final Context context;
    private String title;
    private String author;
    private String date;
    private String[] articleBody;
    private String photoUrl;

    public ArticleAdapter(Context context) {
        this.context = context;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        switch (viewType) {
            case TITLE_TYPE:
                view = inflater.inflate(R.layout.article_title_item, parent, false);
                return new TitleViewHolder(view);
            default:
                view = inflater.inflate(R.layout.article_body_item, parent, false);
                return new ArticleBodyViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TITLE_TYPE:
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                titleViewHolder.bindTitle();
                break;
            default:
                ArticleBodyViewHolder articleBodyViewHolder = (ArticleBodyViewHolder) holder;
                articleBodyViewHolder.bindArticleBody(position);
        }

    }

    @Override
    public int getItemCount() {
        if (articleBody != null) {
            return articleBody.length + 1;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TITLE_TYPE;
        }
        return ARTICLE_BODY_TYPE;
    }

    public void setArticleData(String title, String author, String date, String[] articles, String photoUrl) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.articleBody = articles;
        this.photoUrl = photoUrl;
        notifyDataSetChanged();
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.detail_title)
        TextView titleTextView;
        @BindView(R.id.detail_author)
        TextView authorTextView;
        @BindView(R.id.detail_date)
        TextView dateTextView;
        @BindView(R.id.detail_data_cl)
        ConstraintLayout constraintLayout;


        TitleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);

        }


        void bindTitle() {
            titleTextView.setText(title);
            authorTextView.setText(String.format(context.getString(R.string.by_author), author));
            dateTextView.setText(date);
            Picasso.get().load(photoUrl).resize(PALETTE_IMAGE_SIDE, PALETTE_IMAGE_SIDE)
                    .error(R.drawable.ic_library_books_black_24dp).into(new com.squareup.picasso.Target() {
                @Override
                public void onBitmapLoaded(android.graphics.Bitmap bitmap, Picasso.LoadedFrom from) {
                    //set the background color of the title to one of the main colors of the image
                    // if a swatch can be created.
                    Palette.from(bitmap).generate(palette -> {
                        Palette.Swatch colorSwatch = palette.getDarkVibrantSwatch();
                        if (colorSwatch != null) {
                            constraintLayout.setBackgroundColor(colorSwatch.getRgb());
                        } else {
                            colorSwatch = palette.getDarkMutedSwatch();
                            if (colorSwatch != null) {
                                constraintLayout.setBackgroundColor(colorSwatch.getRgb());
                            }
                        }
                    });

                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    
                }


                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }

            });
        }

    }

    class ArticleBodyViewHolder extends RecyclerView.ViewHolder {

        final TextView articleBodyTextView;

        ArticleBodyViewHolder(View view) {
            super(view);
            articleBodyTextView = view.findViewById(R.id.article_body_text);
        }

        void bindArticleBody(int position) {
            String bodyPart = articleBody[position - 1];
            // remove unnecessary newlines, maintains newlines if followed by space as in verses in some articles
            String bodyPartNoReturn = bodyPart.replaceAll("\n(?!\\s)", " ");
            articleBodyTextView.setText(bodyPartNoReturn);
        }
    }
}
