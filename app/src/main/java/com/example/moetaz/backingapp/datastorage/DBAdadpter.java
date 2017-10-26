package com.example.moetaz.backingapp.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.moetaz.backingapp.models.RecipeModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moetaz on 9/20/2017.
 */

public class DBAdadpter {

    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private static  DBAdadpter dbAdadpter;
    private static final String DB_NAME = "recipedb.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "ingtable";

    private static final String QUANTITY = "quantity";
    private static final String MEASURE = "measure";
    private static final String INGGREDIENT ="ingredient";

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("
            +QUANTITY+" TEXT PRIMARY KEY , "
            +MEASURE+" TEXT ,"
            +INGGREDIENT+" TEXT)"
            ;


    private DBAdadpter (Context context){
        this.context = context;
        sqLiteDatabase = new SqlHelpter(this.context,DB_NAME,null,DB_VERSION).getWritableDatabase();

    }

    public static DBAdadpter getDBAdadpterInstance (Context context){
        if (dbAdadpter == null){
            dbAdadpter = new DBAdadpter(context);

        }
        return dbAdadpter;
    }


    public List<RecipeModel.ingredients> GetData (){
        List<RecipeModel.ingredients> ingredientses = new ArrayList<>();
        String [] cols = new String[] {QUANTITY,MEASURE,INGGREDIENT};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,cols,null,null,null,null,null);

        if(cursor != null && cursor.getCount() > 0){

            while (cursor.moveToNext()){

                RecipeModel.ingredients ingredients = new RecipeModel.ingredients();
                ingredients.setQuantity(cursor.getString(cursor.getColumnIndex(QUANTITY)));
                ingredients.setMeasure(cursor.getString(cursor.getColumnIndex(MEASURE)));
                ingredients.setIngredient(cursor.getString(cursor.getColumnIndex(INGGREDIENT)));

                ingredientses.add(ingredients);

            }

        }
        if (cursor != null)
         cursor.close();
        return ingredientses;
    }


    public long InsertContentValue(ContentValues values) {
        return sqLiteDatabase.insert(TABLE_NAME,null,values);
    }

    public Cursor getCursorsForAllData() {
        return sqLiteDatabase.query(TABLE_NAME
                ,new String []{QUANTITY,MEASURE,INGGREDIENT},null,null,null,null,null,null);
    }

    public int deleteIng() {
          return sqLiteDatabase.delete(TABLE_NAME,null,null);
    }


    private static class SqlHelpter extends SQLiteOpenHelper{

        public SqlHelpter(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            onCreate(db);

        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
        }
    }
}
