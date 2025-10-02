package com.temzotech;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;

public class ServiceRequestActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);

        TextInputEditText serviceType = findViewById(R.id.editTextServiceType);
        TextInputEditText details = findViewById(R.id.editTextDetails);
        Button btnRequest = findViewById(R.id.btnRequestService);

        btnRequest.setOnClickListener(v -> {
            String serviceTypeStr = serviceType.getText().toString().trim();
            String detailsStr = details.getText().toString().trim();

            if (serviceTypeStr.isEmpty() || detailsStr.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:admin@temzotechsl.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Service Request: " + serviceTypeStr);
            emailIntent.putExtra(Intent.EXTRA_TEXT, detailsStr);

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                finish();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
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
            startActivity(new Intent(this, ContactUsActivity.class));
            return true;
        }
        return false;
    }
}
