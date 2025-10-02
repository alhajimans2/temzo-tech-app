package com.temzotech;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private AutoCompleteTextView serviceSpinner;
    private TextInputEditText messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Contact Us");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        serviceSpinner = findViewById(R.id.service_spinner);
        messageEditText = findViewById(R.id.message_edit_text);

        // In a real app, you would fetch this list from a database or API
        List<String> services = new ArrayList<>();
        services.add("Computer Desktops Leasing");
        services.add("Laptop Sales");
        services.add("Web Design/Development");
        services.add("Networking");
        services.add("Software Installations & Fixes");
        services.add("Android App Development");
        services.add("Desktop Sales");
        services.add("Printers & Accessories");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, services);
        serviceSpinner.setAdapter(adapter);

        Button sendEmailButton = findViewById(R.id.send_email_button);
        sendEmailButton.setOnClickListener(v -> {
            String selectedService = serviceSpinner.getText().toString();
            String message = messageEditText.getText().toString();

            if (selectedService.isEmpty()) {
                Toast.makeText(this, "Please select a service", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:admin@temzotechsl.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Service Inquiry: " + selectedService);
            intent.putExtra(Intent.EXTRA_TEXT, message);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
            }
        });

        Button callNowButton = findViewById(R.id.call_now_button);
        callNowButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+23280696909"));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No phone app found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Go back to the previous activity
            return true;
        }
        if (item.getItemId() == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, AuthActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
