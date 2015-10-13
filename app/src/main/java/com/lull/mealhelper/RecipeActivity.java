package com.lull.mealhelper;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;


public class RecipeActivity extends AppCompatActivity{

    private static final int NUM_PAGES = 2;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private int mealID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Intent intent = getIntent();
        mealID = intent.getIntExtra("mealID", 2);

        mPager = (ViewPager) findViewById(R.id.recipePager);
        mPagerAdapter = new RecipePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    private class RecipePagerAdapter extends FragmentStatePagerAdapter {
        public RecipePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new IngredientFragment();
                case 1:
                    return new RecipeStepFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
