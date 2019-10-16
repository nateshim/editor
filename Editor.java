package editor;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.ArrayList;


public class Editor extends Application {
    /** An EventHandler to handle keys that get pressed. */

    public Content content;
    public Cursor cursor;
    public Renderer renderer;

    public Editor() {
    	renderer = new Renderer();
    	cursor = new Cursor(renderer);
    	content = new Content(renderer);
    }
    
    private class CursorBlinkEventHandler implements EventHandler<ActionEvent> {
        private int currentColorIndex = 0;
        private Color[] boxColors =
                {Color.BLACK, Color.WHITE};

        public void CursorBlinkEventHandler() {
            changeColor();
        }

        private void changeColor() {
            cursor.cursor.setFill(boxColors[currentColorIndex]);
            currentColorIndex = (currentColorIndex + 1) % boxColors.length;
        }

        @Override
        public void handle(ActionEvent event) {
            changeColor();
        }
    }
    private class KeyEventHandler implements EventHandler<KeyEvent> {
        int textCenterX;
        int textCenterY;

        public KeyEventHandler() {
            
        }

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
                // Use the KEY_TYPED event rather than KEY_PRESSED for letter keys, because with
                // the KEY_TYPED event, javafx handles the "Shift" key and associated
                // capitalization.
                String characterTyped = keyEvent.getCharacter();
                if (characterTyped == "+" || characterTyped == "-" || characterTyped == "=") {
                	keyEvent.consume();
                }
                
                if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8 && !keyEvent.isShortcutDown()) {
                    // Ignore control keys, which have non-zero length, as well as the backspace key, which is
                    // represented as a character of value = 8 on Windows.                   
                	content.add(cursor.getIndex(), characterTyped);

                	cursor.setIndex(cursor.getIndex() + 1);
                    renderer.render();
                    cursor.rendercursor();
                    keyEvent.consume();
                } 
            } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                // Arrow keys should be processed using the KEY_PRESSED event, because KEY_PRESSED
                // events have a code that we can check (KEY_TYPED events don't have an associated
                // KeyCode).
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.BACK_SPACE) {
                	content.remove(cursor.getIndex() - 1);
                	cursor.setIndex(cursor.getIndex() - 1);
                    renderer.render();
                    cursor.rendercursor();
                    keyEvent.consume();
                }
                if (code == KeyCode.UP) {
                	cursor.positionUp();
                	keyEvent.consume();

                }
                if (code == KeyCode.DOWN) {
                	cursor.positionDown();
                	keyEvent.consume();

                }
                if (code == KeyCode.LEFT) {
           			cursor.setIndex(cursor.getIndex() - 1);
                	cursor.positionLeft();
                	keyEvent.consume();

                }
                if (code == KeyCode.RIGHT) {
                	cursor.setIndex(cursor.getIndex() + 1);
                	cursor.positionRight();
                	keyEvent.consume();

                }
                if (keyEvent.isShortcutDown()) {
                	if (code == KeyCode.PLUS || code == KeyCode.EQUALS) {
                		renderer.increaseFontSize();
                		cursor.increaseCursorFontSize();
                		renderer.render();
                		cursor.rendercursor();
                	} else if (code == KeyCode.MINUS) {
                		renderer.decreaseFontSize();
                		cursor.decreaseCursorFontSize();
                		renderer.render();
                		cursor.rendercursor();
                	}

                }            
                
        	} 
    	}
    }
    public void makeCursorBlink() {
	        final Timeline timeline = new Timeline();
	        timeline.setCycleCount(Timeline.INDEFINITE);
	        CursorBlinkEventHandler cursorChange = new CursorBlinkEventHandler();
	        KeyFrame keyFrame = new KeyFrame(Duration.seconds(.5), cursorChange);
	        timeline.getKeyFrames().add(keyFrame);
	        timeline.play();
	    }

    @Override
    public void start(Stage primaryStage) {
        // Create a Node that will be the parent of all things displayed on the screen.
        Group root = new Group();
        // The Scene represents the window: its height and width will be the height and width
        // of the window displayed.
        int windowWidth = 500;
        int windowHeight = 500;
        Scene scene = new Scene(root, windowWidth, windowHeight, Color.WHITE);

        renderer.setWindow(root, windowWidth, windowHeight);
        cursor.setcursor(root);

        // To get information about what keys the user is pressing, create an EventHandler.
        // EventHandler subclasses must override the "handle" function, which will be called
        // by javafx.
        EventHandler<KeyEvent> keyEventHandler = new KeyEventHandler();
        // Register the event handler to be called for all KEY_PRESSED and KEY_TYPED events.
        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);
        makeCursorBlink();

        primaryStage.setTitle("Single Letter Display");

        // This is boilerplate, necessary to setup the window where things are displayed.
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}