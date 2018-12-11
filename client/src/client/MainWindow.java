package client;

import java.awt.AWTException;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MainWindow extends JFrame {
	ServerAction server=new ServerAction();
	private String pasnummer = "";
	private String banknummer = "";
	private String saldo="";
	private String path="C:/Users/Rene Schouten/Documents/project3/eclipse/project3";
	public MainWindow() {
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
		setSize(1920, 1080);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainPanel.setLayout(c);
		mainPanel.add(j1, "home");
		mainPanel.add(j2, "pin");
		mainPanel.add(j3, "Money");
		mainPanel.add(j4, "end");
		mainPanel.add(j5, "chose");
		mainPanel.add(j6, "saldo");
		mainPanel.add(j7, "Biljeten");
		mainPanel.add(j8, "error");
		
		add(mainPanel); // zodat de mainPanel ook daadwerkelijk op mij JFrame
						// komt,,, mainPanel is namelijk maar een panel.. en
						// geen jframe
		c.show(mainPanel, "home"); // op deze marnier switch je van tabbladen
		
		

		knopLoginPin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "pin");
				paginanaam = "pin";
			}
		});
		knopNaarGeldKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = pinInvoer.getText();
				pinCode = new PasswordAES().encrypt(input);
				System.out.println(pinCode);
				if(server.login(pasnummer,"01",pinCode)==true)
				{
					c.show(mainPanel, "Money");
					paginanaam = "Money";
				} else {
				    c.show(mainPanel, "error");
					error.setText(server.getError());
				    paginanaam = "error";
				    oudepaginanaam = "pin";
				}
				pinInvoer.setText("");
				
			}
		});
		deleteknop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String invoer=pinInvoer.getText();
				if(invoer.length()>=1)
				{
					pinInvoer.setText(invoer.substring(0,invoer.length()-1));
				}
			}
		});
		knopTienRoebelsKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				money = 10;
				c.show(mainPanel, "Biljeten");
				paginanaam = "Biljeten";
			}
		});
		knopTwintigRoebelsKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				money = 20;
				c.show(mainPanel, "Biljeten");
				paginanaam = "Biljeten";
			}
		});
		knopVeertigRoebelsKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				money = money + 40;
				c.show(mainPanel, "Biljeten");
				paginanaam = "Biljeten";
			}
		});
		knopSaldoOpvragen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "saldo");
				paginanaam = "saldo";
				if(server.getSaldo(pasnummer,"01",pinCode)==true)
				{
					saldo=server.getMoney();
					saldoweergeven.setText("uw saldo is " + saldo);
					
				}
				else
				{
					System.out.println("het is niet gelukt");
					System.out.println(server.getError());
					c.show(mainPanel, "error");
					error.setText(server.getError());
					paginanaam="error";
					oudepaginanaam="Money";
				}
				
				
			}
		});
		knopCancelTransactie1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "home");
				reset();
			}
		});
		knopCancelTransactie2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "home");
				reset();
			}
		});
		knopCancelTransactie3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "home");
				reset();
			}
		});
		knopTerugNaarHomepage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "home");
				reset();
			}
		});
		knopChooseAmount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "chose");
				paginanaam = "chose";
			}
		});
		knopBevestigBetaling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "Biljeten");
				String input = txtInput2.getText();
				money = Integer.parseInt(input);
				txtInput2.setText("");
				paginanaam = "Biljeten";
			}
		});
		knopGeldKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "Money");
				paginanaam = "Money";
			}
		});
		knopKrijgBonnetje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//new Printer(Integer.parseInt(saldo));// todo verander bonnentje voor om het saldo er op te zetten
				reset();
				c.show(mainPanel, "home");
				paginanaam = "home";
			}
		});
		knopBonnetjeKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "home");
				new Printer(money);
				reset();
			}
		});
		knopEenKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				biljetCheck(1);
			}
		});
		knopVijfKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				biljetCheck(5);
			}
		});
		knopTienKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				biljetCheck(10);
			}
		});
		knopVijftigKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				biljetCheck(50);
			}
		});
		knopDirectPinnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				biljetCheck(0);
			}
		});
		knopCancelTransactie4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "home");
				reset();
			}
		});
		knopReturnToPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String convert = "";
				convert = oudepaginanaam;
				c.show(mainPanel, convert);
				paginanaam = convert;
			}
		});
		
		Thread timeOut = new Thread(timeout);
		timeOut.start();
		 try {
			r=new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	Robot r=null;
	TimeOutThread timeout = new TimeOutThread(this);
	public void reset()
	{
		paginanaam = "home";
		banknummer = "";
		pasnummer = "";
		saldo="";
		pinCode="";
		txtInput2.setText("");
		pinInvoer.setText("");
	}
	private void biljetCheck(int keuze)
	{
		if(money >= keuze){
			if(server.withdraw(pasnummer,"01",pinCode,money)==true)
			{
				System.out.println("het is gelukt");
				c.show(mainPanel, "end");
				new Biljetten(money,keuze);
				gepint.setText("de hoeveelheid die u gepind hebt is " + money);
				paginanaam = "end";
			}
			else
			{
				System.out.println("het is niet gelukt");
				System.out.println(server.getError());
				c.show(mainPanel, "error");
				paginanaam="error";
				error.setText(server.getError());
				oudepaginanaam="Money";
			}
		} else {
			c.show(mainPanel, "error");
		    error.setText("Uw heeft minder geld gepint dan het biljet waard is");
		    oudepaginanaam = "Biljeten";
		    paginanaam="error";
		}
	}
	

	public CardLayout c = new CardLayout();
	public JPanel mainPanel = new JPanel();

	JButton knopLoginPin = new JButton("Hou uw pinpas bij de scanner");
	JButton knopNaarGeldKiezen = new JButton("#");
	JButton deleteknop = new JButton("*");
	JButton knopTienRoebelsKiezen = new JButton("A");
	JButton knopTwintigRoebelsKiezen = new JButton("B");
	JButton knopVeertigRoebelsKiezen = new JButton("C");
	JButton knopSaldoOpvragen = new JButton("D");
	JButton knopCancelTransactie1 = new JButton("#");
	JButton knopCancelTransactie2 = new JButton("#");
	JButton knopCancelTransactie3 = new JButton("#");
	JButton knopCancelTransactie4 = new JButton("#");
	JButton knopChooseAmount = new JButton("*");
	JButton knopTerugNaarHomepage = new JButton("#");
	JButton knopBevestigBetaling = new JButton("*");
	JButton knopBonnetjeKiezen = new JButton("*");
	JButton knopGeldKiezen = new JButton("A");
	JButton knopKrijgBonnetje = new JButton("B");
	JButton knopEenKiezen = new JButton("A");
	JButton knopVijfKiezen = new JButton("B");
	JButton knopTienKiezen = new JButton("C");
	JButton knopVijftigKiezen = new JButton("D");
	JButton knopDirectPinnen = new JButton("*");
	JButton knopReturnToPage = new JButton("#");
	ImageIcon background = new ImageIcon(path+"/homepage.png");
	JLabel saldoweergeven;
	JLabel gepint;
	JLabel error;

	JPanel j1 = homePanel();
	JPanel j2 = pinInvoerPanel();
	JPanel j3 = geldKiesPanel();
	JPanel j4 = eindeTransactiePanel();
	JPanel j5 = chooseAmountPanel();
	JPanel j6 = saldoOpvragen();
	JPanel j7 = BanbiljetenKiezen();
	JPanel j8 = errorpage();

	JPasswordField pinInvoer;
	JTextField txtInput2;

	private String pinCode = "";
	private int money = 0;
	private int bankAccount = 900;
	public String paginanaam = "home";
	public String oudepaginanaam = "home";
	private JPanel homePanel() {
		paginanaam = "home";
		JPanel j = new JPanel();
		j.setLayout(null);

		knopLoginPin.setBounds(640, 400, 550, 60);
		knopLoginPin.setFont(new Font("MS PGothic", Font.BOLD, 30));
		//j.add(knopLoginPin);

		JLabel label2 = new JLabel("houdt uw pas bij de scanner");
		label2.setFont(new Font("MS PGothic", Font.BOLD, 40));
		label2.setBounds(570, 330, 1030, 202);
		j.add(label2);
		
		JLabel label = new JLabel("Welkom bij The dark bank");
		label.setFont(new Font("MS PGothic", Font.BOLD, 60));
		label.setBounds(540, 250, 1000, 122);
		j.add(label);

		JLabel jLabelObject = new JLabel();
		jLabelObject.setIcon(background);
		jLabelObject.setBounds(0, 0, 1920, 1080);
		j.add(jLabelObject);

		return j;
	}

	private JPanel pinInvoerPanel() {
		paginanaam = "login";

		JPanel j = new JPanel();
		j.setLayout(null);

		JLabel label = new JLabel("Voer uw pincode in");
		label.setFont(new Font("MS PGothic", Font.BOLD, 60));
		label.setBounds(630, 250, 1000, 122);
		j.add(label);

		JLabel tachtigRoebels = new JLabel("bevestig");
		tachtigRoebels.setFont(new Font("MS PGothic", Font.BOLD, 60));
		tachtigRoebels.setBounds(1500, 670, 400, 120);
		j.add(tachtigRoebels);

		pinInvoer = new JPasswordField("");
		pinInvoer.setBounds(630, 400, 500, 100);
		pinInvoer.setFont(new Font("MS PGothic", Font.BOLD, 60));
		j.add(pinInvoer);

		knopNaarGeldKiezen.setBounds(1780, 680, 100, 90);
		knopNaarGeldKiezen.setFont(new Font("MS PGothic", Font.BOLD, 30));
		j.add(knopNaarGeldKiezen);
		
		deleteknop.setBounds(280, 680, 100, 90);
		deleteknop.setFont(new Font("MS PGothic", Font.BOLD, 30));
		j.add(deleteknop);
		
		JLabel correctie = new JLabel("correctie");
		correctie.setFont(new Font("MS PGothic", Font.BOLD, 60));
		correctie.setBounds(400, 670, 400, 120);
		j.add(correctie);

		JLabel jLabelObject = new JLabel();
		jLabelObject.setIcon(background);
		jLabelObject.setBounds(0, 0, 1920, 1080);
		j.add(jLabelObject);
		return j;
	}

	private JPanel geldKiesPanel() {
		paginanaam = "Money";
		JPanel j = new JPanel();
		j.setLayout(null);

		Font f = new Font("MS PGothic", Font.BOLD, 60);

		JLabel label = new JLabel("Kies de hoeveelheid geld die je wilt");
		label.setFont(f);
		label.setBounds(540, 250, 1000, 120);
		j.add(label);

		JLabel tienRoebels = new JLabel("10 Roebels");
		tienRoebels.setFont(f);
		tienRoebels.setBounds(1400, 370, 400, 120);
		j.add(tienRoebels);
		JLabel twintigRoebels = new JLabel("20 Roebels");
		twintigRoebels.setFont(f);
		twintigRoebels.setBounds(1400, 470, 400, 120);
		j.add(twintigRoebels);
		JLabel veertigRoebels = new JLabel("40 Roebels");
		veertigRoebels.setFont(f);
		veertigRoebels.setBounds(1400, 570, 400, 120);
		j.add(veertigRoebels);
		JLabel tachtigRoebels = new JLabel("saldo opvragen");
		tachtigRoebels.setFont(f);
		tachtigRoebels.setBounds(1300, 670, 500, 120);
		j.add(tachtigRoebels);

		JLabel cancel = new JLabel("stop transactie");
		cancel.setFont(f);
		cancel.setBounds(180, 670, 500, 120);
		j.add(cancel);
		JLabel chose = new JLabel("kies eigen hoeveelheid geld");
		chose.setFont(f);
		chose.setBounds(180, 570, 800, 120);
		j.add(chose);

		j.add(knopTienRoebelsKiezen);
		knopTienRoebelsKiezen.setBounds(1780, 380, 100, 90);
		knopTienRoebelsKiezen.setFont(f);

		j.add(knopTwintigRoebelsKiezen);
		knopTwintigRoebelsKiezen.setBounds(1780, 480, 100, 90);
		knopTwintigRoebelsKiezen.setFont(f);

		j.add(knopVeertigRoebelsKiezen);
		knopVeertigRoebelsKiezen.setBounds(1780, 580, 100, 90);
		knopVeertigRoebelsKiezen.setFont(f);

		j.add(knopSaldoOpvragen);
		knopSaldoOpvragen.setBounds(1780, 680, 100, 90);
		knopSaldoOpvragen.setFont(f);

		j.add(knopCancelTransactie1);
		knopCancelTransactie1.setBounds(40, 680, 100, 90);
		knopCancelTransactie1.setFont(f);

		j.add(knopChooseAmount);
		knopChooseAmount.setBounds(40, 580, 100, 90);
		knopChooseAmount.setFont(f);

		// j.add(txtInput2);

		JLabel jLabelObject = new JLabel();
		jLabelObject.setIcon(background);
		jLabelObject.setBounds(0, 0, 1920, 1080);
		j.add(jLabelObject);

		return j;
	}

	private JPanel eindeTransactiePanel() {
		paginanaam = "end";
		JPanel j = new JPanel();
		j.setLayout(null);

		gepint = new JLabel("de hoeveelheid die u gepind hebt is " + money);
		gepint.setFont(new Font("MS PGothic", Font.BOLD, 60));
		gepint.setBounds(450, 250, 1400, 122);
		j.add(gepint);

		JLabel bonnetje = new JLabel("klik voor bonnetje");
		bonnetje.setFont(new Font("MS PGothic", Font.BOLD, 60));
		bonnetje.setBounds(1250, 670, 500, 120);
		j.add(bonnetje);
		
		JLabel terug = new JLabel("terug naar hompage");
		terug.setFont(new Font("MS PGothic", Font.BOLD, 60));
		terug.setBounds(180, 670, 1000, 120);
		j.add(terug);

		j.add(knopBonnetjeKiezen);
		knopBonnetjeKiezen.setBounds(1780, 680, 100, 90);
		knopBonnetjeKiezen.setFont(new Font("MS PGothic", Font.BOLD, 60));

		j.add(knopTerugNaarHomepage);
		knopTerugNaarHomepage.setBounds(40, 680, 100, 90);
		knopTerugNaarHomepage.setFont(new Font("MS PGothic", Font.BOLD, 30));

		JLabel jLabelObject = new JLabel();
		jLabelObject.setIcon(background);
		jLabelObject.setBounds(0, 0, 1920, 1080);
		j.add(jLabelObject);

		return j;
	}

	private JPanel chooseAmountPanel() {
		paginanaam = "chose";

		JPanel j = new JPanel();
		j.setLayout(null);

		JLabel label = new JLabel("kies de hoeveelheid geld");
		label.setFont(new Font("MS PGothic", Font.BOLD, 60));
		label.setBounds(530, 250, 1000, 122);
		j.add(label);

		JLabel label2 = new JLabel("bevestig");
		label2.setFont(new Font("MS PGothic", Font.BOLD, 60));
		label2.setBounds(1500, 670, 400, 120);
		j.add(label2);

		txtInput2 = new JTextField("");
		txtInput2.setBounds(630, 400, 500, 120);
		txtInput2.setFont(new Font("MS PGothic", Font.BOLD, 60));
		j.add(txtInput2);

		knopBevestigBetaling.setBounds(1780, 680, 100, 90);
		knopBevestigBetaling.setFont(new Font("MS PGothic", Font.BOLD, 30));
		j.add(knopBevestigBetaling);

		j.add(knopCancelTransactie3);
		knopCancelTransactie3.setBounds(40, 680, 100, 90);
		knopCancelTransactie3.setFont(new Font("MS PGothic", Font.BOLD, 30));

		JLabel cancel = new JLabel("stop transactie");
		cancel.setFont(new Font("MS PGothic", Font.BOLD, 60));
		cancel.setBounds(180, 670, 500, 120);
		j.add(cancel);

		JLabel jLabelObject = new JLabel();
		jLabelObject.setIcon(background);
		jLabelObject.setBounds(0, 0, 1920, 1080);
		j.add(jLabelObject);
		return j;
	}

	private JPanel saldoOpvragen() {

		JPanel j = new JPanel();
		j.setLayout(null);

		saldoweergeven = new JLabel("uw saldo is " + bankAccount);
		saldoweergeven.setFont(new Font("MS PGothic", Font.BOLD, 60));
		saldoweergeven.setBounds(630, 250, 1000, 122);
		j.add(saldoweergeven);

		JLabel naarBonnetje = new JLabel("krijg bonnetje");
		naarBonnetje.setFont(new Font("MS PGothic", Font.BOLD, 60));
		naarBonnetje.setBounds(1400, 670, 500, 120);
		j.add(naarBonnetje);

		JLabel naarpinnen = new JLabel("geld pinnen");
		naarpinnen.setFont(new Font("MS PGothic", Font.BOLD, 60));
		naarpinnen.setBounds(1400, 570, 500, 120);
		j.add(naarpinnen);

		knopKrijgBonnetje.setBounds(1780, 680, 100, 90);
		knopKrijgBonnetje.setFont(new Font("MS PGothic", Font.BOLD, 30));
		j.add(knopKrijgBonnetje);

		j.add(knopCancelTransactie2);
		knopCancelTransactie2.setBounds(40, 680, 100, 90);
		knopCancelTransactie2.setFont(new Font("MS PGothic", Font.BOLD, 30));

		j.add(knopGeldKiezen);
		knopGeldKiezen.setBounds(1780, 580, 100, 90);
		knopGeldKiezen.setFont(new Font("MS PGothic", Font.BOLD, 30));

		JLabel cancel = new JLabel("stop transactie");
		cancel.setFont(new Font("MS PGothic", Font.BOLD, 60));
		cancel.setBounds(180, 670, 500, 120);
		j.add(cancel);

		JLabel jLabelObject = new JLabel();
		jLabelObject.setIcon(background);
		jLabelObject.setBounds(0, 0, 1920, 1080);
		j.add(jLabelObject);
		return j;
	}

	private JPanel BanbiljetenKiezen() {

		paginanaam = "biljeten";
		JPanel j = new JPanel();
		j.setLayout(null);

		Font f = new Font("MS PGothic", Font.BOLD, 60);

		JLabel label = new JLabel("Kies hoe u uw geld wil");
		label.setFont(f);
		label.setBounds(540, 250, 1000, 120);
		j.add(label);

		JLabel tienRoebels = new JLabel("Briefjes van een");
		tienRoebels.setFont(f);
		tienRoebels.setBounds(1200, 370, 600, 120);
		j.add(tienRoebels);
		JLabel twintigRoebels = new JLabel("Briefjes van vijf");
		twintigRoebels.setFont(f);
		twintigRoebels.setBounds(1200, 470, 600, 120);
		j.add(twintigRoebels);
		JLabel veertigRoebels = new JLabel("Briefjes van tien");
		veertigRoebels.setFont(f);
		veertigRoebels.setBounds(1200, 570, 600, 120);
		j.add(veertigRoebels);
		JLabel tachtigRoebels = new JLabel("Briefjes van vijftig");
		tachtigRoebels.setFont(f);
		tachtigRoebels.setBounds(1200, 670, 600, 120);
		j.add(tachtigRoebels);

		JLabel cancel = new JLabel("stop transactie");
		cancel.setFont(f);
		cancel.setBounds(180, 670, 500, 120);
		j.add(cancel);
		JLabel chose = new JLabel("pin direct");
		chose.setFont(f);
		chose.setBounds(180, 570, 800, 120);
		j.add(chose);

		j.add(knopEenKiezen);
		knopEenKiezen.setBounds(1780, 380, 100, 90);
		knopEenKiezen.setFont(f);

		j.add(knopVijfKiezen);
		knopVijfKiezen.setBounds(1780, 480, 100, 90);
		knopVijfKiezen.setFont(f);

		j.add(knopTienKiezen);
		knopTienKiezen.setBounds(1780, 580, 100, 90);
		knopTienKiezen.setFont(f);

		j.add(knopVijftigKiezen);
		knopVijftigKiezen.setBounds(1780, 680, 100, 90);
		knopVijftigKiezen.setFont(f);

		j.add(knopCancelTransactie4);
		knopCancelTransactie4.setBounds(40, 680, 100, 90);
		knopCancelTransactie4.setFont(f);

		j.add(knopDirectPinnen);
		knopDirectPinnen.setBounds(40, 580, 100, 90);
		knopDirectPinnen.setFont(f);

		JLabel jLabelObject = new JLabel();
		jLabelObject.setIcon(background);
		jLabelObject.setBounds(0, 0, 1920, 1080);
		j.add(jLabelObject);

		return j;
	}
	private JPanel errorpage() {

		paginanaam = "error";
		JPanel j = new JPanel();
		j.setLayout(null);

		Font f = new Font("MS PGothic", Font.BOLD, 60);

		error = new JLabel("error 404: intelligent life not found");
		error.setFont(f);
		error.setBounds(340, 250, 2000, 120);
		j.add(error);
		
		JLabel Return = new JLabel("terug naar de pagina");
		Return.setFont(new Font("MS PGothic", Font.BOLD, 60));
		Return.setBounds(180, 670, 1000, 120);
		j.add(Return);
		
		j.add(knopReturnToPage);
		knopReturnToPage.setBounds(40, 680, 100, 90);
		knopReturnToPage.setFont(new Font("MS PGothic", Font.BOLD, 30));

		JLabel jLabelObject = new JLabel();
		jLabelObject.setIcon(background);
		jLabelObject.setBounds(0, 0, 1920, 1080);
		j.add(jLabelObject);

		return j;
	}
	sound a=null;
	Process proc = null;
	
	public void give(String input) {
		// if(paginanaam=="choose")
		// {
		try {
			r=new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(input + " " + paginanaam);
		if (input.length() == 1) {
			if ((input.equals("1")) && (paginanaam == "home")) {
				try {
					 proc = Runtime.getRuntime().exec("java -jar mario.jar");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("easteregg");
				sleep(4000);
				r.keyPress(KeyEvent.VK_ENTER);
				sleep(10);
				r.keyRelease(KeyEvent.VK_ENTER);
				paginanaam="game";
				
			}
			else if ((input.equals("2")) && (paginanaam == "home")) 
			{
				try
				{
				 a.clip.close();
				}catch(Exception e){}
				 a = new sound(path+"/song.wav");
			     a.start();
			}	
			else if ((input.equals("3")) && (paginanaam == "home")) 
			{
				try
				{
				 a.clip.close();
				}catch(Exception e){}
				 a = new sound(path+"/song2.wav");
			     a.start();
			}	
			else if ((input.equals("4")) && (paginanaam == "home")) 
			{
				try
				{
				 a.clip.close();
				}catch(Exception e){}
				a = new sound(path+"/song3.wav");
			    a.start();
			}	
			else if ((input.equals("5")) && (paginanaam == "home")) 
			{
				try
				{
				 a.clip.close();
				}catch(Exception e){}
			}	
			else if ((input.equals("5")) && (paginanaam == "home")) 
			{
					
			}	
			else if ((input.equals("1")) && (paginanaam == "game")) {
				proc.destroy();
				paginanaam="home";
			}
			else if ((input.equals("7")) && (paginanaam == "game")) {
				r.keyRelease(KeyEvent.VK_RIGHT);
				r.keyRelease(KeyEvent.VK_LEFT);
				sleep(10);
				r.keyPress(KeyEvent.VK_LEFT);
				
			}
			else if ((input.equals("8")) && (paginanaam == "game")) {
				r.keyRelease(KeyEvent.VK_LEFT);
				r.keyRelease(KeyEvent.VK_RIGHT);
			}
			else if ((input.equals("5")) && (paginanaam == "game")) {
				r.keyRelease(KeyEvent.VK_LEFT);
				r.keyRelease(KeyEvent.VK_RIGHT);
				r.keyPress(KeyEvent.VK_X);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				r.keyRelease(KeyEvent.VK_X);
			}
			else if ((input.equals("9")) && (paginanaam == "game")) {
				r.keyRelease(KeyEvent.VK_RIGHT);
				r.keyRelease(KeyEvent.VK_LEFT);
				sleep(10);
				r.keyPress(KeyEvent.VK_RIGHT);
				
			}
			else if ((input.equals("#")) && (paginanaam == "home")) {
				knopLoginPin.doClick(300);
				System.out.println("#");
			} else if ((input.equals("#")) && (paginanaam == "pin")) {
				knopNaarGeldKiezen.doClick(300);
				System.out.println("#");
			}
			else if ((input.equals("*")) && (paginanaam == "pin")) {
				deleteknop.doClick(300);
				System.out.println("*");
			} else if ((input.equals("A")) && (paginanaam == "Money")) {
				knopTienRoebelsKiezen.doClick(300);
				System.out.println("A");
			} else if ((input.equals("B")) && (paginanaam == "Money")) {
				knopTwintigRoebelsKiezen.doClick(300);
				System.out.println("B");
			} else if ((input.equals("C")) && (paginanaam == "Money")) {
				knopVeertigRoebelsKiezen.doClick(300);
				System.out.println("C");
			} else if ((input.equals("D")) && (paginanaam == "Money")) {
				knopSaldoOpvragen.doClick(300);
				System.out.println("D");
			} else if ((input.equals("#")) && (paginanaam == "Money")) {
				knopCancelTransactie1.doClick(300);
				System.out.println("#");
			} else if ((input.equals("#")) && (paginanaam == "saldo")) {
				knopCancelTransactie2.doClick(300);
				System.out.println("#");
			} else if ((input.equals("#")) && (paginanaam == "chose")) {
				knopCancelTransactie3.doClick(300);
				System.out.println("#");
			} else if ((input.equals("#")) && (paginanaam == "end")) {
				knopTerugNaarHomepage.doClick(300);
				System.out.println("#");
			} else if ((input.equals("*")) && (paginanaam == "Money")) {
				knopChooseAmount.doClick(300);
				System.out.println("*");
			} else if ((input.equals("*")) && (paginanaam == "chose")) {
				knopBevestigBetaling.doClick(300);
				System.out.println("*");
			} else if ((input.equals("A")) && (paginanaam == "saldo")) {
				knopGeldKiezen.doClick(300);
				System.out.println("A");
			} else if ((input.equals("B")) && (paginanaam == "saldo")) {
				knopKrijgBonnetje.doClick(300);
				System.out.println("B");
			} else if ((input.equals("*")) && (paginanaam == "end")) {
				knopBonnetjeKiezen.doClick(300);
				System.out.println("*");
			} else if ((input.equals("A")) && (paginanaam == "Biljeten")) {
				knopEenKiezen.doClick(300);
				System.out.println("A");
			} else if ((input.equals("B")) && (paginanaam == "Biljeten")) {
				knopVijfKiezen.doClick(300);
				System.out.println("B");
			} else if ((input.equals("C")) && (paginanaam == "Biljeten")) {
				knopTienKiezen.doClick(300);
				System.out.println("C");
			} else if ((input.equals("D")) && (paginanaam == "Biljeten")) {
				knopVijftigKiezen.doClick(300);
				System.out.println("D");
			} else if ((input.equals("*")) && (paginanaam == "Biljeten")) {
				knopDirectPinnen.doClick(300);
				System.out.println("*");
			} else if ((input.equals("#")) && (paginanaam == "Biljeten")) {
				knopCancelTransactie4.doClick(300);
				System.out.println("#");
			} else if ((input.equals("#")) && (paginanaam == "error")) {
				knopReturnToPage.doClick(300);
				System.out.println("#");
			}
			if (paginanaam == "pin") {
				try {
					int number = Integer.parseInt(input);
					pinInvoer.setText(pinInvoer.getText() + number);
				} catch (Exception e) {

				}
			}
			if (paginanaam == "chose") {
				try {
					int number = Integer.parseInt(input);
					txtInput2.setText(txtInput2.getText() + number);
				} catch (Exception e) {

				}
			}
			timeout.beginTime=System.currentTimeMillis();
			playsound();
		}
		if(paginanaam=="home")
		{
			if (input.length() == 8) {
				pasnummer = input;
				System.out.println("pasnummer " + pasnummer);
			}
			if (input.length() == 6) {
				banknummer = input;
				System.out.println("banknummer " + banknummer);
			}
			if((banknummer!="")&&(pasnummer!=""))
			{
				System.out.println("pas detected");
				knopLoginPin.doClick(300);
			}
		}
		System.out.println("  "+pasnummer);
		System.out.println("  "+banknummer);

	}

	public void playsound() {
		
		 sound s = new sound(path+"/boing1.wav");
	     s.start();
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