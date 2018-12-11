package gui;

import java.util.Scanner;

public class FakeConnection {
	public String getSerialInput(boolean print)
	{
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
		
	}
}
