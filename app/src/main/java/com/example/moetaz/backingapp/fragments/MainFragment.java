package com.example.moetaz.backingapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.moetaz.backingapp.utilities.Mysingleton;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.moetaz.backingapp.utilities.MyUtilities.message;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerView;
    private List<RecipeModel> recipeModelList = new ArrayList<>();
    private RecipeAdapter recipeAdapter;
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

        if(savedInstanceState != null) {
            LoadFromBundle(savedInstanceState);
        }else {
            LoadRecipe();
        }
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", (Serializable) recipeModelList);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            LoadFromBundle(savedInstanceState);
        }
    }

    private void LoadRecipe() {
        SetGridManager();
        recipeAdapter = new RecipeAdapter(getActivity(), recipeModelList);
        recyclerView.setAdapter(recipeAdapter);
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
                    recipeAdapter.notifyItemInserted(recipeModelList.size() - 1);
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
                        recipeAdapter.notifyItemInserted(recipeModelList.size() - 1);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    message(getActivity(),"Something went Wrong");
                }
            });
            Mysingleton.getInstance(getActivity()).addToRequest(stringRequest);
        }
    }

    private void SetGridManager(){
        gridLayoutManager=new GridLayoutManager(getActivity(), 1);

        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void DeleteData()
    {
        if (recipeModelList != null){
            recipeModelList.clear();
            recipeAdapter.notifyDataSetChanged();
        }
    }
    @SuppressWarnings("unchecked")
    private void LoadFromBundle(Bundle savedInstanceState){
        List<RecipeModel>  m = (List<RecipeModel> ) savedInstanceState.getSerializable("list");
        SetGridManager();
        recipeModelList.clear();
        assert m != null;
        recipeModelList.addAll(0, m);
        recipeAdapter = new RecipeAdapter(getActivity(), recipeModelList);
        recyclerView.setAdapter(recipeAdapter);
    }
}
