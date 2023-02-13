/*************************************************************************
System.out.println("FINISH"); * Name        : 
    : 
 * Description : 
 *************************************************************************/

import java.awt.*;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;

public class WavPlayer extends JFrame implements ActionListener
{
	private Timer timer;
    private JFileChooser chooser;
	private Toolbar toolbar;
    private File audioFile;

	private Sound player;

	private WavPanel panel;

	private Clip audioClip;
    public WavPlayer() {
		super("WavPlayer");
		timer = new Timer(1, this);

		chooser = new JFileChooser();

		chooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean accept(File f) {
				if(f.getName().endsWith(".wav")) return true;
				else return false;
			}
		});

		setSize(1000, 800);

		setLayout(new BorderLayout());
        toolbar = new Toolbar();
		player = new Sound("/home/rbencheikh/Documents/repo/SoundThread/src/vocal.wav");
		panel = new WavPanel();
		panel.setPreferredSize(new Dimension(500, 150));
		audioClip = player.getClip();
        toolbar.setStringListener(new StringListener() {
            @Override
            public void textEmitted(String text) {
                switch (text) {
                    case "Play":
//						panel.setClipLenght(player.getClip().getFrameLength());
						player.play(panel);
                        break;
                    case "Pause":
						player.pause(panel);
                        break;
                    case "Stop":
						player.stop(panel);
                        break;
                    case "Resume":
						player.resume(panel);
                    default:
                        break;
                }
            }
        });

        // Adds components(child's) into the Layout
		panel.load("vocal.wav");
        add(toolbar, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

		audioClip.addLineListener(event -> {
			if (event.getType() == LineEvent.Type.STOP) {
				player.setRunning(false);
			}
		});
		pack();

        // Close the program when close the windows
        
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


    }

	protected void setFile() {
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			audioFile = chooser.getSelectedFile();
		}

	}

	public static void main(String[] args) {
		new WavPlayer();
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

	}
}
