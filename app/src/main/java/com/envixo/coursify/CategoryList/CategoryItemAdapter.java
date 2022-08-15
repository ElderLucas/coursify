package com.envixo.coursify.CategoryList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.envixo.coursify.R;

import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.CategoryViewHolder> {

    // An object of RecyclerView.RecycledViewPool
    // is created to share the Views
    // between the child and
    // the parent RecyclerViews
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<CategoryItem> itemList;
    private TextView Vermais;
    private CategoryItem mCategoryItem;

    public CategoryItemAdapter(List<CategoryItem> itemList)
    {
        this.itemList = itemList;
    }

    public CategoryItemAdapter()
    {
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        /* Here we inflate the corresponding layout of the Category item */
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int position)
    {
        CategoryItem categoryItem = itemList.get(position);
        categoryViewHolder.CategoryItemTitle.setText(categoryItem.getCategoryItemTitle());

        LinearLayoutManager layoutManager = new LinearLayoutManager(categoryViewHolder.PostRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setInitialPrefetchItemCount(categoryItem.getPostItemList().size());
        PostItemAdapter postItemAdapter = new PostItemAdapter(categoryItem.getPostItemList());

        categoryViewHolder.PostRecyclerView.setLayoutManager(layoutManager);
        categoryViewHolder.PostRecyclerView.setAdapter(postItemAdapter);
        categoryViewHolder.PostRecyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount()
    {

        return itemList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView CategoryItemTitle;
        private RecyclerView PostRecyclerView;

        CategoryViewHolder(final View itemView)
        {
            super(itemView);

            CategoryItemTitle = itemView.findViewById(R.id.parent_item_title);
            PostRecyclerView = itemView.findViewById(R.id.child_recyclerview);
            Vermais = itemView.findViewById(R.id.VerMais);

            itemView.findViewById(R.id.VerMais).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.w("ClickItem","click");

                    Log.w("ClickItem","click");
                    int mpos = getAdapterPosition();


                    mCategoryItem = itemList.get(mpos);
                    int id = mCategoryItem.getCategoryID();
                    Log.w("ClickItem","click");

                    /* Aqui se Chama uma nova Activity para Listar os POsts da Categoria.*/


                }
            });
        }


    }


}
