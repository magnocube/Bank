package client;


public class TimeOutThread implements Runnable {
	MainWindow window=null;
	public TimeOutThread(MainWindow in)
	{
		window=in;
	}
	String paginanaam="";
	long beginTime=0;
	long beginTime2=0;
	public void run()
	{
		while(true)
		{
			if(paginanaam != window.paginanaam)
			{
				paginanaam = window.paginanaam;
				System.out.println(paginanaam);
				beginTime=System.currentTimeMillis();
				beginTime2=System.currentTimeMillis();
			}
			if(System.currentTimeMillis()-beginTime>15000)
			{
				if((paginanaam != "home")&&(paginanaam!="game"))
				{
					window.paginanaam = "home";
					System.out.println(window.paginanaam);
					window.c.show(window.mainPanel, "home");
					window.reset();
					System.out.println("stop");
				}
				
				beginTime=System.currentTimeMillis();
			}
			if(System.currentTimeMillis()-beginTime2>30000)
			{
				if((paginanaam != "home")&&(paginanaam!="game"))
				{
					window.paginanaam = "home";
					System.out.println(window.paginanaam);
					window.c.show(window.mainPanel, "home");
					window.reset();
					System.out.println("stop");
				}
				
				beginTime=System.currentTimeMillis();
			}
			sleep(100);
			
			
		}
	}
	
	public void sleep(int time)
	{
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
