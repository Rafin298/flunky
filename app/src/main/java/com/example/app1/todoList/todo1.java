package com.example.app1.todoList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Window;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.app1.Dashboard;
import com.example.app1.R;
import com.example.app1.login;
import com.example.app1.MainPage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import model.TodoListModel;
import ui.TodoListRecyclerAdapter;
import util.JournalApi;

public class todo1 extends AppCompatActivity {
    private ImageButton addListButton,homeButton ;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
   // Button homeButton,signOutButton;

    Window window;
    private List<TodoListModel> myDoes;
    private RecyclerView recyclerView;
    private TodoListRecyclerAdapter todoListRecyclerAdapter;

    private CollectionReference collectionReference = db.collection("ToDoList");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo1);
        window=this.getWindow();
        window.setStatusBarColor(getColor(R.color.darkaction) );
       // window.setNavigationBarColor(getColor(R.color.darknav) );
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        homeButton = findViewById(R.id.backss);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(todo1.this, Dashboard.class));
            }
        });



       // noJournalEntry = findViewById(R.id.list_no_thoughts);
        addListButton= findViewById(R.id.addListButton);
       // journalList = new ArrayList<>();
        myDoes = new ArrayList<>();

        recyclerView = findViewById(R.id.todoRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        addListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(todo1.this,
                        todo2.class));
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

        collectionReference.whereEqualTo("userId", JournalApi.getInstance()
                .getUserId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot todoLists : queryDocumentSnapshots) {
                              //  Journal journal = journals.toObject(Journal.class);
                                TodoListModel todos = todoLists.toObject(TodoListModel.class);
                                todos.setKeydoes(todoLists.getId());
                                myDoes.add(todos);
                            }

                            //Invoke recyclerview
                            todoListRecyclerAdapter = new TodoListRecyclerAdapter(todo1.this,
                                    myDoes);
                            recyclerView.setAdapter(todoListRecyclerAdapter);
                            todoListRecyclerAdapter.notifyDataSetChanged();

                        }else {
                          //  noJournalEntry.setVisibility(View.VISIBLE);

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}