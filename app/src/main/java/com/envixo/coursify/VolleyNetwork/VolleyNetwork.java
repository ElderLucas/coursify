package com.envixo.coursify.VolleyNetwork;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleyNetwork {

    public static final String TAG = VolleyNetwork.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static VolleyNetwork mInstance;

    Context context;

    public VolleyNetwork(Context context){
        this.context = context;
        mInstance = this;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**************************************************************/
    /**************************** VOLLEY **************************/
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public int volley_failure_analisy(NetworkResponse response){

        switch (response.statusCode) {
            case 404:
                Log.i(TAG, "404");
            case 405:
                Log.i(TAG, "Method Not Allowed");
            case 422:
                Log.i(TAG, "422");
            case 400:
                Log.i(TAG, "400");
            case 401:
                Log.i(TAG, "40X : invalid request");
            case 403:
                Log.i(TAG, "403 : Auth Failure ");
            default:
                Log.i(TAG, "XXX : ERR_UNKNOWN_STATUS_CODE");
        }

        return 0;
    }

    public static synchronized VolleyNetwork getInstance() {
        return mInstance;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

}
