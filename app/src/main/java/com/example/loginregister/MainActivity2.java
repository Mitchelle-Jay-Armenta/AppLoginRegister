package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    final String TAG = "FIRESTORE";
    FirebaseFirestore db;
    Button backButton;
    Button registerButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        db = FirebaseFirestore.getInstance();

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText username = findViewById(R.id.Username);
                EditText password = findViewById(R.id.Password);

                if(username != null && password != null){
                    String unameInput = username.getText().toString();
                    String passInput = password.getText().toString();

                    addUser(unameInput, passInput);
                    Toast.makeText(MainActivity2.this, "Successfully Registered"
                    , Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity2.this,"Please make sure there are no empty fields"
                            , Toast.LENGTH_SHORT).show();
                }

            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent back = new Intent(getApplication(), MainActivity.class);
                startActivity(back);
            }
        });

    }

public void addUser(String uname, String pass){
        //create a new user with its full name, uname and password
        Map<String, Object> user = new HashMap<>();
        user.put("Username", uname);
        user.put("Password", pass);

        //adding a new document
        db.collection("Users").document(uname)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot added: " + uname);
                        Toast.makeText(MainActivity2.this,"Successfully Added "
                        + uname, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity2.this, "Error adding user" + e,
                                Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Error adding document", e);
                    }
                });
}
}