package com.example.navigationdrawermateriallibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText Name, PhoneNumber, Password;
    Button CreateAccountButton;
    ProgressDialog progressDialog;
    String name, phoneNumber, password;
    DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Name = findViewById(R.id.register_name);
        PhoneNumber = findViewById(R.id.register_phone_number);
        Password = findViewById(R.id.register_password);
        CreateAccountButton = findViewById(R.id.create_account_button);
        progressDialog = new ProgressDialog(this);


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

    }

    private void createAccount() {

        name = Name.getText().toString();
        phoneNumber = PhoneNumber.getText().toString();
        password = Password.getText().toString();

        //check for empty entries
        if ((TextUtils.isEmpty(name)) && (TextUtils.isEmpty(phoneNumber)) && (TextUtils.isEmpty(password)))
        {
            Toast.makeText(RegisterActivity.this, "Please enter your name, phone number, and password", Toast.LENGTH_SHORT).show();
        }
        else if ((TextUtils.isEmpty(name)) && (TextUtils.isEmpty(phoneNumber)))
        {
            Toast.makeText(RegisterActivity.this, "Please enter your name and phone number", Toast.LENGTH_SHORT).show();
        }
        else if ((TextUtils.isEmpty(name)) && (TextUtils.isEmpty(password)))
        {
            Toast.makeText(RegisterActivity.this, "Please enter your name and password", Toast.LENGTH_SHORT).show();
        }
        else if ((TextUtils.isEmpty(phoneNumber)) && (TextUtils.isEmpty(password)))
        {
            Toast.makeText(RegisterActivity.this, "Please enter your phone number and password", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(name)){
            Toast.makeText(RegisterActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(RegisterActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(RegisterActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
        }
        else{
            progressDialog.setTitle("CreateAccount");
            progressDialog.setMessage("Please wait while we check the credentials");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            addEntryToDB(name, phoneNumber, password); //validate phone number, if its new, create the account, if not new, report number exists

        }
    }

    private void addEntryToDB(final String name, final String phoneNumber, final String password) {
        //final DatabaseReference rootRef;

        FirebaseApp.initializeApp(this);
        rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() { //listens for DB reads
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { //snapshot is a pointer to the db

                if(!snapshot.child("Users").child(phoneNumber).exists()){ //First read from the DB to check if the phone number exists
                    //create a hashmap of the data
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone",phoneNumber);
                    userDataMap.put("password", password);
                    userDataMap.put("name", name);

                    //write the data to the DB
                    rootRef.child("Users").child(phoneNumber).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Great job, you have created your account", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(loginIntent);
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this, "Network error. Please try again after a few minutes", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(RegisterActivity.this, "The phone number " + phoneNumber + " already exists", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this,"Try again using different phone number", Toast.LENGTH_SHORT).show();

                    //Intent welcomeIntent = new Intent(RegisterActivity.this, WelcomeActivity.class);
                    //startActivity(welcomeIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
