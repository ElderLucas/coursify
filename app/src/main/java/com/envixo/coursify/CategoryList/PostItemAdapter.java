package com.envixo.coursify.CategoryList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.envixo.coursify.R;
import com.envixo.coursify.RecyclerImageManagement.RecyclerImageView;
import com.envixo.coursify.VolleyNetwork.VolleyNetwork;

import java.util.List;

public class PostItemAdapter extends RecyclerView.Adapter<PostItemAdapter.PostViewHolder> {

    private List<PostItem> PostItemList;
    private int PostId;
    private PostItem mPostItem;

    /* TODO Verificar erro de getImageLoadre Return Null */
    ImageLoader imageLoader = VolleyNetwork.getInstance().getImageLoader();

    /* 2 - Cria-se dois Listeners do tipo Stático, um ClickListener e um LongListener */
    static ClickListener clickListener;
    static LongClickListener longclickListener;

    // Constructor
    PostItemAdapter(List<PostItem> postItemList)
    {
        this.PostItemList = postItemList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item, viewGroup, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int position)
    {
        PostItem postItem = PostItemList.get(position);

        postViewHolder.PostItemTitle.setText(postItem.getPostItemTitle());
        postViewHolder.ExcerptItem.setText(postItem.getPostItemExcerpt());
        postViewHolder.mRecyclerImageView.setImageUrl(postItem.getThumbnailURL(),imageLoader);

    }

    @Override
    public int getItemCount()
    {
        return PostItemList.size();
    }


    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView PostItemTitle;
        TextView ExcerptItem;

        RecyclerImageView mRecyclerImageView;

        PostViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this::onClick);
            itemView.setHapticFeedbackEnabled(true);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            PostItemTitle = itemView.findViewById(R.id.title);
            ExcerptItem = itemView.findViewById(R.id.excerpt);
            mRecyclerImageView = itemView.findViewById(R.id.RecyclerViewImage);
        }

        @Override
        public void onClick(View v) {
            Log.w("ClickItem","click");
            int mpos = getAdapterPosition();

            if(getAdapterPosition() > 0){
                Log.w("ClickItem","click");
            }

            mPostItem = PostItemList.get(mpos);

            String Title = mPostItem.getPostItemTitle();
            String Text = mPostItem.getPostItemText();

            Intent intent = new Intent (v.getContext(), DetailPostActivity.class);

            intent.putExtra("TITLE", Title);
            intent.putExtra("TEXT", Text);

            v.getContext().startActivity(intent);
        }

        @Override
        public boolean onLongClick(View view) {
            Log.w("ClickItem","click");
            return false;
        }
    }


    /* 10 - Implementa-se os métodos de ClickListener e LongClickListener */
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setLongClickListener(LongClickListener longClickListener) {
        this.longclickListener = longClickListener;
    }

    /* 11 - Implementa-se as interfaces de ClickListener e LongClickListener */
    public interface ClickListener {
        void ItemClicked(View v, int position);
    }

    public interface LongClickListener {
        void ItemLongClicked(View v, int position);
    }
}

