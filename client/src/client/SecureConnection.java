package client;

import java.util.Random;


public class SecureConnection {
	public static TwoWaySerialComm serial = new TwoWaySerialComm();
	public static String sharedKey="";
	public static arduinoAes aesDecrypter = new arduinoAes();
	public SecureConnection()
	{
		sendRandom(false);
	}
	public static String getSerialInput(boolean print)
	{
		String dataIn=serial.getDataIn();
		
		if(dataIn.length()>0)
		{
			dataIn.trim();
			if(print==true)
			{
				System.out.print("input= "+dataIn);
			}
			String aesLine = null;
			try
			{
				int index = dataIn.indexOf("==");
				aesLine = dataIn.substring(index-22,index+2);
				dataIn = dataIn.substring(index+2,dataIn.length());
				serial.clearDataIn();
				String decrypted = "";
				try {
					decrypted = aesDecrypter.decrypt(aesLine);
					decrypted = decrypted.trim();
					String inKey=decrypted.substring(0,4);
					String inData=decrypted.substring(4,decrypted.length());
					if(print==true)
					{
						System.out.println("decrypted string= "+decrypted);
						System.out.println("inkey= "+inKey);
						System.out.println("	indata= "+inData);
					}
					sharedKey.trim();
					inKey.trim();
					if(inKey.equals(sharedKey))
					{
						if(print==true)
						{
							System.out.println("random key oke");
						}
						sendRandom(print);
						return inData;
					}
					else
					{
						System.out.println("bad key");
						sendRandom(print);
					}
				} catch(Exception e){
					e.printStackTrace();
				}
				
				
			}catch(Exception e)
			{
				
			}
        }
		return null;
	}
	private static void sendRandom(boolean print)
	{
		Random ran = new Random();
		int random = ran.nextInt(8999)+1000;
		sharedKey=random+"";
		serial.send(sharedKey);
		if(print==true)
		{
			System.out.println("new random "+sharedKey);
		}
	}

}
