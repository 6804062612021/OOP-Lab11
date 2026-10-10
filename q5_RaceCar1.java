package homework11;

import java.awt.*;
import javax.swing.*;

public class q5_RaceCar1 extends JFrame {
	RaceCar rc=new RaceCar();

	q5_RaceCar1(){
		add(rc);
	}

	public static void main(String[] args) {
		q5_RaceCar1 frame=new q5_RaceCar1();
		frame.setTitle("Exercise");
		frame.setLocationRelativeTo(null); // Center the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 100);
		frame.setVisible(true);
	}

	class RaceCar extends JPanel implements Runnable {
		int xBase=0;
		int speed=1;

		RaceCar(){
			// Instantiate and start a dedicated thread for each RaceCar
			Thread thread = new Thread(this);
			thread.start();

			this.setFocusable(true);
		}

		@Override
		public void run() {
			while (true) {
				// Repaint safely on the Event Dispatch Thread (EDT)
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						repaint();
					}
				});

				try {
					Thread.sleep(1);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
					break;
				}
			}
		}

		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);

			int yBase=getHeight();
			if(xBase>getWidth()){
				xBase=-20;
			}else{
				xBase+=speed;
			}

			g.setColor(Color.BLACK);
			g.fillOval(xBase+10, yBase-10, 10, 10);
			g.fillOval(xBase+30, yBase-10, 10, 10);
			g.setColor(Color.GREEN);
			g.fillRect(xBase, yBase-20, 50, 10);

			g.setColor(Color.RED);
			Polygon polygon=new Polygon();
			polygon.addPoint(xBase+10, yBase-20);
			polygon.addPoint(xBase+20, yBase-30);
			polygon.addPoint(xBase+30, yBase-30);
			polygon.addPoint(xBase+40, yBase-20);
			g.fillPolygon(polygon);
		}
	}
}