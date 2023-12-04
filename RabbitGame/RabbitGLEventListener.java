package RabbitGame;

import RabbitGame.AnimListener;
import Texture.TextureReader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class RabbitGLEventListener extends Component  implements  GLEventListener, MouseMotionListener,MouseListener,KeyListener {
    GL gl;
    GLCanvas glc;
    protected String assetsFolderName = "Images";

    double scaleButton = 1.2;

    int responseOption = 0;

      int maxWidth = 1500, maxHeight = 900, level; // cooredinates of ortho


    String currentScreen = "Home";
    String textureNames[] = {"background.png","Diffuclty.png","Pause.png","credits.png","quit.png","title.png","Level.png","ffLevel.png","ssLevel.png","llLevel.png","Menu.png","Display.png"};
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int textureIndex[] = new int[textureNames.length];


    public void init(GLAutoDrawable gld) {

        gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);


        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        //number of textures,array to hold the indeces
        gl.glGenTextures(textureNames.length, textureIndex, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[i]);

                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }

        }

        //Farouk:For playing music after loading game
//        String filepath ="Sound/Run-Amok(chosic.com).wav";
//        SwingUtilities.invokeLater(() -> PlayMusic(filepath));
//         JOptionPane.showMessageDialog(null,"press button to stop playing");
    }



    @Override
    public void display(GLAutoDrawable glad) {

        gl = glad.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();
        switchBetweenScreens();

    }

    // Wafdy:this method for moving from screen to anthor screen (Navigator)
    public void switchBetweenScreens() {
        //abdelfattah
        //draw page
        switch (currentScreen) {
            case "Home": {
                DrawParentBackground(11);
                break;
            }
            case "Play":
                DrawParentBackground(1);
                break;
            case "Level":
                DrawParentBackground(6);
                break;

            case "Game":
                if (level < 4) { //easy
                    DrawParentBackground(8);
                } else if (level < 7) {
                    DrawParentBackground(8);
                } else  {
                    DrawParentBackground(9);
                }
                break;

            case "Credits":
                DrawParentBackground(10);
                break;
            case "How to play":
                DrawParentBackground(10);

                break;
            case "Pause":
                DrawParentBackground(2);

                break;

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

        System.out.println(e.getX() + "  " + e.getY());




    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //abdelfattah
      //action to nvegite
        switch (currentScreen) {
            //Home page
            case "Home":
                if (e.getX() > 60 && e.getX() < 375 && e.getY() > 125 && e.getY() < 195) {
                    currentScreen = "Play";
                }
                if (e.getX() > 60 && e.getX() < 375 && e.getY() > 220 && e.getY() < 290) {
                    currentScreen = "Credits";
                }
                if (e.getX() > 60 && e.getX() < 375 && e.getY() > 310 && e.getY() < 380) {
                    currentScreen = "How to play";
                }
                if (e.getX() > 60 && e.getX() < 375 && e.getY() > 400 && e.getY() < 470) {
                    responseOption = JOptionPane.showConfirmDialog(this, "Are you sure to exit?",
                            "Exit", JOptionPane.ERROR_MESSAGE);
                    if (responseOption == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
                break;
//Diffuclty page
            case "Play":
                //back button
                if (e.getX() > 474 && e.getX() < 560 && e.getY() > 710 && e.getY() < 790) {
                    currentScreen = "Home";
                }
                if (e.getX() > 590 && e.getX() < 915 && e.getY() > 230 && e.getY() < 350) {
                    currentScreen = "Level";

                }
                if (e.getX() > 590 && e.getX() < 915 && e.getY() > 405 && e.getY() < 520) {
                currentScreen = "Level";

                }
                if (e.getX() > 590 && e.getX() < 915 && e.getY() > 600 && e.getY() < 703) {
                    currentScreen = "Level";

                }

                break;
                //Level Selection
            case "Level":
                //back button
                if (e.getX() > 25 && e.getX() < 150 && e.getY() > 15 && e.getY() < 95) {
                    currentScreen = "Play";
                }
                if (e.getX() > 440 && e.getX() < 630 && e.getY() > 280 && e.getY() < 390) {
                    currentScreen = "Game";
                    level = 1;
                }
                if (e.getX() > 760 && e.getX() < 925 && e.getY() > 280 && e.getY() < 390) {
                    currentScreen = "Game";
                    level = 2;
                }
                if (e.getX() > 1025 && e.getX() < 1180 && e.getY() > 280 && e.getY() < 390) {
                    currentScreen = "Game";
                    level = 3;
                }
                if (e.getX() > 470 && e.getX() < 615 && e.getY() > 440 && e.getY() < 540) {
                    currentScreen = "Game";
                    level = 4;
                }
                if (e.getX() > 750 && e.getX() < 935 && e.getY() > 430 && e.getY() < 530) {
                    currentScreen = "Game";
                    level = 5;
                }
                if (e.getX() > 1015 && e.getX() < 1200 && e.getY() > 425 && e.getY() < 525) {
                    currentScreen = "Game";
                    level = 6;
                }
                if (e.getX() > 450 && e.getX() < 630 && e.getY() > 590 && e.getY() < 690) {
                    currentScreen = "Game";
                    level = 7;
                }
                if (e.getX() > 735 && e.getX() < 910 && e.getY() > 590 && e.getY() < 690) {
                    currentScreen = "Game";
                    level = 8;
                }
                if (e.getX() > 1020 && e.getX() < 1120 && e.getY() > 590 && e.getY() < 690) {
                    currentScreen = "Game";
                    level = 9;
                }
                break;
                //Game page
            case "Game":
                if (e.getX() > 30 && e.getX() < 130 && e.getY() > 20 && e.getY() < 120) {
                    currentScreen = "Pause";
                }
                break;
                //Pause page
            case "Pause":
//                if (e.getX() > 30 && e.getX() < 130 && e.getY() > 20 && e.getY() < 120) {
//                    currentScreen = "Pause";
//                }
                if (e.getX() > 595 && e.getX() < 790 && e.getY() > 485 && e.getY() < 555) {
                    currentScreen = "Home";
                }
                if (e.getX() > 595 && e.getX() < 790 && e.getY() > 590 && e.getY() < 660) {
                    responseOption = JOptionPane.showConfirmDialog(this, "Are you sure to exit?",
                            "Exit", JOptionPane.ERROR_MESSAGE);
                    if (responseOption == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
                break;

        }
glc.repaint();
        //game(1); --> 5 hit 20s
        //game(2); ---> 5 hits 18s
        //game(3); ---> 5 hits 16s
        // game(4) --> 7
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        int minX = 45, maxX = 310;
//        int minY = 105, maxY = 185, Height = maxY - minY;
//        for (int i = 0; i < 5; i++) {
//
//            if (e.getX() > minX && e.getX() < maxX && e.getY() > minY && e.getY() < maxY) {
//                System.out.println("i = " + i);
//                scaleButton = 1;
//                if (i == 4) {
//                    responseOption = JOptionPane.showConfirmDialog(this, "Are you sure to exit?",
//                            "exit", JOptionPane.ERROR_MESSAGE);
//                    if (responseOption == JOptionPane.YES_OPTION) {
//                        System.exit(0);
//                    }
//                } else if (i == 1) {
//
//                    currentScreen = "Credits";
//                    System.out.println(currentScreen);
//                } else if (i == 2) {
//
//                    currentScreen = "How to play";
//                    System.out.println(currentScreen);
//                } else if (i == 3) {
//                    currentScreen = "Diffculty";
//                    minY = 105;
//                    maxY = 185;
//                    for (int j = 0; j < 3; j++) {
//                        if (e.getX() > minX && e.getX() < maxX && e.getY() > minY && e.getY() < maxY) {
//
////                        System.out.println(currentScreen);
//                            if (j == 0) {
//
//                                currentScreen = "Easy";
//                                System.out.println(currentScreen);
//                            } else if (j == 1) {
//
//                                currentScreen = "Normal";
//                                System.out.println(currentScreen);
//
//                            } else if (j == 2) {
//
//                                currentScreen = "Hard";
//                                System.out.println(currentScreen);
//
//                            }
//                        }
//                        minY += Height + 55;
//                        maxY += Height + 55;
//                    }
//
//                }
//
//            }
//
//            minY += Height + 55;
//            maxY += Height + 55;
//        }

    }

//                glc.repaint();

//            }
//
//            minY += Height + 55;
//            maxY += Height + 55;
//        }

//    }
    @Override
    public void mouseReleased(MouseEvent e) {


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void DrawParentBackground(int index) {
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
    public void DrawChildBackground(int x, int y, double scale, int index) {
        gl.glEnable(GL.GL_BLEND);	// Turn Blending On
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[index]);
        gl.glPushMatrix();
        gl.glScaled(scale, scale, 1);
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 1);

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
    public static void PlayMusic(String location) {
        try
        {
            File musicPath = new File(location);
            if(musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip =AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
            else
            {
                System.out.println("Can't find file");

            }
        }
        catch (Exception e){
            System.out.println(e);

        }
    }

}
