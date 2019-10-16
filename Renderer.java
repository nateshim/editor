package editor;

import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.geometry.VPos;
import javafx.scene.text.Font;
import java.util.ArrayList;

public class Renderer {
	Group myRoot;
    int myWindowWidth;
    int myWindowHeight;
    ArrayList<Text> textList;
    
    /** The Text to display on the screen. */
    public int fontSize = 20;
    private String fontName = "Verdana";

    public Renderer() {
        textList = new ArrayList<Text>();
    }

    public void setWindow(final Group root, int windowWidth, int windowHeight) {
        myRoot = root;
        myWindowWidth = windowWidth;
        myWindowHeight = windowHeight;
    }
    
    public void add(int index, String element) {
        Text text_element = new Text(element);
        textList.add(index, text_element);
        myRoot.getChildren().add(text_element);

    }
    
    public void remove(int index) {
        myRoot.getChildren().remove(textList.get(index));
        textList.remove(index);
    }
    public static int getWordWidth(ArrayList<Text> word) {
        int totalWidth = 0;
        for (int x = 0; x != word.size(); x++) {
            totalWidth = totalWidth + (int) Math.round(word.get(x).getLayoutBounds().getWidth());
        }
        return totalWidth;
    }
    public void increaseFontSize() {
        fontSize = fontSize + 4;
    }
    public void decreaseFontSize() {
        if (fontSize - 4 > 0) {
            fontSize = fontSize - 4;
        }
    }
    public void changeCoordinates() {

    }
    
    public void render() {
        double xPos = 0;
        double yPos = 0;
        Text currentText;
        int currentTextWidth;
        ArrayList<Text> word = new ArrayList<Text>();
        for (int x = 0; x != textList.size(); x++) {
            currentText = textList.get(x);
            currentTextWidth = (int) Math.round(textList.get(x).getLayoutBounds().getWidth());
            word.add(currentText);
            currentText.setTextOrigin(VPos.TOP);
            currentText.setFont(Font.font (fontName, fontSize));

            if (currentText.getText().contains(" ")) {
                word.clear();
            }

            if (xPos >= myWindowWidth - currentTextWidth) {
                xPos = 0;
                yPos = yPos + (int) Math.round(currentText.getLayoutBounds().getHeight());
                if (Renderer.getWordWidth(word) >= myWindowWidth) {
                    currentText.setX(xPos);
                    currentText.setY(yPos);
                    xPos = xPos + currentTextWidth;
                } else { 
                    for (int y = 0; y != word.size(); y++) {
                        word.get(y).setX(xPos);
                        word.get(y).setY(yPos);
                        xPos = xPos + currentTextWidth;
                    }
                }
            } else {
                currentText.setX(xPos);
                currentText.setY(yPos);
                xPos = xPos + currentTextWidth;
            }
        }
    }
}