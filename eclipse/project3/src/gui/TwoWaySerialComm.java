package gui;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TwoWaySerialComm {
	public static String output="";
			public static boolean sendMessage=false;
	public TwoWaySerialComm() {
		try {
			connect("COM7");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void send(String _output) {
		output=_output;
		sendMessage=true;
	}

	public String getDataIn() {
		String data = SerialReader.dataIn;
		SerialReader.dataIn = "";
		return data;

	}

	public void clearDataIn() {
		SerialReader.dataIn = "";
	}

	private void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);
			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);

				InputStream in = serialPort.getInputStream();
				OutputStream out = serialPort.getOutputStream();

				(new Thread(new SerialReader(in))).start();
				(new Thread(new SerialWriter(out))).start();

			} else {
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	/** */
	public static class SerialReader implements Runnable {
		InputStream in;
		static String dataIn = "";

		public SerialReader(InputStream in) {
			this.in = in;
		}

		public void run() {
			byte[] buffer = new byte[1024];
			int len = -1;
			try {
				while ((len = this.in.read(buffer)) > -1) {
					// System.out.print(new String(buffer,0,len));
					String newData = new String(buffer, 0, len);
					dataIn += newData;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** */
	public static class SerialWriter implements Runnable {
		OutputStream out;

		public SerialWriter(OutputStream out) {
			this.out = out;
		}

		public void run() {
			while (true) {
				try {
					if(sendMessage==true)
					{
						sendMessage=false;
						output+="\n";
						this.out.write(output.getBytes());
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}