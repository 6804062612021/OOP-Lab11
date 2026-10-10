package homework11;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class q4_ClockAnimation_ThaiAndJapan extends JFrame implements Runnable {
	private StillClock clock = new StillClock();
	private final int jpOffset = 120;
	private StillClock clockJP = new StillClock(jpOffset);

	public q4_ClockAnimation_ThaiAndJapan() {
		super();
		setLayout(new GridLayout(1, 2));

		JPanel p1 = new JPanel(new BorderLayout());
		JLabel lblTH = new JLabel("Thailand Time", SwingConstants.CENTER);
		p1.add(lblTH, BorderLayout.NORTH);
		p1.add(clock, BorderLayout.CENTER);

		// Panel for Japan/Offset Clock
		JPanel p2 = new JPanel(new BorderLayout());
		JLabel lblJP = new JLabel("Japan Time", SwingConstants.CENTER);
		p2.add(lblJP, BorderLayout.NORTH);
		p2.add(clockJP, BorderLayout.CENTER);

		// Add both wrapped panels to the grid layout
		add(p1);
		add(p2);

		// Create and start a Thread
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (true) {
			// Ensure GUI updates are safely executed on the Swing Event Dispatch Thread (EDT)
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					clock.setCurrentTime();
					clock.repaint();
					clockJP.setCurrentTime(jpOffset);
					clockJP.repaint();
				}
			});

			try {
				Thread.sleep(1000); // Sleep for 1000 ms (1 second)
			} catch (InterruptedException ex) {
				ex.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new q4_ClockAnimation_ThaiAndJapan();
		frame.setTitle("ClockAnimation");
		frame.setSize(400, 200);
		frame.setLocationRelativeTo(null); // Center the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class StillClock extends JPanel {
	private int hour;
	private int minute;
	private int second;

	/** Construct a default clock with the current time*/
	public StillClock() { setCurrentTime(); }

	/** Construct a clock with minute offset*/
	public StillClock(int minOffset) { setCurrentTime(minOffset); }

	/** Construct a clock with specified hour, minute, and second */
	public StillClock(int hour, int minute, int second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	/** Return hour */
	public int getHour() { return hour; }

	/** Set a new hour */
	public void setHour(int hour) { this.hour = hour; repaint(); }

	/** Return minute */
	public int getMinute() { return minute; }

	/** Set a new minute */
	public void setMinute(int minute) { this.minute = minute; repaint(); }

	/** Return second */
	public int getSecond() { return second; }

	/** Set a new second */
	public void setSecond(int second) { this.second = second; repaint(); }

	@Override /** Draw the clock */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Initialize clock parameters
		int clockRadius = (int)(Math.min(getWidth(), getHeight()) * 0.8 * 0.5);
		int xCenter = getWidth() / 2;
		int yCenter = getHeight() / 2;

		// Draw circle
		g.setColor(Color.black);
		g.drawOval(xCenter - clockRadius, yCenter - clockRadius, 2 * clockRadius, 2 * clockRadius);
		g.drawString("12", xCenter - 5, yCenter - clockRadius + 12);
		g.drawString("9", xCenter - clockRadius + 3, yCenter + 5);
		g.drawString("3", xCenter + clockRadius - 10, yCenter + 3);
		g.drawString("6", xCenter - 3, yCenter + clockRadius - 3);

		// Draw second hand
		int sLength = (int)(clockRadius * 0.8);
		int xSecond = (int)(xCenter + sLength * Math.sin(second * (2 * Math.PI / 60)));
		int ySecond = (int)(yCenter - sLength * Math.cos(second * (2 * Math.PI / 60)));
		g.setColor(Color.red);
		g.drawLine(xCenter, yCenter, xSecond, ySecond);

		// Draw minute hand
		int mLength = (int)(clockRadius * 0.65);
		int xMinute = (int)(xCenter + mLength * Math.sin(minute * (2 * Math.PI / 60)));
		int yMinute = (int)(yCenter - mLength * Math.cos(minute * (2 * Math.PI / 60)));
		g.setColor(Color.blue);
		g.drawLine(xCenter, yCenter, xMinute, yMinute);

		// Draw hour hand
		int hLength = (int)(clockRadius * 0.5);
		int xHour = (int)(xCenter + hLength * Math.sin((hour % 12 + minute / 60.0) * (2 * Math.PI / 12)));
		int yHour = (int)(yCenter - hLength * Math.cos((hour % 12 + minute / 60.0) * (2 * Math.PI / 12)));
		g.setColor(Color.green);
		g.drawLine(xCenter, yCenter, xHour, yHour);
	}

	public void setCurrentTime(int minOffset) {
		// Construct a calendar for the current date and time
		Calendar calendar = new GregorianCalendar();

		int hourOffset = 0;
		if (minOffset >= 60) {
			hourOffset = minOffset / 60;
			minOffset = minOffset % 60;
		}

		// Set current hour, minute and second
		this.hour = calendar.get(Calendar.HOUR_OF_DAY) + hourOffset;
		this.minute = calendar.get(Calendar.MINUTE) + minOffset;
		this.second = calendar.get(Calendar.SECOND);
	}

	public void setCurrentTime() {
		setCurrentTime(0);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}
}