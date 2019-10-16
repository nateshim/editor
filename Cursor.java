package editor;

import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.text.Text;


public class Cursor {
	private int index;
	private Renderer myRenderer;
	public Rectangle cursor;
    
    public int fontSize = 20;
    private String fontName = "Verdana";
	
	public Cursor(Renderer renderer) {
		index = 0;
		myRenderer = renderer;
		cursor = new Rectangle(1,fontSize);
	}

	public void setIndex(int newIndex) {
		if (newIndex < 0) {
			index = 0;
		} else  if (newIndex > myRenderer.textList.size()){
			index = newIndex - 1;
		} else {
			index = newIndex;
	}
	}
	public void newLine() {
		double xPos = 0;
		double yPos = 0;
		if (index == myRenderer.textList.size()) {
			Text letter = myRenderer.textList.get(index);
			xPos = 0;
			yPos = yPos + (letter.getLayoutBounds().getHeight());
			cursor.setX(xPos);
			cursor.setY(yPos);
		}
	}

	public int getIndex() {
		return index;
	}
	public void setcursor(final Group root) {
        root.getChildren().add(cursor);
    }
    public void positionUp() {
    	double xPos = myRenderer.textList.get(index).getLayoutBounds().getWidth();
    	double yPos = myRenderer.textList.get(index).getLayoutBounds().getHeight();
    	if (yPos == 0) {
    	} else if (yPos - (myRenderer.textList.get(index).getLayoutBounds().getHeight()) <= 0) {
    		xPos = xPos;
    		yPos = 0;
    	} else {
    		xPos = xPos;
    		yPos = yPos - (myRenderer.textList.get(index).getLayoutBounds().getHeight());
    		cursor.setX(xPos);
    		cursor.setY(yPos);
    	}
    }
    public void positionDown() {
    	double xPos = myRenderer.textList.get(index).getLayoutBounds().getWidth();
    	double yPos = myRenderer.textList.get(index).getLayoutBounds().getWidth();

    	/*if (yPos == 500) {
    		yPos = 500;
    		cursor.setX(xPos);
    		cursor.setY(yPos);
    	} else if (yPos + (myRenderer.textList.get(index).getLayoutBounds().getHeight()) >= 500) {
    		yPos = 500;
    		cursor.setX(xPos);
    		cursor.setY(yPos);
    	} else {
    		yPos = yPos + Math.round(myRenderer.textList.get(index).getLayoutBounds().getHeight());
    		cursor.setX(xPos);
    		cursor.setY(yPos);
    	}*/
    }
    public void positionLeft() {
    	System.out.println(this.getIndex());
    	double xPos = 0;
		double yPos = 0;
		if (index > 0) {
			System.out.println(xPos);
			Text letter = myRenderer.textList.get(index);
			xPos = Math.round(letter.getX()) - Math.round(letter.getLayoutBounds().getWidth());
			System.out.println(xPos);
			yPos = Math.round(letter.getY());
			cursor.setX(xPos);
            cursor.setY(yPos);
		} else if (index == 0) {
		}
	}
	public void positionRight() {
		double xPos = 0;
		double yPos = 0;
		System.out.println(this.getIndex());
		if (index <= myRenderer.textList.size() && index != 0) {
			Text letter = myRenderer.textList.get(index - 1);
			xPos = Math.round(letter.getX()) + Math.round(letter.getLayoutBounds().getWidth());
			yPos = Math.round(letter.getY());
			cursor.setX(xPos);
			cursor.setY(yPos);
			
		} else if (index + 1 > myRenderer.textList.size()) {
		} else {
			xPos = 0;
			yPos = 0;
			cursor.setX(xPos);
			cursor.setY(yPos);
		}
	}
	public void rendercursor() {
		double xPos = 0;
		double yPos = 0;
		if (index - 1 >= 0) {
			Text letter = myRenderer.textList.get(index - 1);
			xPos =  letter.getX() + letter.getLayoutBounds().getWidth();
			yPos = letter.getY();
			cursor.setX(xPos);
            cursor.setY(yPos);
		} else {
			xPos = 0;
			yPos = 0;
			cursor.setX(xPos);
			cursor.setY(yPos);
		}  



	}
	public void increaseCursorFontSize() {
        fontSize = fontSize + 4;
        cursor.setHeight(fontSize);
    }
    public void decreaseCursorFontSize() {
        if (fontSize - 4 > 0) {
        	fontSize = fontSize - 4;
            cursor.setHeight(fontSize);
        }
    }

}