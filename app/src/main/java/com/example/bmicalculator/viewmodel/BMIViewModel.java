package com.example.bmicalculator.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.bmicalculator.model.BMIModel;
import com.example.bmicalculator.utils.BMICalculator;

public class BMIViewModel extends ViewModel {
    private MutableLiveData<BMIModel> bmiResult = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<BMIModel> getBMIResult() {
        return bmiResult;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void calculateBMI(String weightStr, String heightStr) {
        isLoading.setValue(true);

        try {
            // Input validation
            if (weightStr.trim().isEmpty() || heightStr.trim().isEmpty()) {
                errorMessage.setValue("Please enter both weight and height");
                isLoading.setValue(false);
                return;
            }

            double weight = Double.parseDouble(weightStr);
            double height = Double.parseDouble(heightStr);

            // Validate ranges
            if (weight <= 0 || weight > 1000) {
                errorMessage.setValue("Please enter a valid weight (1-1000 kg)");
                isLoading.setValue(false);
                return;
            }

            if (height <= 0 || height > 3.0) {
                errorMessage.setValue("Please enter a valid height (0.1-3.0 meters)");
                isLoading.setValue(false);
                return;
            }

            // Calculate BMI
            BMIModel result = BMICalculator.calculateBMI(weight, height);
            bmiResult.setValue(result);
            errorMessage.setValue(null);

        } catch (NumberFormatException e) {
            errorMessage.setValue("Please enter valid numbers");
        } catch (Exception e) {
            errorMessage.setValue("An error occurred during calculation");
        } finally {
            isLoading.setValue(false);
        }
    }

    public void clearResults() {
        bmiResult.setValue(null);
        errorMessage.setValue(null);
    }
}