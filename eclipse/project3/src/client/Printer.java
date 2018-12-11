package client;
/*

 * To change this template, choose Tools | Templates

 * and open the template in the editor.

 */

import java.awt.Color;

import java.awt.Font;

import java.awt.Graphics;

import java.awt.Graphics2D;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;

import java.awt.print.Paper;

import java.awt.print.Printable;

import java.awt.print.PrinterException;

import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;

import javax.swing.JFrame;

import javax.swing.JPanel;

/**
 *
 * 
 * 
 * @author BlaJF
 * 
 */

public class Printer {
	private String path="C:/Users/Rene Schouten/Documents/project3/eclipse/project3";
	private PageFormat mPageFormat;
	
	public int gepint = 90;
	
	public String[] message = {"test" , "u heeft gepint op ","" + dateToString()};
	
	static boolean voorbeeld = true;
	
	public String dateToString() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(cal.getTime());
	}

	public void preview() {

		JFrame frame = new JFrame();

		frame.add(new OpScherm());

		frame.setTitle("Preview van label 99014");

		frame.setSize(170, 300);

		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

		frame.setVisible(true);

	}

	public Printer(int money) {
		
		gepint = money;

		if (!voorbeeld) {
			
			PrinterJob pj = PrinterJob.getPrinterJob();
				
			mPageFormat = new PageFormat();

			Paper paper = new Paper();

			// mPageFormat.setOrientation(mPageFormat.LANDSCAPE);

			paper.setImageableArea(0, 0, 160, 290);

			paper.setSize(160, 290);

			mPageFormat.setPaper(paper);

			pj.setPrintable(new OutPrintable(), mPageFormat);

			// if (pj.printDialog())

			{

				try {

					pj.print();
				} catch (PrinterException e) {

					System.out.println(e);

				}

			}

		}
		if (voorbeeld) {

			preview();

		}

	}

	public void makeGraphics(Graphics2D g3) {

		g3.setColor(Color.white);

		g3.fillRect(0, 0, 160, 280);

		Image img = new ImageIcon(path+"/logo.gif").getImage();

		g3.drawImage(img, 85, 10, 70, 85, null);
		
		Image rubel = new ImageIcon(path+"/rubel.gif").getImage();

		g3.drawImage(rubel, 80, 105, 10, 10, null);

		g3.setColor(Color.black);

		g3.setFont(new Font("Monospaced", Font.BOLD, 25));

		g3.drawString("The", 25, 25);
		
		g3.setFont(new Font("Monospaced", Font.BOLD, 25));

		g3.drawString("Dark", 25, 55);
		
		g3.setFont(new Font("Monospaced", Font.BOLD, 25));

		g3.drawString("Bank ", 25, 85);

		g3.setFont(new Font("Monospaced", Font.BOLD, 12));

		g3.drawString("uw heeft:  " + gepint + " gepint", 15, 115);
		
		g3.setFont(new Font("Monospaced", Font.BOLD, 12));

		g3.drawString(message[1], 15, 145);
		
		g3.setFont(new Font("Monospaced", Font.BOLD, 12));

		g3.drawString(message[2], 15, 175);

		g3.drawLine(0, 280, 150, 280);

	}

	class OutPrintable

			implements Printable {

		public int print(Graphics g, PageFormat pf, int pageIndex) {

			if (pageIndex != 0) {

				return NO_SUCH_PAGE;

			}

			Graphics2D g2 = (Graphics2D) g;

			makeGraphics(g2);

			return PAGE_EXISTS;

		}

	}

	class OpScherm extends JPanel {

		public void paintComponent(Graphics g) {

			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;

			makeGraphics(g2);

		}

	}

}