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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ServicesAdapter.OnItemClickListener, PromotionsAdapter.OnItemClickListener, NavigationBarView.OnItemSelectedListener {

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

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);

        // Welcome Message
        TextView welcomeText = findViewById(R.id.welcome_text);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String displayName = user.getDisplayName();
            if (displayName != null && !displayName.isEmpty()) {
                String[] nameParts = displayName.split(" ");
                String firstName = nameParts[0];
                welcomeText.setText("Hello, " + firstName + "!");
                collapsingToolbarLayout.setTitle("Hi, " + firstName + "!");
            } else {
                welcomeText.setText("Hello!");
                collapsingToolbarLayout.setTitle("Hi, User!");
            }
        } else {
            collapsingToolbarLayout.setTitle("Welcome");
        }


        // Recommended Services
        RecyclerView recommendedRecyclerView = findViewById(R.id.recommendedRecyclerView);
        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Service> recommendedServiceList = getRecommendedServices();
        ServicesAdapter recommendedServicesAdapter = new ServicesAdapter(recommendedServiceList, this, ServicesAdapter.ViewType.SIMPLE);
        recommendedRecyclerView.setAdapter(recommendedServicesAdapter);

        // Promotions
        RecyclerView promotionsRecyclerView = findViewById(R.id.promotionsRecyclerView);
        promotionsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Promotion> promotionList = getPromotions();
        PromotionsAdapter promotionsAdapter = new PromotionsAdapter(promotionList, this);
        promotionsRecyclerView.setAdapter(promotionsAdapter);

        // Popular Services
        RecyclerView popularServicesRecyclerView = findViewById(R.id.popularServicesRecyclerView);
        popularServicesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        List<Service> popularServiceList = getPopularServices();
        ServicesAdapter popularServicesAdapter = new ServicesAdapter(popularServiceList, this, ServicesAdapter.ViewType.SIMPLE);
        popularServicesRecyclerView.setAdapter(popularServicesAdapter);

        // See All Services
        Button seeAllServicesButton = findViewById(R.id.seeAllServicesButton);
        seeAllServicesButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ServicesActivity.class));
        });

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

    private List<Service> getPopularServices() {
        List<Service> serviceList = new ArrayList<>();
        serviceList.add(new Service("Computer Desktops Leasing", R.drawable.ic_computer, "Affordable and flexible leasing options for high-quality desktop computers. Perfect for businesses and individuals.", "Hardware"));
        serviceList.add(new Service("Laptop Sales", R.drawable.ic_laptop, "A wide selection of new and refurbished laptops from top brands. Find the perfect laptop to fit your needs and budget.", "Hardware"));
        serviceList.add(new Service("Web Design/Development", R.drawable.ic_globe, "Professional web design and development services to create a stunning online presence for your business.", "Development"));
        serviceList.add(new Service("Networking", R.drawable.ic_network, "Expert network setup, configuration, and troubleshooting services for homes and offices.", "Hardware"));
        return serviceList;
    }

    private List<Service> getRecommendedServices() {
        List<Service> serviceList = new ArrayList<>();
        serviceList.add(new Service("Software Installations & Fixes", R.drawable.ic_software, "Hassle-free software installation and troubleshooting services. We'll get your software up and running in no time.", "Software"));
        serviceList.add(new Service("Android App Development", R.drawable.ic_android, "Custom Android app development services to bring your ideas to life. From concept to launch, we've got you covered.", "Development"));
        return serviceList;
    }

    private List<Promotion> getPromotions() {
        List<Promotion> promotionList = new ArrayList<>();
        promotionList.add(new Promotion("Laptop Sale!", "Get up to 20% off on selected laptops.", R.drawable.logo));
        promotionList.add(new Promotion("Free Consultation", "Free consultation for new clients this month.", R.drawable.logo));
        return promotionList;
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
    public void onPromotionItemClick(Promotion promotion) {
        // Handle promotion click
        Toast.makeText(this, "Claiming offer for " + promotion.getTitle(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            // Already on the home screen
            return true;
        } else if (itemId == R.id.nav_services) {
            startActivity(new Intent(MainActivity.this, ServicesActivity.class));
            return true;
        } else if (itemId == R.id.nav_contact) {
            startActivity(new Intent(this, ContactUsActivity.class));
            return true;
        }
        return false;
    }
}
