package com.example.moetaz.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moetaz.bakingapp.R;
import com.example.moetaz.bakingapp.activities.RecipeInfo;
import com.example.moetaz.bakingapp.activities.StepInfo;
import com.example.moetaz.bakingapp.fragments.StepInfoFragment;
import com.example.moetaz.bakingapp.fragments.ingredientsFragment;
import com.example.moetaz.bakingapp.models.RecipeModel;
import com.example.moetaz.bakingapp.utilities.MyUtilities;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by moetaz on 09/10/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private boolean IsRecipeInfo = false;
    private Context context;
    private List<RecipeModel> recipeModels = new ArrayList<>();
    private List<RecipeModel.steps> stepses = new ArrayList<>();
    private List<RecipeModel.ingredients> ingredientses = new ArrayList<>();
    private String[] pLinks;
    String RecipeTitle;

    public RecipeAdapter(Context context,List<RecipeModel> recipeModels) {
        this.context = context;
        this.recipeModels = recipeModels;
        pLinks =this.context.getResources().getStringArray(R.array.PicsLinks);

    }
    public RecipeAdapter(Context context, List<RecipeModel.steps> steps, List<RecipeModel.ingredients> ingredientses
            , String name, boolean IsRecopeInfo) {
        this.context = context;
        this.stepses = steps;
        this.IsRecipeInfo = IsRecopeInfo;
        this.ingredientses = ingredientses;
        this.RecipeTitle = name;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(IsRecipeInfo){
              view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stepinfo_row, parent, false);
        }
        else
          view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_row, parent, false);

        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final int index = position;
        if (!IsRecipeInfo) {

            final RecipeModel recipeModel = recipeModels.get(position);
            holder.textView.setText(recipeModel.getName());
            Picasso.with(context).load(pLinks[position]).into(holder.pic);
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context,RecipeInfo.class);

                    intent.putExtra("modelPass",recipeModel);
                    context.startActivity(intent);
                }
            });
        }else{
            if(position == 0){
                holder.textView.setText("Recipe ingredients");
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyUtilities.IsIngredientFragment = true;
                        if (!MyUtilities.IsTablet(context)) {
                            Intent intent= new Intent(context,StepInfo.class);
                            intent.putExtra("IngPass", (Serializable) ingredientses);
                            intent.putExtra("rName",RecipeTitle);
                            context.startActivity(intent);
                        }else {
                            ingredientsFragment detailFragment=new ingredientsFragment();
                            Bundle b=new Bundle();
                            b.putSerializable("IngPass",(Serializable)ingredientses);
                            b.putString("rName",RecipeTitle);
                            detailFragment.setArguments(b);
                            ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fstep,detailFragment)
                                    .commit();
                        }
                    }
                });
            }else {
                final RecipeModel.steps step  = stepses.get(position-1);

                holder.textView.setText(step .getShortDescription());
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!MyUtilities.IsTablet(context)) {
                            Intent intent= new Intent(context,StepInfo.class);
                            intent.putExtra("stepPass", (Serializable) stepses);

                            intent.putExtra("position",index-1);
                            context.startActivity(intent);
                        }else {
                            StepInfoFragment detailFragment=new StepInfoFragment();
                            Bundle b=new Bundle();
                            b.putSerializable("stepPass",(Serializable)stepses);
                            b.putInt("position",index-1);
                            detailFragment.setArguments(b);
                            ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fstep,detailFragment)
                                    .commit();
                        }
                    }
                });
            }
    }
    }

    @Override
    public int getItemCount() {
        if(!IsRecipeInfo)
        return recipeModels.size();
        else
          return   stepses.size()+1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView pic;
        public MyViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.title);
            if (!IsRecipeInfo)
                pic = itemView.findViewById(R.id.picrec);


        }
    }
}
