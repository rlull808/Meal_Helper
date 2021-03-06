package com.lull.mealhelper;

/**
 * Created by Bob Lull on 9/26/2015.
 */

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;




public class MealHelperDB {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    //database constants
    public static final String DB_NAME = "meals.db";
    public static final int DB_VERSION = 8;

    // Ingredients table constants
    public static final String INGRED_TABLE = "ingredientTable";

    public static final String INGRED_ID = "ingredientId";
    public static final int INGRED_ID_COL = 0;

    public static final String INGRED_MEAL_ID = "meal_id";
    public static final int INGRED_MEAL_ID_COL = 1;

    public static final String INGRED_NUM = "ingredientNum";
    public static final int INGRED_NUM_COL = 2;

    public static final String INGRED_TITLE = "ingredientTitle";
    public static final int INGRED_TITLE_COL = 3;

    public static final String INGRED_DESC = "ingredientDesc";
    public static final int INGRED_DESC_COL = 4;

    // Recipe Step table constants

    public static final String STEP_TABLE = "recipeStepTable";

    public static final String STEP_ID = "_stepId";
    public static final int STEP_ID_COL = 0;

    public static final String STEP_MEAL_ID = "meal_id";
    public static final int STEP_MEAL_ID_COL = 1;

    public static final String STEP_NUM = "stepNum";
    public static final int STEP_NUM_COL = 2;

    public static final String STEP_INSTRUCTION = "stepInstruction";
    public static final int STEP_INST_COL = 3;


    // Recipe table constants

    public static final String MEAL_TABLE = "mealTable";

    public static final String MEAL_ID = "_mealID";
    public static final int MEAL_ID_COL = 0;

    public static final String MEAL_NAME = "mealName";
    public static final int MEAL_NAME_COL = 1;

    public static final String MEAL_TIMER_LENGTH = "timerLength";
    public static final int MEAL_TIMER_LENGTH_COL = 2;


    //CREATE and DROP TABLE statements

    public static final String CREATE_MEAL_TABLE = "CREATE TABLE " + MEAL_TABLE + " (" +
            MEAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MEAL_NAME + " TEXT NOT NULL, " +
            MEAL_TIMER_LENGTH + " INTEGER);";

    public static final String CREATE_INGREDIENT_TABLE = "CREATE TABLE " + INGRED_TABLE + " (" +
            INGRED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            INGRED_MEAL_ID + " INTEGER NOT NULL, " +
            INGRED_NUM + " INTEGER NOT NULL, " +
            INGRED_TITLE + " TEXT NOT NULL, " +
            INGRED_DESC + " TEXT);";

    public static final String CREATE_RECIPE_STEP_TABLE = "CREATE TABLE " + STEP_TABLE + " (" +
            STEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            STEP_MEAL_ID + " INTEGER NOT NULL, " +
            STEP_NUM + " INTEGER NOT NULL, " +
            STEP_INSTRUCTION + " TEXT NOT NULL);";

    public static final String DROP_INGREDIENT_TABLE = "DROP TABLE IF EXISTS " + INGRED_TABLE;
    public static final String DROP_STEP_TABLE = "DROP TABLE IF EXISTS " + STEP_TABLE;
    public static final String DROP_MEAL_TABLE = "DROP TABLE IF EXISTS " + MEAL_TABLE;

    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //create mealTable
            db.execSQL(CREATE_MEAL_TABLE);

            // add default entries into mealTable
            db.execSQL("INSERT INTO " + MEAL_TABLE + " VALUES ('1', 'Pancakes', '0');");
            db.execSQL("INSERT INTO " + MEAL_TABLE + " VALUES ('2', 'Old-Fashioned Eggs and Bacon', '0');");

            //create ingredientTable
            db.execSQL(CREATE_INGREDIENT_TABLE);

            //add default entries into ingredientTable
            //INGRED_ID, INGRED_MEAL_ID, INGRED_NUM, INGRED_TITLE, INGRED_DESC
            //pancakes
            db.execSQL("INSERT INTO " + INGRED_TABLE + " VALUES ('1', '1', '1', 'flour', 'one cup');");
            db.execSQL("INSERT INTO " + INGRED_TABLE + " VALUES ('2', '1', '2', '1 egg', 'beaten');");
            db.execSQL("INSERT INTO " + INGRED_TABLE + " VALUES ('3', '1', '3', 'water', '1/4 cup');");
            db.execSQL("INSERT INTO " + INGRED_TABLE + " VALUES ('4', '1', '4', 'milk', '1/4 cup');");
            db.execSQL("INSERT INTO " + INGRED_TABLE + " VALUES ('5', '1', '5', 'butter', 'small pat');");
            db.execSQL("INSERT INTO " + INGRED_TABLE + " VALUES ('6', '1', '6', 'syrup', 'maple');");

            //old fashioned bacon and eggs
            db.execSQL("INSERT INTO " + INGRED_TABLE + " VALUES ('7', '2', '1', 'bacon', 'thick-sliced');");
            db.execSQL("INSERT INTO " + INGRED_TABLE + " VALUES ('8', '2', '2', '2 eggs', 'beaten');");
            db.execSQL("INSERT INTO " + INGRED_TABLE + " VALUES ('9', '2', '3', 'dash of pepper', 'for the eggs');");

            //create recipeStepTable
            db.execSQL(CREATE_RECIPE_STEP_TABLE);

            //add default steps to recipyStepTable
            //STEP_ID, STEP_MEAL_ID, STEP_NUM, STEP_INSTRUCTION, STEP_NEEDS_TIMER, STEP_TIMER_AMT
            //pancakes
            db.execSQL("INSERT INTO " + STEP_TABLE + " VALUES ('1', '1', '1', " +
                    "'mix egg, flour, water, and milk together in a bowl until thick');");
            db.execSQL("INSERT INTO " + STEP_TABLE + " VALUES ('2', '1', '2', " +
                    "'spray PAM into a large frying pan');");
            db.execSQL("INSERT INTO " + STEP_TABLE + " VALUES ('3', '1', '3', " +
                    "'put pan on stove top at medium-high heat (2 min)');");
            db.execSQL("INSERT INTO " + STEP_TABLE + " VALUES ('4', '1', '4', " +
                    "'pour pancake batter into hot pan and wait one minute');");
            db.execSQL("INSERT INTO " + STEP_TABLE + " VALUES ('5', '1', '5', " +
                    "'flip pancake to make sure it is golden brown on the bottom, then wait 1 minute');");
            db.execSQL("INSERT INTO " + STEP_TABLE + " VALUES ('6', '1', '6', " +
                    "'remove pancake from heat and spread butter on top, then serve with syrup');");
            db.execSQL("INSERT INTO " + STEP_TABLE + " VALUES ('7', '2', '1', " +
                    "'place bacon strips in a frying pan at medium heat');");
            db.execSQL("INSERT INTO " + STEP_TABLE + " VALUES ('8', '2', '2', " +
                    "'use tongs to flip bacon every minute or so - make sure not to burn it');");
            db.execSQL("INSERT INTO " + STEP_TABLE + " VALUES ('9', '2', '3', " +
                    "'lay finished bacon on a plate and dab with paper towels');");
            db.execSQL("INSERT INTO " + STEP_TABLE + " VALUES ('10', '2', '4', " +
                    "'pour eggs into still-cooking bacon grease in the pan(unhealthy, but so delicious)');");
            db.execSQL("INSERT INTO " + STEP_TABLE + " VALUES ('11', '2', '5', " +
                    "'cook eggs until fluffy - about 30 to 45 seconds');");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL(DROP_INGREDIENT_TABLE);
            db.execSQL(DROP_STEP_TABLE);
            db.execSQL(DROP_MEAL_TABLE);
            onCreate(db);
        }
    }

    public MealHelperDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    private void openWritableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void closeDB() {
        db.close();
    }


    public ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        this.openReadableDB();
        Cursor cursor = db.query(MEAL_TABLE, null, null, null, null, null, MEAL_NAME, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Recipe recipe = new Recipe();
            recipe.setRecipeID(cursor.getInt(MEAL_ID_COL));
            recipe.setRecipeName(cursor.getString(MEAL_NAME_COL));
            recipe.setTimerAmount(cursor.getLong(MEAL_TIMER_LENGTH_COL));
            recipe.setIngredients(this.getRecipeIngredients(recipe.getRecipeID()));
            recipe.setRecipeSteps(this.getRecipeSteps(recipe.getRecipeID()));
            recipes.add(recipe);
            cursor.moveToNext();
        }
        this.closeDB();
        if (cursor != null){
            cursor.close();
        }
        return recipes;

    }
    public ArrayList<Ingredient> getRecipeIngredients(int mealID){
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        this.openReadableDB();
        Ingredient ingred;
        String where = INGRED_MEAL_ID + " = ? ";
        String[] whereargs = {Integer.toString(mealID)};

        Cursor cursor = db.query(INGRED_TABLE, null, where, whereargs, null, null, INGRED_NUM, null);
        cursor.moveToFirst();

        //INGRED_ID, INGRED_MEAL_ID, INGRED_NUM, INGRED_TITLE, INGRED_DESC
        while (!cursor.isAfterLast()){
            ingred = new Ingredient();
            ingred.setIngredientID(cursor.getInt(INGRED_ID_COL));
            ingred.setMealID(cursor.getInt(INGRED_MEAL_ID_COL));
            ingred.setIngredientNum(cursor.getInt(INGRED_NUM_COL));
            ingred.setIngredientTitle(cursor.getString(INGRED_TITLE_COL));
            ingred.setIngredientDesc(cursor.getString(INGRED_DESC_COL));
            ingredients.add(ingred);
            cursor.moveToNext();
        }
        this.closeDB();
        if (cursor != null){
            cursor.close();
        }
        return ingredients;
    }

    public ArrayList<RecipeStep> getRecipeSteps(int mealID){
        ArrayList<RecipeStep> recipeSteps = new ArrayList<RecipeStep>();
        this.openReadableDB();
        RecipeStep step;
        String where = STEP_MEAL_ID + " = ? ";
        String[] whereargs = {Integer.toString(mealID)};

        Cursor cursor = db.query(STEP_TABLE, null, where, whereargs, null, null, STEP_NUM, null);
        cursor.moveToFirst();

        //STEP_ID, STEP_MEAL_ID, STEP_NUM, STEP_INSTRUCTION
        while (!cursor.isAfterLast()){
            step = new RecipeStep();
            step.setStepID(cursor.getInt(STEP_ID_COL));
            step.setStepMealID(cursor.getInt(STEP_MEAL_ID_COL));
            step.setStepNumber(cursor.getInt(STEP_NUM_COL));
            step.setStepInstruction(cursor.getString(STEP_INST_COL));
            recipeSteps.add(step);
            cursor.moveToNext();
        }
        this.closeDB();
        if (cursor != null){
            cursor.close();
        }
        return recipeSteps;
    }

    public void addRecipeIngredientsToDB(ArrayList<Ingredient> list){
        //INGRED_ID, INGRED_MEAL_ID, INGRED_NUM, INGRED_TITLE, INGRED_DESC
        ContentValues cv;

        if(!list.isEmpty()){
            this.openWritableDB();
            for (Ingredient ingred : list){
                cv = new ContentValues();
                cv.put(INGRED_MEAL_ID, ingred.getMealID());
                cv.put(INGRED_NUM, ingred.getIngredientNum());
                cv.put(INGRED_TITLE, ingred.getIngredientTitle());
                cv.put(INGRED_DESC, ingred.getIngredientDesc());
                if(ingred.getIngredientID() == -1){
                    db.insert(INGRED_TABLE, null, cv);
                }
                else{
                    String where = INGRED_ID + " = ?";
                    String[] whereargs = {String.valueOf(ingred.getIngredientID())};
                    cv.put(INGRED_ID, ingred.getIngredientID());
                    db.update(INGRED_TABLE, cv, where, whereargs);
                }

            }
            this.closeDB();
        }
    }

    public void addRecipeStepsToDB(ArrayList<RecipeStep> list) {
        ContentValues cv;
        if(!list.isEmpty()){
            this.openWritableDB();
            for (RecipeStep step : list){
                cv = new ContentValues();
                cv.put(STEP_MEAL_ID, step.getStepMealID());
                cv.put(STEP_NUM, step.getStepNumber());
                cv.put(STEP_INSTRUCTION, step.getStepInstruction());
                if (step.getStepID() == -1) {
                    db.insert(STEP_TABLE, null, cv);
                } else {
                    cv.put(STEP_ID, step.getStepID());
                    String where = STEP_ID + " = ?";
                    String[] whereargs = {String.valueOf(step.getStepID())};
                    db.update(STEP_TABLE, cv, where, whereargs);
                }
            }
            this.closeDB();
        }
    }

    public long addNewRecipeToDB(Recipe recipe){
        ContentValues cv = new ContentValues();
        this.openWritableDB();
        cv.put(MEAL_NAME, recipe.getRecipeName());
        cv.put(MEAL_TIMER_LENGTH, recipe.getTimerAmount());
        long mealID = db.insert(MEAL_TABLE,null, cv);

        this.closeDB();
        return mealID;
    }

    public void deleteRecipeStepsfromDB(int mealID){
        String where = MEAL_ID + " = ?";
        String[] whereargs = {String.valueOf(mealID)};
        this.openWritableDB();
        db.delete(STEP_TABLE, where, whereargs);
        this.closeDB();
    }
    public void addTimerToRecipe(long timerMillis, int mealID){
        String where = MEAL_ID + " = ?";
        this.openWritableDB();
        String mealIDStr = String.valueOf(mealID);
        String[] whereargs = {mealIDStr};
        ContentValues cv = new ContentValues();
        cv.put(MEAL_TIMER_LENGTH, timerMillis);

        db.update(MEAL_TABLE, cv, where, whereargs);
        this.closeDB();
    }
}
