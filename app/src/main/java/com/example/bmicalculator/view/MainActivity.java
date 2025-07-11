package com.example.bmicalculator.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.bmicalculator.R;
import com.example.bmicalculator.databinding.ActivityMainBinding;
import com.example.bmicalculator.model.BMIModel; // Ensure this import is present
import com.example.bmicalculator.viewmodel.BMIViewModel;
import com.example.bmicalculator.utils.BMICalculator;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.widget.NestedScrollView; // Import NestedScrollView


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private BMIViewModel viewModel;

    private NestedScrollView nestedScrollView; // Declare NestedScrollView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(BMIViewModel.class);

        setupUI();
        observeViewModel();
    }

    private void setupUI() {
        setSupportActionBar(binding.toolbar);

        // Initialize NestedScrollView
        nestedScrollView = findViewById(R.id.nestedScrollView); // Get reference using the new ID

        // Set up click listeners
        binding.buttonCalculate.setOnClickListener(v -> calculateBMI());
        binding.buttonClear.setOnClickListener(v -> clearInputs());
        binding.buttonShareResult.setOnClickListener(v -> shareBMIResult(viewModel.getBMIResult().getValue()));

        // Set up text change listeners for real-time validation
        binding.editTextWeight.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) validateWeight();
        });

        binding.editTextHeight.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) validateHeight();
        });
    }

    private void observeViewModel() {
        viewModel.getBMIResult().observe(this, bmiModel -> {
            if (bmiModel != null) {
                displayBMIResult(bmiModel);
            }
        });

        viewModel.getErrorMessage().observe(this, errorMsg -> {
            if (errorMsg != null) {
                showErrorMessage(errorMsg);
            } else {
                binding.textInputLayoutWeight.setError(null);
                binding.textInputLayoutHeight.setError(null);
            }
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.buttonCalculate.setEnabled(!isLoading);
        });
    }

    private void calculateBMI() {
        String weight = binding.editTextWeight.getText().toString().trim();
        String height = binding.editTextHeight.getText().toString().trim();

        viewModel.calculateBMI(weight, height);
    }

    private void displayBMIResult(com.example.bmicalculator.model.BMIModel bmiModel) {
        binding.cardViewResult.setVisibility(View.VISIBLE);
        binding.textViewBMIValue.setText(bmiModel.getFormattedBMI());
        binding.textViewBMICategory.setText(bmiModel.getCategory());
        binding.textViewAdvice.setText(BMICalculator.getBMIAdvice(bmiModel.getBmi()));

        // Set color based on BMI category
        int color = BMICalculator.getBMIColor(bmiModel.getBmi());
        binding.textViewBMIValue.setTextColor(color);
        binding.textViewBMICategory.setTextColor(color);

        // Automatically scroll to the result card
        // Use a post() to ensure layout has been measured after visibility change
        binding.cardViewResult.post(() -> {
            if (nestedScrollView != null) {
                nestedScrollView.smoothScrollTo(0, binding.cardViewResult.getTop());
            }
        });

        // Show success message
        Snackbar.make(binding.getRoot(), "BMI calculated successfully!", Snackbar.LENGTH_SHORT)
                .setAction("Share", v -> shareBMIResult(bmiModel))
                .show();
    }

    private void showErrorMessage(String message) {
        binding.cardViewResult.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void clearInputs() {
        binding.editTextWeight.setText("");
        binding.editTextHeight.setText("");
        binding.cardViewResult.setVisibility(View.GONE);
        binding.textInputLayoutWeight.setError(null);
        binding.textInputLayoutHeight.setError(null);
        viewModel.clearResults();
        // Scroll to top when clearing inputs
        if (nestedScrollView != null) {
            nestedScrollView.smoothScrollTo(0, 0);
        }
    }

    private void validateWeight() {
        String weightStr = binding.editTextWeight.getText().toString().trim();
        if (!weightStr.isEmpty()) {
            try {
                double weight = Double.parseDouble(weightStr);
                if (weight <= 0 || weight > 1000) {
                    binding.textInputLayoutWeight.setError("Weight must be between 1-1000 kg");
                } else {
                    binding.textInputLayoutWeight.setError(null);
                }
            } catch (NumberFormatException e) {
                binding.textInputLayoutWeight.setError("Please enter a valid number");
            }
        }
    }

    private void validateHeight() {
        String heightStr = binding.editTextHeight.getText().toString().trim();
        if (!heightStr.isEmpty()) {
            try {
                double height = Double.parseDouble(heightStr);
                if (height <= 0 || height > 3.0) {
                    binding.textInputLayoutHeight.setError("Height must be between 0.1-3.0 meters");
                } else {
                    binding.textInputLayoutHeight.setError(null);
                }
            } catch (NumberFormatException e) {
                binding.textInputLayoutHeight.setError("Please enter a valid number");
            }
        }
    }

    private void shareBMIResult(com.example.bmicalculator.model.BMIModel bmiModel) {
        String shareText = String.format("My BMI is %.1f (%s). Calculated using BMI Calculator app!",
                bmiModel.getBmi(), bmiModel.getCategory());

        android.content.Intent shareIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
        startActivity(android.content.Intent.createChooser(shareIntent, "Share BMI Result"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}