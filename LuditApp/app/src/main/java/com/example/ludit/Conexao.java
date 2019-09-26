package com.example.ludit;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Conexao {

    public static void inserir(final HashMap<String, String> params, String url, final Context context, final  VolleyCallback callback)
    {
        StringRequest jsObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()  {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject jsonObject = array.getJSONObject(0);
                            callback.onSuccess(jsonObject);
                        } catch (JSONException erro) {
                            erro.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG ).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams(){
                return  params;
            }
        };

        Volley.newRequestQueue(context).add(jsObjRequest);
    }

    public static void login(final HashMap<String, String> params, String url, final Context context, final  VolleyCallback callback)
    {
        StringRequest jsObjRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()  {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject jsonObject = array.getJSONObject(0);
                            callback.onSuccess(jsonObject);
                        } catch (JSONException erro) {
                            erro.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG ).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams(){
                return  params;
            }
        };

        Volley.newRequestQueue(context).add(jsObjRequest);
    }
}
