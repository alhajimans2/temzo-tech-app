# TTIT App Documentation

## 1. Overview

The TTIT app is a mobile application for Temzotech IT Solutions, providing users with a convenient way to browse and request IT services. The app offers a seamless user experience, from onboarding to service requests, and is designed to be a one-stop shop for all of Temzotech's clients' IT needs.

## 2. Features

### 2.1. Onboarding

*   **Informative Slides:** The app presents a series of onboarding slides that introduce users to the app's key features and benefits.
*   **Get Started:** A "Get Started" button on the final slide directs users to the authentication screen.

### 2.2. Authentication

*   **Email & Password:** Users can create an account and log in using their email and password.
*   **Firebase Integration:** The app uses Firebase Authentication for a secure and reliable authentication process.
*   **Login/Sign Up Toggle:** Users can easily switch between the login and sign-up screens.

### 2.3. Main Screen

*   **Welcome Message:** The app displays a personalized welcome message to greet the user.
*   **Promotional Slider:** A prominent image slider showcases the latest promotions and offers.
*   **Service Grid:** A grid layout displays the various services offered by Temzotech.
*   **Contact Options:** Quick access to email and call options for immediate contact.
*   **Bottom Navigation:** A persistent bottom navigation bar allows users to easily navigate between the home, services, and contact sections.

### 2.4. Services

*   **Service Details:** Tapping on a service in the grid opens a detailed view with more information.
*   **Service Categories:** Services are categorized for easy browsing, including:
    *   Computer Desktops Leasing
    *   Laptop Sales
    *   Web Design/Development
    *   Networking
    *   Software Installations & Fixes
    *   Android App Development
    *   Desktop Sales
    *   Printers & Accessories

### 2.5. Contact Us

*   **Multiple Contact Methods:** Users can contact Temzotech via email or phone.
*   **Service Request Form:** A service request form allows users to select the services they are interested in and send a request via email.
*   **Service Selection:** A multi-select dialog allows users to easily choose from a list of available services.

## 3. Technical Details

*   **Platform:** Android
*   **Language:** Java
*   **Authentication:** Firebase Authentication
*   **UI Components:**
    *   ViewPager2 for onboarding and authentication
    *   RecyclerView for service grid
    *   ImageSlider for promotional content
    *   BottomNavigationView for navigation
    *   Material Components for a modern UI
