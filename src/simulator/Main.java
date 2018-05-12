package simulator;

import javax.swing.*;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

import simulator.camera.Camera;

import java.awt.*;

public class Main {
	public static void main(String[] args) {
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);

		Camera camera = new Camera(800, 800, capabilities);
		// the window frame
		JFrame frame = new JFrame("Solar system");
		frame.getContentPane().add(camera, BorderLayout.CENTER);

		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		camera.requestFocus();
	}
}
