package com.temzotech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class ServicesActivity extends AppCompatActivity implements ServicesAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private ServicesAdapter adapter;
    private List<Service> allServices;
    private String currentSearchText = "";
    private String currentCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        allServices = getAllServices();

        RecyclerView recyclerView = findViewById(R.id.servicesRecyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new ServicesAdapter(new ArrayList<>(allServices), this, ServicesAdapter.ViewType.DETAILED);
        recyclerView.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearchText = newText;
                filter();
                return true;
            }
        });

        Spinner filterSpinner = findViewById(R.id.filterSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.service_categories, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(spinnerAdapter);
        filterSpinner.setOnItemSelectedListener(this);
    }

    private void filter() {
        List<Service> filteredList = new ArrayList<>();
        for (Service service : allServices) {
            boolean categoryMatches = "All".equals(currentCategory) || service.getCategory().equalsIgnoreCase(currentCategory);
            boolean searchMatches = service.getName().toLowerCase().contains(currentSearchText.toLowerCase());
            if (categoryMatches && searchMatches) {
                filteredList.add(service);
            }
        }
        adapter.filterList(filteredList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemClick(Service service) {
        Intent intent = new Intent(this, ServiceDetailActivity.class);
        intent.putExtra("service", service);
        startActivity(intent);
    }

    private List<Service> getAllServices() {
        List<Service> serviceList = new ArrayList<>();
        serviceList.add(new Service("Computer Desktops Leasing", R.drawable.ic_computer, "Affordable and flexible leasing options for high-quality desktop computers. Perfect for businesses and individuals.", "Hardware"));
        serviceList.add(new Service("Laptop Sales", R.drawable.ic_laptop, "A wide selection of new and refurbished laptops from top brands. Find the perfect laptop to fit your needs and budget.", "Hardware"));
        serviceList.add(new Service("Web Design/Development", R.drawable.ic_globe, "Professional web design and development services to create a stunning online presence for your business.", "Development"));
        serviceList.add(new Service("Networking", R.drawable.ic_network, "Expert network setup, configuration, and troubleshooting services for homes and offices.", "Hardware"));
        serviceList.add(new Service("Software Installations & Fixes", R.drawable.ic_software, "Hassle-free software installation and troubleshooting services. We'll get your software up and running in no time.", "Software"));
        serviceList.add(new Service("Android App Development", R.drawable.ic_android, "Custom Android app development services to bring your ideas to life. From concept to launch, we've got you covered.", "Development"));
        serviceList.add(new Service("Desktop Sales", R.drawable.ic_desktop, "A wide variety of desktop computers for sale, from basic home PCs to high-performance gaming rigs.", "Hardware"));
        serviceList.add(new Service("Printers & Accessories", R.drawable.ic_printer, "All your printing needs in one place. We offer a wide selection of printers, ink, toner, and other accessories.", "Hardware"));
        return serviceList;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currentCategory = parent.getItemAtPosition(position).toString();
        filter();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }
}
