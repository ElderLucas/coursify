package com.envixo.coursify.VolleyNetwork;

import org.json.JSONObject;

public class JsonPackUtility {

    private int numberOfCategory;
    private int numberOfCategoryRequested;
    private int currentNumberOfCategoryRequested;
    private int maxNumberOfPostPerPageRequested;

    private int numberOfMedia;
    private int numberOfMediaRequested;
    private int currentNumberOfMediaRequested;
    private int maxNumberOfMediaRequested;

    private int numberOfPost;
    private int numberOfPostRequested;
    private int currentNumberOfPostRequested;
    private int maxNumberOfPostRequested;

    private int PostsPerCategory;

    /***************** Categorias ******************/
    public void setPostsPerCategory(Integer quantity){
        this.PostsPerCategory = quantity;
    }

    public int getPostsPerCategoryy(){
        return this.PostsPerCategory;
    }

    /***************** Categorias ******************/
    public void setNumberOfCategory(Integer quantity){
        this.numberOfCategory = quantity;
    }

    public int getNumberOfCategory(){
        return this.numberOfCategory;
    }

    public void setNumberOfCategoryRequested(Integer quantity){
        this.numberOfCategoryRequested = quantity;
    }

    public int getNumberOfCategoryRequested(){
        return this.numberOfCategoryRequested;
    }


    public void setCurrentNumberOfCategoryRequested(Integer quantity){
        this.currentNumberOfCategoryRequested = quantity;
    }

    public int getCurrentNumberOfCategoryRequested(){
        return this.currentNumberOfCategoryRequested;
    }

    /***************** Medias ******************/

    public void setNumberOfMediaRequested(Integer quantity){
        this.numberOfMediaRequested = quantity;
    }

    public int getNumberOfMediaRequested(){
        return this.numberOfMediaRequested;
    }

    public void setCurrentNumberOfMediaRequested(Integer quantity){
        this.currentNumberOfMediaRequested = quantity;
    }

    public int getCurrentNumberOfMediaRequested(){
        return this.currentNumberOfMediaRequested;
    }

    public void incrementNumberOfCategoryRequested(int increment){
        this.numberOfCategoryRequested = numberOfCategoryRequested + increment;
    }

    public void incrementCurrentNumberOfCategoryRequested(int increment){
        this.currentNumberOfCategoryRequested = currentNumberOfCategoryRequested + increment;
    }

    public void incrementNumberOfMediaRequested(int increment){
        this.numberOfMediaRequested = numberOfMediaRequested + increment;
    }

    public void incrementCurrentNumberOfMediaRequested(int increment){
        this.currentNumberOfMediaRequested = currentNumberOfMediaRequested + increment;
    }

    public void incrementCurrentNumberOfPostRequested(int increment){
        this.currentNumberOfPostRequested = currentNumberOfPostRequested + increment;
    }

}
