package com.example.bmicalculator.utils;

import com.example.bmicalculator.model.BMIModel;

public class BMICalculator {

    public static BMIModel calculateBMI(double weight, double height) {
        return new BMIModel(weight, height);
    }

    public static String getBMIAdvice(double bmi) {
        if (bmi < 18.5) {
            return "Consider consulting a healthcare provider about healthy weight gain strategies.";
        } else if (bmi >= 18.5 && bmi < 25) {
            return "Great! Maintain your current healthy lifestyle.";
        } else if (bmi >= 25 && bmi < 30) {
            return "Consider incorporating regular exercise and a balanced diet.";
        } else {
            return "Please consult a healthcare provider for personalized advice.";
        }
    }

    public static int getBMIColor(double bmi) {
        if (bmi < 18.5) {
            return android.graphics.Color.parseColor("#3498db"); // Blue
        } else if (bmi >= 18.5 && bmi < 25) {
            return android.graphics.Color.parseColor("#2ecc71"); // Green
        } else if (bmi >= 25 && bmi < 30) {
            return android.graphics.Color.parseColor("#f39c12"); // Orange
        } else {
            return android.graphics.Color.parseColor("#e74c3c"); // Red
        }
    }
}
