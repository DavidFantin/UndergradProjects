// TowersApp.java
// TOWER of Hanoi Applet
// Author: Roger Wainwright 4/9/98

import java.applet.*;
import java.awt.*;
import java.util.*;

public class TowersApp extends Applet {
  
    public void init() {
	// repaint();
    }

    public void paint(Graphics g) {  
	int rings     =  7;
	Pole[] pole   =  new Pole[3];
	Color[] color =  {Color.red, Color.blue, Color.green,
			  Color.orange, Color.white, Color.cyan,
			  Color.magenta, Color.black,
			  new Color(255,0,255), // Violet
			  new Color(0,255,255) // Blue-Green
	                 };
   
	pole[0] = new Pole(75,450, 75,1,20);
	pole[1] = new Pole(75,450,250,1,20);
	pole[2] = new Pole(75,450,425,1,20);
   
	pole[0].DrawPole(g);
        pole[1].DrawPole(g);
        pole[2].DrawPole(g);
 
	for (int i=0; i<rings; i++) {
            Disk d = new Disk(70-5*i,20,color[i]);
	    pole[0].AddDisk(d,g);
	}

	Towers(rings, 0, 1, 2, pole, g);
    }// end paint

    public void Towers(int rings, int Source, int Dest,
	           int Spare, Pole[] pole, Graphics g) {
	if (rings == 1) {
	    // Move one disk from Source to Dest
	    Disk d = pole[Source].RemoveDisk(g);
	    pole[Dest].AddDisk(d,g);
	}
	else {
	    Towers(rings-1, Source, Spare, Dest, pole, g); 
	    Towers(1, Source, Dest, Spare, pole, g);      
	    try {
		Thread.sleep(100);
	    }
	    catch(InterruptedException x) {
		System.out.println("Problem sleeping!");
	    }
	    Towers(rings-1, Spare, Dest, Source, pole, g); 
	}  // end if
    }  // end Towers

}// end TowersApp class 

// ***********************************************
class Disk { 

    private int radius;
    private int height;
    private Color color;

    public Disk(int r, int h, Color c) { 
	radius = r;
	height = h;
	color = c;
    }

    public void DrawDisk(Graphics g, int x, int  y) {
	// Given (x,y) as a "base reference point", we can draw
	// the disk given its height, width and color on Graphics g
	// (x,y) is the located at the center of the base of the Disk

	g.setColor(color);
	g.fillRect(x-radius, y, 2*radius, height);
    }
  
    public  void OmitDisk(Graphics g, int x, int  y) {
	// Given (x,y) as a "base reference point", we can draw
	// the disk given its height, width and color on Graphics g
	// (x,y) is the located at the center of the base of the Disk
	
	g.clearRect(x-radius, y, 2*radius, height);
    }  

}// end Disk class

// ***********************************************
class Pole {
 
    private int radius;
    private int height;
    private int x;
    private int y;
    private int num_disks;
    private int delta;
    private Disk[] disk;

    public Pole(int r, int h, int px, int py, int dist) { 
	radius = r;
	height = h;
	x = px;
	y = py;
	delta = dist;
	num_disks = -1; // Disks numbered 0..9,  So -1 means empty
	disk = new Disk[10];
	//Disk[] disk = new Disk[10];  Note this compiles OK, but
	//causes a run time array problem
    }

    public void DrawPole(Graphics g) {
	// (x,y) is the center of the Pole location
	// along with radius and height we can draw the Pole

	g.setColor(Color.black);
	g.fillRect(x-radius, y, 2*radius, 4);
	g.fillRect(x-2, y, 4, height);
    }
  
    public void AddDisk(Disk d, Graphics g) {
	num_disks ++;
	disk[num_disks] = d;
	d.DrawDisk(g,x,10 + 22*num_disks);
    }

    public Disk RemoveDisk(Graphics g) { 
	Disk d = disk[num_disks];
	d.OmitDisk(g,x,10 + 22*num_disks);
	num_disks --;
	return d;
    }

}// end Pole class

