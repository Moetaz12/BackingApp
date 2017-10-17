package com.example.moetaz.backingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moetaz.backingapp.R;
import com.example.moetaz.backingapp.activities.RecipeInfo;
import com.example.moetaz.backingapp.activities.StepInfo;
import com.example.moetaz.backingapp.models.RecipeModel;
import com.example.moetaz.backingapp.utilities.MyUtilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.message;

/**
 * Created by moetaz on 09/10/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private boolean IsRecopeInfo = false;
    private Context context;
    List<RecipeModel> recipeModels = new ArrayList<>();
    private List<RecipeModel.steps> stepses = new ArrayList<>();
    private List<RecipeModel.ingredients> ingredientses = new ArrayList<>();


    public RecipeAdapter(Context context,List<RecipeModel> recipeModels) {
        this.context = context;
        this.recipeModels = recipeModels;

    }
    public RecipeAdapter(Context context, List<RecipeModel.steps> steps,List<RecipeModel.ingredients> ingredientses ,boolean IsRecopeInfo) {
        this.context = context;
        this.stepses = steps;
        this.IsRecopeInfo = IsRecopeInfo;
        this.ingredientses = ingredientses;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_row, parent, false);

        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final int index = position;
        if (!IsRecopeInfo) {

            final RecipeModel recipeModel = recipeModels.get(position);
            holder.textView.setText(recipeModel.getName());
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
                        Intent intent= new Intent(context,StepInfo.class);
                        intent.putExtra("IngPass", (Serializable) ingredientses);
                        context.startActivity(intent);
                    }
                });
            }else {
                final RecipeModel.steps step  = stepses.get(position-1);

                holder.textView.setText(step .getShortDescription());
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(context,StepInfo.class);
                        intent.putExtra("stepPass", (Serializable) stepses);

                        intent.putExtra("position",index-1);
                        context.startActivity(intent);
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
        public MyViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.title);


        }
    }
}
