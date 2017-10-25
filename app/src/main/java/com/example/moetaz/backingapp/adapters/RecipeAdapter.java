package com.example.moetaz.backingapp.adapters;

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

import com.example.moetaz.backingapp.R;
import com.example.moetaz.backingapp.activities.RecipeInfo;
import com.example.moetaz.backingapp.activities.StepInfo;
import com.example.moetaz.backingapp.fragments.StepInfoFragment;
import com.example.moetaz.backingapp.fragments.ingredientsFragment;
import com.example.moetaz.backingapp.models.RecipeModel;
import com.example.moetaz.backingapp.utilities.MyUtilities;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;

import static android.R.id.message;

/**
 * Created by moetaz on 09/10/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private boolean IsRecopeInfo = false;
    private Context context;
    private List<RecipeModel> recipeModels = new ArrayList<>();
    private List<RecipeModel.steps> stepses = new ArrayList<>();
    private List<RecipeModel.ingredients> ingredientses = new ArrayList<>();
    private String[] pLinks;

    public RecipeAdapter(Context context,List<RecipeModel> recipeModels) {
        this.context = context;
        this.recipeModels = recipeModels;
        pLinks =this.context.getResources().getStringArray(R.array.PicsLinks);

    }
    public RecipeAdapter(Context context, List<RecipeModel.steps> steps,List<RecipeModel.ingredients> ingredientses ,boolean IsRecopeInfo) {
        this.context = context;
        this.stepses = steps;
        this.IsRecopeInfo = IsRecopeInfo;
        this.ingredientses = ingredientses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(IsRecopeInfo){
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
        if (!IsRecopeInfo) {

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
                            context.startActivity(intent);
                        }else {
                            ingredientsFragment detailFragment=new ingredientsFragment();
                            Bundle b=new Bundle();
                            b.putSerializable("IngPass",(Serializable)ingredientses);

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
        if(!IsRecopeInfo)
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
            if (!IsRecopeInfo)
                pic = itemView.findViewById(R.id.picrec);


        }
    }
}
