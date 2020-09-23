package com.example.navigationdrawermateriallibrary;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.navigationdrawermateriallibrary.Model.Users;
import com.example.navigationdrawermateriallibrary.Prevalent.PrevalentItem;
import com.example.navigationdrawermateriallibrary.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    Button LoginButton;
    EditText PhoneNumber, Password;
    ProgressDialog progressDialog;
    private TextView CreateAccount, IamAdmin, IamNotAdmin;
    private CheckBox RememberMeCheckbox;
    private String parentDbName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = findViewById(R.id.login_button);
        PhoneNumber = findViewById(R.id.login_phone_number);
        Password = findViewById(R.id.login_password);
        CreateAccount = findViewById(R.id.login_pg_create_account);
        IamAdmin = findViewById(R.id.i_am_admin);
        IamNotAdmin = findViewById(R.id.i_am_not_admin);
        parentDbName = "Users";
        RememberMeCheckbox = findViewById(R.id.check_box_remember_me);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Checking credentials...");
        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = PhoneNumber.getText().toString();
                String password = Password.getText().toString();

                if((phoneNumber.isEmpty()) && password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter phone number / password", Toast.LENGTH_SHORT).show();
                }
                else if(phoneNumber.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                }
                else {
                    AllowAccessToAccount(phoneNumber, password);
                    progressDialog.show();
                }
            }
        });

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        IamAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login as Farmer");
                CreateAccount.setVisibility(View.INVISIBLE);
                IamAdmin.setVisibility(View.INVISIBLE);
                IamNotAdmin.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });

        IamNotAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login");
                CreateAccount.setVisibility(View.VISIBLE);
                IamAdmin.setVisibility(View.VISIBLE);
                IamNotAdmin.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });
    }

    private void AllowAccessToAccount(final String phoneNumber, final String password) {

        if(RememberMeCheckbox.isChecked()){
            //store the incoming values in phone memory
            Paper.book().write(PrevalentItem.UserPhoneKey, phoneNumber);
            Paper.book().write(PrevalentItem.UserPasswordKey, password);
        }

        //first check to see if person who entered their credentials is a valid user (in the DB)
        //if the person is not in the DB, then say something like "Would you like to create account")
        //if they are in the DB, then secondly, check to see if they have entered the right matching password, if so log them in, if not, report mismatch

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(parentDbName).child(phoneNumber).exists()){
                    //check to see if the password matches
                    Users user = snapshot.child(parentDbName).child(phoneNumber).getValue(Users.class); //get the element corresponding to that phone number
                    String dbPassword = user.getPassword();
                    String dbName = user.getName();
                    if (dbPassword.equals(password)){//Log the person in
                        if(parentDbName.equals("Users")) {
                            Toast.makeText(LoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            PrevalentItem.currentOnlineUser = user;
                            startActivity(intent);
                        }
                        else if(parentDbName.equals("Admins")){
                            Toast.makeText(LoginActivity.this, "Successful Farmer Login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, FarmerCategoryActivity.class);
                            intent.putExtra("Name", dbName);
                            startActivity(intent);
                        }
                        progressDialog.dismiss();
                    }
                    else{//if password doesnt match, but the phone number existed
                        Toast.makeText(LoginActivity.this, "Phone number and password don't match. Please try again", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                }
                else{ //phonenumber does not exist in database
                    Toast.makeText(LoginActivity.this, "Phone number not registered, please double check it or create new account", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
