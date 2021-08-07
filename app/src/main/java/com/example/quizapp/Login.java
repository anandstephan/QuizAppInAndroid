package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.quizapp.helperClasses.AddUser;
import com.example.quizapp.helperClasses.SharedData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private static final String TAG ="abc" ;
    EditText pno,password;
    Button btnlgn;
    ProgressBar pb;
    SignInButton btnsignin;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pb = findViewById(R.id.pb);
        mAuth = FirebaseAuth.getInstance();
        pno = findViewById(R.id.pno);
        password = findViewById(R.id.pass);
        btnlgn = findViewById(R.id.login);
        btnsignin = findViewById(R.id.btnsignin);

        requestGoogleSignIn();
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        if(!SharedData.getShareData(getApplicationContext()).equals("false")){
            Intent in;
            if(SharedData.getShareData(getApplicationContext()).equals("-MZHMxUSKl5fVMWCqZpX")){
                in = new Intent(getApplicationContext(),AdminPanel.class);
            }else{
                in = new Intent(getApplicationContext(),Dashboard.class);
            }
            startActivity(in);
        }
        btnlgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MobilePattern = "[0-9]{10}";
                if (pno.getText().toString().isEmpty()) {
                    pno.setError("Phone Number Can't be blank");
                } else if (!pno.getText().toString().matches(MobilePattern)) {
                    pno.setError("Please insert a valid Phone Number");
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("Password Can't be blank");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users");
                    myRef.orderByChild("nameAndPassword").equalTo(pno.getText().toString() + password.getText().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String id = "";
//                            Toast.makeText(Login.this, "clicked happen" + snapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                id = snapshot1.getValue(AddUser.class).getId();

                            }
                            if (snapshot.getChildrenCount() == 1) {
                                Toast.makeText(Login.this, "Login Suceess", Toast.LENGTH_SHORT).show();
                                SharedData.savedSharedData(getApplicationContext(), id);
//                                Toast.makeText(Login.this, "------->>" + id.equals("-MZHMxUSKl5fVMWCqZpX"), Toast.LENGTH_SHORT).show();
                                if (id.equals("-MZHMxUSKl5fVMWCqZpX")) {
                                    Intent in = new Intent(getApplicationContext(), AdminPanel.class);
                                    startActivity(in);
                                } else {
                                    Intent in = new Intent(getApplicationContext(), Dashboard.class);
                                    startActivity(in);
                                }
                            } else {
                                Toast.makeText(Login.this, "Login Fail", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });

    }

    private void requestGoogleSignIn(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }



    private void signIn() {
        pb.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
                SharedData.savedSharedData(getApplicationContext(),account.getId());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent in = new Intent(getApplicationContext(),Dashboard.class);
                            startActivity(in);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}



//keytool -exportcert -alias androiddebugkey -keystore "C:\Users\Lenovo\.android\debug.keystore" | "C:\openssl\bin\openssl" sha1 -binary | "C:\openssl\bin\openssl" base64

