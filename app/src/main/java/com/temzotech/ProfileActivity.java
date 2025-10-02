package com.temzotech;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private String[] profileItems = {"My Requests", "Settings", "About"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView userName = findViewById(R.id.userName);
        TextView userEmail = findViewById(R.id.userEmail);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userName.setText(user.getDisplayName());
            userEmail.setText(user.getEmail());
        }

        ListView profileListView = findViewById(R.id.profileListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, profileItems);
        profileListView.setAdapter(adapter);

        profileListView.setOnItemClickListener((parent, view, position, id) -> {
            // Handle other items here
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
