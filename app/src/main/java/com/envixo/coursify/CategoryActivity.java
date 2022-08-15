package com.envixo.coursify;

import static com.envixo.coursify.Utility.PER_PAGE;
import static com.envixo.coursify.Utility.URL_CATEGORY_PER_PAGE;
import static com.envixo.coursify.Utility.URL_MIDIA_BY_ID;
import static com.envixo.coursify.Utility.URL_POSTS_BY_ID_CATEGORY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.envixo.coursify.CategoryList.CategoryItem;
import com.envixo.coursify.CategoryList.CategoryItemAdapter;
import com.envixo.coursify.CategoryList.PostItem;
import com.envixo.coursify.VolleyNetwork.JsonPackUtility;
import com.envixo.coursify.VolleyNetwork.VolleyNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    /* Criação de Objeto para fazer request com o BackEnd */
    VolleyNetwork volleyNetwork;
    PostItem myPostItem = new PostItem();

    JSONArray ApiCollectionRetrieveData;
    JSONArray ApiArrayRetrieveData;
    JSONArray ApiArrayMediaRetrieveData;
    Integer MediaCountCall = new Integer(0);
    Integer MediaCountRequest = new Integer(0);

    JsonPackUtility mPackInformation = new JsonPackUtility();

    /* 1 - Cria-se uma Recycler View */
    private RecyclerView CategoryRecyclerView;

    /* 2 - Cria-se uma lista com o Tipo do Item da Classe criada para abrigar os Itens */
    private List<CategoryItem> CategoryItemList = new ArrayList<>();

    /* 3 - Cria-se um Adaptador para a Recycler View */
    CategoryItemAdapter categoryItemAdapter;

    /* Lista para os Posts de Cada Categoria */
    List<PostItem> PostItemList = new ArrayList<>();

    /* 4 - Cria-se uma LinearLayoutManager */
    LinearLayoutManager layoutManager;

    /* 3 - Adapter for Recycler View */
    //CategoryItemAdapter categoryItemAdapter;

    /*
     * Resumo de como o Volley vai funcionar:
     * 1 - Primeiro se solicitará a lista das Categorias, que é um JSON Array.
     *
     * 2 - Para cada categoria, buscar a lista de Posts, que também é uma JSON Array
     * 3 - Nas lista dos Posts, cada um tem o ID da mídia que será necessario para a Thumbnail
     * 4 - Buscar a lista de Mídis que serão usadas nos Posts
     * 5 - Popular os Posts de uma Categoria e Popular as Categorias
     * 6 - Escutar os ScrollViews Horizontal para Post e Vertical para Categoria e adicionar
     *     mais itens conforme o feed vai rolando.
     *
     *
     * */


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Handle the splash screen transition Coursify Logo
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        setContentView(R.layout.activity_category);

        volleyNetwork = new VolleyNetwork(this);

        /* 7 - Associa-se a Recycler View ao Seu Layout */
        CategoryRecyclerView = (RecyclerView) findViewById(R.id.category_recyclerview);

        /* 8 - Associa-se o Adaptador à Classe dos Itens */
        categoryItemAdapter = new CategoryItemAdapter(CategoryItemList);

        /* 9 - Configura o Layout manager para Smooth Scrollbar Enabled */
        layoutManager = new LinearLayoutManager(CategoryActivity.this);

        /* 10 - Associa-se o Layout Manager à RecyclerView */
        CategoryRecyclerView.setLayoutManager(layoutManager);

        /* 11 - Seta o Adaptador à Recycler View */
        CategoryRecyclerView.setAdapter(categoryItemAdapter);

        /* Define o numero de categorias por request*/
        mPackInformation.setNumberOfCategory(5);

        /*
            TODO :  https://developer.android.com/training/permissions/index.html  -- Vefificar o novo sistema de Permissões
        */
        //Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo_splash);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        resetInitState();
        requestDataFromServer();

    }

    public void resetInitState(){
        ApiArrayRetrieveData = new JSONArray();
        ApiCollectionRetrieveData = new JSONArray();
        ApiArrayMediaRetrieveData = new JSONArray();
        CategoryItemList.clear();
    }

    public void requestDataFromServer(){
        //String URL = URL_GET_API_LIST_DELIVERY_BY_DELIVERYMANID + "?" + STATUS_LANG + DELIVERY_WAITING + "&" + INIT_PERIOD_DATA + formattedDate + "&" + END_PERIOD_DATA + formattedDate;
        String URL = URL_CATEGORY_PER_PAGE + String.valueOf(10);
        mPackInformation.setPostsPerCategory(4);
        volley_get_CategoryList_request(URL);
    }

    /*******************************************************/
    /******** 1 - Volley para Buscar as Categorias *********/
    /*******************************************************/
    int countResponse = 0;

    public void volley_get_CategoryList_request(String URL){

        JsonArrayRequest get_category_info_VolleyRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ApiCollectionRetrieveData.put(response);
                CategoryArrayParse_JSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    switch (response.statusCode) {
                        /**
                         * TODO: Implementar os tratamentos para as responses
                         */
                        case 404:
                            Toast.makeText(getApplicationContext(), getApplication().getString(R.string.network_error), Toast.LENGTH_LONG).show();
                        case 422:
                            Toast.makeText(getApplicationContext(), getApplication().getString(R.string.network_error), Toast.LENGTH_LONG).show();
                        case 400:
                            Toast.makeText(getApplicationContext(), getApplication().getString(R.string.network_error), Toast.LENGTH_LONG).show();
                        case 401:
                            Toast.makeText(getApplicationContext(), getApplication().getString(R.string.network_error), Toast.LENGTH_LONG).show();
                        default:
                            return;
                    }
                }
            }
        });

        /* Get data from API */
        volleyNetwork.addToRequestQueue(get_category_info_VolleyRequest);


    }


    /*******************************************************/
    /**** 2 - Volley para Buscar os Posts das Categorias ***/
    /*******************************************************/
    public void volley_get_PostsByCategoryIdList_request(String URL){

        JsonArrayRequest get_PostsByCategoryIdList_VolleyRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                mPackInformation.incrementCurrentNumberOfCategoryRequested(1);

                if(mPackInformation.getCurrentNumberOfCategoryRequested() < mPackInformation.getNumberOfCategoryRequested())
                    ApiArrayRetrieveData.put(response);
                else{
                    ApiArrayRetrieveData.put(response);
                    try {
                        PostArrayParse_JSON(ApiArrayRetrieveData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    switch (response.statusCode) {
                        /**
                         * TODO: Implementar os tratamentos para as responses
                         */
                        case 404:
                            Toast.makeText(getApplicationContext(), getApplication().getString(R.string.network_error), Toast.LENGTH_LONG).show();
                        case 422:
                            Toast.makeText(getApplicationContext(), getApplication().getString(R.string.network_error), Toast.LENGTH_LONG).show();
                        case 400:
                            Toast.makeText(getApplicationContext(), getApplication().getString(R.string.network_error), Toast.LENGTH_LONG).show();
                        case 401:
                            Toast.makeText(getApplicationContext(), getApplication().getString(R.string.network_error), Toast.LENGTH_LONG).show();
                        default:
                            return;
                    }
                }
            }
        });

        /* Get data from API */
        volleyNetwork.addToRequestQueue(get_PostsByCategoryIdList_VolleyRequest);
    }

    /*******************************************************/
    /**** 3 - Volley para Buscar as Medias de Cada Post ****/
    /*******************************************************/
    public void volley_get_MediaURLById_request(String URL) throws JSONException {

        JsonArrayRequest get_MediaURLById_VolleyRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mPackInformation.incrementCurrentNumberOfMediaRequested(1);
                if(mPackInformation.getCurrentNumberOfMediaRequested() < mPackInformation.getNumberOfMediaRequested())
                    ApiArrayMediaRetrieveData.put(response);
                else{
                    ApiArrayMediaRetrieveData.put(response);
                    buildRecyclerview();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    switch (response.statusCode) {
                        /**
                         * TODO: Implementar os tratamentos para as responses
                         */
                        case 404:
                            Toast.makeText(getApplicationContext(), getApplication().getString(R.string.network_error), Toast.LENGTH_LONG).show();
                        case 422:
                            Toast.makeText(getApplicationContext(), getApplication().getString(R.string.network_error), Toast.LENGTH_LONG).show();
                        case 400:
                            Toast.makeText(getApplicationContext(), getApplication().getString(R.string.network_error), Toast.LENGTH_LONG).show();
                        case 401:
                            Toast.makeText(getApplicationContext(), getApplication().getString(R.string.network_error), Toast.LENGTH_LONG).show();
                        default:
                            return;
                    }
                }
            }
        });

        /* Get data from API */
        volleyNetwork.addToRequestQueue(get_MediaURLById_VolleyRequest);
    }



    /* PARSE & WRITE DATA ITEM */
    public void CategoryArrayParse_JSON(JSONArray mJSONArrayData){
        try {
            for (int i = 0; i < mJSONArrayData.length(); i++) {

                /* 1 - Solicita os posts da primeira Categoria */
                JSONObject row = mJSONArrayData.getJSONObject(i);

                int id = row.getInt("id"); //Id of Category
                int count = row.getInt("count"); //How many posts are in Category
                String name = row.getString("name"); //Name of Category

                String numberOfCategory = String.valueOf(mPackInformation.getNumberOfCategory());
                String URL_POSTS = URL_POSTS_BY_ID_CATEGORY + String.valueOf(id) + PER_PAGE + mPackInformation.getPostsPerCategoryy();

                /* Chama a API para buscar os dados dos POSTS dentro de cada categoria*/
                /*Incrementa a informação com o numero requisições dos posts por categoria*/
                mPackInformation.incrementNumberOfCategoryRequested(1);

                volley_get_PostsByCategoryIdList_request(URL_POSTS);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //mAdapter.notifyDataSetChanged();
        //hideSwipeRefreshin();
    }

    /* PARSE & WRITE DATA ITEM */
    public void PostArrayParse_JSON(JSONArray mJSONArrayData) throws JSONException {

        JSONArray MediaIdListArray = new JSONArray();
        JSONArray MediaIdList;

        try {
            for (int i = 0; i < mJSONArrayData.length(); i++) {

                JSONArray postArray = new JSONArray();
                postArray = mJSONArrayData.getJSONArray(i);

                MediaIdList = new JSONArray();

                for (int x = 0; x < postArray.length(); x++) {

                    JSONObject PostJsonObj = postArray.getJSONObject(x);

                    String PostTitle = PostJsonObj.getString("title");
                    int media_id = PostJsonObj.getInt("featured_media");
                    JSONObject JsonTextContent = PostJsonObj.getJSONObject("excerpt");
                    String rendered_text = JsonTextContent.getString("rendered");

                    myPostItem.setPostItemTitle(PostTitle);
                    myPostItem.setPostItemText(rendered_text);
                    myPostItem.setPostMediaId(media_id);

                    MediaIdList.put(media_id);
                    //PostItemList().add(myPostItem);
                }
                MediaIdListArray.put(i,MediaIdList);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        int count = 0;

        MediaArrayParse_JSON(MediaIdListArray);

    }

    public void MediaArrayParse_JSON(JSONArray mediaIdListArray) throws JSONException {
        String concat_MediaIds = URL_MIDIA_BY_ID;
        int sizeOfArrayList = mediaIdListArray.length();
        JSONArray mListMedia = new JSONArray();

        for (int i = 0; i < sizeOfArrayList; i++){

            mListMedia = mediaIdListArray.getJSONArray(i);

            for (int x = 0; x < mListMedia.length(); x++){
                if(x == mListMedia.length()-1){
                    concat_MediaIds = concat_MediaIds.concat(String.valueOf(mListMedia.get(x)));
                }else{
                    concat_MediaIds = concat_MediaIds.concat(String.valueOf(mListMedia.get(x))+",");
                }
            }

            //concat_MediaIds = concat_MediaIds.concat(PER_PAGE + String.valueOf(10));
            String URL = concat_MediaIds;
            /*Clear URL*/
            concat_MediaIds = URL_MIDIA_BY_ID;
            mPackInformation.incrementNumberOfMediaRequested(1);
            volley_get_MediaURLById_request(URL);
        }



    }

    public void buildRecyclerview(){
        try {

            int categoryPages = ApiCollectionRetrieveData.length();
            JSONArray PageCategory = ApiCollectionRetrieveData.getJSONArray(0);
            int sizeCategory = PageCategory.length();
            int postsPages = ApiArrayRetrieveData.length();

            CategoryItem mCategory;

            mCategory = new CategoryItem();

            /* Monta a Categoria*/
            for (int x = 0; x < sizeCategory; x++){

                /* Limpa PostItem */
                PostItemList = new ArrayList<>();
                mCategory = new CategoryItem();

                JSONObject category = PageCategory.getJSONObject(x);

                JSONArray PagePosts = ApiArrayRetrieveData.getJSONArray(x);
                int sizePosts = PagePosts.length();

                JSONArray PageMedia = ApiArrayMediaRetrieveData.getJSONArray(x);

                for (int y = 0; y < sizePosts; y++){

                    JSONObject post = PagePosts.getJSONObject(y);
                    JSONObject media = PageMedia.getJSONObject(y);

                    JSONObject mediaDetailsJsonObj = media.getJSONObject("media_details");
                    JSONObject mediaSizesJsonObj = mediaDetailsJsonObj.getJSONObject("sizes");
                    JSONObject thumbnailJsonObj = mediaSizesJsonObj.getJSONObject("thumbnail");
                    String source_url = thumbnailJsonObj.getString("source_url");

                    PostItem mPostItem = new PostItem();
                    mPostItem.setThumbnailURL(source_url);

                    JSONObject title = new JSONObject(post.getString("title"));
                    mPostItem.setPostItemTitle(title.getString("rendered"));

                    JSONObject excerpt = new JSONObject(post.getString("excerpt"));
                    String _content = excerpt.getString("rendered");

                    // set Text in TextView using fromHtml() method with version check
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        mPostItem.setPostItemExcerpt(String.valueOf(Html.fromHtml(_content, Html.FROM_HTML_MODE_LEGACY)));
                    } else
                        mPostItem.setPostItemExcerpt(String.valueOf(Html.fromHtml(_content)));

                    JSONObject fulltext = new JSONObject(post.getString("content"));
                    String _fullcontent = fulltext.getString("rendered");

                    // set Text in TextView using fromHtml() method with version check
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        mPostItem.setPostItemText(String.valueOf(Html.fromHtml(_fullcontent, Html.FROM_HTML_MODE_LEGACY)));
                    } else
                        mPostItem.setPostItemText(String.valueOf(Html.fromHtml(_fullcontent)));

                    mPostItem.setThumbnailURL(source_url);

                    PostItemList.add(mPostItem);
                }


                mCategory.setCategoryItemTitle(category.getString("name"));
                mCategory.setCategoryID(category.getInt("id"));
                mCategory.setPostItemList(PostItemList);


                CategoryItemList.add(mCategory);

            }





        } catch (JSONException e) {
            e.printStackTrace();
        }

        categoryItemAdapter.notifyDataSetChanged();
    }





}