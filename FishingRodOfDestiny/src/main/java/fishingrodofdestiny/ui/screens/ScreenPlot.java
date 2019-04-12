/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.screens;

import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import fishingrodofdestiny.world.Game;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenPlot extends Screen {
    private Game game;
    
    public ScreenPlot(Game game, Screen parent, Stage stage) {
        super(parent, stage);
        this.game = game;
    }

    @Override
    protected Node createUserInterface() {
        VBox vb = new VBox(0);
        vb.setAlignment(Pos.CENTER);

        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        vb.getChildren().add(UserInterfaceFactory.createLogo(null));

        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(100));

        String plot = this.game.getPlot().stream().reduce("", (a, b) -> a + "\n" + b);
        Text t = UserInterfaceFactory.createText(plot);
        t.setFont(new Font(20));
        t.setWrappingWidth(500);
        t.setTextAlignment(TextAlignment.CENTER);
        vb.getChildren().add(t);
        
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(100));
        
        Button cont = new Button("Continue");
        cont.setOnAction(e-> this.gameOn());
        vb.getChildren().add(cont);
        
        return vb;
    }
    
    
    private void gameOn() {
        this.close();
        
        Screen screen = new ScreenGame(this.game, this.getParent(), this.getStage());
        screen.show();
    }
}
