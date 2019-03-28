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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenPlot extends Screen {
    public ScreenPlot(Screen parent, Stage stage) {
        super(parent, stage);
    }

    @Override
    protected void setup(Group root, Scene scene) {
        VBox vb = new VBox(0);
        vb.setAlignment(Pos.CENTER);

        {
            Image logo = new Image("file:gfx/Logo.png", false);
            ImageView logov = new ImageView(logo);
            logov.setTranslateX((scene.getWidth() - logo.getWidth()) / 2);
            
            Region spacer = new Region();
            spacer.setMinHeight(50);
            
            vb.getChildren().addAll(logov, spacer);
        }

        {
            Text t = new Text();
            t.setFont(new Font(20));
            t.setWrappingWidth(500);
            t.setTextAlignment(TextAlignment.CENTER);
            t.setText("A princess/prince has fallen into the Lake of Sunken Nobles!\n\n"
                    + "You must rescue her/him by retrieving the Magical Fishing Rod from the depths of cave Caerrbannogh!");
            t.setFill(Color.WHITE);
            Region spacer = new Region();
            spacer.setMinHeight(50);

            vb.getChildren().addAll(t, spacer);
        }
        
        {
            VBox buttons = new VBox(10);
            buttons.setAlignment(Pos.CENTER);

            Button cont = new Button("Continue");
            cont.setOnAction(e-> this.gameOn());
            buttons.getChildren().addAll(cont);
            
            vb.getChildren().add(buttons);
        }
        
        root.getChildren().add(vb);
    }
    
    
    private void gameOn() {
        this.close();
        
        Screen game = new ScreenGame(this.getParent(), this.getStage());
        game.show();
    }
}
