package GPS;

import java.awt.Color;

/**
* The enumState class stores color states for the
* points shown on the GPS GUI.
*
* @author  Dylan Parker
* @version 1.0
* @since   2021-12-05 
* 
* @apiNote
* 		Project_04
* 		Final Project: GPS
*/
public enum enuState {
	UNSELECTED	(Color.WHITE),
	START		(Color.GREEN),
	END			(Color.RED);
	
	private Color color;
	
	private enuState(Color c) {
		color = c;
	}
	
	public Color getColor() { return color; }
}
