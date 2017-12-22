/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package culm.will;//package name

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Arc2D;
import java.io.BufferedReader;
import java.io.FileReader;
import javafx.scene.shape.Arc;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CulmWill extends JPanel implements ActionListener, KeyListener {

    private Graphics dbg;
    Graphics2D dr, guy;
    Image dbImage, master;
    Timer frame;
    boolean press[] = {false, false};
    double pos = 0;
    int score = 0;

    public CulmWill() {//program name      
        frame = new Timer(1, this); //sets the delay between frames
        frame.start();

        Timer count = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //runs every second                
                score++;
            }
        });

        count.start();

        addKeyListener(this);
    }

    public void spawn() {

    }

    public static void main(String[] args) {
        CulmWill panel = new CulmWill();
        JFrame f = new JFrame("");
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(panel);
        f.setUndecorated(true); //Takes away the toolBar
        f.setSize(700, 400);
        f.setVisible(true);
        f.setLocationRelativeTo(null);

    }

    public void paint(Graphics g) { //Static
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        dbg.drawImage(new ImageIcon("Special-Stage-Sonic-the-Hedgehog-2-2013.png").getImage(), 0, 0, 700, 400, this);
        dbg.drawArc(50, -180, 600, 500, 0, -180);
        paintComponent(dbg);
        g.drawImage(dbImage, 0, 0, this);
        g.setFont(new Font("Consolas", Font.PLAIN, 20));
        g.drawString("SCORE: " + score, 300, 30);
    }

    public void paintComponent(Graphics g) { //Rotates        
        guy = (Graphics2D) g;
        guy.drawRect(350, 140, 1, 1);
        guy.rotate(pos, 350, 140);

        //guy.drawImage(new ImageIcon("man.jpg").getImage(), 250, 200, 200, 300, this);       
        guy.drawRect(250, 200, 200, 300);
        
        Arc a = new Arc(50, -180, 600, 500, 0, -180);
        if(!a.intersects(250, 200, 200, 300)){
            System.out.println("22");
        }
        
        super.paintComponents(g);

        if (press[0] == true) {
            pos += .02;
        } else if (press[1] == true) {
            pos -= .02;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            press[0] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            press[1] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            press[0] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            press[1] = false;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        requestFocus();
        setFocusTraversalKeysEnabled(false);
    }
}
