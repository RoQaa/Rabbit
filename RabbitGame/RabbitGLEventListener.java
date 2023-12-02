package RabbitGame;

import RabbitGame.AnimListener;
import Texture.TextureReader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.*;

public class RabbitGLEventListener extends Component  implements  GLEventListener, MouseMotionListener,MouseListener,KeyListener {
    GL gl;
    GLCanvas glc;
    protected String assetsFolderName = "Images";

    double scaleButton = 1.2;

    int responseOption = 0;

    int maxWidth =1500,maxHeight = 900; // cooredinates of ortho

    String currentScreen = "Home";
    String textureNames[] = {"Play.png","credits.png","credits.png","credits.png","quit.png","title.png","instructions.png","Menu.png"};
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int textureIndex[] = new int[textureNames.length];


    public void init(GLAutoDrawable gld) {

        gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);


        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        //number of textures,array to hold the indeces
        gl.glGenTextures(textureNames.length, textureIndex, 0);


        for(int i = 0; i < textureNames.length; i++){
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i] , true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[i]);

                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch( IOException e ) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }



    @Override
    public void display(GLAutoDrawable glad) {

        gl = glad.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();
        switchBetweenScreens();


    }

    // Wafdy:this method for moving from screen to anthor screen (Navigator)
   public void switchBetweenScreens(){
        if(currentScreen=="Home") {
            DrawParentBackground(textureIndex.length - 1);
            DrawChildBackground(500,450,0.5,5);
            int marginBetweenbutton = 650;
            for (int i = 0; i < 5; i++) {
                DrawChildBackground(200, marginBetweenbutton, 1.2, i);
                marginBetweenbutton -= 120;
            }
        }else if(currentScreen=="Credits"){
            DrawParentBackground(textureIndex.length-1);


        }
   else if(currentScreen=="How to play"){

            DrawParentBackground(textureIndex.length-1);

            DrawChildBackground(10,540,0.5,6);

    }

    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glad, boolean bln, boolean bln1) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        System.out.println(e.getX()+"  "+e.getY());




    }

    @Override
    public void mouseClicked(MouseEvent e) {

        }



    @Override
    public void mousePressed(MouseEvent e) {
        int minX = 45, maxX = 310;
        int minY = 105, maxY = 185, Height = maxY - minY;
        for (int i = 0; i < 5; i++) {


            if (e.getX() > minX && e.getX() < maxX && e.getY() > minY && e.getY() < maxY) {
                System.out.println("i = " + i);
                scaleButton = 1;
                if(i==4){
    responseOption = JOptionPane.showConfirmDialog(this, "Are you sure to exit?",
            "exit", JOptionPane.ERROR_MESSAGE);
    if (responseOption == JOptionPane.YES_OPTION) {
        System.exit(0);
                }
    }  else if(i==1){

        currentScreen = "Credits";
        System.out.println(currentScreen);
    }
                else if(i==2){

                    currentScreen = "How to play";
                    System.out.println(currentScreen);
                }

                glc.repaint();

            }

            minY += Height + 55;
            maxY += Height + 55;
        }

    }
    @Override
    public void mouseReleased(MouseEvent e) {


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }



    public void DrawParentBackground(int index){
        gl.glEnable(GL.GL_BLEND);	// Turn Blending On
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[index]);

        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();

        gl.glDisable(GL.GL_BLEND);
    }

    //created by wafdy
    // this method for draw any background
    //your role is to call this method in the display if you want to draw any background
    public void DrawChildBackground(int x ,int y,double scale,int index){
        gl.glEnable(GL.GL_BLEND);	// Turn Blending On
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[index]);
        gl.glPushMatrix();
        gl.glScaled(scale,scale,1);
        gl.glTranslated(x/(maxWidth/2.0)-0.9,y/(maxHeight/2.0)-0.9,1);

        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void setGLCanvas(GLCanvas glc) {
        this.glc = glc;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
