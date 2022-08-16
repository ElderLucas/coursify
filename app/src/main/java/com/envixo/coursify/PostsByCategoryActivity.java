package com.envixo.coursify;

import static com.envixo.coursify.Utility.PER_PAGE;
import static com.envixo.coursify.Utility.URL_CATEGORY_PER_PAGE;
import static com.envixo.coursify.Utility.URL_MIDIA_BY_ID;
import static com.envixo.coursify.Utility.URL_POSTS_BY_ID_CATEGORY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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
import com.envixo.coursify.CategoryList.PostItemAdapter;
import com.envixo.coursify.VolleyNetwork.JsonPackUtility;
import com.envixo.coursify.VolleyNetwork.VolleyNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostsByCategoryActivity extends AppCompatActivity {

    /* Criação de Objeto para fazer request com o BackEnd */
    VolleyNetwork volleyNetwork;

    PostItem myPostItem = new PostItem();

    JSONArray ApiCollectionRetrieveData;
    JSONArray ApiArrayRetrieveData;
    JSONArray ApiArrayMediaRetrieveData;

    JsonPackUtility mPackInformation = new JsonPackUtility();

    /* 1 - Cria-se uma Recycler View */
    private RecyclerView mRecyclerView;

    /* 2 - Cria-se uma lista com o Tipo do Item da Classe criada para abrigar os Itens */
    private List<PostItem> PostItemList = new ArrayList<>();

    /* 3 - Cria-se um Adaptador para a Recycler View */
    PostItemAdapter postItemAdapter;

    /* Lista para os Posts de Cada Categoria */
    //List<PostItem> PostItemList = new ArrayList<>();

    /* 4 - Cria-se uma LinearLayoutManager */
    LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_posts_by_category);

        /* 2 - Cria-se uma lista com o Tipo do Item da Classe criada para abrigar os Itens */
        List<PostItem> PostItemList = new ArrayList<>();


        volleyNetwork = new VolleyNetwork(this);

        /* 7 - Associa-se a Recycler View ao Seu Layout */
        mRecyclerView = (RecyclerView) findViewById(R.id.post_recyclerview);

        /* 8 - Associa-se o Adaptador à Classe dos Itens */
        postItemAdapter = new PostItemAdapter(PostItemList);

        /* 9 - Configura o Layout manager para Smooth Scrollbar Enabled */
        layoutManager = new LinearLayoutManager(PostsByCategoryActivity.this);

        /* 10 - Associa-se o Layout Manager à RecyclerView */
        mRecyclerView.setLayoutManager(layoutManager);

        /* 11 - Seta o Adaptador à Recycler View */
        mRecyclerView.setAdapter(postItemAdapter);

        /* Define o numero de categorias por request*/
        mPackInformation.setNumberOfCategory(5);

        resetInitState();
        requestDataFromServer();

    }

    public void resetInitState(){
        ApiArrayRetrieveData = new JSONArray();
        ApiCollectionRetrieveData = new JSONArray();
        ApiArrayMediaRetrieveData = new JSONArray();
        PostItemList.clear();
    }

    public void requestDataFromServer(){
        //String URL = URL_GET_API_LIST_DELIVERY_BY_DELIVERYMANID + "?" + STATUS_LANG + DELIVERY_WAITING + "&" + INIT_PERIOD_DATA + formattedDate + "&" + END_PERIOD_DATA + formattedDate;

        Integer CategoryID = getIntent().getIntExtra("Category_ID",0);

        String URL = URL_POSTS_BY_ID_CATEGORY + String.valueOf(CategoryID) + PER_PAGE + String.valueOf(4);

        mPackInformation.setPostsPerCategory(10);

        GetPosts(URL);
    }


    /* PARSE & WRITE DATA ITEM */
    public void GetPosts(String URL){
        volley_get_PostsByCategoryIdList_request(URL);
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
                }
                MediaIdListArray.put(i,MediaIdList);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

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

            String URL = concat_MediaIds;
            /*Clear URL*/
            concat_MediaIds = URL_MIDIA_BY_ID;
            mPackInformation.incrementNumberOfMediaRequested(1);
            volley_get_MediaURLById_request(URL);
        }
    }

    public void buildRecyclerview(){
        try {

            int sizeCategory = 1;
            int postsPages = ApiArrayRetrieveData.length();

            /* Monta a Categoria*/
            for (int x = 0; x < sizeCategory; x++){

                /* Limpa PostItem */
                PostItemList = new ArrayList<>();
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
                postItemAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}