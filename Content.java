package editor;

import java.util.ArrayList;

public class Content {
	private ArrayList<String> content;
    private Renderer myRenderer;

	public Content(Renderer renderer) {
		// magically load file
		// make content into the contents of the file
		content = new ArrayList<String>();
        myRenderer = renderer;
	}

	public void add(int index, String element) {
		content.add(index, element);
        myRenderer.add(index, element);

	}

	public void remove(int index) {
		if (index < 0) {
            
        } else {
            content.remove(index);
            myRenderer.remove(index);
        }
	}

	public String get(int index) {
		return content.get(index);
	}

	public String getAll() {
		String total = "";
		for (int x = 0; x != content.size(); x++) {
			total = total + content.get(x);
		}
		return total;
	}
}
