package snake;

import javax.swing.JFrame;

public class board extends JFrame{

	board(){
		this.add(new Snake());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}