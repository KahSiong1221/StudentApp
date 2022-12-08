package com.example.studentapp_eoc_soc.library;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import com.example.studentapp_eoc_soc.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link computerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class roomFragment extends ListFragment{
    private ArrayList<roombooking> myRoomBookings = new ArrayList<roombooking>();
    private lib_DbManager dbManager;
    private mybookingFragmentAdapter myAdapter;
    public  static final String SHARED_PREFS = "shared_prefs";
    public  static final String USER_ID_KEY = "user_key";
    private int user_id;
    private String bookingType = "Room";

    SharedPreferences sharedPreferences;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public roomFragment() {
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
    public static roomFragment newInstance(String param1, String param2) {
        roomFragment fragment = new roomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new lib_DbManager(getContext()); ;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getInt(USER_ID_KEY,0);
        getMyRoomBookings(user_id);




    }

    public void getMyRoomBookings(int userID) {
        dbManager.open();
        Cursor cursor;
        cursor = dbManager.getMyRoomBooking(userID);
        cursor.moveToFirst();
        if(cursor.moveToFirst())
        {
            do{
                roombooking thisRoombooking = new roombooking(
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

                myRoomBookings.add(thisRoombooking);
            } while (cursor.moveToNext());


        }

        dbManager.close();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.library_fragment_room,container,false);

        ListView mylist = (ListView)v.findViewById(android.R.id.list);
        myRoombookingAdapter myAdapter = new myRoombookingAdapter(getContext(),R.layout.library_my_booking,myRoomBookings);
        mylist.setAdapter(myAdapter);


        return v;
    }






    class myRoombookingAdapter extends ArrayAdapter<roombooking> {

        private ArrayList<roombooking> myRoomBookings;
        private ArrayList<roombooking> cancelThisBooking;

        public myRoombookingAdapter(Context context, int textViewresourceId, ArrayList<roombooking> objects )
        {
            super(context,textViewresourceId,objects);
            this.myRoomBookings = objects;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;

            if(v == null) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.library_my_booking, null);
            }

            roombooking currentRoomBooking = myRoomBookings.get(position);

            if(currentRoomBooking != null) {
                String myTime = currentRoomBooking.getStartTime() + "-" + currentRoomBooking.getEndTime();

                TextView mybookingNameText = (TextView) v.findViewById(R.id.myBookingName);
                TextView mybookingDateText = (TextView) v.findViewById(R.id.mybookingDate);
                TextView mybookingTimeText = (TextView) v.findViewById(R.id.myBookingTime);
                Button mybookingStatusButton = (Button) v.findViewById(R.id.myBookingStatus);
                mybookingStatusButton.setText("cancel");


                mybookingStatusButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),bookCancelLibrary.class);

                        intent.putExtra("BOOKINGTYPE_KEY", "LibraryRoom");
                        intent.putExtra("BookingID", currentRoomBooking.getBookingID());
                        intent.putExtra("BookingName", currentRoomBooking.getBookingName());
                        intent.putExtra("startTime", currentRoomBooking.getStartTime());
                        intent.putExtra("endTime", currentRoomBooking.getEndTime());
                        intent.putExtra("date", currentRoomBooking.getDate());
                        intent.putExtra("BookingType", bookingType);
                        intent.putExtras(intent);

                        Log.i("this is: ",Integer.toString(currentRoomBooking.getBookingID()));

                        getActivity().startActivity(intent);

                    }
                });




                mybookingNameText.setText("Name: "+ currentRoomBooking.getBookingName());
                mybookingDateText.setText(currentRoomBooking.getDate() );
                mybookingTimeText.setText(myTime);
            }

            return v;
        }
    }
}