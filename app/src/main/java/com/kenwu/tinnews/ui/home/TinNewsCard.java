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

    public TinNewsCard(Article article) {
        this.article = article;
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
    }

    @SwipeIn
    private void onSwipedIn() {
        Log.d("Event", "onSwipeIn");
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        Log.d("Event", "onSwipeCancelState");
    }
}
