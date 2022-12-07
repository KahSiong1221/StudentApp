package com.example.studentapp_eoc_soc.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.ListFragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.studentapp_eoc_soc.R;
import com.example.studentapp_eoc_soc.SaDbManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link computerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class computerFragment extends ListFragment{
   private ArrayList<computerbooking> myComputerBookings = new ArrayList<computerbooking>();
   private SaDbManager dbManager;
   private mybookingFragmentAdapter myAdapter;
   public  static final String SHARED_PREFS = "shared_prefs";
   public  static final String USER_ID_KEY = "user_key";
   private int user_id;
   View lv;
   SharedPreferences sharedPreferences;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public computerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment computerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static computerFragment newInstance(String param1, String param2) {
        computerFragment fragment = new computerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new SaDbManager(getContext()); ;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getInt(USER_ID_KEY,0);
        getMyComputerBookings(user_id);




    }

    public void getMyComputerBookings(int userID) {
        dbManager.open();
        Cursor cursor;
        cursor = dbManager.getMyComputerBooking(userID);
        cursor.moveToFirst();
        if(cursor.moveToFirst())
        {
            do{
                computerbooking thisComputerbooking = new computerbooking(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6)
                );

                Log.i("This is 1", cursor.getString(1));
                Log.i("This is 2", cursor.getString(2));
                Log.i("This is 3", cursor.getString(3));
                Log.i("This is 4", cursor.getString(4));
                Log.i("This is 5", cursor.getString(5));

                myComputerBookings.add(thisComputerbooking);
            } while (cursor.moveToNext());


        }

        dbManager.close();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_computer,container,false);

        ListView mylist = (ListView)v.findViewById(android.R.id.list);
            myComputerbookingAdapter myAdapter = new myComputerbookingAdapter(getContext(),R.layout.mybooking,myComputerBookings);
                mylist.setAdapter(myAdapter);




    return v;
    }






    class myComputerbookingAdapter extends ArrayAdapter<computerbooking> {

        private ArrayList<computerbooking> myComputerBookings;
        private ArrayList<computerbooking> cancelThisBooking;

        public myComputerbookingAdapter(Context context, int textViewresourceId, ArrayList<computerbooking> objects )
        {
            super(context,textViewresourceId,objects);
            this.myComputerBookings = objects;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;

            if(v == null) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.mybooking, null);
            }

            computerbooking currentComputerBooking = myComputerBookings.get(position);

            if(currentComputerBooking != null) {
                String myTime = currentComputerBooking.getStartTime() + "-" + currentComputerBooking.getEndTime();

                TextView mybookingNameText = (TextView) v.findViewById(R.id.myBookingName);
                TextView mybookingDateText = (TextView) v.findViewById(R.id.mybookingDate);
                TextView mybookingTimeText = (TextView) v.findViewById(R.id.myBookingTime);
                Button mybookingStatusButton = (Button) v.findViewById(R.id.myBookingStatus);
                mybookingStatusButton.setText("cancel");


                mybookingStatusButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),bookCancelLibrary.class);

                        intent.putExtra("BOOKINGTYPE_KEY", "LibraryComputer");
                        intent.putExtra("BookingID", currentComputerBooking.getBookingID());
                        intent.putExtra("startTime", currentComputerBooking.getStartTime());
                        intent.putExtra("endTime", currentComputerBooking.getEndTime());
                        intent.putExtra("date", currentComputerBooking.getDate());
                        intent.putExtras(intent);

                        getActivity().startActivity(intent);
                    }
                });




                mybookingNameText.setText("ID: "+ currentComputerBooking.getComputerName());
                mybookingDateText.setText(currentComputerBooking.getDate() );
                mybookingTimeText.setText(myTime);
            }

            return v;
        }
    }
}