package com.example.moetaz.backingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moetaz.backingapp.R;
import com.example.moetaz.backingapp.models.RecipeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moetaz on 09/10/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    Context context;
    List<RecipeModel> recipeModels = new ArrayList<>();

    public RecipeAdapter(Context context,List<RecipeModel> recipeModels) {
        this.context = context;
        this.recipeModels = recipeModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_row, parent, false);

        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final RecipeModel recipeModel = recipeModels.get(position);
        holder.textView.setText(recipeModel.getName());
    }

    @Override
    public int getItemCount() {
        return recipeModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title);


        }
    }
}
