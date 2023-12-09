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

public class RabbitGLEventListener extends Assets  {
    boolean mouseMove=false , hitStatus=false, dizzyRabbitStatus=false , isPause = false , SoundOn = true;
    int score, cordMouseX ,cordMouseY, delay, randomHole,level,animationIndex=0, animationIndexDizzyRabbit=0,indexImageSound = 5,mode
        ,CurrentSmashedRabbit, Timer = 45,NumberOfHits =1 ,delayAnimationRabbit=0, animtionHammerIndex=0, responseOption = 0,lives =3;
    String currentScreen = "Home";
    Clip clip;

    CordinateHoles[]DifficultyMode;
    TextRenderer textRenderer = new TextRenderer(new Font("sanaSerif", Font.BOLD, 10)); // 10 --> FONT_SIZE

    CordinateHoles[] EasyLevel = {new CordinateHoles(750, 150), new CordinateHoles(600, 100), new CordinateHoles(450, 150)};
    CordinateHoles[] MediumLevel = {new CordinateHoles(700, 200), new CordinateHoles(450, 200), new CordinateHoles(700, 100), new CordinateHoles(450, 100),new CordinateHoles(950, 200),new CordinateHoles(950, 100)};
    CordinateHoles[] HardLevel = {new CordinateHoles(700, 200), new CordinateHoles(450, 200), new CordinateHoles(700, 100),
                                new CordinateHoles(450, 100),new CordinateHoles(950, 200),new CordinateHoles(950, 100),
                                new CordinateHoles(300, 150),new CordinateHoles(1100, 150) };



    static String[] textureNames = {"Diffuclty.png","Pause.png","Level.png","ssLevel.png","llLevel.png","soundOff.png","soundOn.png","win.png","mm.png","tt.png","1.png","2.png","3.png","4.png","back.png","backg.png"};
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
   static  int []textures = new int[textureNames.length];


    static String[] textureNamesRabbit = {"Rabbits(1).png","Rabbits(2).png","Rabbits(3).png","Rabbits(4).png"};
    TextureReader.Texture[] textureRabbit = new TextureReader.Texture[textureNamesRabbit.length];
    static  int[] texturesRabbit = new int[textureNamesRabbit.length];

    static String[] textureNamesDizzyRabbit = {"DizzyRabbit(1).png","DizzyRabbit(2).png","DizzyRabbit(3).png"};
    TextureReader.Texture[] textureDizzyRabbit = new TextureReader.Texture[textureNamesDizzyRabbit.length];
    static int[] texturesDizzyRabbit = new int[textureNamesDizzyRabbit.length];

    static String[] textureNamesHammer = {"Hammer(2).png","Hammer(3).png","Hammer(4).png","Dizzy(1).png","Dizzy(2).png","Dizzy(3).png"};
    TextureReader.Texture[] textureHammer = new TextureReader.Texture[textureNamesHammer.length];
    static int[] texturesHammer = new int[textureNamesHammer.length];


    public void init(GLAutoDrawable gld) {
       GL  gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        ImagesMethods.StoreImages(gl,textureNames,texture,textures,assetsFolderName);
        ImagesMethods.StoreImages(gl,textureNamesRabbit,textureRabbit,texturesRabbit,assetsFolderRabbit);
        ImagesMethods.StoreImages(gl,textureNamesDizzyRabbit,textureDizzyRabbit,texturesDizzyRabbit,assetsFolderRabbit);
        ImagesMethods.StoreImages(gl,textureNamesHammer,textureHammer,texturesHammer,assetsFolderHammer);

//        String filepath ="Sound/Run-Amok(chosic.com).wav";
//        PlayMusic(filepath);
    }

    @Override
    public void display(GLAutoDrawable glad) {
        GL  gl = glad.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();
        switchBetweenScreens(gl);
    }
   public  void drawGame(GL gl,int NumOfRandomHole,int delaySpeed,CordinateHoles[]Difficulty) {

    DifficultyMode = Difficulty;


    for (int i = 0; i < DifficultyMode.length; i++) {
        ImagesMethods.DrawRabbitInHole(gl, DifficultyMode[i].x, DifficultyMode[i].y, 0, 10); //out
    }
    delay += 1;

    if (delay > delaySpeed) {
        randomHole = (int) (Math.random() * NumOfRandomHole);

        delay = 0;
        if (Timer > 0) {
            Timer--;
        }

    }


    if (hitStatus) {
        dizzyRabbitStatus = true;
        animationIndexDizzyRabbit++;
        animationIndexDizzyRabbit = animationIndexDizzyRabbit % 3;

        ImagesMethods.DrawDizzyRabbit(gl, DifficultyMode[CurrentSmashedRabbit].x, DifficultyMode[CurrentSmashedRabbit].y, animationIndexDizzyRabbit, 10);

        delayAnimationRabbit += 1;

        if (delayAnimationRabbit > delaySpeed) {
            delayAnimationRabbit = 0;
            hitStatus = false;
            dizzyRabbitStatus = false;
        }

    } else {
        animationIndex = 3;
        //
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
                ImagesMethods.DrawParentBackground(gl,textures.length-1);
                ImagesMethods.DrawChild(gl,1330,770,indexImageSound,1.5f); //sound image
                break;
            }
            case "Play":
                ImagesMethods.DrawParentBackground(gl,0);
                break;
            case "Easy":
            case "Medium":
            case "Hard":


                    ImagesMethods.DrawParentBackground(gl, 2);

                break;

            case "Game":
                if (mode <7) { //easy
                    if(score==NumberOfHits) {

                        currentScreen ="win";
                    }else if(lives==0 || Timer==0){

                        currentScreen = "lose";
                    }

                    else{
                        ImagesMethods.DrawParentBackground(gl,3);
                        drawGame(gl,3,40,EasyLevel);
                        ImagesMethods.DrawHammer(gl,cordMouseX+60,cordMouseY,animtionHammerIndex,9);
                    }



                }



                break;

            case "Credits":
                ImagesMethods.DrawParentBackground(gl,10);
                ImagesMethods.DrawChild(gl,25,800,14,0.8f);
                break;
            case "How to play":
                ImagesMethods.DrawParentBackground(gl,13);

                break;
            case "Pause":
                ImagesMethods.DrawParentBackground(gl,1);

                break;
            case "win":
                ImagesMethods.DrawParentBackground(gl,11);
                break;
            case "lose":
                ImagesMethods.DrawParentBackground(gl,12);
                break;



        }


    }


    @Override
    public void mouseMoved(MouseEvent e) {

        double frameX = e.getX();
        double frameY = e.getY();
        System.out.println(e.getX()+" "+e.getY());
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
    public void mousePressed(MouseEvent e) {
        //abdelfattah
        //action to navigate
        switch (currentScreen) {
            //Home page
            case "Home":
                // sound
                if (e.getX() > 1307 && e.getX() < 1460 && e.getY() > 30 && e.getY() < 130) {
                    SoundOn = !SoundOn;
                    if (SoundOn){
                        indexImageSound = 5;
                        clip.start();
                    }
                    else {
                        indexImageSound = 6;
                        clip.stop();
                    }
                }

                if (e.getX() > 80 && e.getX() < 380 && e.getY() > 35 && e.getY() < 150) {
                    currentScreen = "Play";
                }
                if (e.getX() > 80 && e.getX() < 380 && e.getY() > 310 && e.getY() < 380) {
                    currentScreen = "Score";
                }
                if (e.getX() > 80 && e.getX() < 380 && e.getY() > 370 && e.getY() < 480) {
                    currentScreen = "Credits";
                }
                if (e.getX() > 80 && e.getX() < 380 && e.getY() > 540 && e.getY() < 650) {
                    currentScreen = "How to play";
                }
                if (e.getX() > 80 && e.getX() < 380 && e.getY() > 700 && e.getY() < 810) {
                    responseOption = JOptionPane.showConfirmDialog(this, "Are you sure to exit?",
                            "Exit", JOptionPane.YES_NO_OPTION);
                    if (responseOption == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
                break;
            case "Credits":
                //back button
                if (e.getX() > 45 && e.getX() < 150 && e.getY() > 20 && e.getY() < 80) {
                    currentScreen = "Home";
                }


                break;
            case "How to play":
                //back button
                if (e.getX() > 45 && e.getX() < 130 && e.getY() > 15 && e.getY() < 100) {
                    currentScreen = "Home";
                }


                break;
            //Difficulty page
            case "Play":
                //back button
                if (e.getX() > 474 && e.getX() < 560 && e.getY() > 710 && e.getY() < 790) {
                    currentScreen = "Home";
                }

                if (e.getX() > 590 && e.getX() < 915 && e.getY() > 230 && e.getY() < 350) {
                    currentScreen = "Easy";
                    mode = 0;

                }
                if (e.getX() > 590 && e.getX() < 915 && e.getY() > 405 && e.getY() < 520) {
                    currentScreen = "Medium";
                        mode = 9;
                }
                if (e.getX() > 590 && e.getX() < 915 && e.getY() > 600 && e.getY() < 703) {
                    currentScreen = "Hard";
                    mode = 18;

                }


                break;
            //Level Selection
            case "Easy":
            case "Medium":
            case "Hard":
                //back button
                if (e.getX() > 25 && e.getX() < 150 && e.getY() > 15 && e.getY() < 95) {
                    currentScreen = "Play";
                }
                if (e.getX() > 440 && e.getX() < 630 && e.getY() > 280 && e.getY() < 390) {
                    currentScreen = "Game";
                    level = 1+mode;
                    NumberOfHits = 3;
                }
                if (e.getX() > 760 && e.getX() < 925 && e.getY() > 280 && e.getY() < 390) {
                    currentScreen = "Game";
                    level = 2+mode;
                    NumberOfHits = 4;
                }
                if (e.getX() > 1025 && e.getX() < 1180 && e.getY() > 280 && e.getY() < 390) {
                    currentScreen = "Game";
                    level = 3+mode;
                    NumberOfHits = 5;

                }
                if (e.getX() > 470 && e.getX() < 615 && e.getY() > 440 && e.getY() < 540) {
                    currentScreen = "Game";
                    level = 4+mode;
                    NumberOfHits = 6;

                }
                if (e.getX() > 750 && e.getX() < 935 && e.getY() > 430 && e.getY() < 530) {
                    currentScreen = "Game";
                    level = 5+mode;
                    NumberOfHits = 7;

                }
                if (e.getX() > 1015 && e.getX() < 1200 && e.getY() > 425 && e.getY() < 525) {
                    currentScreen = "Game";
                    level = 6+mode;
                    NumberOfHits = 8;

                }
                if (e.getX() > 450 && e.getX() < 630 && e.getY() > 590 && e.getY() < 690) {
                    currentScreen = "Game";
                    level = 7+mode;
                    NumberOfHits = 9;

                }
                if (e.getX() > 735 && e.getX() < 910 && e.getY() > 590 && e.getY() < 690) {
                    currentScreen = "Game";
                    level = 8+mode;
                    NumberOfHits = 10;

                }
                if (e.getX() > 1020 && e.getX() < 1120 && e.getY() > 590 && e.getY() < 690) {
                    currentScreen = "Game";
                    level = 9+mode;
                    NumberOfHits = 11;

                }
                break;
            case "win":
                if (e.getX() > 831 && e.getX() < 1365 && e.getY() > 735 && e.getY() < 800) {
                    score =0;
                    Timer = 45;
                    lives = 3;
                    NumberOfHits++;
                    currentScreen = "Game";


                }
                // back button
                if (e.getX() > 118 && e.getX() < 652 && e.getY() > 735 && e.getY() < 800) {
                    score =0;
                    Timer = 45;
                    lives = 3;
                    currentScreen = "Home";


                }

                break;
            case "lose":
                if (e.getX() > 831 && e.getX() < 1365 && e.getY() > 735 && e.getY() < 800) {
                    score =0;
                    Timer = 45;
                    lives = 3;
                    currentScreen = "Game";

                }

                // back to menu
                if (e.getX() > 118 && e.getX() < 652 && e.getY() > 735 && e.getY() < 800) {
                    score =0;
                    Timer = 45;
                    lives = 3;
                    currentScreen = "Home";


                }

                break;
            case "Game":
                if (e.getX() > 30 && e.getX() < 130 && e.getY() > 20 && e.getY() < 120) {
                    currentScreen = "Pause";
                }
                else{

                    // x+12 => x+140
                    //  y+90  => y+155

                    //Rabbits Coordinates

                    //Checkkk if anime

                        if (cordMouseX >= DifficultyMode[randomHole].x + 12 && cordMouseX <= DifficultyMode[randomHole].x + 140 && cordMouseY >= DifficultyMode[randomHole].y + 90 && cordMouseY <= DifficultyMode[randomHole].y + 155) {
                            CurrentSmashedRabbit = randomHole;
                            hitStatus = true;
                            if (!dizzyRabbitStatus)
                                score++;

                        } else {
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
                if (e.getX() > 600 && e.getX() < 790 && e.getY() > 275 && e.getY() < 340) {
                    currentScreen = "Game";
                    isPause = true;
                }

                //Repaly button

                if (e.getX() > 579 && e.getX() < 787 && e.getY() > 382 && e.getY() < 445) {
                    currentScreen = "Game";
                Timer = 60;
                score = 0;
                isPause= true;
                }


                // home button
                if (e.getX() > 595 && e.getX() < 790 && e.getY() > 485 && e.getY() < 555) {
                    currentScreen = "Home";
                    isPause = false;
                    Timer = 60;
                    score = 0;
                    mode = 0;
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

    }
    @Override
    public void mouseReleased(MouseEvent e) {
        animtionHammerIndex =0;
    }

    public void PlayMusic(String location) {
        try
        {
            File musicPath = new File(location);
            if(musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                 clip =AudioSystem.getClip();
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
