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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenMainMenu extends Screen {

    public ScreenMainMenu(Screen parent, Stage stage) {
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
            VBox buttons = new VBox(10);
            buttons.setAlignment(Pos.CENTER);
            Button newgame = new Button("New Game");
            buttons.getChildren().addAll(newgame);

            Button loadgame = new Button("Load Saved Game");
            buttons.getChildren().addAll(loadgame);

            Button highscores = new Button("Highscore lists");
            highscores.setOnAction(e -> this.showHighscores());
            buttons.getChildren().addAll(highscores);

            Button quit = new Button("Quit");
            quit.setOnAction(e-> this.close());
            buttons.getChildren().addAll(quit);
            
            vb.getChildren().add(buttons);
        }

        root.getChildren().add(vb);
    }
    
    private void showHighscores() {
        Screen hs = new ScreenHighscores(this, this.getStage());
        hs.show();
    }
}