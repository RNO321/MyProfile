package com.dvora.myprofile;

// Imports
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ThemeSettingActivity extends AppCompatActivity {

    // Imports
    private final String POSITION_KEY = "selected_theme_position";
    private String themeIndex;
    private ActionBar actionBar;
    private Button btnConfirm;
    private Spinner themeSpinner;
    private String selectedTheme;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_setting);

        // Change the color of action bar from purple to black.
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        }

        // Get all components by each component's id and store them in variables.
        btnConfirm = findViewById(R.id.btn_confirm);
        themeSpinner = findViewById(R.id.spinner);
        constraintLayout = findViewById(R.id.theme_settings_layout);

        // Set up the adapter and attach it to the spinner.
        ArrayAdapter<CharSequence> themeAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_themes, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        themeAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        themeSpinner.setAdapter(themeAdapter);

    }//end method

    @Override
    protected void onStart() {
        super.onStart();

        /*
        It checks if there is any intent received with a message to set the value of
        "selectedTheme" variable.
         */
        Intent receivedIntent = getIntent();

        if (receivedIntent != null && receivedIntent.getStringExtra(MainActivity.MY_KEY) != null) {
            selectedTheme = receivedIntent.getStringExtra(MainActivity.MY_KEY);
        }

        // Helper method call to determine the index of the selected theme in spinner.
        getIndex(selectedTheme);

        // Helper method call to get the user selected theme from the spinner.
        selectThemeFromSpinner();

        // Set the user selected theme on the spinner.
        themeSpinner.setSelection(Integer.parseInt(themeIndex));

        // Helper method call to perform the operation when the "confirm" button is clicked.
        clickConfirmBtn();

    }//end method

    /**
     * This is a private utility method to send an intent with a string message to the target
     * activity when the user clicks on the "confirm" button. It sends the user selected theme
     * value to the target activity with the intent.
     */
    private void clickConfirmBtn() {
        btnConfirm.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra(MainActivity.MY_KEY, selectedTheme);
            startActivity(intent);
        });
    }//end method

    /**
     * This method sets the listener on the selected item by the user in spinner. If the user
     * selects a theme in the spinner, then set that theme as the value of the "selectedTheme"
     * variable.
     */
    private void selectThemeFromSpinner() {
        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTheme = adapterView.getSelectedItem().toString();
                getIndex(selectedTheme);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }//end method

    /**
     * This method determines the index of the selected theme in spinner and set it as the value
     * of the global variable "themeIndex".
     * @param selectedTheme user selected theme whose index needs to be determined in the spinner.
     */
    private void getIndex(String selectedTheme) {

        if (selectedTheme.equalsIgnoreCase("spring")) {
            themeIndex = "0";
        }
        else if (selectedTheme.equalsIgnoreCase("summer")) {
            themeIndex = "1";
        }
        else if (selectedTheme.equalsIgnoreCase("fall")) {
            themeIndex = "2";
        }
        else {
            themeIndex = "3";
        }

    }//end method

}//end class