package controller;

import IO.IOHandler;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * A fő felhasználói felületet vezérlő osztály.
 */

public class ViewController implements Initializable {

    private static Logger logger = LoggerFactory.getLogger(ViewController.class);

    /**
     * Az adatokat tartalmazó táblázat.
     */
    @FXML
    TableView table;
    /**
     * A beérkező feladat mezője.
     */
    @FXML
    TextField inputTask;
    /**
     * "Hozzáadás" gomb.
     */
    @FXML
    Button addNewTaskButton;
    /**
     * A prioritást kiválasztó legördülő menü.
     */
    @FXML
    ComboBox priorityComboBox;
    /**
     * A dátumkiválasztó.
     */
    @FXML
    DatePicker datePicker;
    /**
     *  "Mai feladatok" gomb.
     */
    @FXML
    RadioButton todaySelect;
    /**
     * "Összes törlése" gomb.
     */
    @FXML
    Button deleteAllButton;
    /**
     *  A "pane" panelt tartalmazó panel.
     */
    @FXML
    AnchorPane anchor;
    /**
     *  Az egész nézetet tartalmazó panel.
     */
    @FXML
    Pane pane;
    /**
     * "Import" gomb.
     */
    @FXML
    Button importButton;
    /**
     * "Mentés" gomb.
     */
    @FXML
    Button exportButton;

    @FXML
    Label highPriorityTasksLabel;

    @FXML
    Label todayTasksLabel;


    /**
     * A feladatokat tartalmazó listanézet.
     */
    private ObservableList<Task> tasks = FXCollections.observableArrayList();

   public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ObservableList<Task> tasks) {
        this.tasks.addAll(tasks);
    }


    /**
     * A prioritást kiválasztó legördülő menühöz tartozó választható értékek.
     */
    private final ObservableList<String> priorityData =
            FXCollections.observableArrayList("Magas", "Közepes", "Alacsony");


    /**
     * Hozzáad egy új feladatot a "Hozzáadás" gomb megnyomásakor.
     */
    @FXML
    private void addNewTask() {
        String taskText = inputTask.getText();
      //String priorityText = priorityComboBox.getValue().toString();
        LocalDate date = datePicker.getValue();

        if (taskText.length()<1 || priorityComboBox.getSelectionModel().isEmpty() || date == null){
            alert("Tölts ki minden mezőt!");
        }else{
            tasks.add(new Task(taskText, priorityComboBox.getValue().toString(), date));
            inputTask.clear();
            priorityComboBox.setValue(null);
            datePicker.setValue(null);
            logger.info("Uj feladat hozzaadasa");
        }
        setNumbers();
    }


    /**
     * Xml-be exprtálja az adatokat a megadott helyre, a "Mentés" gomb megnyomásakor.
     */
    @FXML
    private void exportData() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Feladatok exportalasa");

        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.setInitialFileName("pelda.xml");

        FileChooser.ExtensionFilter xmlExtensionFilter =
                new FileChooser.ExtensionFilter(
                        "eXtensible Markup Language file", "*.xml");

        fileChooser.getExtensionFilters().add(xmlExtensionFilter);
        fileChooser.setSelectedExtensionFilter(xmlExtensionFilter);
        File file = fileChooser.showSaveDialog(null);
        System.out.println(file);
        IOHandler.XmlWriter(file,tasks);
        logger.info(file + " exportalasa");
    }

    /**
     * A kiválasztott xml-ből beimportálja az adatokat az "Import" gomb megnyomasakor.
     */
    @FXML
    private void importData () {

        tasks = FXCollections.observableArrayList();
        //table.getItems().clear();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Feladatok importálása");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("xml file plese", "*.xml");

        fileChooser.getExtensionFilters().add(filter);

        File imput = fileChooser.showOpenDialog(null);

        List<Task> importXmlDBList = IOHandler.XmlReader(imput);

        for (Task currTask : importXmlDBList) {
            tasks.addAll(currTask);
        }

        table.setItems(tasks);

        logger.info(imput + " importalasa");

    }

    /**
     * Kiválaszja a mai feladatokat a táblázatból a "Mai feladatok" gomb megnyomásával.
     */
    @FXML
    private void selectTodaysTasks() {
        ObservableList<Task> todayTasks = FXCollections.observableArrayList();
        System.out.println("maitaskok");
        System.out.println(todaySelect.isSelected());
        LocalDate dateNow = LocalDate.now();

        for (Task t : tasks) {
            if (t.getTaskDate().getDayOfYear() == dateNow.getDayOfYear()) {
                System.out.println(t);
                todayTasks.addAll(t);
            }
        }

        if (todaySelect.isSelected()) {
            table.setItems(todayTasks);
        } else {
            table.setItems(tasks);
        }
    }


    /**
     * Az Összes feladatot törli a táblázatból az "Összes törlése" gomb megnyomásával.
     */
    @FXML
    private void deleteAll(){
        System.out.println("deleteall");
        tasks = FXCollections.observableArrayList();
        table.getItems().clear();
        setNumbers();
    }


    /**
     * Összeállítja az összes feladatot tartalmazó táblázat nézetét.
     */
    private void setTableData(){
        File startFile = new File("tasks.xml");
        System.out.println(startFile);
        List<Task> XmlDBList = null;
        File currentDirFile = new File("tasks.xml");
        String helper = currentDirFile.getAbsolutePath();
        System.out.println(helper);
        if(startFile.exists()){
            XmlDBList = IOHandler.XmlReader(startFile.getAbsoluteFile());
        }else {
            IOHandler.XmlWriter(currentDirFile, XmlDBList);
        }

        logger.info("Tablazat feltoltese");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");

        TableColumn dateCol = new TableColumn("Datum");
        dateCol.setMinWidth(100);
        dateCol.setCellFactory(tc -> new TableCell<Task, LocalDate>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(formatter.format(date));
                }
            }
        });

        dateCol.setCellValueFactory(new PropertyValueFactory<>("taskDate"));

        TableColumn taskCol = new TableColumn("Feladat");
        taskCol.setMinWidth(400);
        taskCol.setCellFactory(TextFieldTableCell.forTableColumn());
        taskCol.setCellValueFactory(new PropertyValueFactory<Task, String>("taskDesc"));

        taskCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> event) {
                        ((Task) event.getTableView().getItems().get(
                                event.getTablePosition().getRow())
                        ).setTaskDesc(event.getNewValue());
                    }
                }
        );

        TableColumn priorityCol = new TableColumn("Fontossag");
        priorityCol.setMinWidth(120);
        priorityCol.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), priorityData));
        priorityCol.setCellValueFactory(new PropertyValueFactory<Task, String>("priority"));

        priorityCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> event) {
                        (event.getTableView().getItems().get(
                                event.getTablePosition().getRow())
                        ).setPriority(event.getNewValue());
                        setNumbers();
                    }
                }

        );

//     ez jo eddig
        TableColumn doneCol = new TableColumn("Kesz");
        doneCol.setMinWidth(60);
        doneCol.setSortable(false);

        doneCol.setCellValueFactory(new PropertyValueFactory<Task, Boolean>("done"));
        doneCol.setCellFactory(CheckBoxTableCell.forTableColumn(new TableColumn<>()));



        TableColumn deleteCol = new TableColumn("Törlés");
        deleteCol.setMinWidth(100);

        Callback<TableColumn<Task, String>, TableCell<Task, String>> cellFactory =
                new Callback<TableColumn<Task, String>, TableCell<Task, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Task, String> param) {
                        final TableCell<Task, String> cell = new TableCell<Task, String>() {
                            final Button btn = new Button("Törlés");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction((ActionEvent event) ->
                                    {
                                        Task task = getTableView().getItems().get(getIndex());
                                        tasks.remove(task);
                                        System.out.println(task);
                                        setNumbers();
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;

                    }
                };

        deleteCol.setCellFactory(cellFactory);


        table.getColumns().addAll(dateCol, taskCol, priorityCol, doneCol, deleteCol);

        for (Task currTask : XmlDBList) {
            tasks.addAll(currTask);
        }

        table.setItems(tasks);
        setNumbers();
    }

    private void setNumbers(){

        todayTasksLabel.setText("" + TaskReport.todayTasksNumber(tasks));
        highPriorityTasksLabel.setText("" + TaskReport.highPriorityTasksNumber(tasks));
    }

    /**
     * Hiba esetén felugró figyelmeztető ablak.
     * @param text a beérkező szöveget jeleníti meg.
     */
    private void alert(String text) {
        pane.setDisable(true);
        pane.setOpacity(0.3);

        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);

        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                pane.setDisable(false);
                pane.setOpacity(1);
                vbox.setVisible(false);
            }
        });

        anchor.getChildren().add(vbox);
        anchor.setTopAnchor(vbox, 250.0);
        anchor.setLeftAnchor(vbox, 360.0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setTableData();
        setNumbers();
    }
}
