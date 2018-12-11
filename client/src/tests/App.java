package tests;

public class App {
	public static void main(String args[])
	{
		MainWindow gui = new MainWindow();
	//	MainWindow gui2 = new MainWindow();
	//	MainWindow gui3 = new MainWindow();
	//	MainWindow gui4 = new MainWindow();
	//	MainWindow gui5 = new MainWindow();

		KeyListener lisener = new KeyListener(gui);
		lisener.run();
	}
	
	
}
