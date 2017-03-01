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
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(30), 0.9);
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(10), 0.35);
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(8), 0.4);
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(6), 0.8);
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(4), 0.4);
        performanceManagementTool.addTrainingsDay(LocalDate.now().minusDays(2), 0.65);
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
