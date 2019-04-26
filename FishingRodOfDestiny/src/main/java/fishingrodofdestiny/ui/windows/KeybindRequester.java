/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.windows;

import fishingrodofdestiny.ui.screens.Screen;
import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author joyr
 */
public class KeybindRequester extends Window {
    public interface KeybindRequesterHandler {
        void bind(KeyCode keyCode);
    }

    private final String                  targetText;
    private final KeybindRequesterHandler handler;

    public KeybindRequester(Screen screen, String targetText, KeybindRequesterHandler handler) {
        super(screen);
        this.targetText = targetText;
        this.handler    = handler;
    }

    @Override
    protected Node createUserInterface() {
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("window");
        pane.setMaxSize(400, 150);
        
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        pane.setCenter(vbox);
        
        Text t = UserInterfaceFactory.createText("Press key to bind to " + this.targetText + ".");
        
        Button b = new Button("Cancel");
        b.setOnAction(e -> this.close());
        b.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            this.close();
            this.handler.bind(event.getCode());
        });

        this.setFocusDefault(b);
        
        vbox.getChildren().addAll(t, b);
        
        return pane;
    }
    
}
