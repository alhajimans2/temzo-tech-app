package com.temzotech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private ViewPager2 viewPager;
    private MaterialButton getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.view_pager);
        onboardingAdapter = new OnboardingAdapter(new ArrayList<>());
        viewPager.setAdapter(onboardingAdapter);

        getStartedButton = findViewById(R.id.get_started_button);

        DotsIndicator dotsIndicator = findViewById(R.id.dots_indicator);
        dotsIndicator.setViewPager2(viewPager);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == onboardingAdapter.getItemCount() - 1) {
                    getStartedButton.setVisibility(View.VISIBLE);
                } else {
                    getStartedButton.setVisibility(View.GONE);
                }
            }
        });

        getStartedButton.setOnClickListener(v -> {
            // TODO: Replace AuthActivity.class with the actual authentication activity
            startActivity(new Intent(getApplicationContext(), AuthActivity.class));
            finish();
        });

        setupOnboardingItems();
    }

    private void setupOnboardingItems() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem item1 = new OnboardingItem();
        item1.setTitle("Your Trusted IT Partner");
        item1.setSubtitle("From sales to support, we provide comprehensive IT solutions for your business and personal needs.");
        item1.setImage(R.drawable.logo); // TODO: Replace with actual drawable

        OnboardingItem item2 = new OnboardingItem();
        item2.setTitle("A Wide Range of Services");
        item2.setSubtitle("Web development, hardware sales, networking, software fixes, and much more.");
        item2.setImage(R.drawable.logo); // TODO: Replace with actual drawable

        OnboardingItem item3 = new OnboardingItem();
        item3.setTitle("Request a Service in Minutes");
        item3.setSubtitle("Create an account and let's get your tech problems solved.");
        item3.setImage(R.drawable.logo); // TODO: Replace with actual drawable

        onboardingItems.add(item1);
        onboardingItems.add(item2);
        onboardingItems.add(item3);

        onboardingAdapter.setOnboardingItems(onboardingItems);
    }
}