package com.example.studentapp_eoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

public class FavFoodListAdapter extends ArrayAdapter<FoodItem> {
    private final List<FoodItem> favFoodItems;

    public FavFoodListAdapter(@NonNull Context context, @NonNull List<FoodItem> objects) {
        super(context, 0, objects);
        this.favFoodItems = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_food_row, parent, false);

        FoodItem thisFood = favFoodItems.get(position);

        TextView favFoodCategory = convertView.findViewById(R.id.favFoodCategory);
        favFoodCategory.setText(thisFood.getCategory());

        TextView favFoodName = convertView.findViewById(R.id.favFoodName);
        favFoodName.setText(thisFood.getFoodName());

        TextView favFoodVeganTag = convertView.findViewById(R.id.favFoodVegan);
        if (thisFood.isVegan())
            favFoodVeganTag.setText("v");

        ImageButton removeFavButton = convertView.findViewById(R.id.removeFavButton);

        removeFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EocDbManager eocDb = new EocDbManager(parent.getContext());
                eocDb.open();

                favFoodItems.remove(thisFood);
                MainActivity.favFoodIds.remove(Integer.valueOf(thisFood.getFoodId()));
                eocDb.removeFavouriteItem(MainActivity.user.getUserId(), thisFood.getFoodId());
                Toast.makeText(parent.getContext(), thisFood.getFoodName() + " is removed from favourites", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

                eocDb.close();
            }
        });

        return convertView;
    }
}
