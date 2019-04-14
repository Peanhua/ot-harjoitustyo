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
public class ConfirmationRequester extends Window {
    
    public interface ConfirmationHandler {
        void confirmed();
    }

    private final String messageText;
    private final String cancelText;
    private final String confirmText;
    private final ConfirmationHandler handler;
    
    public ConfirmationRequester(Screen screen, String message, String cancel, String confirm, ConfirmationHandler handler) {
        super(screen);
        this.messageText = message;
        this.cancelText  = cancel;
        this.confirmText = confirm;
        this.handler     = handler;
    }
    
    @Override
    protected Node createUserInterface() {
        BorderPane pane = new BorderPane();
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
        List<Button> buttons = new ArrayList<>();
        Node brow = UserInterfaceFactory.createButtonRow(labels, buttons);
        brow.setStyle("-fx-padding: 0 0 10 0;");
        pane.setBottom(brow);
        
        buttons.get(0).setOnAction(e -> {
            this.close();
        });

        buttons.get(1).setOnAction(e -> {
            this.close();
            this.handler.confirmed();
        });
        
        this.setFocusDefault(buttons.get(0));

        return pane;
    }
}
