package com.example.moetaz.backingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.moetaz.backingapp.R;
import com.example.moetaz.backingapp.adapters.RecipeAdapter;
import com.example.moetaz.backingapp.models.RecipeModel;
import com.example.moetaz.backingapp.parsing.Parse;
import com.example.moetaz.backingapp.utilities.Constants;
import com.example.moetaz.backingapp.utilities.MyUtilities;
import com.example.moetaz.backingapp.utilities.Mysingleton;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerView;
    private List<RecipeModel> recipeModelList = new ArrayList<>();
    private RecipeAdapter customAdapter;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.recyleviewrecipe);
        setHasOptionsMenu(true);

        LoadRecip();

        return view;
    }

    private void LoadRecip() {
        SetGridManager();
        customAdapter = new RecipeAdapter(getActivity(), recipeModelList);
        recyclerView.setAdapter(customAdapter);
        LoadByVolley();
    }

    private void LoadByVolley() {

        Cache cache = Mysingleton.getInstance(getActivity()).getRequestQueue().getCache();
        Cache.Entry entry = cache.get(Constants.JsonUrl);
        if (entry != null) {

            try {
                String data = new String(entry.data, "UTF-8");
                DeleteData();
                Iterator iterator = Parse.parseRecip(data).iterator();
                while (iterator.hasNext()){
                    RecipeModel movie = (RecipeModel) iterator.next();
                    recipeModelList.add(movie);
                    customAdapter.notifyItemInserted(recipeModelList.size() - 1);
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }else {


            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.JsonUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    DeleteData();
                    Iterator iterator = Parse.parseRecip(response).iterator();
                    while (iterator.hasNext()){
                        RecipeModel movie = (RecipeModel) iterator.next();
                        recipeModelList.add(movie);
                        customAdapter.notifyItemInserted(recipeModelList.size() - 1);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    MyUtilities.message(getActivity(),"Something went Wrong");
                }
            });
            Mysingleton.getInstance(getActivity()).addToRequest(stringRequest);
        }
    }

    private void SetGridManager(){
        gridLayoutManager=new GridLayoutManager(getActivity(), 2);

        /*
        if(MainActivity.IsTowPane)
            gridLayoutManager = new GridLayoutManager(getActivity(), 3);
            */

        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void DeleteData()
    {
        if (recipeModelList != null){
            recipeModelList.clear();
            customAdapter.notifyDataSetChanged();
        }
    }
}
