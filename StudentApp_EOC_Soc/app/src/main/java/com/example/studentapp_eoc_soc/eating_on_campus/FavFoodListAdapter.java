package com.example.studentapp_eoc_soc.eating_on_campus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.studentapp_eoc_soc.MainActivity;
import com.example.studentapp_eoc_soc.R;

import java.util.List;

public class FavFoodListAdapter extends ArrayAdapter<FoodItem> {
    private final List<FoodItem> favFoodItems;

    public FavFoodListAdapter(@NonNull Context context, @NonNull List<FoodItem> objects) {
        super(context, 0, objects);
        this.favFoodItems = objects;
    }

    // instantiate favourite food
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_food_row, parent, false);

        FoodItem thisFood = favFoodItems.get(position);

        // initialise favourite food category
        TextView favFoodCategory = convertView.findViewById(R.id.favFoodCategory);
        favFoodCategory.setText(thisFood.getCategory());
        // initialise favourite food name
        TextView favFoodName = convertView.findViewById(R.id.favFoodName);
        favFoodName.setText(thisFood.getFoodName());
        // initialise favourite food vegan tag
        TextView favFoodVeganTag = convertView.findViewById(R.id.favFoodVegan);
        if (thisFood.isVegan())
            favFoodVeganTag.setText("v");

        // initialise remove favourite food button
        ImageButton removeFavButton = convertView.findViewById(R.id.removeFavButton);
        // remove favourite food button: click
        removeFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EocDbManager eocDb = new EocDbManager(parent.getContext());
                eocDb.open();

                // remove favourite food from database
                favFoodItems.remove(thisFood);
                EocActivity.favFoodIds.remove(Integer.valueOf(thisFood.getFoodId()));
                eocDb.removeFavouriteItem(MainActivity.user.getUserId(), thisFood.getFoodId());
                Toast.makeText(parent.getContext(), thisFood.getFoodName() + " is removed from favourites", Toast.LENGTH_SHORT).show();

                // notify favourite food list that data set is changed
                notifyDataSetChanged();

                eocDb.close();
            }
        });

        return convertView;
    }
}
