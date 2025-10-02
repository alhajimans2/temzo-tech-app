package com.temzotech;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginFragment extends Fragment {

    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private Button signInButton;
    private TextView dontHaveAccountText;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = view.findViewById(R.id.email_edit_text);
        passwordEditText = view.findViewById(R.id.password_edit_text);
        signInButton = view.findViewById(R.id.sign_in_button);
        dontHaveAccountText = view.findViewById(R.id.dont_have_account_text);
        progressBar = view.findViewById(R.id.login_progress_bar);

        signInButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                emailEditText.setError("Email is required.");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordEditText.setError("Password is required.");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (isAdded()) { // Check if the fragment is currently added to its activity.
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                if (getActivity() != null) {
                                    getActivity().finish();
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                Exception exception = task.getException();
                                Log.e("LoginFragment", "Authentication failed", exception);
                                String errorMessage;
                                if (exception instanceof FirebaseAuthException) {
                                    String errorCode = ((FirebaseAuthException) exception).getErrorCode();
                                    switch (errorCode) {
                                        case "ERROR_INVALID_EMAIL":
                                            errorMessage = "Invalid email format.";
                                            break;
                                        case "ERROR_WRONG_PASSWORD":
                                            errorMessage = "Incorrect password.";
                                            break;
                                        case "ERROR_USER_NOT_FOUND":
                                            errorMessage = "No account found with this email.";
                                            break;
                                        case "ERROR_USER_DISABLED":
                                            errorMessage = "This user account has been disabled.";
                                            break;
                                        default:
                                            errorMessage = "Authentication failed: " + errorCode;
                                    }
                                } else {
                                    errorMessage = "Authentication failed: " + exception.getMessage();
                                }
                                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        });

        dontHaveAccountText.setOnClickListener(v -> {
            if (isAdded()) {
                ViewPager2 viewPager = getActivity().findViewById(R.id.view_pager_auth);
                viewPager.setCurrentItem(1);
            }
        });

        return view;
    }
}