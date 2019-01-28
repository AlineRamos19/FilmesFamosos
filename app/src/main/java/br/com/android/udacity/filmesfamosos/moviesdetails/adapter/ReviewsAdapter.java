package br.com.android.udacity.filmesfamosos.moviesdetails.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.android.udacity.filmesfamosos.R;
import br.com.android.udacity.filmesfamosos.models.ResultReviews;

/**
 * Created by Aline on 28/01/2019.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsHolder> {

    private List<ResultReviews> mListReviews;
    private Context mContext;

    public ReviewsAdapter(List<ResultReviews> listReviews, Context context) {
        this.mListReviews = listReviews;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ReviewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_reviews, parent, false);
        return new ReviewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsHolder reviewsHolder, int position) {
        ResultReviews itemReviews = mListReviews.get(position);
        reviewsHolder.author.setText(itemReviews.getAuthor());
        reviewsHolder.bodyReview.setText(itemReviews.getContent());
    }

    @Override
    public int getItemCount() {
        return mListReviews.size();
    }

    public class ReviewsHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView bodyReview;

        public ReviewsHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.txt_author);
            bodyReview = itemView.findViewById(R.id.reviews_body);
        }
    }
}
