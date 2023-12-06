package RabbitGame;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import java.awt.BorderLayout;
import javax.media.opengl.GLCanvas;
import javax.swing.*;


public class Rabbit extends JFrame {

    Rabbit(){
        GLCanvas glcanvas;
        Animator animator;

        RabbitGLEventListener listener = new RabbitGLEventListener();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        glcanvas.addMouseListener(listener);
        glcanvas.addMouseMotionListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(22);
        animator.add(glcanvas);
        animator.start();

        setTitle("Rabbit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 900);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
 }
    
    public static void main(String []args){



        new Rabbit();
    }

}

