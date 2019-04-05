/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui;

import fishingrodofdestiny.ui.widgets.PointDistributor;
import fishingrodofdestiny.gameobjects.Game;
import fishingrodofdestiny.gameobjects.Player;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenNewGame extends Screen {
    
    private Player           player;
    private TextField        name;
    private PointDistributor pointDistributor;
    
    public ScreenNewGame(Screen parent, Stage stage) {
        super(parent, stage);
        
        this.player           = new Player();
        this.name             = null;
        this.pointDistributor = null;
    }

    @Override
    protected void setup(Group root, Scene scene) {
        VBox vb = new VBox(0);
        vb.setAlignment(Pos.CENTER);
        vb.setMinWidth(scene.getWidth());

        vb.getChildren().add(UserInterfaceFactory.createTitle("START NEW GAME"));
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        
        this.name = new TextField();
        vb.getChildren().add(UserInterfaceFactory.createLabeledInput("Name:", this.name));
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        
        this.pointDistributor = new PointDistributor(this.player, 20); 
        vb.getChildren().add(this.pointDistributor.createUserInterface());
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        
        { // Saving target:
            String[] labels = { "Save princess:", "Save prince:" };
            List<RadioButton> buttons = new ArrayList<>();
            vb.getChildren().add(UserInterfaceFactory.createRadiobuttonGrid(labels, buttons));
            vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        }

        TextField randseed = new TextField();
        vb.getChildren().add(UserInterfaceFactory.createLabeledInput("Random seed:", randseed));
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        
        { // Cancel/Go -buttons:
            String[] labels = { "Cancel", "Start New Game" };
            List<Button> buttons = new ArrayList<>();
            vb.getChildren().add(UserInterfaceFactory.createButtonRow(labels, buttons));
            buttons.get(0).setOnAction(e-> this.close());
            buttons.get(1).setOnAction(e-> this.startNewGame());
        }
        
        root.getChildren().add(vb);
    }
    
    
    private void startNewGame() {
        player.setName(this.name.getText());
        player.adjustAttack(this.pointDistributor.getPoints(PointDistributor.PointType.ATTACK));
        player.adjustDefence(this.pointDistributor.getPoints(PointDistributor.PointType.DEFENCE));
        player.adjustMaxHitpoints(this.pointDistributor.getPoints(PointDistributor.PointType.HITPOINTS));
        player.getInventory().adjustWeightLimit(this.pointDistributor.getPoints(PointDistributor.PointType.CARRYING_CAPACITY));
        
        this.close();
        
        Game game = new Game(player);
        Screen plot = new ScreenPlot(game, this.getParent(), this.getStage());
        plot.show();
    }
}
