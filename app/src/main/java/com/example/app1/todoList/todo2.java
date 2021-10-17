package com.example.app1.todoList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.app1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import model.TodoListModel;
import util.JournalApi;

public class todo2 extends AppCompatActivity {

    private EditText todoTitle;
    private EditText todoDes;
    private EditText todoDate;
    CircularProgressButton saveButton,cancelButton;
    ImageButton backss;
    private String currentUserId;
    private String currentUserName;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    Window window;
    //Connection to Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("ToDoList");

    Integer keyyy=new Random().nextInt();
    String key=Integer.toString(keyyy);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo2);
        window=this.getWindow();
        window.setStatusBarColor(getColor(R.color.darkaction) );
       // window.setNavigationBarColor(getColor(R.color.darknav) );

        firebaseAuth = FirebaseAuth.getInstance();

        todoTitle = findViewById(R.id.todoTitle);
        todoDes = findViewById(R.id.todoDes);
        todoDate = findViewById(R.id.todoDate);
        saveButton = findViewById(R.id.updateButton);
        cancelButton= findViewById(R.id.deleteButton);
        backss= findViewById(R.id.backss);

        if (JournalApi.getInstance() != null) {
            currentUserId = JournalApi.getInstance().getUserId();
            currentUserName = JournalApi.getInstance().getUsername();

           // currentUserTextView.setText(currentUserName);
        }

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {

                }

            }
        };
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveButton.startAnimation();
            saveTodo();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(todo2.this, todo1.class));
            }
        });
        backss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(todo2.this, todo1.class));
            }
        });
    }
    private void saveTodo() {
        final String todoTitled = todoTitle.getText().toString().trim();
        final String todoDesd = todoDes.getText().toString().trim();
        final String todoDated = todoDate.getText().toString().trim();

       // progressBar.setVisibility(View.VISIBLE);
        Log.d("sssdd", "LALALA" );
        if (!TextUtils.isEmpty(todoTitled) &&
                !TextUtils.isEmpty(todoDesd)
                && !TextUtils.isEmpty(todoDated)) {

                                    TodoListModel todoList =new TodoListModel();
                                    todoList.setTitledoes(todoTitled);
                                    todoList.setDescdoes(todoDesd);
                                    todoList.setDatedoes(todoDated);
                                   // todoList.setTimeAdded(new Timestamp(new Date()));
                                    todoList.setUserName(currentUserName);
                                    todoList.setUserId(currentUserId);
                                    todoList.setKeydoes(key);

                                    //invoke our collectionReference
                                    collectionReference.add(todoList)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {

                                                  //  progressBar.setVisibility(View.INVISIBLE);
                                                    startActivity(new Intent(todo2.this,
                                                            todo1.class));
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    //Log.d(TAG, "onFailure: " + e.getMessage());

                                                }
                                            });


                                } else {

           // progressBar.setVisibility(View.INVISIBLE);

        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        user = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuth != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}