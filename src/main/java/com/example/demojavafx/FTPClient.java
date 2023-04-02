import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.*;

import java.io.*;

public class HelloApplication extends Application {

private static final String FTP_HOST = "ftp.example.com";
private static final String FTP_USERNAME = "username";
private static final String FTP_PASSWORD = "password";

private FTPClient ftpClient;
private TextField serverTextField;
private TextField usernameTextField;
private PasswordField passwordField;
private ListView<String> filesListView;

@Override
public void start(Stage primaryStage) throws Exception {
ftpClient = new FTPClient();

GridPane gridPane = new GridPane();
gridPane.setPadding(new Insets(10));
gridPane.setHgap(10);
gridPane.setVgap(10);

Label serverLabel = new Label("Server:");
serverTextField = new TextField();
serverTextField.setText(FTP_HOST);
gridPane.add(serverLabel, 0, 0);
gridPane.add(serverTextField, 1, 0);

Label usernameLabel = new Label("Username:");
usernameTextField = new TextField();
usernameTextField.setText(FTP_USERNAME);
gridPane.add(usernameLabel, 0, 1);
gridPane.add(usernameTextField, 1, 1);

Label passwordLabel = new Label("Password:");
passwordField = new PasswordField();
passwordField.setText(FTP_PASSWORD);
gridPane.add(passwordLabel, 0, 2);
gridPane.add(passwordField, 1, 2);

Button connectButton = new Button("Connect");
connectButton.setOnAction(event -> connect());
gridPane.add(connectButton, 0, 3);

filesListView = new ListView<>();
gridPane.add(filesListView, 0, 4, 2, 1);

Scene scene = new Scene(gridPane, 400, 400);
primaryStage.setScene(scene);
primaryStage.show();
}

private void connect() {
try {
ftpClient.connect(serverTextField.getText());
ftpClient.login(usernameTextField.getText(), passwordField.getText());
FTPFile[] files = ftpClient.listFiles();
for (FTPFile file : files) {
filesListView.getItems().add(file.getName());
}
} catch (IOException e) {
Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to connect to FTP server");
alert.showAndWait();
}
}
}
