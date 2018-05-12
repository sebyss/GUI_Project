package simulator.texture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import simulator.texture.TextureReader;

public class TextureHandler{
	
	private final int NO_TEXTURES = 2;

	private int texture[] = new int[NO_TEXTURES];
	TextureReader.Texture[] tex = new TextureReader.Texture[NO_TEXTURES];

	// GLU object used for mipmapping.
	private GLU glu;
	
	Animator animator;
	
	public TextureHandler(GL gl, GLU glu, String filename1, boolean mipmapped){
		
		// Create a new GLU object.
				glu = GLU.createGLU();

				// Generate a name (id) for the texture.
				// This is called once in init no matter how many textures we want to generate in the texture vector
			        gl.glGenTextures(NO_TEXTURES, texture, 0);

				// Bind (select) the FIRST texture.
				gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);

				// Read the texture from the image.
				try {
					tex[0] = TextureReader.readTexture(filename1);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}

				// Define the filters used when the texture is scaled.
				gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
				gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
				
				gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
				//gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);

				// Construct the texture and use mipmapping in the process.
				this.makeRGBTexture(gl, glu, tex[0], GL.GL_TEXTURE_2D, true);
				
				// Bind (select) the SECOND texture.
				
				

				// Do not forget to enable texturing.
				gl.glEnable(GL.GL_TEXTURE_2D);
		
	}
	
	
	
	
	public void bind(GL gl){
		gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);
		gl.glTexSubImage2D(GL.GL_TEXTURE_2D, 0, 0, 0, tex[0].getWidth(), tex[0].getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, tex[0].getPixels());
	}
	
	public void enable(GL gl){
		gl.glEnable(GL.GL_TEXTURE_2D);
	}
	public void disable(GL gl){
		gl.glDisable(GL.GL_TEXTURE_2D);
	}
	
	public TextureReader.Texture getTexture(GL gl, int a){
		return tex[a];
	}
	
	
	private void makeRGBTexture(GL gl, GLU glu, TextureReader.Texture img, int target, boolean mipmapped) {     
        if (mipmapped) {
		glu.gluBuild2DMipmaps(target, GL.GL_RGB8, img.getWidth(), img.getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
	} else {
		gl.glTexImage2D(target, 0, GL.GL_RGB, img.getWidth(), img.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
	}
}	
	
}
