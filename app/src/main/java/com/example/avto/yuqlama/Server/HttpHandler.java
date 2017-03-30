package com.example.avto.yuqlama.Server;

/**
 * Created by Azat on 19.03.2017.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import com.android.internal.http.multipart.MultipartEntity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public String doGetQuery(String url) throws UnsupportedEncodingException {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "utf-8");
        params.setBooleanParameter("http.protocol.expect-continue", false);

        String encodeUrl = URLEncoder.encode(url, "UTF-8");
        StringBuilder sb= new StringBuilder();
        HttpClient client = new DefaultHttpClient(params);
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = null;
            try {
                response = client.execute(request);
            }
            catch (Exception e) {
                return null;
            }
            StatusLine sl = response.getStatusLine();
            int sc = sl.getStatusCode();
            if (sc==200)
            {
                HttpEntity ent = response.getEntity();
                InputStream inpst = ent.getContent();
                BufferedReader rd = new BufferedReader(new InputStreamReader(inpst));
                String line;
                while ((line=rd.readLine())!=null)
                {
                    sb.append(line);
                }
                return sb.toString();
            }
            else
            {
                Log.e( "log_tag","I didn't  get the response!");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int postNBQuery(String password, JSONArray jsonArray) throws JSONException, UnsupportedEncodingException {
        try {
            String url = Config.getUrlNbSend();
            HttpClient httpclient=new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new StringEntity(jsonArray.toString(), "UTF8"));
            httppost.setHeader("Content-type", "application/json");
            httppost.setHeader("password", password);
            HttpResponse response = httpclient.execute(httppost);
            if(response.getStatusLine().getStatusCode()==200)
                return 1;
            else if (response.getStatusLine().getStatusCode()==400)
                return 2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int postNBGroupQuery(String password, JSONArray jsonArray) throws JSONException, UnsupportedEncodingException {
        try {
            String url = Config.getUrlNbGroupSend();
            HttpClient httpclient=new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new StringEntity(jsonArray.toString(), "UTF8"));
            httppost.setHeader("Content-type", "application/json");
            httppost.setHeader("password", password);
            HttpResponse response = httpclient.execute(httppost);
            if(response.getStatusLine().getStatusCode()==200)
                return 1;
            if(response.getStatusLine().getStatusCode()==400)
                return 2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}


