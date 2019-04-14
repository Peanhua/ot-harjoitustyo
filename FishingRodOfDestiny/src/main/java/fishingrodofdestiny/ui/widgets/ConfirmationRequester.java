/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.widgets;

import fishingrodofdestiny.ui.screens.Screen;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author joyr
 */
public class ConfirmationRequester {
    
    public interface ConfirmationHandler {
        void confirmed();
    }

    private final Screen screen;
    private Pane         window;
    private List<Button> buttons;
    private final String messageText;
    private final String cancelText;
    private final String confirmText;
    private final ConfirmationHandler handler;
    
    public ConfirmationRequester(Screen screen, String message, String cancel, String confirm, ConfirmationHandler handler) {
        this.screen      = screen;
        this.window      = null;
        this.messageText = message;
        this.cancelText  = cancel;
        this.confirmText = confirm;
        this.handler     = handler;
    }
    
    public void show() {
        this.window = new StackPane();
        
        Region shadow = new Region();
        window.getChildren().add(shadow);
        shadow.setStyle("-fx-background-color: #000000;");
        shadow.setOpacity(0.5);
        
        BorderPane pane = new BorderPane();
        window.getChildren().add(pane);
        pane.setStyle(""
                + "-fx-background-color: #000000;"
                + "-fx-border-width: 2;"
                + "-fx-border-color: #ffffff;"
                + "-fx-border-radius: 5"
        );
        pane.setMaxSize(300, 200);
        
        Text content = UserInterfaceFactory.createText(this.messageText);
        content.setWrappingWidth(250);
        content.setTextAlignment(TextAlignment.CENTER);
        content.setStyle("-fx-padding: 10 10 10 10;");
        pane.setCenter(content);
        
        String[] labels = { this.cancelText, this.confirmText };
        this.buttons = new ArrayList<>();
        Node brow = UserInterfaceFactory.createButtonRow(labels, this.buttons);
        brow.setStyle("-fx-padding: 0 0 10 0;");
        pane.setBottom(brow);
        
        buttons.get(0).setOnAction(e -> {
            this.close();
            screen.enableInputHandlers();
        });

        buttons.get(1).setOnAction(e -> {
            this.close();
            screen.enableInputHandlers();
            this.handler.confirmed();
        });
        
        screen.getRoot().getChildren().add(this.window);
        
        screen.disableInputHandlers();
        this.buttons.get(0).requestFocus();
    }

    public void close() {
        screen.getRoot().getChildren().remove(this.window);
    }
}
