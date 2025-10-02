package com.temzotech;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ContactUsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private TextView textViewSelectServices;
    private Button buttonRequest;
    private String[] servicesArray;
    private boolean[] selectedServices;
    private final ArrayList<Integer> servicesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        CardView cardEmail = findViewById(R.id.cardEmail);
        CardView cardCall = findViewById(R.id.cardCall);
        textViewSelectServices = findViewById(R.id.textViewSelectServices);
        buttonRequest = findViewById(R.id.buttonRequest);

        servicesArray = getResources().getStringArray(R.array.services_array);
        selectedServices = new boolean[servicesArray.length];

        cardEmail.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:admin@temzotechsl.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Service Inquiry");
            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(emailIntent);
            } else {
                Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
            }
        });

        cardCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:+23280696909"));
            if (callIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(callIntent);
            } else {
                Toast.makeText(this, "No phone app found", Toast.LENGTH_SHORT).show();
            }
        });

        textViewSelectServices.setOnClickListener(v -> showServicesDialog());

        buttonRequest.setOnClickListener(v -> {
            String selectedServicesString = textViewSelectServices.getText().toString();
            if (selectedServicesString.isEmpty() || selectedServicesString.equals("Select Services")) {
                Toast.makeText(ContactUsActivity.this, "Please select at least one service", Toast.LENGTH_SHORT).show();
            } else {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:admin@temzotechsl.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Service Request");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "I would like to request the following services:\n\n" + selectedServicesString);
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                } else {
                    Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.nav_contact);
    }

    private void showServicesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Services");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(servicesArray, selectedServices, (dialog, which, isChecked) -> {
            if (isChecked) {
                servicesList.add(which);
                Collections.sort(servicesList);
            } else {
                servicesList.remove(Integer.valueOf(which));
            }
        });

        builder.setPositiveButton("OK", (dialog, which) -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < servicesList.size(); i++) {
                stringBuilder.append(servicesArray[servicesList.get(i)]);
                if (i != servicesList.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
            textViewSelectServices.setText(stringBuilder.toString());
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.setNeutralButton("Clear All", (dialog, which) -> {
            Arrays.fill(selectedServices, false);
            servicesList.clear();
            textViewSelectServices.setText("Select Services");
        });

        builder.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        } else if (itemId == R.id.nav_services) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("navigateTo", "services");
            startActivity(intent);
            return true;
        } else if (itemId == R.id.nav_contact) {
            // Already on the contact screen
            return true;
        }
        return false;
    }
}
