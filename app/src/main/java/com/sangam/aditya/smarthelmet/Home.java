package com.sangam.aditya.smarthelmet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;

import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;


public class Home extends ActionBarActivity {
    //CONSTANTS

    // this is the String used to identify and accesses the shared preferences file used to store the numbers.
    public static final String EMERGENCY_NUMBER_MASTER = "com.sangam.smarthelmet_emergencynumbermasterkey";
    public static final String EMERGENCY_NUMBER1 = "com.sangam.smarthelmet_emergencynumber1key";
    public static final String EMERGENCY_NUMBER2 = "com.sangam.smarthelmet_emergencynumber2key";
    public static final String EMERGENCY_NUMBER3 = "com.sangam.smarthelmet_emergencynumber3key";

    // this is the default number which will be stored as emergency numbers
    private static final long DEFAULT_EMERGENCY_NUMBER = 0;


    // these are the variables which hold the numbers them self
    long emergency_number1;
    long emergency_number2;
    long emergency_number3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // We get the stored emergency numbers onto application's variables
        restore_data();
    }

    // This is called when we click the change emergency numbers button
    public void set_emergency_number(View v){
        /* Make an intent from "this" ie Home - the class to EditEmergencyNumber the class */
        Intent intent = new Intent(this, EditEmergencyNumber.class);
        /*
        For now we don't have any Extra to send along with this intent
        So lets just launch the next activity.
        */
        startActivity(intent);
    }

    // This is called when the user clicks the start journey button
    public void start_journey(View v){
        // Because this requires Google Play Services
        if(isGooglePlayServicesAvailable(this)== ConnectionResult.SUCCESS){
            Intent intent = new Intent(this, MapsActivity.class);
            Log.i("debug", "before");
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Your Mobile Doesn't support Google Play Service", Toast.LENGTH_LONG).show();
        }

    }

    private void restore_data() {
        // here we get a handle on the shared preferences
        SharedPreferences sharedPref = this.getSharedPreferences(EMERGENCY_NUMBER_MASTER, Context.MODE_PRIVATE);

        // Here we get the shared preferences values into usable variables
        emergency_number1 = sharedPref.getLong(EMERGENCY_NUMBER1, DEFAULT_EMERGENCY_NUMBER);
        emergency_number2 = sharedPref.getLong(EMERGENCY_NUMBER2, DEFAULT_EMERGENCY_NUMBER);
        emergency_number3 = sharedPref.getLong(EMERGENCY_NUMBER3, DEFAULT_EMERGENCY_NUMBER);

        // Now if at least one such number has been saved then
        if(emergency_number1 != DEFAULT_EMERGENCY_NUMBER || emergency_number2 != DEFAULT_EMERGENCY_NUMBER || emergency_number3 != DEFAULT_EMERGENCY_NUMBER) {
            //change color of button which is used to Set new emergency numbers AND change its text
            Button emergency = (Button) findViewById(R.id.button_emergency);
            emergency.setTextColor(Color.BLUE);
            emergency.setText("Change Emergency Numbers");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
