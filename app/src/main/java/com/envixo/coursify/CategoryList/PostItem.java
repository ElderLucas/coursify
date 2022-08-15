package com.envixo.coursify.CategoryList;

import androidx.recyclerview.widget.RecyclerView;

/*
    As ReclyclerView serão divididas em:

    Categoria (CategoryItem): são os items da lista vertical #Parent
    Poste (PostItem): Lista horizontal aninhada em cada linha da lista vertical #Child

*/

public class PostItem {

    /* Declaration of the variable */
    private String PostItemTitle;
    private String PostItemExcerpt;
    private String PostItemText;
    private String PostItemContentText;
    private String ThumbnailURL;
    private int PostId;
    private int MediaId;

    /* Constructor of the class to initialize the variable */
    public PostItem(String postItemTitle) {
        this.PostItemTitle = postItemTitle;
    }

    /* Constructor of the class to initialize the variable */
    public PostItem() {
    }

    /* Getter and Setter method for the parameter */
    public String getPostFullText() {
        return PostItemContentText;
    }

    public void setPostFullText(String postFulltext) {
        PostItemContentText = postFulltext;
    }



    /* Getter and Setter method for the parameter */
    public int getPostId() {
        return PostId;
    }

    public void setPostId(int postId) {
        PostId = postId;
    }


    /* Getter and Setter method for the parameter */
    public String getPostItemExcerpt() {
        return PostItemExcerpt;
    }

    public void setPostItemExcerpt(String postItemExcerpt) {
        PostItemExcerpt = postItemExcerpt;
    }

    /* Getter and Setter method for the parameter */
    public String getPostItemTitle() {
        return PostItemTitle;
    }

    public void setPostItemTitle(String postItemTitle) {
        PostItemTitle = postItemTitle;
    }

    /* Getter and Setter method for the parameter */
    public String getPostItemText() {
        return PostItemText;
    }

    public void setPostItemText(String postItemText) {
        PostItemText = postItemText;
    }

    /* Getter and Setter method for the parameter */
    public String getThumbnailURL() {
        return ThumbnailURL;
    }

    public void setThumbnailURL(String postItemUrl) {
        ThumbnailURL = postItemUrl;
    }

    /* Getter and Setter method for the parameter */
    public int getPostMediaId() {
        return MediaId;
    }

    public void setPostMediaId(int postMediaId) {
        MediaId = postMediaId;
    }
}