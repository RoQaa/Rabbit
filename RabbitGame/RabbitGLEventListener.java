package RabbitGame;

import Texture.TextureReader;
import com.sun.opengl.util.j2d.TextRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class RabbitGLEventListener extends Assets {

    boolean mouseMove, hitStatus, dizzyRabbitStatus, isPause, SoundOn = true, soundButton = true;
    int score, cordMouseX, cordMouseY, delay, randomHole, level, animationIndex = 0, animationIndexDizzyRabbit = 0, indexImageSound = 5, mode, CurrentSmashedRabbit, Timer = 45, NumberOfHits = 1, delayAnimationRabbit = 0, animtionHammerIndex = 0, responseOption = 0, lives = 3;
    String currentScreen = "Home";
    Clip clip, clip2;

    CordinateHoles[] DifficultyMode;
    TextRenderer textRenderer = new TextRenderer(new Font("sanaSerif", Font.BOLD, 10)); // 10 --> FONT_SIZE

    CordinateHoles[] EasyLevel = {new CordinateHoles(750, 150), new CordinateHoles(600, 100), new CordinateHoles(450, 150)};
    CordinateHoles[] MediumLevel = {new CordinateHoles(700, 200), new CordinateHoles(450, 200), new CordinateHoles(700, 100), new CordinateHoles(450, 100), new CordinateHoles(950, 200), new CordinateHoles(950, 100)};
    CordinateHoles[] HardLevel = {new CordinateHoles(700, 200), new CordinateHoles(450, 200), new CordinateHoles(700, 100),
            new CordinateHoles(450, 100), new CordinateHoles(950, 200), new CordinateHoles(950, 100),
            new CordinateHoles(300, 150), new CordinateHoles(1100, 150)};

    static String[] textureNames = {"Diffuclty.png", "Pause.png", "Level.png", "ssLevel.png", "llLevel.png", "soundOff.png", "soundOn.png", "win.png", "mm.png", "tt.png", "1.png", "2.png", "3.png", "4.png", "back.png", "backg.png"};
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    static int[] textures = new int[textureNames.length];

    static String[] textureNamesRabbit = {"Rabbits(1).png", "Rabbits(2).png", "Rabbits(3).png", "Rabbits(4).png"};
    TextureReader.Texture[] textureRabbit = new TextureReader.Texture[textureNamesRabbit.length];
    static int[] texturesRabbit = new int[textureNamesRabbit.length];

    static String[] textureNamesDizzyRabbit = {"DizzyRabbit(1).png", "DizzyRabbit(2).png", "DizzyRabbit(3).png"};
    TextureReader.Texture[] textureDizzyRabbit = new TextureReader.Texture[textureNamesDizzyRabbit.length];
    static int[] texturesDizzyRabbit = new int[textureNamesDizzyRabbit.length];

    static String[] textureNamesHammer = {"Hammer(2).png", "Hammer(3).png", "Hammer(4).png", "Dizzy(1).png", "Dizzy(2).png", "Dizzy(3).png"};
    TextureReader.Texture[] textureHammer = new TextureReader.Texture[textureNamesHammer.length];
    static int[] texturesHammer = new int[textureNamesHammer.length];

    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        ImagesMethods.StoreImages(gl, textureNames, texture, textures, assetsFolderName);
        ImagesMethods.StoreImages(gl, textureNamesRabbit, textureRabbit, texturesRabbit, assetsFolderRabbit);
        ImagesMethods.StoreImages(gl, textureNamesDizzyRabbit, textureDizzyRabbit, texturesDizzyRabbit, assetsFolderRabbit);
        ImagesMethods.StoreImages(gl, textureNamesHammer, textureHammer, texturesHammer, assetsFolderHammer);

        String filepath = "Sound/Run-Amok(chosic.com).wav";
        PlayMusic(filepath);

    }

    @Override
    public void display(GLAutoDrawable glad) {
        GL gl = glad.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        switchBetweenScreens(gl);

    }

    void drawGame(GL gl, int delaySpeed) {
        for (int i = 0; i < DifficultyMode.length; i++) {
            ImagesMethods.DrawRabbitInHole(gl, DifficultyMode[i].x, DifficultyMode[i].y, 0, 10); //out
        }
        if (randomHole > DifficultyMode.length) {
            randomHole = (int) (Math.random() * DifficultyMode.length);
        }
        delay++;
        if (delay > delaySpeed) {
            randomHole = (int) (Math.random() * DifficultyMode.length);
            delay = 0;
            if (Timer > 0) {
                Timer--;
            }
        }
        if (hitStatus) {
            dizzyRabbitStatus = true;
            animationIndexDizzyRabbit++;
            animationIndexDizzyRabbit = animationIndexDizzyRabbit % 3;
            delayAnimationRabbit++;

            if (delayAnimationRabbit < delaySpeed * 0.25) {
                ImagesMethods.DrawDizzyRabbit(gl, DifficultyMode[CurrentSmashedRabbit].x, DifficultyMode[CurrentSmashedRabbit].y, animationIndexDizzyRabbit, 10);
            }
            if (delayAnimationRabbit >= delaySpeed * 0.25) {
                ImagesMethods.DrawRabbitInHole(gl, DifficultyMode[randomHole].x, DifficultyMode[randomHole].y, 0, 10);
            }
            if (delayAnimationRabbit > delaySpeed) {
                delayAnimationRabbit = 0;
                hitStatus = false;
                dizzyRabbitStatus = false;
            }
        } else {
            animationIndex = 3;
            ImagesMethods.DrawRabbitInHole(gl, DifficultyMode[randomHole].x, DifficultyMode[randomHole].y, animationIndex, 10);
        }
        textRenderer.beginRendering(300, 300);
        textRenderer.setColor(Color.red);
        textRenderer.draw("score: " + score, 130, 280);
        textRenderer.setColor(Color.red);
        textRenderer.draw("Lives: " + lives, 240, 280);
        textRenderer.setColor(Color.red);
        textRenderer.draw("you must hit " + NumberOfHits + " to win", 190, 10);
        textRenderer.setColor(Color.WHITE);
        textRenderer.setColor(Color.red);
        textRenderer.draw("Remaining Time: " + Timer, 5, 10);
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
                ImagesMethods.DrawParentBackground(gl, textures.length - 1);
                ImagesMethods.DrawChild(gl, 1330, 770, indexImageSound, 1.5f); //sound image
                break;
            }
            case "Play":
                ImagesMethods.DrawParentBackground(gl, 0);
                break;
            case "Easy":
            case "Medium":
            case "Hard":
                ImagesMethods.DrawParentBackground(gl, 2);
                break;

            case "Game":
                NumberOfHits = level - mode + 2;
                if (score == NumberOfHits) {
                    currentScreen = level == mode+9 ? "Complete Mode" : "win";

                } else if (lives == 0 || Timer == 0) {
                    currentScreen = "lose";
                } else {
                    DifficultyMode = mode == 0 ? EasyLevel : mode == 9 ? MediumLevel : HardLevel;
                    int delay = mode == 0 ? 40 : mode == 9 ? 25 : 15;
                    ImagesMethods.DrawParentBackground(gl, 3);
                    drawGame(gl, delay);
                    ImagesMethods.DrawHammer(gl, cordMouseX + 60, cordMouseY, animtionHammerIndex, 9);
                }
                break;

            case "Credits":
                ImagesMethods.DrawParentBackground(gl, 10);
                ImagesMethods.DrawChild(gl, 25, 800, 14, 0.8f);
                break;
            case "How to play":
                ImagesMethods.DrawParentBackground(gl, 13);

                break;
            case "Pause":
                ImagesMethods.DrawParentBackground(gl, 1);

                break;
            case "win":
                ImagesMethods.DrawParentBackground(gl, 11);
                break;
            case "Complete Mode":
                ImagesMethods.DrawParentBackground(gl, 9);
                break;
            case "lose":
                ImagesMethods.DrawParentBackground(gl, 12);
                break;

        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        double frameX = e.getX();
        double frameY = e.getY();
//        System.out.println(e.getX() + " " + e.getY());
        Component c = e.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();

        cordMouseX = (int) ((frameX / width) * 1500); // hebat el canves
        cordMouseY = (int) ((frameY / height) * 900); //hesbat el canves
        cordMouseY = 900 - cordMouseY;
        System.err.println(  "(" + cordMouseX + "," + cordMouseY + ")");

        if (mouseMove) {
            System.err.println("(cordMouseX , cordMouseY) = " + "(" + cordMouseX + "," + cordMouseY + ")");

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //abdelfattah
        //action to navigate
        switch (currentScreen) {
            //Home page
            case "Home":
                // sound
                if (cordMouseX> 1320 && cordMouseX < 1480 &&cordMouseY > 760 && cordMouseY < 870) {
                    SoundOn = !SoundOn;
                    if (SoundOn) {
                        indexImageSound = 5;
                        clip.start();
                    } else {
                        indexImageSound = 6;
                        clip.stop();
                    }
                }

                if (cordMouseX> 75 && cordMouseX < 385 &&cordMouseY > 745 && cordMouseY < 865) {
                    currentScreen = "Play";
                    soundObject("Sound/mouse-click-153941.wav");
                }
                if (cordMouseX> 75 && cordMouseX < 385 &&cordMouseY > 568 && cordMouseY < 690) {
                    currentScreen = "Score";
                    soundObject("Sound/mouse-click-153941.wav");
                }
                if (cordMouseX> 75 && cordMouseX < 385 &&cordMouseY > 400 && cordMouseY < 518) {
                    currentScreen = "Credits";
                    soundObject("Sound/mouse-click-153941.wav");
                }
                if (cordMouseX> 75 && cordMouseX < 385 &&cordMouseY > 220 && cordMouseY < 340) {
                    currentScreen = "How to play";
                    soundObject("Sound/mouse-click-153941.wav");
                }
                if (cordMouseX> 75 && cordMouseX < 385 &&cordMouseY > 50 && cordMouseY < 150) {
                    soundObject("Sound/mouse-click-153941.wav");
                    responseOption = JOptionPane.showConfirmDialog(this, "Are you sure to exit?",
                            "Exit", JOptionPane.YES_NO_OPTION);
                    if (responseOption == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
                break;
            case "Credits":
                //back button
                if (cordMouseX> 45 && cordMouseX < 150 &&cordMouseY > 820 && cordMouseY < 880) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Home";
                }

                break;
            case "How to play":
                //back button
                if (cordMouseX > 45 && cordMouseX < 130 && cordMouseY > 810 && cordMouseY < 885) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Home";
                }

                break;
            //Difficulty page
            case "Play":
                //back button
                if (cordMouseX> 480 && cordMouseX < 565 &&cordMouseY > 75 && cordMouseY < 155) {
                    soundObject("Sound/mouse-click-153941.wav");

                    currentScreen = "Home";
                }

                if (cordMouseX> 590 && cordMouseX < 925 &&cordMouseY > 540 && cordMouseY < 660) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Easy";
                    mode = 0;
                }
                if (cordMouseX> 590 && cordMouseX < 925 &&cordMouseY > 360 && cordMouseY < 480) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Medium";
                    mode = 9;
                }
                if (cordMouseX> 590 && cordMouseX < 925 &&cordMouseY > 160 && cordMouseY < 280) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Hard";
                    mode = 18;

                }
                break;
            //Level Selection
            case "Easy":
            case "Medium":
            case "Hard":
                //back button
                if (cordMouseX> 25 && cordMouseX < 150 &&cordMouseY > 800 && cordMouseY < 885) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Play";
                }
                if (cordMouseX> 460 && cordMouseX < 630 &&cordMouseY > 500 && cordMouseY < 600) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Game";
                    level = 1 + mode;
                }
                if (cordMouseX> 770 && cordMouseX < 935 &&cordMouseY > 500 && cordMouseY < 600) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Game";
                    level = 2 + mode;
                }
                if (cordMouseX> 1035 && cordMouseX < 1205 &&cordMouseY > 500 && cordMouseY < 600) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Game";
                    level = 3 + mode;
                }
                if (cordMouseX> 460 && cordMouseX < 630 &&cordMouseY > 335 && cordMouseY < 435) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Game";
                    level = 4 + mode;
                }
                if (cordMouseX> 770 && cordMouseX < 935 &&cordMouseY > 345 && cordMouseY < 445) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Game";
                    level = 5 + mode;
                }
                if (cordMouseX> 1035 && cordMouseX < 1205 &&cordMouseY > 350 && cordMouseY < 450) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Game";
                    level = 6 + mode;
                }
                if (cordMouseX> 460 && cordMouseX < 630 &&cordMouseY > 180 && cordMouseY < 280) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Game";
                    level = 7 + mode;
                }
                if (cordMouseX> 770 && cordMouseX < 935 &&cordMouseY > 180 && cordMouseY < 280) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Game";
                    level = 8 + mode;
                }
                if (cordMouseX> 1035 && cordMouseX < 1205 &&cordMouseY > 180 && cordMouseY < 280) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Game";
                    level = 9 + mode;

                }
                break;
            case "Complete Mode":
                if (cordMouseX> 840 && cordMouseX < 1371 &&cordMouseY > 66 && cordMouseY < 130) {
                    soundObject("Sound/mouse-click-153941.wav");
                    score = 0;
                    Timer = 45;
                    lives = 3;
                    level = 0;
                    currentScreen = "Play";
                }
                // back button
                if (cordMouseX> 125 && cordMouseX < 650 &&cordMouseY > 66 && cordMouseY < 130) {
                    soundObject("Sound/mouse-click-153941.wav");
                    score = 0;
                    Timer = 45;
                    lives = 3;
                    level = 0;
                    currentScreen = "Home";

                }

                break;
            case "win":
                score = 0;
                Timer = 45;
                lives = 3;
                if (cordMouseX> 845 && cordMouseX < 1370 &&cordMouseY > 68 && cordMouseY < 128) {
                    soundObject("Sound/mouse-click-153941.wav");
                    level++;
                    currentScreen = "Game";

                }
                // back button
                if (cordMouseX> 120 && cordMouseX < 645 &&cordMouseY > 69 && cordMouseY < 128) {
                    soundObject("Sound/mouse-click-153941.wav");
                    level = 0;
                    currentScreen = "Home";

                }

                break;
            case "lose":
                score = 0;
                Timer = 45;
                lives = 3;
                if (cordMouseX> 845 && cordMouseX < 1371 &&cordMouseY > 67 && cordMouseY < 131) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Game";
                }
                // back to menu
                if (cordMouseX> 122 && cordMouseX < 653 &&cordMouseY > 67 && cordMouseY < 131) {
                    soundObject("Sound/mouse-click-153941.wav");
                    level = 0;
                    currentScreen = "Home";
                }

                break;
            case "Game":
                if (cordMouseX> 15 && cordMouseX < 115 &&cordMouseY > 780 && cordMouseY < 880) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Pause";
                } else {

                    // x+12 => x+140
                    //  y+90  => y+155
                    //Rabbits Coordinates
                    //Checkkk if anime
                    if (cordMouseX >= DifficultyMode[randomHole].x + 12 && cordMouseX <= DifficultyMode[randomHole].x + 140 && cordMouseY >= DifficultyMode[randomHole].y + 90 && cordMouseY <= DifficultyMode[randomHole].y + 155) {
                        CurrentSmashedRabbit = randomHole;
                        hitStatus = true;
                        if (!dizzyRabbitStatus) {
                            soundObject("Sound/mixkit-cartoon-dazzle-hit-and-birds-746.wav");
                            score++;
                        }
                    } else {
                        soundObject("Sound/mixkit-metallic-sword-strike-2160 (1).wav");
                        if (lives > 0) {
                            lives--;
                        }
                    }
                    animtionHammerIndex = 1;

                }
                break;
            //Pause page
            case "Pause":
                // Resume button
                if (cordMouseX> 600 && cordMouseX < 800 &&cordMouseY > 540 && cordMouseY < 615) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Game";
                    isPause = true;
                }

                //Repaly button
                if (cordMouseX> 600 && cordMouseX < 800 &&cordMouseY > 430 && cordMouseY < 500) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Game";
                    Timer = 60;
                    score = 0;
                    isPause = true;
                }

                // home button
                if (cordMouseX> 600 && cordMouseX < 800 &&cordMouseY > 320 && cordMouseY < 400) {
                    soundObject("Sound/mouse-click-153941.wav");
                    currentScreen = "Home";
                    isPause = false;
                    Timer = 60;
                    score = 0;
                    mode = 0;
                }
                if (cordMouseX> 600 && cordMouseX < 800 &&cordMouseY > 110 && cordMouseY < 280) {
                    soundObject("Sound/mouse-click-153941.wav");
                    responseOption = JOptionPane.showConfirmDialog(this, "Are you sure to exit?",
                            "Exit", JOptionPane.YES_NO_OPTION);
                    if (responseOption == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
                break;

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        animtionHammerIndex = 0;
    }

    public void PlayMusic(String location) {
        try {
            File musicPath = new File(location);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Can't find file");
            }
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void soundObject(String filepath) {
        if (soundButton) {
            ActionSound(filepath);
            clip2.start();
        }
    }

    public void ActionSound(String location) {
        try {
            File musicPath = new File(location);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            clip2 = AudioSystem.getClip();
            clip2.open(audioInput);
            clip2.start();
        } catch (Exception e) {
            System.out.println(e);

        }
    }

}
