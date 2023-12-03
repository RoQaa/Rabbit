package RabbitGame;

import com.sun.opengl.util.Animator;
import java.awt.BorderLayout;
import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Rabbit extends JFrame {
     private Animator Animation;
    Rabbit(){
        super("RabbitGame.Rabbit");
        GLCanvas glcanvas = new GLCanvas();
        RabbitGLEventListener listener = new RabbitGLEventListener();
        Animation=new Animator(glcanvas);
        
        
        
        
        //TODO do What u want Team Members(Farouk,Wafdy,manar,Boda)




        glcanvas.addGLEventListener(listener);
        glcanvas.addMouseListener(listener);
        glcanvas.addMouseMotionListener(listener);
        listener.setGLCanvas(glcanvas);

        getContentPane().add(glcanvas,BorderLayout.CENTER);
        setSize(1500,900);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
 }
    
    public static void main(String []args){



        new Rabbit();
    }

}

