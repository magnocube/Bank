package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	public String pasnummer = "";
	public String banknummer = "";

	public MainWindow() {
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		// setUndecorated(true);
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

		add(mainPanel); // zodat de mainPanel ook daadwerkelijk op mij JFrame
						// komt,,, mainPanel is namelijk maar een panel.. en
						// geen jframe
		c.show(mainPanel, "home"); // op deze marnier switch je van tabbladen
		
		knopNaarGeldKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = pinInvoer.getText();
				if(input!="")
				{
					int pin = Integer.parseInt(input);
					if (pin == 1234) {
						c.show(mainPanel, "Money");
						paginanaam = "Money";
					}
				}
				pinInvoer.setText("");	
				
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
				saldoweergeven.setText("uw saldo is " + bankAccount);
				paginanaam = "saldo";
			}
		});
		knopCancelTransactie1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "home");
				paginanaam = "home";
				banknummer = "";
				pasnummer = "";
			}
		});
		knopCancelTransactie2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "home");
				paginanaam = "home";
				banknummer = "";
				pasnummer = "";
			}
		});
		knopCancelTransactie3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "home");
				paginanaam = "home";
				banknummer = "";
				pasnummer = "";
			}
		});
		knopTerugNaarHomepage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "home");
				paginanaam = "home";
				banknummer = "";
				pasnummer = "";
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
				c.show(mainPanel, "chose");
				paginanaam = "chose";
			}
		});
		knopKrijgBonnetje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "Biljeten");
				paginanaam = "Biljeten";
			}
		});
		knopBonnetjeKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "home");
				paginanaam = "home";
				banknummer = "";
				pasnummer = "";
			}
		});
		knopBriefjeTienKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "end");
				gepint.setText("de hoeveelheid die u gepind hebt is " + money);
				paginanaam = "end";
			}
		});
		knopBriefjeTwintigKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "end");
				gepint.setText("de hoeveelheid die u gepind hebt is " + money);
				paginanaam = "end";
			}
		});
		knopBriefjeVeertigKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "end");
				gepint.setText("de hoeveelheid die u gepind hebt is " + money);
				paginanaam = "end";
			}
		});
		knopBriefjeTachtigKiezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "end");
				gepint.setText("de hoeveelheid die u gepind hebt is " + money);
				paginanaam = "end";
			}
		});
		knopDirectPinnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "end");
				gepint.setText("de hoeveelheid die u gepind hebt is " + money);
				paginanaam = "end";
			}
		});
		knopCancelTransactie4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show(mainPanel, "home");
				paginanaam = "home";
				banknummer = "";
				pasnummer = "";
			}
		});
	}

	CardLayout c = new CardLayout();
	JPanel mainPanel = new JPanel();

	JLabel knopLoginPin = new JLabel("Hou uw pinpas bij de scanner");
	JButton knopNaarGeldKiezen = new JButton("*");
	JButton knopTienRoebelsKiezen = new JButton("A");
	JButton knopTwintigRoebelsKiezen = new JButton("B");
	JButton knopVeertigRoebelsKiezen = new JButton("C");
	JButton knopSaldoOpvragen = new JButton("D");
	JButton knopCancelTransactie1 = new JButton("#");
	JButton knopCancelTransactie2 = new JButton("#");
	JButton knopCancelTransactie3 = new JButton("#");
	JButton knopCancelTransactie4 = new JButton("#");
	JButton knopChooseAmount = new JButton("*");
	JButton knopTerugNaarHomepage = new JButton("Terug naar homepage");
	JButton knopBevestigBetaling = new JButton("*");
	JButton knopBonnetjeKiezen = new JButton("*");
	JButton knopGeldKiezen = new JButton("A");
	JButton knopKrijgBonnetje = new JButton("B");
	JButton knopBriefjeTienKiezen = new JButton("A");
	JButton knopBriefjeTwintigKiezen = new JButton("B");
	JButton knopBriefjeVeertigKiezen = new JButton("C");
	JButton knopBriefjeTachtigKiezen = new JButton("D");
	JButton knopDirectPinnen = new JButton("*");

	JLabel saldoweergeven;
	JLabel gepint;

	JPanel j1 = homePanel();
	JPanel j2 = pinInvoerPanel();
	JPanel j3 = geldKiesPanel();
	JPanel j4 = eindeTransactiePanel();
	JPanel j5 = chooseAmountPanel();
	JPanel j6 = saldoOpvragen();
	JPanel j7 = BanbiljetenKiezen();

	JPasswordField pinInvoer;
	JTextField txtInput2;

	int pinCode = 1234, money = 0, bankAccount = 900;
	String paginanaam = "home";

	private JPanel homePanel() {
		paginanaam = "home";
		JPanel j = new JPanel();
		j.setLayout(null);

		knopLoginPin.setBounds(640, 400, 550, 60);
		knopLoginPin.setFont(new Font("MS PGothic", Font.BOLD, 30));
		j.add(knopLoginPin);

		JLabel label = new JLabel("Welkom bij The dark bank");
		label.setFont(new Font("MS PGothic", Font.BOLD, 60));
		label.setBounds(540, 250, 1000, 122);
		j.add(label);

		JLabel jLabelObject = new JLabel();
		jLabelObject.setIcon(new ImageIcon("C:/Users/Rene Schouten/Documents/project3/homepage.png"));
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

		JLabel jLabelObject = new JLabel();
		jLabelObject.setIcon(new ImageIcon("C:/Users/Rene Schouten/Documents/project3/homepage.png"));
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
		tachtigRoebels.setBounds(1100, 670, 500, 120);
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
		jLabelObject.setIcon(new ImageIcon("C:/Users/Rene Schouten/Documents/project3/homepage.png"));
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

		j.add(knopBonnetjeKiezen);
		knopBonnetjeKiezen.setBounds(1780, 680, 100, 90);
		knopBonnetjeKiezen.setFont(new Font("MS PGothic", Font.BOLD, 60));

		j.add(knopTerugNaarHomepage);
		knopTerugNaarHomepage.setBounds(630, 400, 700, 60);
		knopTerugNaarHomepage.setFont(new Font("MS PGothic", Font.BOLD, 30));

		JLabel jLabelObject = new JLabel();
		jLabelObject.setIcon(new ImageIcon("C:/Users/Rene Schouten/Documents/project3/homepage.png"));
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
		jLabelObject.setIcon(new ImageIcon("C:/Users/Rene Schouten/Documents/project3/homepage.png"));
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
		jLabelObject.setIcon(new ImageIcon("C:/Users/Rene Schouten/Documents/project3/homepage.png"));
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

		JLabel tienRoebels = new JLabel("Briefjes van tien");
		tienRoebels.setFont(f);
		tienRoebels.setBounds(1200, 370, 600, 120);
		j.add(tienRoebels);
		JLabel twintigRoebels = new JLabel("Briefjes van twintig");
		twintigRoebels.setFont(f);
		twintigRoebels.setBounds(1200, 470, 600, 120);
		j.add(twintigRoebels);
		JLabel veertigRoebels = new JLabel("Briefjes van veertig");
		veertigRoebels.setFont(f);
		veertigRoebels.setBounds(1200, 570, 600, 120);
		j.add(veertigRoebels);
		JLabel tachtigRoebels = new JLabel("Briefjes van tachtig");
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

		j.add(knopBriefjeTienKiezen);
		knopBriefjeTienKiezen.setBounds(1780, 380, 100, 90);
		knopBriefjeTienKiezen.setFont(f);

		j.add(knopBriefjeTwintigKiezen);
		knopBriefjeTwintigKiezen.setBounds(1780, 480, 100, 90);
		knopBriefjeTwintigKiezen.setFont(f);

		j.add(knopBriefjeVeertigKiezen);
		knopBriefjeVeertigKiezen.setBounds(1780, 580, 100, 90);
		knopBriefjeVeertigKiezen.setFont(f);

		j.add(knopBriefjeTachtigKiezen);
		knopBriefjeTachtigKiezen.setBounds(1780, 680, 100, 90);
		knopBriefjeTachtigKiezen.setFont(f);

		j.add(knopCancelTransactie4);
		knopCancelTransactie4.setBounds(40, 680, 100, 90);
		knopCancelTransactie4.setFont(f);

		j.add(knopDirectPinnen);
		knopDirectPinnen.setBounds(40, 580, 100, 90);
		knopDirectPinnen.setFont(f);

		JLabel jLabelObject = new JLabel();
		jLabelObject.setIcon(new ImageIcon("C:/Users/Rene Schouten/Documents/project3/homepage.png"));
		jLabelObject.setBounds(0, 0, 1920, 1080);
		j.add(jLabelObject);

		return j;
	}

	public void give(String input) {
		// if(paginanaam=="choose")
		// {
		playsound();
		System.out.println(input + " " + paginanaam);
		if (input.length() == 1) {
			if ((input.equals("*")) && (paginanaam == "pin")) {
				knopNaarGeldKiezen.doClick(10);
				System.out.println("*");
			} else if ((input.equals("A")) && (paginanaam == "Money")) {
				knopTienRoebelsKiezen.doClick(10);
				System.out.println("A");
			} else if ((input.equals("B")) && (paginanaam == "Money")) {
				knopTwintigRoebelsKiezen.doClick(10);
				System.out.println("B");
			} else if ((input.equals("C")) && (paginanaam == "Money")) {
				knopVeertigRoebelsKiezen.doClick(10);
				System.out.println("C");
			} else if ((input.equals("D")) && (paginanaam == "Money")) {
				knopSaldoOpvragen.doClick(10);
				System.out.println("D");
			} else if ((input.equals("#")) && (paginanaam == "Money")) {
				knopCancelTransactie1.doClick(10);
				System.out.println("#");
			} else if ((input.equals("#")) && (paginanaam == "saldo")) {
				knopCancelTransactie2.doClick(10);
				System.out.println("#");
			} else if ((input.equals("#")) && (paginanaam == "chose")) {
				knopCancelTransactie3.doClick(10);
				System.out.println("#");
			} else if ((input.equals("#")) && (paginanaam == "end")) {
				knopTerugNaarHomepage.doClick(10);
				System.out.println("#");
			} else if ((input.equals("*")) && (paginanaam == "Money")) {
				knopChooseAmount.doClick(10);
				System.out.println("*");
			} else if ((input.equals("*")) && (paginanaam == "chose")) {
				knopBevestigBetaling.doClick(10);
				System.out.println("*");
			} else if ((input.equals("A")) && (paginanaam == "saldo")) {
				knopGeldKiezen.doClick(10);
				System.out.println("A");
			} else if ((input.equals("B")) && (paginanaam == "saldo")) {
				knopKrijgBonnetje.doClick(10);
				System.out.println("B");
			} else if ((input.equals("*")) && (paginanaam == "end")) {
				knopBonnetjeKiezen.doClick(10);
				System.out.println("*");
			} else if ((input.equals("A")) && (paginanaam == "Biljeten")) {
				knopBriefjeTienKiezen.doClick(10);
				System.out.println("A");
			} else if ((input.equals("B")) && (paginanaam == "Biljeten")) {
				knopBriefjeTwintigKiezen.doClick(10);
				System.out.println("B");
			} else if ((input.equals("C")) && (paginanaam == "Biljeten")) {
				knopBriefjeVeertigKiezen.doClick(10);
				System.out.println("C");
			} else if ((input.equals("D")) && (paginanaam == "Biljeten")) {
				knopBriefjeTachtigKiezen.doClick(10);
				System.out.println("D");
			} else if ((input.equals("*")) && (paginanaam == "Biljeten")) {
				knopDirectPinnen.doClick(10);
				System.out.println("*");
			} else if ((input.equals("#")) && (paginanaam == "Biljeten")) {
				knopCancelTransactie4.doClick(10);
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
				c.show(mainPanel, "pin");
				paginanaam = "pin";
			}
		}
		

	}

	public void playsound() {	
	new SoundUtils().start();
	//sound.start();
	}
	
}
