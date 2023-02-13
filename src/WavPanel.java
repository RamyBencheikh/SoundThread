import java.awt.*;
import javax.swing.*;


public class WavPanel extends JPanel 
{
	// Holds the currently loaded audio data
	private double [] audio = null;
	private int clipLenght = 1;

	// Present playback position, -1 if not currently playing
	private int currentIndex = -1;

	private boolean cursor;

	public void setClipLenght(int i) {
		this.clipLenght = i;
	}

	// Attempts to load the audio in the given filename.
	// Returns true on success, false otherwise.
	public boolean load(String filename) {
		audio = StdAudio.read(filename);
		if(audio == null || audio.length == 0) {
			audio = null;
			return false;
		}else {
			currentIndex = 0;
			repaint();
			return true;
		}
	}

	// Return the number of samples in the currently loaded audio.
	// Returns 0 if no audio loaded.
	public int getNumSamples() {
		return (audio == null ? 0 : audio.length);
	}

	// Get the index of the next audio sample that will be played.
	// Returns -1 if playback isn't active.
	public int getCurrentIndex() {
		return currentIndex;
	}

	// Sets the index of the current audio sample.
	// Client may set to -1 when playback is not active (no red line drawn).
	// Client may set to 0 when playback is about to start.
	public void setCurrentIndex(int i) {
		currentIndex = i;
	}

	// Play a single audio sample based on the current index.
	// Advance the index one position.
	// Returns the panel x-coordinate of the played sample.
	// Returns -1 if playback failed (no audio or index out of range).
	public int playAndAdvance() {
		if(audio == null || currentIndex >= audio.length) {
			return -1;
		}else {
			StdAudio.play(audio[currentIndex++]);
//			currentIndex++;
			repaint();
			return (currentIndex-1) * this.getWidth() / audio.length;
		}
	}

	// Draw the waveform and the current playing position (if any)
	// This method shouldn't be called directly.  The player
	// should call repaint() whenever you need to update the
	// waveform visualization.
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		drawWaveform(g2d);
		drawCursor(g2d);
//	    if(audio != null) {
//			for (int i = 0; i < audio.length; i++) {
//		    	int x = i * getWidth() / audio.length;
//				int y = 0;
//				double toDrawVal = Math.abs(audio[i]);
//				double lengthToDraw = getHeight() * toDrawVal;
//				int yMin = (int)(getHeight() - lengthToDraw) / 2;
//				int yMax = (int)(yMin + lengthToDraw);
//				g.setColor(Color.BLUE);
//				g2d.drawLine(x, yMin, x, yMax);
//		    }
//		}
	}

	private void drawWaveform(Graphics2D g2d) {
		if (audio != null) {
			for (int i = 0; i < audio.length; i++) {
				int x = i * getWidth() / audio.length;
				double toDrawVal = Math.abs(audio[i]);
				double lengthToDraw = getHeight() * toDrawVal;
				int yMin = (int)(getHeight() - lengthToDraw) / 2;
				int yMax = (int)(yMin + lengthToDraw);
				g2d.setColor(Color.BLUE);
				g2d.drawLine(x, yMin, x, yMax);
			}
		}
	}

	private void drawCursor(Graphics2D g2d) {
		if (audio != null) {
			System.out.println("Draw cursor  " + currentIndex +"--" +clipLenght);
			int x = currentIndex * getWidth() / clipLenght;
			g2d.setColor(Color.RED);
			g2d.drawLine(x, 0, x, getHeight());
		}
	}

	public void update() {
		repaint();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("TEST WAVFORM");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WavPanel panel = new WavPanel();
		panel.setPreferredSize(new Dimension(500, 150));
		panel.load("vocal.wav");
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		int curPlaying = 0;
		System.out.println("START");
		while(curPlaying != -1) {
			curPlaying = panel.playAndAdvance();
		}
		System.out.println("FINISH");
	}



}
