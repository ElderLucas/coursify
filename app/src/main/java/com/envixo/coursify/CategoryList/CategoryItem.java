package com.envixo.coursify.CategoryList;

import android.widget.TextView;

import java.util.List;

public class CategoryItem {

    // Declaration of the variables
    private String CategoryItemTitle;
    private List<PostItem> PostItemList;
    private int CategoryID;

    // Constructor of the class
    // to initialize the variables
    public CategoryItem(String CategoryItemTitle, List<PostItem> PostItemList)
    {
        this.CategoryItemTitle = CategoryItemTitle;
        this.PostItemList = PostItemList;
    }

    public CategoryItem() {
    }

    public void setCategoryID(int CategoryID){
        this.CategoryID = CategoryID;
    }

    public int getCategoryID(){
        return this.CategoryID;
    }

    // Getter and Setter methods
    // for each parameter
    public String getCategoryItemTitle()
    {
        return CategoryItemTitle;
    }

    public void setCategoryItemTitle(String categoryItemTitle){
        CategoryItemTitle = categoryItemTitle;
    }

    public List<PostItem> getPostItemList()
    {
        return PostItemList;
    }

    public void setPostItemList(List<PostItem> postItemList)
    {
        PostItemList = postItemList;
    }
}