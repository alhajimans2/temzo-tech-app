package com.temzotech;

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

public class SignUpFragment extends Fragment {

    private TextInputEditText firstNameEditText;
    private TextInputEditText lastNameEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private Button createAccountButton;
    private TextView alreadyHaveAccountText;
    private ProgressBar signupProgressBar;

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        mAuth = FirebaseAuth.getInstance();

        firstNameEditText = view.findViewById(R.id.first_name_edit_text);
        lastNameEditText = view.findViewById(R.id.last_name_edit_text);
        emailEditText = view.findViewById(R.id.email_edit_text);
        passwordEditText = view.findViewById(R.id.password_edit_text);
        createAccountButton = view.findViewById(R.id.create_account_button);
        alreadyHaveAccountText = view.findViewById(R.id.already_have_account_text);
        signupProgressBar = view.findViewById(R.id.signup_progress_bar);

        createAccountButton.setOnClickListener(v -> {
            String firstName = firstNameEditText.getText().toString().trim();
            String lastName = lastNameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(firstName)) {
                firstNameEditText.setError("First name is required.");
                return;
            }

            if (TextUtils.isEmpty(lastName)) {
                lastNameEditText.setError("Last name is required.");
                return;
            }

            if (TextUtils.isEmpty(email)) {
                emailEditText.setError("Email is required.");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordEditText.setError("Password is required.");
                return;
            }

            signupProgressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (isAdded()) { // Check if the fragment is currently added to its activity.
                            signupProgressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Sign in success, navigate to login
                                ViewPager2 viewPager = getActivity().findViewById(R.id.view_pager_auth);
                                viewPager.setCurrentItem(0);
                            } else {
                                // If sign in fails, display a message to the user.
                                Exception exception = task.getException();
                                Log.e("SignUpFragment", "Authentication failed", exception);
                                String errorMessage;
                                if (exception instanceof FirebaseAuthException) {
                                    String errorCode = ((FirebaseAuthException) exception).getErrorCode();
                                    switch (errorCode) {
                                        case "ERROR_EMAIL_ALREADY_IN_USE":
                                            errorMessage = "This email address is already in use.";
                                            break;
                                        case "ERROR_INVALID_EMAIL":
                                            errorMessage = "Invalid email format.";
                                            break;
                                        case "ERROR_WEAK_PASSWORD":
                                            errorMessage = "Password is too weak. Please use at least 6 characters.";
                                            break;
                                        default:
                                            errorMessage = "Authentication failed.";
                                    }
                                } else {
                                    errorMessage = "Authentication failed.";
                                }
                                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        });

        alreadyHaveAccountText.setOnClickListener(v -> {
            if (isAdded()) {
                ViewPager2 viewPager = getActivity().findViewById(R.id.view_pager_auth);
                viewPager.setCurrentItem(0);
            }
        });

        return view;
    }
}