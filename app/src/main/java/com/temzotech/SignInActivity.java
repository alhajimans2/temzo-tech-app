package com.temzotech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.temzotech.R;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        TextInputEditText email = findViewById(R.id.editTextEmail);
        TextInputEditText password = findViewById(R.id.editTextPassword);
        Button btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(v -> {
            Log.d(TAG, "Sign-in button clicked");
            String emailStr = email.getText().toString().trim();
            String passwordStr = password.getText().toString().trim();
            if (emailStr.isEmpty() || passwordStr.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Sign in failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        });
    }
}
