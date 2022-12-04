package com.example.studentapp_eoc_soc;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

// reference: https://www.youtube.com/watch?v=26PVJh9PmgU
public class FoodMenuExpListAdapter extends BaseExpandableListAdapter {
    private final ArrayList<String> foodCategories;
    private final HashMap<String, ArrayList<FoodItem>> menus;

    public FoodMenuExpListAdapter(ArrayList<String> foodCategories, HashMap<String, ArrayList<FoodItem>> menus) {
        this.foodCategories = foodCategories;
        this.menus = menus;
    }

    // number of food category
    @Override
    public int getGroupCount() {
        return foodCategories.size();
    }

    // number of food item in a food category
    @Override
    public int getChildrenCount(int i) {
        if (menus.get(foodCategories.get(i)) != null) {
            return menus.get(foodCategories.get(i)).size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int i) {
        return foodCategories.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return Objects.requireNonNull(menus.get(foodCategories.get(i))).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    // instantiate food category
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_expandable_list_item_1, viewGroup, false);
        // initialise food category
        TextView groupTitle = view.findViewById(android.R.id.text1);
        groupTitle.setText(String.valueOf(getGroup(i)));
        // text style
        groupTitle.setTypeface(null, Typeface.BOLD);
        groupTitle.setTextColor(Color.BLACK);

        return view;
    }

    // instantiate food item in a food category
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_menu_row, viewGroup, false);

        FoodItem thisFood = (FoodItem) getChild(i, i1);

        // initialise food name
        TextView foodName = view.findViewById(R.id.foodName);
        foodName.setText(thisFood.getFoodName());
        // initialise food vegan tag
        TextView veganTag = view.findViewById(R.id.vegan);
        if (thisFood.isVegan())
            veganTag.setText("v");
        // initialise food price
        TextView foodPrice = view.findViewById(R.id.foodPrice);
        foodPrice.setText(String.format("\u20ac %.2f", thisFood.getPrice()));

        // initialise favourite food checkbox, checked if its in favourite food list
        CheckBox favCheckbox = view.findViewById(R.id.favCheckbox);
        favCheckbox.setChecked(EocActivity.favFoodIds.contains(thisFood.getFoodId()));
        // favourite food checkbox: click
        favCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EocDbManager eocDb = new EocDbManager(viewGroup.getContext());
                eocDb.open();

                // if its checked, insert favourite food to database
                // else, remove favourite food from database
                if (((CheckBox) view).isChecked()) {
                    EocActivity.favFoodIds.add(thisFood.getFoodId());
                    eocDb.insertFavouriteItem(MainActivity.user.getUserId(), thisFood.getFoodId());
                    Toast.makeText(viewGroup.getContext(), thisFood.getFoodName() + " is added to favourites", Toast.LENGTH_SHORT).show();
                } else {
                    EocActivity.favFoodIds.remove(Integer.valueOf(thisFood.getFoodId()));
                    eocDb.removeFavouriteItem(MainActivity.user.getUserId(), thisFood.getFoodId());
                    Toast.makeText(viewGroup.getContext(), thisFood.getFoodName() + " is removed from favourites", Toast.LENGTH_SHORT).show();
                }

                eocDb.close();
            }
        });

        return view;
    }

    // set food item can't be selected
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
