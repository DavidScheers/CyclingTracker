package main; /**
 * Created by davids on 27/02/2017.
 */
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
        DataBasedFunction tssFunction = new DataBasedFunction(getFakeData());
        Function functionToPlot = new PerformanceFunction(tssFunction, 42, LocalDate.now());
        XYChart.Series series = getData(functionToPlot, LocalDate.now(), 20);
        // STOP

        lineChart.getData().add(series);

        StackPane pane = new StackPane();
        pane.getChildren().add(lineChart);

        Scene scene  = new Scene(pane,800,600);
        stage.setScene(scene);
        stage.show();
    }

    private Map<LocalDate,Double> getFakeData() {
        Map<LocalDate, Double> aMap = new HashMap<>();
        aMap.put(LocalDate.now(), 5.0);
        aMap.put(LocalDate.now().plusDays(1), 6.3);
        aMap.put(LocalDate.now().plusDays(2), 7.7);

        return aMap;
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
