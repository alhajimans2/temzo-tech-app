package com.temzotech;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class ServiceDetailActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Service service = (Service) getIntent().getSerializableExtra("service");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(service.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView serviceIcon = findViewById(R.id.service_detail_icon);
        TextView serviceName = findViewById(R.id.service_detail_name);
        TextView serviceDescription = findViewById(R.id.service_detail_description);

        serviceIcon.setImageResource(service.getIcon());
        serviceName.setText(service.getName());
        serviceDescription.setText(service.getDescription());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
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
