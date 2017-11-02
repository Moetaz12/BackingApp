package com.example.moetaz.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moetaz.bakingapp.R;
import com.example.moetaz.bakingapp.activities.RecipeInfo;
import com.example.moetaz.bakingapp.activities.StepInfo;
import com.example.moetaz.bakingapp.datastorage.SharedPref;
import com.example.moetaz.bakingapp.fragments.StepInfoFragment;
import com.example.moetaz.bakingapp.fragments.ingredientsFragment;
import com.example.moetaz.bakingapp.models.RecipeModel;
import com.example.moetaz.bakingapp.utilities.Constants;
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
    private String RecipeTitle;

    public RecipeAdapter(Context context, List<RecipeModel> recipeModels) {
        this.context = context;
        this.recipeModels = recipeModels;
        pLinks = this.context.getResources().getStringArray(R.array.PicsLinks);

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
        if (IsRecipeInfo) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stepinfo_row, parent, false);
        } else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (!IsRecipeInfo) {
            Set_Main_List(position, holder.textView, holder.pic, holder.cardView);

        } else {
            if (position == 0) {
                GoTO_Ingredients(holder.pic, holder.textView);
            } else {
                final RecipeModel.steps step = stepses.get(position - 1);

                holder.textView.setText(step.getShortDescription());
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!MyUtilities.IsTablet(context)) {
                            Intent intent = new Intent(context, StepInfo.class);
                            intent.putExtra(Constants.Step_Pass_Key, (Serializable) stepses);

                            intent.putExtra(Constants.Step_Position_Key, position - 1);
                            context.startActivity(intent);
                        } else {
                            StepInfoFragment detailFragment = new StepInfoFragment();
                            Bundle b = new Bundle();
                            b.putSerializable(Constants.Step_Pass_Key, (Serializable) stepses);
                            b.putInt(Constants.Step_Position_Key, position - 1);
                            detailFragment.setArguments(b);
                            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fstep, detailFragment)
                                    .commit();
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (!IsRecipeInfo)
            return recipeModels.size();
        else
            return stepses.size() + 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView;
        ImageView pic;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.title);
            if (IsRecipeInfo)
                pic = itemView.findViewById(R.id.sample_icon);
            else {
                pic = itemView.findViewById(R.id.picrec);
                cardView = itemView.findViewById(R.id.main_card);
            }


        }

    }

    private void GoTO_Ingredients(ImageView pic, TextView textView) {
        pic.setVisibility(View.INVISIBLE);
        textView.setText(R.string.Recipe_ingredients);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtilities.IsIngredientFragment = true;
                if (!MyUtilities.IsTablet(context)) {
                    Intent intent = new Intent(context, StepInfo.class);
                    intent.putExtra(Constants.Ing_List_Key, (Serializable) ingredientses);
                    intent.putExtra(Constants.Recipe_Name_Key, RecipeTitle);
                    context.startActivity(intent);
                } else {
                    ingredientsFragment detailFragment = new ingredientsFragment();
                    Bundle b = new Bundle();
                    b.putSerializable(Constants.Ing_List_Key, (Serializable) ingredientses);
                    b.putString(Constants.Recipe_Name_Key, RecipeTitle);
                    detailFragment.setArguments(b);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fstep, detailFragment)
                            .commit();
                }
            }
        });
    }

    private void Set_Main_List(final int position, TextView textView, final ImageView pic, CardView cardView) {
        final RecipeModel recipeModel = recipeModels.get(position);

        textView.setText(recipeModel.getName());
        if (!recipeModel.getImage().equals("")) {
            Picasso.with(context).load(recipeModel.getImage()).error(R.drawable.sample).into(pic);
        }else {
            Picasso.with(context).load(pLinks[position]).error(R.drawable.sample).into(pic);
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SharedPref(context).SaveItem(Constants.Action_Bar_Title_Key, recipeModel.getName());
                Intent intent = new Intent(context, RecipeInfo.class);
                intent.putExtra(Constants.Model_Key, recipeModel);
                context.startActivity(intent);
            }
        });
    }
}
