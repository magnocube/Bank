package gui;

import javax.sound.sampled.*;

public class SoundUtils extends Thread {

  public static float SAMPLE_RATE = 8000f;

  public SoundUtils()
  {
	  
  }

 
  public void run()
  {
	 try{
	  byte[] buf = new byte[1];
	    AudioFormat af = 
	        new AudioFormat(
	            SAMPLE_RATE, // sampleRate
	            8,           // sampleSizeInBits
	            1,           // channels
	            true,        // signed
	            false);      // bigEndian
	    SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
	    sdl.open(af);
	    sdl.start();
	    for (int i=0; i < 100*8; i++) {
	      double angle = i / (SAMPLE_RATE / 100) * 2.0 * Math.PI;
	      buf[0] = (byte)(Math.sin(angle) * 127.0 * 1.0);
	      sdl.write(buf,0,1);
	    }
	    sdl.drain();
	    sdl.stop();
	    sdl.close();
	 }
	 catch(Exception e){}
  }
}