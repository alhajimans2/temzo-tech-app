package com.temzotech;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.text.DateFormat;
import java.util.Date;

public class WelcomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return; // Skip the rest of onCreate
        }

        TextView textViewDate = findViewById(R.id.textViewDate);
        String currentDate = DateFormat.getDateInstance().format(new Date());
        textViewDate.setText(currentDate);

        Button btnSignIn = findViewById(R.id.btnSignInWelcome);
        Button btnSignUp = findViewById(R.id.btnSignUpWelcome);

        btnSignIn.setOnClickListener(v -> {
            startActivity(new Intent(this, SignInActivity.class));
        });

        btnSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }
}
