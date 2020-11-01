package com.timekeeperapp.timekeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.timekeeperapp.timekeeper.MESSAGE";
    public static final String TOTAL_TIME = "com.timekeeperapp.timekeeper.TOTALTIME";
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        int totaltime = Integer.parseInt(editText.getText().toString());
        Map<String, Integer> dbtime = new HashMap<>();
        dbtime.put("Time", totaltime);
        db.collection("Time")
                .add(dbtime)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("[INFO]", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("[INFO]", "Error adding document", e);
                    }
                });

//        String message = editText.getText().toString();
//        int totaltime = Integer.parseInt(editText.getText().toString());
//        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(TOTAL_TIME,totaltime);
        startActivity(intent);
    }
}