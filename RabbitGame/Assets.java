package RabbitGame;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.media.opengl.GLEventListener;


public abstract class Assets extends Component implements GLEventListener, MouseMotionListener, MouseListener,KeyListener {
            //Secret path for Images
        protected String assetsFolderName = "Images";

    
}
