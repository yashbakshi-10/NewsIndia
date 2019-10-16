package com.yash.newsindia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class LoadList extends Fragment {


    List<Article> articles;
    Base base;
    final static String url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=0539d614d79342519a1efdfeaf1477cf";


    RecyclerView list;
    boolean flag = false;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.list_layout, container, false);


        RequestQueue queue = Volley.newRequestQueue(v.getContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                base = gson.fromJson(response.toString(), Base.class);
                try {
                    JSONArray array = response.getJSONArray("articles");
                    articles = Arrays.asList(gson.fromJson(array.toString(), Article[].class));

                    flag = true;



                    list = v.findViewById(R.id.list_view);
                    list.setLayoutManager(new LinearLayoutManager(v.getContext()));

                    list.setAdapter(new ListAdapter(articles));


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(v.getContext(), "error ", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(v.getContext(), error.networkResponse.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(request);



        return v;

    }

}
