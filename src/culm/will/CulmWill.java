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
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CulmWill extends JPanel implements ActionListener, KeyListener {

    private Graphics dbg;
    Graphics2D dr, guy;
    Image dbImage, master;
    Timer frame;
    boolean press[] = {false, false, false, false};
    double pos = 0, rand;
    int score = 0, time = 0, bullx, bully;

    public CulmWill() {//program name      
        frame = new Timer(30, this); //sets the delay between frames
        frame.start();

        Timer count = new Timer(30, new ActionListener() { // this will run the code inside ever 30ms
            @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                if (bullx == 0 || time > 150) { //if its off screen
                    time = 0;
                    rand = Math.random() * Math.PI * 2;
                    bullx = (int) (Math.sin(rand) * 400 + 400); //spawn a new one
                    bully = (int) (Math.cos(rand) * 400);
                }
            }
        });

        count.start(); //starts the timer

        addKeyListener(this); //checks if keys are pressed
    }

    public void spawn() {

    }

    public static void main(String[] args) {
        CulmWill panel = new CulmWill();
        JFrame f = new JFrame("");
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(panel);
        //f.setUndecorated(true); //Takes away the toolBar
        f.setSize(800, 600);
        f.setVisible(true);
        f.setLocationRelativeTo(null);

    }

    public void paint(Graphics g) { //double buffer
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        paintComponent(dbg);

        g.drawImage(dbImage, 0, 0, this);

        g.setFont(new Font("Consolas", Font.PLAIN, 20));
        g.drawString("SCORE: " + score, 300, 30);
    }

    public void paintComponent(Graphics g) {
        guy = (Graphics2D) g;
        guy.drawRect(350, 140, 1, 1);

        guy.rotate(pos, 400, 0); //all the code below will rotate
        guy.drawImage(new ImageIcon("back.jpg").getImage(), -800, -600, 1600, 1600, this);
        guy.drawImage(new ImageIcon("airplane.png").getImage(), bullx - (20 + (time * time) / 14) / 2, bully - (20 + (time * time) / 14) / 2, 20 + (time * time) / 14, 20 + (time * time) / 14, this);

        guy.rotate(-pos, 400, 0); //all the code below wont rotate
        //guy.drawImage(new ImageIcon("man.jpg").getImage(), 350, 300, 100, 150, this);

        guy.drawLine(400, 0, (int) (Math.cos(-0.15 + (Math.PI / 2)) * 800) + 400, (int) (Math.sin(-0.15 + (Math.PI / 2)) * 800));
        guy.drawLine(400, 0, (int) (Math.cos(0.15 + (Math.PI / 2)) * 800) + 400, (int) (Math.sin(0.15 + (Math.PI / 2)) * 800));

        //guy.drawRect((int) (Math.cos(pos + (Math.PI / 2)) * 400) + 400, (int) (Math.sin(pos + (Math.PI / 2)) * 400), 30, 30);
        super.paintComponents(g);

        if (press[0] == true) { //checks which key is being pressed
            pos -= .04; //moves left
            if (pos <= 0) {
                pos = Math.PI * 2;
            }
        } else if (press[1] == true) {
            pos += .04; //moves right
            if (pos >= Math.PI * 2) {
                pos = 0;
            }
        }

        if (press[2] == true) { //checks which key is being pressed
            //shoot            
            if (pos <= rand + 0.15 && pos >= rand - 0.15) {
                time = 200;
                bullx = 3000;
                score++;
            }
        } else if (press[3] == true) {
            //power
        }
        if (time > 149 && time < 160 && pos <= rand + 0.15 && pos >= rand - 0.15) {
            System.out.println("GAMEOVER");
            score = 0;
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
        } else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            press[2] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            press[3] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            press[0] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            press[1] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            press[2] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            press[3] = false;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        requestFocus();
        setFocusTraversalKeysEnabled(false);
    }
}
