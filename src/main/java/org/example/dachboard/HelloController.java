package org.example.dachboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONObject;


public class HelloController {

    @FXML
    private LineChart chartSensor;

    // Variable to keep track of the current sensor ID
    private int currentSensorID = 1;

    @FXML
    private TextField sensorIdField;

    @FXML
    private TextField diepteField;

    @FXML
    private TextField locatieField;

    @FXML
    private Label sensorGelukt;

    private final ApiClient client = new ApiClient();

    private ScheduledExecutorService scheduler;
    @FXML
    private Label Sensor1LiveData;
    @FXML
    private Label Sensor2LiveData;
    @FXML
    private Label Sensor3LiveData;

    @FXML
    public void initialize() throws Exception {
        System.out.println("initialize");
        List<JSONObject> lastTen = client.getLastTenValuesFromSensor(currentSensorID);
        System.out.println( lastTen);

        // ... existing code ...

        // Initialize the scheduler
        scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the task to run every 10 seconds
        scheduler.scheduleAtFixedRate(this::updateSensorData, 0, 10, TimeUnit.SECONDS);

    }

    //grafiek

    public void btnSensor1(ActionEvent actionEvent) throws Exception {
        currentSensorID = 1;
        updateChartForCurrentSensor();
    }

    public void btnSensor2(ActionEvent actionEvent) throws Exception {
        currentSensorID = 2;
        updateChartForCurrentSensor();
    }

    public void btnSensor3(ActionEvent actionEvent) throws Exception {
        currentSensorID = 3;
        updateChartForCurrentSensor();
    }

    public void btnReloadPage(ActionEvent actionEvent) throws Exception {
        updateChartForCurrentSensor();
    }

    private void updateChartForCurrentSensor() throws Exception {
        chartSensor.getData().clear();
        List<JSONObject> data = client.getLast24hFromSensor(currentSensorID);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (JSONObject jsonObject : data) {
            String timestamp = jsonObject.getString("Timestamp");
            Float waarde = jsonObject.getFloat("Waarde");
            series.getData().add(new XYChart.Data<>(timestamp, waarde));
        }
        chartSensor.getData().add(series);
    }

    // addsensor
    public void btnAddSensor(ActionEvent actionEvent) throws Exception {
        String type = sensorIdField.getText();
        String locatieBeschrijving = locatieField.getText();
        String diepteStr = diepteField.getText();

        if (type.isEmpty() || locatieBeschrijving.isEmpty() || diepteStr.isEmpty()) {
            sensorGelukt.setText("Alle velden moeten gevuld zijn.");
        } else if (!diepteStr.matches("\\d+")) {
            sensorGelukt.setText("Sensordiepte moet een getal zijn.");
        } else {
            try {
                int diepte = Integer.parseInt(diepteStr);

                JSONObject newSensor = client.addNewSensor(type, locatieBeschrijving, diepte);

                sensorGelukt.setText("Sensor toegevoegd!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

// actuele data
    private void updateSensorData() {
        try {
            float temp1 = client.getLastValueFromSensor(5);
            float temp2 = client.getLastValueFromSensor(1);
            float temp3 = client.getLastValueFromSensor(2);
            this.Sensor1LiveData.setText(String.valueOf(temp1 + " graden"));
            this.Sensor2LiveData.setText(String.valueOf(temp2 + " graden"));
            this.Sensor3LiveData.setText(String.valueOf(temp3 + " graden"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}