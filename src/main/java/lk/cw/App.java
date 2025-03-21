package lk.cw;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {


//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/view/Login.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/view/DashBoad.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Therapy Center");
        stage.setResizable(false);

        Image image = new Image(getClass().getResourceAsStream("/style/appLogo.png"));
        stage.getIcons().add(image);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }

}