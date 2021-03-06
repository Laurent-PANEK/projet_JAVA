package com.classe;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.classe.Numbers;

/**
 * 
 * Nom du fichier: Fenetre.java Date: 29 mars 2017 Membres du Projet: Laurent
 * Panek, Abdessamad Douhi Abdessalam Benharira, Branis Lamrani Chef de Projet:
 * Branis Lamrani
 */
public class Fenetre extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	BufferedReader in;
	PrintWriter out;
	private CardLayout cl = new CardLayout();
	private String[] listContent = { "CARD_1", "CARD_2", "CARD_3", "CARD_4", "CARD_5" };
	private String isMode;
	private JPanel content = new JPanel();
	private JPanel menu = new JPanel();
	private JPanel mode = new JPanel();
	private JPanel plateau = new JPanel();
	private JPanel network = new JPanel();
	private JPanel onlinePanel = new JPanel();
	private JButton goNB = new JButton("Nombre");
	private JButton goMot = new JButton("Mot");
	private JButton play = new JButton("Jouer");
	private JButton ans = new JButton("Soumettre");
	private JButton restart = new JButton("Restart");
	private JButton solo = new JButton("Solo");
	private JButton multi = new JButton("Multi");
	private JButton online = new JButton("Online");
	private JButton toMenu = new JButton("Menu");
	private JButton close = new JButton("Fermer");
	private JButton refresh = new JButton("Actualiser");
	private JButton send = new JButton("Envoyer");
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JList<String> messageArea = new JList<String>(listModel);
	private JRadioButton infinite_chance = new JRadioButton("Coup Infini");
	private JRadioButton fixed_chance = new JRadioButton("Coup maximum");
	private JTextField nb_coup = new JTextField(5);
	private JTextField answer = new JTextField(20);
	private JPasswordField val_magique = new JPasswordField(10);
	private JLabel label1 = new JLabel("");
	private JLabel labelInfo = new JLabel();

	private JLabel labelReponse = new JLabel();
	private JLabel labelIndice = new JLabel();
	private JLabel info_val = new JLabel("");
	private JLabel error = new JLabel("Vous devez entrer un nombre");
	private JLabel background = new JLabel();
	private JLabel background2 = new JLabel();
	private Numbers jeuNB = new Numbers(answer, labelReponse, labelInfo);
	private Mots jeuMots = new Mots(answer, labelReponse, labelInfo, labelIndice);
	private Socket socket;

	/**
	 * Cette méthode permet de définir la fenêtre et ses caractéristiques
	 * 
	 * @throws IOException
	 */
	public void defFenetre() throws IOException {

		this.setTitle("Mot ou Nombre Magique");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Image/Icon.png"));
		this.setSize(650, 550);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		JPanel boutonPane = new JPanel();
		toMenu.addActionListener(this);
		close.addActionListener(this);

		this.defPlateau();
		this.defNetwork();
		this.defMode();
		this.defOnline();
		this.defMenu();

		boutonPane.add(toMenu);
		boutonPane.add(close);
		// On définit le layout
		content.setLayout(cl);
		// On ajoute les cartes à la pile avec un nom pour les retrouver
		content.add(this.menu, listContent[0]);
		content.add(this.mode, listContent[1]);
		content.add(this.network, listContent[2]);
		content.add(this.onlinePanel, listContent[3]);
		content.add(this.plateau, listContent[4]);

		add(content, BorderLayout.CENTER);
		add(boutonPane, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	/**
	 * Cette méthode permet de définir le menu
	 */
	private void defMenu() {

		menu.setLayout(new GridBagLayout());
		menu.setPreferredSize(new Dimension(650, 550));

		JLabel titre = new JLabel("");
		menu.add(titre, posElement(0, 0, 1, 1, 10, 10, "REMAINDER", true, "CENTER"));
		// Définition de l'action du boutonthis.background.setIcon(new
		// ImageIcon("Image/menujava1.png"));
		this.goNB.addActionListener(this);
		// Définition de l'action du bouton2
		this.goMot.addActionListener(this);
		menu.add(goNB, posElement(0, 1, 1, 1, 3, 10, "", true, "FIRST_LINE_END", true, 0, 0, 0, 10));

		menu.add(goMot, posElement(1, 1, 1, 1, 3, 10, "REMAINDER", true, "FIRST_LINE_START"));

		this.background.setIcon(new ImageIcon("Image/menujava1.png"));
		menu.add(background, posElement(0, 0, 0, 0, 0, 0));
	}

	/**
	 * , posElement(0, 0, 0, 0, 0, 0));
	 * 
	 * Cette méthode permet de définir le Mode
	 */
	private void defMode() {
		mode.setLayout(new GridBagLayout());
		mode.setPreferredSize(new Dimension(650, 550));

		this.play.addActionListener(this);

		ButtonGroup group = new ButtonGroup();

		group.add(this.infinite_chance);
		group.add(this.fixed_chance);
		this.infinite_chance.setFont(new Font("", Font.PLAIN, 20));

		this.infinite_chance.setSelected(true);
		this.infinite_chance.setFont(new Font("", Font.PLAIN, 20));
		this.infinite_chance.setForeground(Color.red);
		this.infinite_chance.addActionListener(this);
		this.fixed_chance.setForeground(Color.ORANGE);
		this.fixed_chance.setFont(new Font("", Font.PLAIN, 20));
		this.fixed_chance.addActionListener(this);

		this.nb_coup.setVisible(false);
		this.nb_coup.setText("10");
		this.val_magique.addActionListener(this);
		this.val_magique.setVisible(false);
		info_val.setVisible(false);
		info_val.setForeground(Color.black);
		this.mode.add(val_magique, posElement(1, 2, 1, 1, 10, 10, "REMAINDER", true, "FIRST_LINE_START"));
		this.mode.add(info_val, posElement(0, 2, 1, 1, 10, 10, "", true, "FIRST_LINE_END", true, 0, 0, 0, 10));

		this.mode.add(this.infinite_chance, posElement(0, 0, 1, 1, 10, 10, "", false, "", true, 0, 0, 0, 10));
		this.mode.add(this.fixed_chance, posElement(1, 0, 1, 1, 10, 10, "REMAINDER", false, ""));
		this.mode.add(nb_coup, posElement(1, 1, 1, 1, 10, 10, "REMAINDER", true, "PAGE_START", true, 10, 0, 0, 0));
		this.mode.add(play, posElement(0, 3, 1, 1, 10, 10, "REMAINDER", true, "CENTER"));

	}

	/**
	 * Cette méthode permet de définir le plateau
	 */

	private void defPlateau() {
		ans.addActionListener(this);
		restart.addActionListener(this);
		answer.addActionListener(this);
		plateau.setLayout(new GridBagLayout());
		plateau.setPreferredSize(new Dimension(650, 550));

		labelInfo.setFont(new Font("", Font.PLAIN, 20));
		plateau.add(labelInfo, posElement(0, 0, 1, 1, 10, 10, "REMAINDER", true, "PAGE_END"));
		plateau.add(answer, posElement(0, 3, 1, 1, 10, 10, "REMAINDER"));
		plateau.add(ans, posElement(0, 5, 1, 1, 10, 20, "", true, "FIRST_LINE_END", true, 0, 0, 0, 10));
		plateau.add(restart, posElement(1, 5, 1, 1, 10, 20, "REMAINDER", true, "FIRST_LINE_START"));
		labelReponse.setFont(new Font("", Font.PLAIN, 20));
		plateau.add(labelReponse, posElement(0, 4, 1, 1, 10, 10, "REMAINDER", true, "PAGE_START"));
		labelIndice.setFont(new Font("", Font.PLAIN, 20));
		plateau.add(labelIndice, posElement(0, 2, 1, 1, 10, 10, "REMAINDER"));
	}

	/**
	 * Cette méthode permet de définir le mode de jeu
	 */
	private void defNetwork() {
		network.setLayout(new GridBagLayout());
		network.setPreferredSize(new Dimension(650, 550));

		JLabel text = new JLabel("");
		network.add(text, posElement(0, 0, 1, 1, 10, 10, "REMAINDER", true, "CENTER"));

		solo.addActionListener(this);
		multi.addActionListener(this);
		online.addActionListener(this);

		network.add(solo, posElement(0, 1, 1, 1, 55, 10, "", true, "FIRST_LINE_END"));
		network.add(multi, posElement(1, 1, 1, 1, 5, 10, "", true, "PAGE_START"));
		network.add(online, posElement(2, 1, 1, 1, 55, 10, "REMAINDER", true, "FIRST_LINE_START"));
		this.background2.setIcon(new ImageIcon("Image/menujava2.png"));
		network.add(background2, posElement(0, 0, 0, 0, 0, 0));
	}

	private void defOnline() {
		onlinePanel.setLayout(new GridBagLayout());
		onlinePanel.setPreferredSize(new Dimension(650, 550));

		refresh.addActionListener(this);
		send.addActionListener(this);

		JLabel info = new JLabel("Utilisateur connecté");
		onlinePanel.add(info, posElement(0, 0, 1, 1, 10, 5, "REMAINDER"));
		onlinePanel.add(messageArea, posElement(0, 1, 1, 1, 10, 10, "REMAINDER"));
		onlinePanel.add(send, posElement(0, 2, 1, 1, 10, 5, "REMAINDER"));
		onlinePanel.add(refresh, posElement(0, 3, 1, 1, 10, 10, "REMAINDER"));
	}

	/**
	 * Prompt for and return the address of the server.
	 */
	private String getServerAddress() {
		return JOptionPane.showInputDialog(this, "Enter IP Address of the Server:", "Welcome to the Chatter",
				JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * Prompt for and return the desired screen name.
	 */
	private String getNameServ() {
		return JOptionPane.showInputDialog(this, "Choose a screen name:", "Screen name selection",
				JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Connects to the server then enters the processing loop.
	 */
	private void run() throws IOException {

		// Make connection and initialize streams
		String serverAddress = getServerAddress();
		socket = new Socket(serverAddress, 9001);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

		// Process all messages from server, according to the protocol.
		while (true) {
			String line = in.readLine();
			if (line == null) {
				System.out.println("error input null");
				break;
			} else if (line.startsWith("SUBMITNAME")) {
				out.println(getNameServ());
			} else if (line.startsWith("CONNECTED")) {
				System.out.println(line);
				listModel.addElement(line.substring(9));
				messageArea.setModel(listModel);
				onlinePanel.validate();
			} else if (line.startsWith("CLOSE")) {
				break;
			}
		}
	}

	/**
	 * Gestion de positions des éléments
	 * 
	 * @param posX
	 * @param posY
	 * @param gridHeight
	 * @param gridWidth
	 * @param WeightX
	 * @param WeightY
	 * @param EndLine
	 * @param Anchor
	 * @param posAnchor
	 * @param Inset
	 * @param InsetTop
	 * @param InsetLeft
	 * @param InsetBottom
	 * @param InsetRight
	 * @return position
	 */
	private GridBagConstraints posElement(int posX, int posY, int gridHeight, int gridWidth, int WeightX, int WeightY,
			String EndLine, boolean Anchor, String posAnchor, boolean Inset, int InsetTop, int InsetLeft,
			int InsetBottom, int InsetRight) {

		GridBagConstraints pos = new GridBagConstraints();
		pos.gridx = posX;
		pos.gridy = posY;
		pos.gridheight = gridHeight;
		pos.gridwidth = gridWidth;
		pos.weightx = WeightX;
		pos.weighty = WeightY;
		switch (EndLine) {
		case "REMAINDER":
			pos.gridwidth = GridBagConstraints.REMAINDER;
			break;

		case "RELATIVE":
			pos.gridwidth = GridBagConstraints.RELATIVE;
			break;

		default:
			break;
		}
		if (Anchor) {

			switch (posAnchor) {
			case "PAGE_START":
				pos.anchor = GridBagConstraints.PAGE_START;
				break;

			case "PAGE_END":
				pos.anchor = GridBagConstraints.PAGE_END;
				break;

			case "FIRST_LINE_START":
				pos.anchor = GridBagConstraints.FIRST_LINE_START;
				break;

			case "FIRST_LINE_END":
				pos.anchor = GridBagConstraints.FIRST_LINE_END;
				break;

			case "LINE_START":
				pos.anchor = GridBagConstraints.LINE_START;
				break;

			case "LINE_END":
				pos.anchor = GridBagConstraints.LINE_END;
				break;

			case "LAST_LINE_START":
				pos.anchor = GridBagConstraints.LAST_LINE_START;
				break;

			case "LAST_LINE_END":
				pos.anchor = GridBagConstraints.LAST_LINE_END;
				break;

			case "CENTER":
				pos.anchor = GridBagConstraints.CENTER;
				break;

			default:
				break;
			}
			if (Inset) {
				pos.insets = new Insets(InsetTop, InsetLeft, InsetBottom, InsetRight);
			}

		}
		return pos;
	}

	private GridBagConstraints posElement(int posX, int posY, int gridHeight, int gridWidth, int WeightX, int WeightY,
			String EndLine, boolean Anchor, String posAnchor) {
		return posElement(posX, posY, gridHeight, gridWidth, WeightX, WeightY, EndLine, Anchor, posAnchor, false, 0, 0,
				0, 0);
	}

	private GridBagConstraints posElement(int posX, int posY, int gridHeight, int gridWidth, int WeightX, int WeightY,
			String EndLine) {
		return posElement(posX, posY, gridHeight, gridWidth, WeightX, WeightY, EndLine, false, "");
	}

	private GridBagConstraints posElement(int posX, int posY, int gridHeight, int gridWidth, int WeightX, int WeightY) {
		return posElement(posX, posY, gridHeight, gridWidth, WeightX, WeightY, "");
	}

	/**
	 * Gestion des actions
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == goMot) {
			cl.show(content, listContent[2]);
			isMode = "mot";
			labelIndice.setVisible(true);
			label1.setText("Devinez le mot magique");
			plateau.validate();
		} else if (source == goNB) {
			cl.show(content, listContent[2]);
			isMode = "nb";
			labelIndice.setVisible(false);
			label1.setText("Devinez le nombre magique");
			plateau.validate();
		} else if (source == val_magique) {
			play.doClick();
		}
		else if (source == play) {
			boolean play = true;
			try {
				if (nb_coup.isVisible()) {
					nb_coup.setText("10");
					nb_coup.requestFocus();
					switch (isMode) {
					case "nb":
						jeuNB.setCoup(Integer.parseInt(nb_coup.getText()));
						break;
					case "mot":
						jeuMots.setCoup(Integer.parseInt(nb_coup.getText()));
						break;
					default:
						break;
					}
				} else {
					switch (isMode) {
					case "nb":
						jeuNB.setIllimite();
						break;
					case "mot":
						jeuMots.setIllimite();
						break;
					default:
						break;
					}
				}
				if (val_magique.isVisible()) {
					switch (isMode) {
					case "nb":
						if (new String(val_magique.getPassword()).matches("^\\d+$")) {
							jeuNB.setNB(Integer.parseInt(new String(val_magique.getPassword())));
							error.setVisible(false);
							play = true;
						} else {
							error.setText("Nombre magique incorrect");
							error.setVisible(true);
							mode.add(error, posElement(1, 1, 1, 1, 10, 10));
							play = false;
						}
						break;
					case "mot":
						if (new String(val_magique.getPassword()).matches("^[A-Za-z, ]++$")) {
							jeuMots.setMot(new String(val_magique.getPassword()));
							error.setVisible(false);
							play = true;
						} else {
							error.setText("Mot Magique vide");
							error.setVisible(true);
							mode.add(error, posElement(1, 1, 1, 1, 10, 10));
							play = false;
						}
						break;
					default:
						break;
					}
					mode.validate();
				} else {
					switch (isMode) {
					case "nb":
						jeuNB.setNB(jeuNB.rand_val());
						break;
					case "mot":
						jeuMots.setMot(jeuMots.rand_word());
						break;
					default:
						break;
					}
				}
				switch (isMode) {
				case "nb":
					jeuNB.getInfo();
					break;
				case "mot":
					jeuMots.getInfo();
					break;
				default:
					break;
				}
				nb_coup.setText("");
				val_magique.setText("");
				answer.requestFocus();
				plateau.validate();
				if (play) {
					cl.show(content, listContent[4]);
				}
			} catch (Exception e2) {
				mode.add(error, posElement(1, 1, 1, 1, 10, 10));
				mode.validate();
			}

		} else if (source == fixed_chance) {
			if (this.fixed_chance.isSelected()) {
				this.nb_coup.setVisible(true);
				this.mode.validate();
			}
		} else if (source == infinite_chance) {
			if (this.infinite_chance.isSelected()) {
				this.nb_coup.setVisible(false);
				this.mode.validate();
			}
		} else if (source == solo) {
			cl.show(content, listContent[1]);
			this.val_magique.setVisible(false);
			this.info_val.setVisible(false);
			this.mode.validate();
		} else if (source == multi) {
			cl.show(content, listContent[1]);
			this.val_magique.setVisible(true);
			this.info_val.setVisible(true);
			switch (isMode) {
			case "nb":
				info_val.setText("Nombre Magique");
				break;
			case "mot":
				info_val.setText("Mot Magique");
				break;
			default:
				break;
			}
			this.mode.validate();
		} else if (source == answer) {
			ans.doClick();
		} else if (source == ans) {
			switch (isMode) {
			case "nb":
				jeuNB.sendValue();
				jeuNB.start();
				break;
			case "mot":
				jeuMots.sendValue();
				jeuMots.start();
				break;
			default:
				break;
			}
			answer.setText("");
			answer.requestFocus();
			plateau.validate();
		} else if (source == restart) {
			switch (isMode) {
			case "nb":
				jeuNB.restart();
				break;
			case "mot":
				jeuMots.restart();
				break;
			default:
				break;
			}
			plateau.validate();
			error.setVisible(false);
			nb_coup.setText("10");
			mode.validate();
			cl.show(content, listContent[1]);
		} else if (source == toMenu) {
			error.setVisible(false);
			nb_coup.setText("10");
			mode.validate();
			listModel.clear();
			messageArea.setModel(listModel);
			onlinePanel.validate();
			cl.show(content, listContent[0]);
		} else if (source == close) {
			this.dispose();
		} else if (source == online) {
			cl.show(content, listContent[3]);
			try {
				this.run();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (source == refresh) {
			listModel.clear();
			out.println("REFRESH");
			while (true) {
				String line;
				try {
					line = in.readLine();
					if (line == null) {
						System.out.println("error input null");
						break;
					} else if (line.startsWith("SUBMITNAME")) {
						out.println(getNameServ());
					} else if (line.startsWith("CONNECTED")) {
						System.out.println(line);
						listModel.addElement(line.substring(9));
						messageArea.setModel(listModel);
						onlinePanel.validate();
					} else if (line.startsWith("CLOSE")) {
						break;
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if (source == send) {
			if (!messageArea.isSelectionEmpty()) {
				String[] infoUser = messageArea.getSelectedValue().split(" ");
				out.println("THREAD" + infoUser[1]);
				System.out.println("send ip");
				while (true) {
					String line;
					try {
						line = in.readLine();
						if (line == null) {
							System.out.println("error input null");
							break;
						} else if (line.startsWith("ANSWER")) {
							System.out.println(line.substring(6));
						} else if (line.startsWith("CLOSE")) {
							break;
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
}