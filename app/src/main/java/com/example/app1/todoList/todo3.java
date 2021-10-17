package com.example.app1.todoList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.app1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import model.TodoListModel;
import util.JournalApi;

public class todo3 extends AppCompatActivity {
    CircularProgressButton updateButton,deleteButton;
    private EditText todoTitle;
    private EditText todoDes;
    private EditText todoDate;
    ImageButton backss;
    private String currentUserId;
    private String currentUserName;
    Window window;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    String key;
    //Connection to Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("ToDoList");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo3);
        window=this.getWindow();
        window.setStatusBarColor(getColor(R.color.darkaction) );
      //  window.setNavigationBarColor(getColor(R.color.darknav) );
        firebaseAuth = FirebaseAuth.getInstance();

        todoTitle = findViewById(R.id.todoTitle);
        todoDes = findViewById(R.id.todoDes);
        todoDate = findViewById(R.id.todoDate);
        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);
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
        todoTitle.setText(getIntent().getStringExtra("titledoes"));
        todoDes.setText(getIntent().getStringExtra("descdoes"));
        todoDate.setText(getIntent().getStringExtra("datedoes"));
        key =getIntent().getStringExtra("keydoes");

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButton.startAnimation();
                updateTodo();
//                startActivity(new Intent(todo3.this,
//                        todo1.class));
            }
        });
        backss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(todo3.this, todo1.class));
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteButton.startAnimation();
            deleteTodo();
            }
        });
    }
    private void updateTodo() {
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
            collectionReference.document(key)
                    .set(todoList)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Updated...",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(todo3.this,
                                    todo1.class));
                            finish();
                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


        } else {

            // progressBar.setVisibility(View.INVISIBLE);

        }
    }
    private void deleteTodo() {

        //invoke our collectionReference
        collectionReference.document(key)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"deleated...",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(todo3.this,
                                todo1.class));
                        finish();
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
//            collectionReference.document(key)
//                    .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    Toast.makeText(getApplicationContext(),"deleated...",Toast.LENGTH_SHORT).show();
//                }
//            })

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