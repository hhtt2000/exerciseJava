package example.chat.server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerGui extends JFrame implements ActionListener {

	private JTextArea textArea = new JTextArea(40, 25);
	private JTextField textField = new JTextField(25);
	private Server server;
	
	public ServerGui() {
		add(textArea, BorderLayout.CENTER);
		add(textField, BorderLayout.SOUTH);
		textField.addActionListener(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(200, 100, 400, 600);
		setTitle("chitchat");
		
		server = new Server(this);
		server.useServer();
	}
	
	public static void main(String[] args) {
		new ServerGui();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = textField.getText()+"\n";
		textArea.append(msg);
		textField.setText("");
	}

	public void appendMsg(String msg) {
		textArea.append(msg);
	}

}
