package org.example.dachboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import java.util.List;
import org.json.JSONObject;


public class HelloController {

    @FXML
    private LineChart chartSensor;

    private final ApiClient client = new ApiClient();

    @FXML
    public void initialize() throws Exception {
        System.out.println("initialize");
        List<JSONObject> lastTen = client.getLastTenValuesFromSensor(1);
        System.out.println( lastTen);
    }

    public void btnSensor1(ActionEvent actionEvent) throws Exception {
        ApiClient apiClient = new ApiClient();
        chartSensor.getData().clear();

        int sensorID = 1;
        List<JSONObject> data = apiClient.getLast24hFromSensor(sensorID);
        XYChart.Series<String, Number> series = null;
        series = new XYChart.Series<>();
        chartSensor.getData().add(series);

        for (JSONObject jsonObject : data) {
            String timestamp = jsonObject.getString("Timestamp");
            Float Waarde = jsonObject.getFloat("Waarde");
            series.getData().add(new XYChart.Data<>(timestamp, Waarde));
            System.out.println("timestamp: " + timestamp + " Waarde: " + Waarde);
        }
    }

    public void btnSensor2(ActionEvent actionEvent) throws Exception {
        ApiClient apiClient = new ApiClient();
        chartSensor.getData().clear();

        int sensorID = 2;
        List<JSONObject> data = apiClient.getLast24hFromSensor(sensorID);
        XYChart.Series<String, Number> series = null;
        series = new XYChart.Series<>();
        chartSensor.getData().add(series);

        for (JSONObject jsonObject : data) {
            String timestamp = jsonObject.getString("Timestamp");
            Float Waarde = jsonObject.getFloat("Waarde");
            series.getData().add(new XYChart.Data<>(timestamp, Waarde));
        }
    }


    public void btnSensor3(ActionEvent actionEvent) throws Exception {
        ApiClient apiClient = new ApiClient();
        chartSensor.getData().clear();

        int sensorID = 3;
        List<JSONObject> data = apiClient.getLast24hFromSensor(sensorID);
        XYChart.Series<String, Number> series = null;
        series = new XYChart.Series<>();
        chartSensor.getData().add(series);

        for (JSONObject jsonObject : data) {
            String timestamp = jsonObject.getString("Timestamp");
            Float Waarde = jsonObject.getFloat("Waarde");
            series.getData().add(new XYChart.Data<>(timestamp, Waarde));
        }
    }

    public void btnReloadPage(ActionEvent actionEvent) {

    }
}
