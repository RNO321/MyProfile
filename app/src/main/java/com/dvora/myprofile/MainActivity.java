package com.dvora.myprofile;

/*
Summer 2022-CCCS-325-755-Assignment 2

This assignment is about making an android application about a profile.

References used for the entire project:

1. CCCS 325 Professor's class lectures and slides.
2. https://static.life.com/wp-content/uploads/2022/01/21143303/Sherlock_Cumberbatch.jpg
3. https://www.geeksforgeeks.org/how-to-change-the-color-of-action-bar-in-an-android-app/
4. https://icons-for-free.com/download-icon-settings+icon-1320183238404656194_512.png
5. https://en.wikipedia.org/wiki/Sherlock_Holmes
6. https://code2care.org/pages/center-align-textview-android-horizontally-or-vertically
7. https://w0.peakpx.com/wallpaper/974/419/HD-wallpaper-ios8-apple-ios-mountains-nature-snow-white-winter.jpg
8. https://i.pinimg.com/originals/90/3f/e2/903fe2a9b2d98a773d14a34af042cac4.jpg
9. https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTnwZgjbrgb2NDU4BaKqglv41X-4seszoZ8u2PxmzzlO0oXOer00OgkD85-WCqmuWsarKk&usqp=CAU
10. https://www.teahub.io/photos/full/319-3194980_maple-leaves-transparent-background-fall-leaves-png.jpg
 */

// Imports
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    // Instance variables
    private ImageButton btn_settings;
    private ConstraintLayout constraintLayout;

    // Default selected theme is spring.
    private String selectedTheme = "spring";

    // Static variables
    public static final String SHARED_PREFERENCES_FILE_NAME = "User_theme";
    public static final String MY_KEY = "User_selected_theme";

    // Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Change the color of action bar from purple to black.
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#000000"));

        if (actionBar != null) {
            actionBar.setBackgroundDrawable(cd);
        }

        // Get all components by each component's id and store them in variables.
        btn_settings = findViewById(R.id.btn_settings);
        constraintLayout = findViewById(R.id.main_layout);

    }//end method

    @Override
    protected void onStart() {
        super.onStart();

        /*
        It checks if there is any intent received with a message to set the value of
        "selectedTheme" variable.
         */
        Intent intent = getIntent();

        // If the intent has been received with a string message.
        if (intent != null && intent.getStringExtra(MY_KEY) != null) {
            selectedTheme = intent.getStringExtra(MY_KEY);
        }
        else {
            /*
            Get the value of "MY_KEY" key from the shared preferences file i.e. the theme
            preference that the user set last time while using this app and set it as the value
            of "selectedTheme" variable.
             */
            selectedTheme = getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE).
                    getString(MY_KEY, "spring");
        }

        // Change the activity background with appropriate image per the user selected theme.
        if (selectedTheme.equalsIgnoreCase("summer")) {
            constraintLayout.setBackgroundResource(R.drawable.bg_summer);
        }
        else if (selectedTheme.equalsIgnoreCase("fall")) {
            constraintLayout.setBackgroundResource(R.drawable.bg_fall);
        }
        else if (selectedTheme.equalsIgnoreCase("winter")) {
            constraintLayout.setBackgroundResource(R.drawable.bg_winter);
        }
        else {
            constraintLayout.setBackgroundResource(R.drawable.bg_spring);
        }

        // Helper method call to store the current value of the user selected theme.
        storeTheme();

        // Helper method call to perform the operation when "settings" button is clicked.
        clickSettingsBtn();

    }//end method

    /**
     * This is a private utility method to store the current value of the "selectedTheme" variable
     * in the shared preferences file as soon as the user selected theme is decided, so that it can
     * remember the previous value of the user selected theme whenever this activity is restarted.
     */
    private void storeTheme() {
        SharedPreferences sharedPreferences = this.getSharedPreferences
                (SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);

        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(MY_KEY, selectedTheme);
        ed.apply();
    }

    /**
     * This is a private utility method to send an intent with a string message to the target
     * activity when the user clicks on the "settings" button. It sends the current value of the
     * "selectedTheme" variable to the target activity with the intent.
     */
    private void clickSettingsBtn() {

        btn_settings.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ThemeSettingActivity.class);
            intent.putExtra(MY_KEY, selectedTheme);
            startActivity(intent);
        });

    }//end method

}//end class