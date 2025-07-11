package com.example.bmicalculator.model;

public class BMIModel {
    private double weight;
    private double height;
    private double bmi;
    private String category;

    public BMIModel() {
        this.weight = 0.0;
        this.height = 0.0;
        this.bmi = 0.0;
        this.category = "";
    }

    public BMIModel(double weight, double height) {
        this.weight = weight;
        this.height = height;
        calculateBMI();
    }

    private void calculateBMI() {
        if (height > 0) {
            this.bmi = weight / (height * height);
            this.category = determineBMICategory(this.bmi);
        }
    }

    private String determineBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.5 && bmi < 25) {
            return "Normal weight";
        } else if (bmi >= 25 && bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    // Getters and Setters
    public double getWeight() { return weight; }
    public void setWeight(double weight) {
        this.weight = weight;
        calculateBMI();
    }

    public double getHeight() { return height; }
    public void setHeight(double height) {
        this.height = height;
        calculateBMI();
    }

    public double getBmi() { return bmi; }
    public String getCategory() { return category; }

    public String getFormattedBMI() {
        return String.format("%.1f", bmi);
    }

    public boolean isValid() {
        return weight > 0 && height > 0;
    }
}