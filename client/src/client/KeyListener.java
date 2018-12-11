package client;

import javax.sound.sampled.LineUnavailableException;

public class KeyListener extends Thread{
	//SecureConnection secureConnection = new SecureConnection();
	FakeConnection secureConnection = new FakeConnection();
	MainWindow gui;
	public KeyListener(MainWindow gui)
	{
		this.gui=gui;
	}
	public void run()
	{
		while(true){
			delay(20);
			String input = secureConnection.getSerialInput(false);
			if(input!=null)
			{
				System.out.println(input);
				gui.give(input);
				
			}
		}
	}
	public static void delay(int time)
	{
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
