/*
    Name: Thomas Cheng
    Date: May 29, 2018
    Description: This is a colour chooser app.
 */

package com.example.a323976001.colourchooser;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
// importing the widgets
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Switch;
// importing the listeners
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
// importing extra needed classes
import java.util.Arrays;

public class MainActivity extends Activity {

    private TextView sampleSentenceTextView;
    private TextView rComponentTextView;
    private TextView gComponentTextView;
    private TextView bComponentTextView;
    private TextView colourSwitchTextView;
    private Button loadColourButton;
    private Button saveColourButton;
    private Switch colourSwitch;
    private SeekBar rComponentSeekBar;
    private SeekBar gComponentSeekBar;
    private SeekBar bComponentSeekBar;
    private Spinner colourSpinner;

    private int rValueBackground = 255;
    private int gValueBackground = 255;
    private int bValueBackground = 255;
    private int rValueText = 0;
    private int gValueText = 0;
    private int bValueText = 0;
    private int spinnerIndex = 0;
    private int[][] colourList = new int[5][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // finding all the widgets that will be used in the app
        sampleSentenceTextView = findViewById(R.id.sampleSentenceTextView);
        rComponentTextView = findViewById(R.id.rComponentTextView);
        gComponentTextView = findViewById(R.id.gComponentTextView);
        bComponentTextView = findViewById(R.id.bComponentTextView);
        colourSwitchTextView = findViewById(R.id.colourSwitchTextView);
        loadColourButton = findViewById(R.id.loadColourButton);
        saveColourButton = findViewById(R.id.saveColourButton);
        colourSwitch = findViewById(R.id.colourViewingSwitch);
        rComponentSeekBar = findViewById(R.id.rComponentSeekBar);
        gComponentSeekBar = findViewById(R.id.gComponentSeekBar);
        bComponentSeekBar = findViewById(R.id.bComponentSeekBar);
        colourSpinner = findViewById(R.id.colourChooserSpinner);

        // Create an ArrayAdapter for our spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.colour_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the adapter for the spinner
        colourSpinner.setAdapter(spinnerAdapter);
        colourSpinner.setOnItemSelectedListener(new SpinnerListener());

        // creating the button listener
        OnClickListener buttonListener = new OnButtonClickListener();
        // associating it with the buttons
        saveColourButton.setOnClickListener(buttonListener);
        loadColourButton.setOnClickListener(buttonListener);

        // setting the listeners
        rComponentSeekBar.setOnSeekBarChangeListener(new SeekBarListener());
        gComponentSeekBar.setOnSeekBarChangeListener(new SeekBarListener());
        bComponentSeekBar.setOnSeekBarChangeListener(new SeekBarListener());

        // setting the listener for the switch
        colourSwitch.setOnCheckedChangeListener(new OnCheckChangeListener());

        // setting all the colours in the spinner widget to white
        for (int row[] : colourList) {
            Arrays.fill(row, 255);
        }

        displayColour();
    }

    /*
        This method will change either all of the TextView colours or the background colour, depending
        on if the switch is on. This method accepts and returns no values.
     */
    private void displayColour(){
        // if the user moves thr switch into the on position
        if (colourSwitch.isChecked()) {
            sampleSentenceTextView.setTextColor(Color.argb(255, rValueText, gValueText, bValueText));
            rComponentTextView.setTextColor(Color.argb(255, rValueText, gValueText, bValueText));
            gComponentTextView.setTextColor(Color.argb(255, rValueText, gValueText, bValueText));
            bComponentTextView.setTextColor(Color.argb(255, rValueText, gValueText, bValueText));
            colourSwitchTextView.setTextColor(Color.argb(255, rValueText, gValueText, bValueText));
        }
        else{
            getWindow().getDecorView().setBackgroundColor(Color.argb(255, rValueBackground, gValueBackground, bValueBackground));
        }
    }

    /*
        This method will set the progress of all the seekBars to either the current text colour or
        the background colour. It will also change the TextView to show the new RGB values. This
        method accepts 3 values, all integer values between 0 and 255 inclusive.
        This method returns no values.
     */
    private void changeSeekBarProgress(int r, int g, int b){
        rComponentSeekBar.setProgress(255 - r);
        gComponentSeekBar.setProgress(255 - g);
        bComponentSeekBar.setProgress(255 - b);

        rComponentTextView.setText("R: " + r);
        gComponentTextView.setText("G: " + g);
        bComponentTextView.setText("B: " + b);
    }

    /*
        This is the inner class that implements OnSeekBarChangeListener.
     */
    class SeekBarListener implements OnSeekBarChangeListener {
        /*
            This overridden method will be called when the user moves any of the seekBars. This
            method will update value of the seekBar's respective integer variables. This method
            accepts 3 parameters. A reference to a SeekBar object, an integer, and a boolean value.
            This method returns no values.
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // This switch block of code is to determine which seekBar the user is interacting with.
            switch (seekBar.getId()) {
                // if the rComponentSeekBar is being interacted with
                case (R.id.rComponentSeekBar):
                    // changes the value of rValueText
                    if (colourSwitch.isChecked()) {
                        rValueText = 255 - progress;
                        rComponentTextView.setText("R: " + rValueText);
                    }
                    // changes the value of rValueBackground
                    else {
                        rValueBackground = 255 - progress;
                        rComponentTextView.setText("R: " + rValueBackground);
                    }
                    break;
                // if the gComponentSeekBar is being interacted with
                case (R.id.gComponentSeekBar):
                    // changes the value of gValueText
                    if (colourSwitch.isChecked()) {
                        gValueText = 255 - progress;
                        gComponentTextView.setText("G: " + gValueText);
                    }
                    // changes the value of gValueBackground
                    else {
                        gValueBackground = 255 - progress;
                        gComponentTextView.setText("G: " + gValueBackground);
                    }
                    break;
                // if the bComponentSeekBar is being interacted with
                case (R.id.bComponentSeekBar):
                    // changes the value of bValueText
                    if (colourSwitch.isChecked()) {
                        bValueText = 255 - progress;
                        bComponentTextView.setText("B: " + bValueText);
                    }
                    // changes the value of bValueBackground
                    else {
                        bValueBackground = 255 - progress;
                        bComponentTextView.setText("B: " + bValueBackground);
                    }
                    break;
            }
            displayColour();
        }

        /*
            This method is not being used.
         */
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // Called when the user starts changing the SeekBar
            // Not Used / Implemented
        }

        /*
            This method is not being used.
         */
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // Called when the user finishes changing the SeekBar
            // Not Used / Implemented
        }
    }

    /*
        This is the inner class that implements OnItemSelectedListener.
     */
    class SpinnerListener implements OnItemSelectedListener {
        /*
            This overridden method will set the value of spinnerIndex to the current index of the
            spinner the user selected. This method accepts 4 parameters. A reference to an
            AdapterView<?> object, a reference to a View object, an integer, and a long value.
            This method returns no values.
         */
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            spinnerIndex = position;
        }

        /*
            This method is not being used.
         */
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Not Used / Implemented
        }
    }

    /*
        This is the inner class that implements OnClickListener.
     */
    class OnButtonClickListener implements OnClickListener{
        /*
            This overridden method will be called if the user taps any of the buttons. This method
            accepts 1 parameter: a reference to a View object. This method returns no values.
         */
        @Override
        public void onClick( View v){
            // This switch code will determine which button the user tapped
            switch (v.getId()){
                // if the load colour button was pressed
                case (R.id.loadColourButton):
                    if (colourSwitch.isChecked()) {
                        rValueText = colourList[spinnerIndex][0];
                        gValueText = colourList[spinnerIndex][1];
                        bValueText = colourList[spinnerIndex][2];

                        changeSeekBarProgress(rValueText, gValueText, bValueText);
                    }
                    else {
                        rValueBackground = colourList[spinnerIndex][0];
                        gValueBackground = colourList[spinnerIndex][1];
                        bValueBackground = colourList[spinnerIndex][2];

                        changeSeekBarProgress(rValueBackground, gValueBackground, bValueBackground);
                    }
                    break;
                // if the save colour button was pressed
                case (R.id.saveColourButton):
                    if (colourSwitch.isChecked()) {
                        colourList[spinnerIndex][0] = rValueText;
                        colourList[spinnerIndex][1] = gValueText;
                        colourList[spinnerIndex][2] = bValueText;
                    }
                    else{
                        colourList[spinnerIndex][0] = rValueBackground;
                        colourList[spinnerIndex][1] = gValueBackground;
                        colourList[spinnerIndex][2] = bValueBackground;
                    }
                    break;
            }
            displayColour();
        }
    }

    /*
        This is the inner class that implements OnCheckedChangeListener,
     */
    class OnCheckChangeListener implements OnCheckedChangeListener{
        /*
            This overridden method will be called when the user taps on the colour switch. This method
            will call the changeSeekBar method, and pass in the appropriate RGB combination. This
            method accepts 2 parameters. A reference to a CompoundButton object, and a boolean value.
            This method returns no values.
         */
        @Override
        public void onCheckedChanged(CompoundButton colourSwitch, boolean isChecked) {
            if (isChecked){
                changeSeekBarProgress(rValueText, gValueText, bValueText);
            }
            else{
                changeSeekBarProgress(rValueBackground, gValueBackground, bValueBackground);
            }
            displayColour();
        }
    }
}