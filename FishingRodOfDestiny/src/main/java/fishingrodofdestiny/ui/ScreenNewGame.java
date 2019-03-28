/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenNewGame extends Screen {
    public ScreenNewGame(Screen parent, Stage stage) {
        super(parent, stage);
    }

    @Override
    protected void setup(Group root, Scene scene) {
        VBox vb = new VBox(0);

        {
            Image logo = new Image("file:gfx/Logo.png", false);
            ImageView logov = new ImageView(logo);
            logov.setTranslateX((scene.getWidth() - logo.getWidth()) / 2);
            
            Region spacer = new Region();
            spacer.setMinHeight(50);
            
            vb.getChildren().addAll(logov, spacer);
        }
        
        {
            Label l = new Label("Name: ");
            l.setTextFill(Color.WHITE);
            vb.getChildren().add(l);
        }

        {
            VBox buttons = new VBox(10);
            buttons.setAlignment(Pos.CENTER);

            Button close = new Button("Cancel");
            close.setOnAction(e-> this.close());
            buttons.getChildren().addAll(close);

            Button start = new Button("Start New Game");
            start.setOnAction(e-> this.startNewGame());
            buttons.getChildren().addAll(start);
            
            vb.getChildren().add(buttons);
        }
        
        root.getChildren().add(vb);
    }
    
    private void startNewGame() {
        this.close();
        
        Screen plot = new ScreenPlot(this.getParent(), this.getStage());
        plot.show();
    }
}
