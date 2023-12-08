package RabbitGame;

import Texture.TextureReader;
import com.sun.opengl.util.j2d.TextRenderer;



import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class RabbitGLEventListener extends Assets  {
    boolean mouseMove=false;
    boolean hitStatus=false;
    boolean dizzyRabbitStatus=false;
    TextRenderer textRenderer = new TextRenderer(new Font("sanaSerif", Font.BOLD, 9)); // 9 --> FONT_SIZE

    int Timer = 30,NumberOfHits =5 , score;

    GLCanvas glc;
    int cordMouseX;
    int cordMouseY;

    int delay, randomHole ;
    int delayAnimationRabbit=0;
    int delayAnimationSmashed=0;
    int CurrentSmashedRabbit;
    int animtionHammerIndex=0;
    CordinateHoles[] EasyLevel = {new CordinateHoles(750, 150), new CordinateHoles(600, 100), new CordinateHoles(450, 150)};

    int responseOption = 0;
    int maxWidth = 1500, maxHeight = 900, level; // cooredinates of ortho
    int animationIndex=0;
    int animationIndexDizzyRabbit=0;
    String currentScreen = "Game";
    String[] textureNames = {"Diffuclty.png","Pause.png","Level.png","ssLevel.png","llLevel.png","Display.png"};
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int []textures = new int[textureNames.length];


    String[] textureNamesRabbit = {"Rabbits(1).png","Rabbits(2).png","Rabbits(3).png","Rabbits(4).png"};
    TextureReader.Texture[] textureRabbit = new TextureReader.Texture[textureNamesRabbit.length];
    int[] texturesRabbit = new int[textureNamesRabbit.length];

String[] textureNamesDizzyRabbit = {"DizzyRabbit(1).png","DizzyRabbit(2).png","DizzyRabbit(3).png"};
    TextureReader.Texture[] textureDizzyRabbit = new TextureReader.Texture[textureNamesDizzyRabbit.length];
    int[] texturesDizzyRabbit = new int[textureNamesDizzyRabbit.length];

    String[] textureNamesHammer = {"Hammer(2).png","Hammer(3).png","Hammer(4).png","Dizzy(1).png","Dizzy(2).png","Dizzy(3).png"};
    TextureReader.Texture[] textureHammer = new TextureReader.Texture[textureNamesHammer.length];
    int[] texturesHammer = new int[textureNamesHammer.length];

    public void init(GLAutoDrawable gld) {

       GL  gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        //Map images to Memory
        StoreImages(gl,textureNames,texture,textures,assetsFolderName);
        StoreImages(gl,textureNamesRabbit,textureRabbit,texturesRabbit,assetsFolderRabbit);
        StoreImages(gl,textureNamesDizzyRabbit,textureDizzyRabbit,texturesDizzyRabbit,assetsFolderRabbit);
        StoreImages(gl,textureNamesHammer,textureHammer,texturesHammer,assetsFolderHammer);

//        String filepath ="Sound/Run-Amok(chosic.com).wav";
//        PlayMusic(filepath);
//        // JOptionPane.showMessageDialog(null,"press button to stop playing");
    }



    @Override
    public void display(GLAutoDrawable glad) {

        GL  gl = glad.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();
        switchBetweenScreens(gl);
    }


    void drawGame(GL gl) {


        DrawRabbitInHole(gl,EasyLevel[0].x,EasyLevel[0].y,0,10); //out
        DrawRabbitInHole(gl,EasyLevel[1].x,EasyLevel[1].y,0,10); //out
        DrawRabbitInHole(gl,EasyLevel[2].x,EasyLevel[2].y,0,10); //out

        delay+=1;

        if (delay > 5) {
            randomHole = (int) (Math.random() * 3);
            delay = 0;
            if(Timer>0){
                Timer--;
            }
         /*   else{
                // if you lose
                responseOption = JOptionPane.showConfirmDialog(this, "Finshed Time you lose press yes to repeat the game?",
                        "Exit", JOptionPane.YES_NO_OPTION);
                if(responseOption==JOptionPane.YES_OPTION){
                    currentScreen = "Game";
                    Timer = 10;
                }
                else{
        currentScreen = "Home";

                }
            }*/

        }



        if(hitStatus){
            dizzyRabbitStatus=true;
            animationIndexDizzyRabbit++;
            animationIndexDizzyRabbit=animationIndexDizzyRabbit%3;



            DrawDizzyRabbit(gl, EasyLevel[CurrentSmashedRabbit].x, EasyLevel[CurrentSmashedRabbit].y, animationIndexDizzyRabbit, 10);


            delayAnimationRabbit+=1;

            if(delayAnimationRabbit>5) {
                delayAnimationRabbit=0;
                hitStatus = false;
                dizzyRabbitStatus=false;
            }

        }else {
            animationIndex=3;
            DrawRabbitInHole(gl, EasyLevel[randomHole].x, EasyLevel[randomHole].y, animationIndex, 10);
        }
        textRenderer.beginRendering(150, 100);
        textRenderer.setColor(Color.white);
        textRenderer.draw("score: " + score, 55, 90);

        textRenderer.setColor(Color.red);
        textRenderer.draw("you must hit : " + NumberOfHits+" to win", 30, 80);
        textRenderer.setColor(Color.WHITE);

        textRenderer.setColor(Color.red);
        textRenderer.draw("Timer: " + Timer, 5, 5);
        textRenderer.setColor(Color.WHITE);
        textRenderer.endRendering();


    }

    //abdelfattah:Edit switch
    // Wafdy:this method for moving from screen to anthor screen (Navigator)
    public void switchBetweenScreens(GL gl) {
        //abdelfattah
        //draw page
        switch (currentScreen) {
            case "Home": {
                DrawParentBackground(gl,textures.length-1);
                break;
            }
            case "Play":
                DrawParentBackground(gl,0);
                break;
            case "Level":
                DrawParentBackground(gl,2);
                break;

            case "Game":
                if (level < 4) { //easy


                    DrawParentBackground(gl,3);
                    drawGame(gl);
                    DrawHammer(gl,cordMouseX+60,cordMouseY,animtionHammerIndex,9);

/*// if you win
if(score == NumberOfHits){
    responseOption = JOptionPane.showConfirmDialog(this, "you Win please enter yes to next level?",
            "Exit", JOptionPane.YES_NO_OPTION);
    if(responseOption==JOptionPane.YES_OPTION){
        currentScreen = "Game";
        score = 0;
        Timer +=5;
        NumberOfHits++;
    }
}*/


                } else if (level < 7) {
                    DrawParentBackground(gl,3);
                } else  {
                    DrawParentBackground(gl,4);
                }
                break;

            case "Credits":
                DrawParentBackground(gl,4);
                break;
            case "How to play":
                DrawParentBackground(gl,4);

                break;
            case "Pause":
                DrawParentBackground(gl,1);

                break;

        }

    }


    @Override
    public void mouseMoved(MouseEvent e) {

        double frameX = e.getX();
        double frameY = e.getY();

        Component c = e.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();

        cordMouseX = (int) ((frameX / width) * 1500); // hebat el canves
        cordMouseY = (int) ((frameY / height) * 900); //hesbat el canves
        cordMouseY = 900 - cordMouseY;
        if(mouseMove) {
        System.err.println("(cordMouseX , cordMouseY) = " + "(" + cordMouseX + "," + cordMouseY + ")");
    }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //abdelfattah
        //action to navigate
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
                            "Exit", JOptionPane.YES_NO_OPTION);
                    if (responseOption == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
                break;
            //Difficulty page
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
            //////////////////////////////////////////////////////////////////////////
            //////////////////////////////////////////////
            case "Game":
                if (e.getX() > 30 && e.getX() < 130 && e.getY() > 20 && e.getY() < 120) {
                    currentScreen = "Pause";
                }
                else{

                    // x+12 => x+140
                    //  y+90  => y+155

                    //Rabbits Coordinates


                    //Checkkk if anime
                    if(cordMouseX>=EasyLevel[randomHole].x+12&&cordMouseX<=EasyLevel[randomHole].x+140&&cordMouseY>=EasyLevel[randomHole].y+90&&cordMouseY<=EasyLevel[randomHole].y+155){
                        CurrentSmashedRabbit=randomHole;
                        hitStatus=true;
                       if(!dizzyRabbitStatus)
                            score++;


                    }


                    animtionHammerIndex =1;


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
                            "Exit", JOptionPane.YES_NO_OPTION);
                    if (responseOption == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
                break;

        }
//glc.repaint();

    }
    @Override
    public void mouseReleased(MouseEvent e) {

        animtionHammerIndex =0;
    }


    public void DrawParentBackground(GL gl,int index) {
        gl.glEnable(GL.GL_BLEND);	// Turn Blending On
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);

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





    public void DrawRabbitInHole(GL gl, int x, int y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, texturesRabbit[index]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        //  gl.glRotated(180, 0, 0, 1);
        //System.out.println(x +" " + y);
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



    public void DrawDizzyRabbit(GL gl, int x, int y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, texturesDizzyRabbit[index]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        //  gl.glRotated(180, 0, 0, 1);
        //System.out.println(x +" " + y);
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

    public void DrawHammer(GL gl, int x, int y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, texturesHammer[index]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        //  gl.glRotated(180, 0, 0, 1);
        //System.out.println(x +" " + y);
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
    public static void StoreImages(GL gl, String[] textureNames, TextureReader.Texture[] texture, int[] textures, String FolderPath) {
        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        // gl.glGenTextures(textureNames.length, textures, 0);
        gl.glGenTextures(textureNames.length, textures, 0);
        try {

            for (int i = 0; i < textureNames.length; i++) {

                texture[i] = TextureReader.readTexture(FolderPath + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            }
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }

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


