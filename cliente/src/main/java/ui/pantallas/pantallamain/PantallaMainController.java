package ui.pantallas.pantallamain;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ui.common.ConstantesUI;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.Pantallas;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PantallaMainController extends BasePantallaController implements Initializable {


    @Inject
    public PantallaMainController(Instance<Object> instance) {
        this.instance = instance;
    }

    @FXML
    private Menu optionsMenu;

    @FXML
    private Menu newspapersMenu;

    @FXML
    private Menu readersMenu;

    @FXML
    private BorderPane root;

    @FXML
    private Stage primaryStage;

    Instance<Object> instance;

    private void closeWindowEvent(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle(ConstantesUI.EXIT_THE_APPLICATION);
        alert.setContentText(ConstantesUI.ARE_YOU_SURE_YOU_WANT_TO_EXIT_THE_APPLICATION);
        alert.initOwner(primaryStage.getOwner());
        Optional<ButtonType> res = alert.showAndWait();

        res.ifPresent(buttonType -> {
            if (buttonType == ButtonType.CANCEL) {
                event.consume();
            }
        });
    }

    public void salir() {
        Platform.exit();
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
        primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

    public void logout() {
        newspapersMenu.setVisible(false);
        optionsMenu.setVisible(false);
        readersMenu.setVisible(false);
        cargarPantalla(Pantallas.PANTALLAMAIN);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarPantalla(Pantallas.LISTREADERSCREEN);
    }

    private void cargarPantalla(Pantallas pantalla) {
        Pane panePantalla;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(controller -> instance.select(controller).get());
            panePantalla = fxmlLoader.load(getClass().getResourceAsStream(pantalla.getRuta()));
            BasePantallaController pantallaController = fxmlLoader.getController();
            pantallaController.setPantallaMainController(this);
            pantallaController.principalCargado();
            root.setCenter(panePantalla);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Reader reader;

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public void listNewspapersMenu() {
        cargarPantalla(Pantallas.LISTNEWSPAPERSCREEN);
    }

    public void deleteNewspaperMenu() {
        cargarPantalla(Pantallas.DELETENEWSPAPERSCREEN);
    }

    public void listReadersMenu() {
        cargarPantalla(Pantallas.LISTREADERSCREEN);
    }

    public void deleteReadersMenu() {
        cargarPantalla(Pantallas.DELETEREADERSCREEN);
    }

    public void addReaderMenu() {
        cargarPantalla(Pantallas.ADDREADERSCREEN);
    }

    public void updateReaderMenu() {
        cargarPantalla(Pantallas.UPDATEREADERSCREEN);
    }

    public void addNewspaperMenu() {
        cargarPantalla(Pantallas.ADDNEWSPAPERSCREEN);
    }

    public void updateNewspaperMenu() {
        cargarPantalla(Pantallas.UPDATENEWSPAPERSCREEN);
    }

    public void createAlert(String error) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(error);
        alert.setContentText(error);
        alert.showAndWait();
    }
}