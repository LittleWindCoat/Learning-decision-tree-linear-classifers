package nn.ui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import nn.core.InputUnit;

public class InputUnitView extends JLabel {
	
	private static final long serialVersionUID = 1L;
	
	protected InputUnit unit;
	
	public InputUnitView(InputUnit unit) {
		super();
		this.unit = unit;
		update();
		// Border
		Border line = BorderFactory.createLineBorder(Color.black);
		Border space = BorderFactory.createEmptyBorder(5,5,5,5);
		this.setBorder(BorderFactory.createCompoundBorder(line, space));
	}
	
	public void update() {
		setText(String.format("%.3f", unit.getOutput()));
	}

}
