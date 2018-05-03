package com.gismo.moviechoose.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gismo.moviechoose.R;
import com.gismo.moviechoose.model.MovieObject;
import com.gismo.moviechoose.model.ReviewObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 2018. 02. 24..
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder>{

    private List<ReviewObject> reviewObjects = new ArrayList<>();
    private Context context;

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    public ReviewObject getItem(int position) {
        return reviewObjects.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView author;
        public final TextView content;
        public final ConstraintLayout root;

        public MyViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.content);
            root = (ConstraintLayout) itemView.getRootView();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.view_review_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ReviewObject review = reviewObjects.get(position);
        if (!review.getContent().equals(context.getResources().getString(R.string.no_reviews))) {
            holder.author.setText(context.getResources().getString(R.string.author_formatting,review.getAuthor()));
            holder.content.setText(context.getResources().getString(R.string.content_formatting,review.getContent()));
        } else {
            holder.content.setText(review.getContent());
            holder.author.setVisibility(View.GONE);
        }
        if (position % 2 == 0) {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.author.setTextColor(context.getResources().getColor(R.color.white));
            holder.content.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.colorLight));
        }
    }

    @Override
    public int getItemCount() {
        if (reviewObjects == null) return 0;
        return reviewObjects.size();
    }

    public void setReviewObjects(List<ReviewObject> reviewObjects) {
        this.reviewObjects = reviewObjects;
        notifyDataSetChanged();
    }

}
