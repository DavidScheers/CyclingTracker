package main.PerformanceManagement;

import main.Functions.Function;

import java.time.Duration;
import java.time.LocalDate;

public interface PerformanceManagement {

    void addTrainingsDay(LocalDate date, WorkoutIntensity intensity);
    void addNewFtpMeasurement(LocalDate date, double ftp);

    Function getFtpFunction();
    Function getFitnessFunction();
    Function getFatigueFunction();

    Function getFormFunction();
}

/*
uw PMT zijn interface uitbreiden zodat ge niet direct TSS aangeeft,
maar dat ge daar kunt zeggen: "ik heb een FTP test gedaan en dit was het resultat" en ook
"ik heb X minuten getrained aan Y Watt (of whatever you need foor TSS te berekenen"
 */
