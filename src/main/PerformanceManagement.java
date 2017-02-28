package main;

import main.Functions.Function;

import java.time.LocalDate;

public interface PerformanceManagement {

    void addTrainingsDay(LocalDate date, double tss);

    Function getFitnessFunction();
    Function getFatigueFunction();

    Function getFormFunction();

}
