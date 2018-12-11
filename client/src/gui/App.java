package gui;

public class App {
	public static void main(String args[])
	{
		MainWindow gui = new MainWindow();

		KeyListener lisener = new KeyListener(gui);
		lisener.run();
	}
	
	
}
