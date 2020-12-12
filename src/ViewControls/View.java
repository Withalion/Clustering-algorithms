package ViewControls;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class View extends Application {
    private Controller controller = new Controller();
    private Pane pane = new Pane();
    private Button button = new Button("Vygeneruj");
    private ComboBox<String> action = new ComboBox<>();
    private Text text = new Text(75,200,"Treba zadat\ndruh zhlukovaca.");

    void prepareStart(){
        button.setLayoutX(90);
        button.setLayoutY(250);
        button.setFont(new Font("times new roman", 20));

        action.getItems().addAll("k-means, centroid", "k-means, medoid", "aglo. zhlukovanie, centroid", "diviz. zhlukovanie, centroid");
        action.setLayoutX(50);
        action.setLayoutY(100);

        text.setFont(new Font("times new roman", 24));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setVisible(false);
        text.setFill(Color.RED);

        pane.getChildren().add(button);
        pane.getChildren().add(action);
        pane.getChildren().add(text);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{ //vytvori prve okno kde sa da vybrat clustrovaci algoritmus a spustit ho
        prepareStart();
        primaryStage.setTitle("UI - klastrovanie");
        primaryStage.getIcons().add(new Image("Utils/ai_icon.png"));
        primaryStage.setScene(new Scene(pane, 300, 300));
        primaryStage.show();
        controller.pairController(this);

        button.setOnAction(e ->{
            controller.generateData(action.getValue());
        });

        action.setOnAction(actionEvent -> {
            text.setVisible(false);
        });
    }

    public void errorInput(){
        text.setVisible(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
