package com.temzotech;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ServicesAdapter.OnItemClickListener, NavigationBarView.OnItemSelectedListener {

    private CoordinatorLayout coordinatorLayout;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        loadingProgressBar = findViewById(R.id.loading_progress_bar);

        coordinatorLayout.setVisibility(View.INVISIBLE);

        // Simulate loading process
        new Handler().postDelayed(() -> {
            loadingProgressBar.setVisibility(View.GONE);
            coordinatorLayout.setVisibility(View.VISIBLE);
        }, 2000); // 2-second delay

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Welcome Message
        TextView welcomeText = findViewById(R.id.welcome_text);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String displayName = user.getDisplayName();
            if (displayName != null && !displayName.isEmpty()) {
                String[] nameParts = displayName.split(" ");
                String firstName = nameParts[0];
                welcomeText.setText("Hello, " + firstName + "!");
            } else {
                welcomeText.setText("Hello!");
            }
        }

        // Contact Icon
        ImageView contactIcon = findViewById(R.id.profileIcon);
        contactIcon.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ContactUsActivity.class)));

        // Image Slider
        ImageSlider imageSlider = findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.logo, "Laptop Sale Live Now!", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.logo, "Free Consultation for New Clients", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        // Services Grid
        RecyclerView servicesRecyclerView = findViewById(R.id.servicesRecyclerView);
        servicesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        List<Service> serviceList = new ArrayList<>();
        serviceList.add(new Service("Computer Desktops Leasing", R.drawable.ic_computer, "Affordable and flexible leasing options for high-quality desktop computers. Perfect for businesses and individuals."));
        serviceList.add(new Service("Laptop Sales", R.drawable.ic_laptop, "A wide selection of new and refurbished laptops from top brands. Find the perfect laptop to fit your needs and budget."));
        serviceList.add(new Service("Web Design/Development", R.drawable.ic_globe, "Professional web design and development services to create a stunning online presence for your business."));
        serviceList.add(new Service("Networking", R.drawable.ic_network, "Expert network setup, configuration, and troubleshooting services for homes and offices."));
        serviceList.add(new Service("Software Installations & Fixes", R.drawable.ic_software, "Hassle-free software installation and troubleshooting services. We'll get your software up and running in no time."));
        serviceList.add(new Service("Android App Development", R.drawable.ic_android, "Custom Android app development services to bring your ideas to life. From concept to launch, we've got you covered."));
        serviceList.add(new Service("Desktop Sales", R.drawable.ic_desktop, "A wide variety of desktop computers for sale, from basic home PCs to high-performance gaming rigs."));
        serviceList.add(new Service("Printers & Accessories", R.drawable.ic_printer, "All your printing needs in one place. We offer a wide selection of printers, ink, toner, and other accessories."));

        ServicesAdapter serviceAdapter = new ServicesAdapter(serviceList, this);
        servicesRecyclerView.setAdapter(serviceAdapter);

        // Contact Buttons
        Button emailButton = findViewById(R.id.buttonSendEmail);
        emailButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:admin@temzotechsl.com"));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
            }
        });

        Button callButton = findViewById(R.id.buttonCallNow);
        callButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+23280696909"));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No phone app found", Toast.LENGTH_SHORT).show();
            }
        });

        // Bottom Navigation
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
        if (item.getItemId() == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, AuthActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Service service) {
        Intent intent = new Intent(this, ServiceDetailActivity.class);
        intent.putExtra("service", service);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            // Already on the home screen
            return true;
        } else if (itemId == R.id.nav_services) {
            // You can scroll to the services section if you want
            Toast.makeText(this, R.string.services_clicked, Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.nav_contact) {
            startActivity(new Intent(this, ContactUsActivity.class));
            return true;
        }
        return false;
    }
}
