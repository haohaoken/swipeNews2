package com.kenwu.tinnews.ui.home;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.kenwu.tinnews.R;
import com.kenwu.tinnews.model.Article;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.squareup.picasso.Picasso;

@Layout(R.layout.tin_news_card)
public class TinNewsCard {
    @View(R.id.news_image)
    private ImageView image;

    @View(R.id.news_title)
    private TextView newsTitle;

    @View(R.id.news_description)
    private TextView newsDescription;

    private final Article article;
    private final OnSwipeListener onSwipeListener;

    public TinNewsCard(Article article, OnSwipeListener onSwipeListener) {
        this.article = article;
        this.onSwipeListener = onSwipeListener;
    }

    @Resolve
    private void onResolved() {
        newsTitle.setText(article.title);
        newsDescription.setText(article.description);
        String urlToImage = article.urlToImage;
        if (urlToImage == null || urlToImage.isEmpty()) {
            image.setImageResource(R.drawable.ic_empty_image);
        } else {
            Picasso.get().load(urlToImage).into(image);
        }
    }

    @SwipeOut
    private void onSwipedOut() {
        Log.d("Event", "onSwipeOut");
        onSwipeListener.onDislike(article);
    }

    @SwipeIn
    private void onSwipedIn() {
        Log.d("Event", "onSwipeIn");
        article.favorite = true;
        onSwipeListener.onLike(article);
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        Log.d("Event", "onSwipeCancelState");
    }

    interface OnSwipeListener {
        void onLike(Article article);
        void onDislike(Article article);
    }
}
