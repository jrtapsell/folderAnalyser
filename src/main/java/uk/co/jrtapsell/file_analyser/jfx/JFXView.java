package uk.co.jrtapsell.file_analyser.jfx;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import org.jetbrains.annotations.Nullable;
import uk.co.jrtapsell.file_analyser.common.DataPair;
import uk.co.jrtapsell.file_analyser.common.View;

public class JFXView implements View {

  @FXML public Button folderButton;
  @FXML public PieChart typeChart;
  @FXML public PieChart folderChart;
  @FXML public PieChart rawChart;
  @FXML public Label totalLabel;
  @FXML public GridPane chartArea;
  @FXML public Slider countSlider;
  @FXML public Label countLabel;

  private Long limit;

  public void setDataVisible(boolean state) {
    chartArea.setVisible(state);
  }

  private void linkChart(String title, Stream<DataPair> data, PieChart chart) {
    chart.setTitle(title);
    chart.getData().clear();
    chart.getData().addAll(data.map(DataPair::makePieData).collect(Collectors.toList()));
  }

  public void setFileData(String title, Stream<DataPair> data) {
    linkChart(title, data, rawChart);
  }

  public void setExtensionData(String title, Stream<DataPair> data) {
    linkChart(title, data, typeChart);
  }

  public void setFolderData(String title, Stream<DataPair> data) {
    linkChart(title, data, folderChart);
  }

  public void initialize() {
    countSlider.valueProperty().addListener(this::onSlide);
    onSlide(null, null,8);
  }

  @Nullable
  public File chooseDirectory() {
    final DirectoryChooser dc = new DirectoryChooser();
    return dc.showDialog(null);
  }

  @Nullable private Consumer<Long> onUpdate;

  private void onSlide(ObservableValue<? extends Number> v, Number o, Number n) {
    System.out.println(n);
    final long value = Math.round(n.doubleValue());
    if (value == 21) {
      countLabel.textProperty().set("Unlimited");
      limit = null;
    } else {
      countLabel.textProperty().set("Top " + value + " items");
      limit = value;
    }
    if (onUpdate != null) {
      onUpdate.accept(limit);
    }
  }


  public void setTotalText(String format) {
    totalLabel.setText(format);
  }

  @Override
  public void setOnLoad(Consumer<Long> function) {
    folderButton.setOnMouseClicked(event -> {
      function.accept(limit);
    });
  }

  @Override
  public void setOnUpdate(Consumer<Long> function) {
    this.onUpdate = function;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initialize();
  }
}
