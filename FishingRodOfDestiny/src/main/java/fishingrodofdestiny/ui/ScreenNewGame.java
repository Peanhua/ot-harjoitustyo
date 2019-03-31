/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui;

import fishingrodofdestiny.gameobjects.Game;
import fishingrodofdestiny.gameobjects.Player;
import fishingrodofdestiny.world.EmptyLevelGenerator;
import fishingrodofdestiny.world.StairsTile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
        player.adjustInventoryWeightLimit(this.pointDistributor.getPoints(PointDistributor.PointType.CARRYING_CAPACITY));
        System.out.println(player);
        
        this.close();
        
        
        // TODO: move this new game setup somewhere else
        Game game = new Game(player);
        
        EmptyLevelGenerator elg = new EmptyLevelGenerator(new Random(0), 20, 20);
        for(int i = 0; i < 10; i++)
            game.addLevel(elg.generateLevel(i));
        
        List<StairsTile> stairs = game.getLevel(0).getStairsUp();
        player.getLocation().set(stairs.get(0));
        
        Screen plot = new ScreenPlot(game, this.getParent(), this.getStage());
        plot.show();
    }
}
