package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.Functions.DataBasedFunction;
import main.Functions.Function;
import main.Functions.PerformanceFunction;
import main.PerformanceManagement.PerformanceManagementTool;
import main.PerformanceManagement.WorkoutIntensity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class MainApplication extends Application {

    @Override public void start(Stage stage) {
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        //creating the chart
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        // START
        PerformanceManagementTool performanceManagementTool = new PerformanceManagementTool(LocalDate.now().minusMonths(2));
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(30), createWorkoutIntensity(30, 220)) ;
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(10), createWorkoutIntensity(30, 220));
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(8), createWorkoutIntensity(30, 220));
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(6), createWorkoutIntensity(30, 220));
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(4), createWorkoutIntensity(30, 220));
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(2), createWorkoutIntensity(30, 220));
        Function functionToPlot = performanceManagementTool.getFitnessFunction();
        XYChart.Series series = getData(functionToPlot, LocalDate.now().minusDays(30), 30);
        // STOP

        lineChart.getData().add(series);

        StackPane pane = new StackPane();
        pane.getChildren().add(lineChart);

        Scene scene  = new Scene(pane,800,600);
        stage.setScene(scene);
        stage.show();
    }

    private WorkoutIntensity createWorkoutIntensity(int duration, double power) {
        return new WorkoutIntensity(duration, power);
    }


    private XYChart.Series getData(Function functionToPlot, LocalDate startDate, int numberOfDaysToPlot) {
        //defining a series
        XYChart.Series series = new XYChart.Series();

        //populating the series with data

        for (int i = 0; i < numberOfDaysToPlot; i++) {
            LocalDate tempDate = startDate.plusDays(i);
            series.getData().add(new XYChart.Data(i, functionToPlot.getValue(tempDate) ));
        }

        return series;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
