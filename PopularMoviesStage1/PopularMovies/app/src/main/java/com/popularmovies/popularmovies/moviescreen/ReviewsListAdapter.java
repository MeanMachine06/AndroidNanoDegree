package com.popularmovies.popularmovies.moviescreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.popularmovies.popularmovies.R;
import com.popularmovies.popularmovies.utils.ReviewData;

/**
 * Created by Aditya on 7/28/2017.
 */

public class ReviewsListAdapter extends ArrayAdapter<ReviewData>
{
    private final String TAG = ReviewsListAdapter.class.getSimpleName();

    private final Context context;
    private ReviewData[] reviewsData;

    public ReviewsListAdapter(Context context)
    {
        super(context, -1);

        this.context = context;
        this.reviewsData = null;
    }

    @Override
    public int getCount()
    {
        if(reviewsData != null)
        {
            return reviewsData.length;
        }

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.reviews_list_item_layout, parent, false);

        TextView reviewTextView = (TextView) rowView.findViewById(R.id.review_text_view);
        TextView authorTextView = (TextView) rowView.findViewById(R.id.author_text_view);

        if(reviewsData != null)
        {
            reviewTextView.setText(reviewsData[position].getContent());
            authorTextView.setText(context.getString(R.string.author_prefix, reviewsData[position].getAuthor()));
        }

        return rowView;
    }

    public void setReviewsData(ReviewData[] reviewsData)
    {
        this.reviewsData = reviewsData;
        notifyDataSetChanged();
    }
}
