package com.example.studentapp_eoc;

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
public class MenuExpListAdapter extends BaseExpandableListAdapter {
    private final ArrayList<String> foodCategories;
    private final HashMap<String, ArrayList<FoodItem>> menus;

    public MenuExpListAdapter(ArrayList<String> foodCategories, HashMap<String, ArrayList<FoodItem>> menus) {
        this.foodCategories = foodCategories;
        this.menus = menus;
    }

    @Override
    public int getGroupCount() {
        return foodCategories.size();
    }

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

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_expandable_list_item_1, viewGroup, false);
        TextView groupTitle = view.findViewById(android.R.id.text1);
        groupTitle.setText(String.valueOf(getGroup(i)));
        // text style
        groupTitle.setTypeface(null, Typeface.BOLD);
        groupTitle.setTextColor(Color.BLUE);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_row, viewGroup, false);

        FoodItem thisFood = (FoodItem) getChild(i, i1);

        TextView foodName = view.findViewById(R.id.foodName);
        foodName.setText(thisFood.getFoodName());

        TextView veganTag = view.findViewById(R.id.vegan);
        if (thisFood.isVegan())
            veganTag.setText("v");

        TextView foodPrice = view.findViewById(R.id.foodPrice);
        foodPrice.setText(String.format("%.2f", thisFood.getPrice()));

        CheckBox favCheckbox = view.findViewById(R.id.favCheckbox);

        favCheckbox.setChecked(EocActivity.favFoodIds.contains(thisFood.getFoodId()));

        favCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EocDbManager eocDb = new EocDbManager(viewGroup.getContext());
                eocDb.open();

                if (((CheckBox) view).isChecked()) {
                    EocActivity.favFoodIds.add(thisFood.getFoodId());
                    eocDb.insertFavouriteItem(EocActivity.user.getUserId(), thisFood.getFoodId());
                    Toast.makeText(viewGroup.getContext(), thisFood.getFoodName() + " is added to favourites", Toast.LENGTH_SHORT).show();
                } else {
                    EocActivity.favFoodIds.remove(Integer.valueOf(thisFood.getFoodId()));
                    eocDb.removeFavouriteItem(EocActivity.user.getUserId(), thisFood.getFoodId());
                    Toast.makeText(viewGroup.getContext(), thisFood.getFoodName() + " is removed from favourites", Toast.LENGTH_SHORT).show();
                }

                eocDb.close();
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
