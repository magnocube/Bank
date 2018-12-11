package client;

public class Biljetten {
	int biljet50 = 10 ,biljet10 = 10 ,biljet5 = 10, biljet1 = 10, krijg50 = biljet50, krijg10 = biljet10,krijg5 = biljet5,krijg1 = biljet1,geld = 0, biljetkeuze = 50;
	boolean wil50 = true, wil10 = true, wil5 = true, wil1 = true, gelukt = true;
	public static void main(String[] args) {
		new Biljetten(125,50);
	}
	public Biljetten(int money,int keuze)
	{
		geld=money;
		biljetkeuze=keuze;
		biljetenkeuze();
		biljetenbereken();
	}
	public void biljetenkeuze() {
		if (biljetkeuze == 50){
			wil50 = true; 
			wil10 = true; 
			wil5 = true;
			wil1 = true;
		} else if (biljetkeuze == 10){
			wil50 = false; 
			wil10 = true; 
			wil5 = true;
			wil1 = true;
		} else if (biljetkeuze == 5){
			wil50 = false; 
			wil10 = false; 
			wil5 = true;
			wil1 = true;
		} else if (biljetkeuze == 1){
			wil50 = false; 
			wil10 = false; 
			wil5 = false;
			wil1 = true;
		}
	}
	
	public void biljetenbereken() {
		while (geld >= 50 && biljet50 >0 && wil50 == true){
			geld = geld - 50;
			biljet50 = biljet50 - 1;
			System.out.println("50");
		}
		while (geld >= 10 && biljet10 >0 && wil10 == true){
			geld = geld - 10;
			biljet10 = biljet10 - 1;
			System.out.println("10");
		}
		while (geld >= 5 && biljet5 >0 && wil5 == true){
			geld = geld - 5;
			biljet5 = biljet5 - 1;
			System.out.println("5");
		}
		while (geld >= 1 && biljet1 >0 && wil1 == true){
			geld = geld - 1;
			biljet1 = biljet1 - 1;
			System.out.println("1");
		}
		if (geld == 0) {
			krijg50 = krijg50 - biljet50;
			krijg10 = krijg10 - biljet10;
			krijg5 = krijg5 - biljet5;
			krijg1 = krijg1 - biljet1;
			System.out.println("de gebruiker krijgt "+ krijg50 +" biljeten van 50 roebel en "+ krijg10 +" biljeten van 10 roebel en "+ krijg5 +" biljeten van 5 roebel en "+ krijg1 +" biljeten van 1 roebel");
			wil50 = true; 
			wil10 = true; 
			wil5 = true;
			wil1 = true;
			gelukt = true;
			krijg50 = biljet50;
			krijg10 = biljet10;
			krijg5 = biljet5;
			krijg1 = biljet1;
		} else {
			System.out.println("niet genoeg biljeten");
			System.out.println(geld);
			gelukt = false;
			biljet50 = krijg50;
			biljet10 = krijg10;		
			biljet5 = krijg5;
			biljet1 = krijg1;
		}
	}
}
