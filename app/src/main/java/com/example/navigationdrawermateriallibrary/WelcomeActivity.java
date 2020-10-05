package com.example.navigationdrawermateriallibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.navigationdrawermateriallibrary.Model.Users;
import com.example.navigationdrawermateriallibrary.Prevalent.PrevalentItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class WelcomeActivity extends AppCompatActivity {
    Button LoginButton;
    Button JoinNowButton;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else{
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            //ActionBar actionBar = getActionBar();
            //actionBar.hide();
        }
        setContentView(R.layout.activity_welcome_alternate_background);

        LoginButton = findViewById(R.id.login_button);
        JoinNowButton = findViewById(R.id.join_now_button);
        Paper.init(this);
        progressDialog = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        JoinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        Paper.book().destroy();

        //check if the person is already logged in, if they are send them straight past this page to the mainactivity page
        String existingPhone = Paper.book().read(PrevalentItem.UserPhoneKey);
        String existingPassword = Paper.book().read(PrevalentItem.UserPasswordKey);


        if ((existingPhone!="") && (existingPassword!="")){ //if both are not null

            if ((!TextUtils.isEmpty(existingPhone)) && (!TextUtils.isEmpty(existingPassword))){ //and both are not empty

                progressDialog.setTitle("Already logged in");
                progressDialog.setMessage("Please wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                AllowAccessToAccount(existingPhone, existingPassword);
            }
        }

    }

    private void AllowAccessToAccount(final String existingPhone, final String existingPassword) {


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(existingPhone).exists()){
                    //check to see if the password matches
                    Users user = snapshot.child("Users").child(existingPhone).getValue(Users.class); //get the element corresponding to that phone number
                    String dbPassword = user.getPassword();
                    if (dbPassword.equals(existingPassword)){//Log the person in
                        Toast.makeText(WelcomeActivity.this, "Welcome back to Walimi", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(WelcomeActivity.this, DisplayItemsActivity.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                    }
                    else{//if password doesnt match, but the phone number existed
                        Toast.makeText(WelcomeActivity.this, "Phone number and password don't match. Please try again", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                }
                else{ //phonenumber does not exist in database
                    Toast.makeText(WelcomeActivity.this, "Phone number not registered, please double check it or create new account", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
