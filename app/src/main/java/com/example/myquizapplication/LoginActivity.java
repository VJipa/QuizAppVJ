package com.example.myquizapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;




    private boolean isAuthenticated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        googleBtn = findViewById(R.id.google_btn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        MaterialButton loginbtn = findViewById(R.id.loginbtn);
        MaterialButton guestbtn = findViewById(R.id.guestbtn);


        googleBtn = findViewById(R.id.google_btn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            navigateToSecondActivity();
        }


        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });



        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    isAuthenticated = true;
                    Toast.makeText(LoginActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    openStartingScreenActivity();
                } else {
                    Toast.makeText(LoginActivity.this, "LOGIN FAILED !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        guestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartingScreenActivity();
            }
        });

        //end OnCreate
    }

    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    private void openStartingScreenActivity() {
        Intent intent = new Intent(LoginActivity.this, StartingScreenActivity.class);
        intent.putExtra("isAuthenticated", isAuthenticated);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }
    void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(LoginActivity.this, StartingScreenActivity.class);
        startActivity(intent);
    }
}
