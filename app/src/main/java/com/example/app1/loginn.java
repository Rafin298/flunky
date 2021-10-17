package com.example.app1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import util.JournalApi;

public class loginn extends AppCompatActivity {
    CountDownTimer countDownTimer;
    CircularProgressButton loginButton;
    ImageView createAcctButton,logo;
    LottieAnimationView logon;
    Window window;
    private EditText emailAddres;
    private EditText password;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    LottieAnimationView weatherIcon;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginn);

        window=this.getWindow();
        window.setStatusBarColor(getColor(R.color.darkaction) );
        window.setNavigationBarColor(getColor(R.color.darknav) );


        loginButton = findViewById(R.id.updateButton);
       // createAcctButton = findViewById(R.id.signUp);

        firebaseAuth = FirebaseAuth.getInstance();


        emailAddres = findViewById(R.id.email_accounts);
        password = findViewById(R.id.password_accounts);
        logon = findViewById(R.id.logon);
        logon.setAnimation(R.raw.robohelmen);

       // logos.setAnimation(R.raw.broken_clouds);

        weatherIcon = findViewById(R.id.logon);

        loginButton = findViewById(R.id.updateButton);
        createAcctButton = findViewById(R.id.logButtons);

        createAcctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginn.this, signUpp.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.startAnimation();
                loginEmailPasswordUser(emailAddres.getText().toString().trim(),
                        password.getText().toString().trim());

            }
        });
    }
    private void loginEmailPasswordUser(String email, String pwd) {

        if (!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(pwd)) {
            firebaseAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            assert user != null;
                            final String currentUserId = user.getUid();

                            collectionReference
                                    .whereEqualTo("userId", currentUserId)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                                            @Nullable FirebaseFirestoreException e) {

                                            if (e != null) {
                                            }
                                            assert queryDocumentSnapshots != null;
                                            if (!queryDocumentSnapshots.isEmpty()) {


                                                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                                    JournalApi journalApi = JournalApi.getInstance();
                                                    journalApi.setUsername(snapshot.getString("username"));
                                                    journalApi.setUserId(snapshot.getString("userId"));

                                                    //Go to ListActivity
                                                    startActivity(new Intent(loginn.this,
                                                            Dashboard.class));


                                                }



                                            }

                                        }
                                    });




                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                          //  progressBar.setVisibility(View.INVISIBLE);

                        }
                    });



        }else {


            Toast.makeText(loginn.this,
                    "Please enter email and password",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
}