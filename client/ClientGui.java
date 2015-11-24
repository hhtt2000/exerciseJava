package example.chat.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientGui extends JFrame implements ActionListener {

	private JTextArea textArea = new JTextArea(40, 25);
	private JTextField textField = new JTextField(25);
	private Client client;
	
	public ClientGui() {
		add(textArea, BorderLayout.CENTER);
		add(textField, BorderLayout.SOUTH);
		textField.addActionListener(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(600, 100, 400, 600);
		setTitle("chitchatForClient");
		
		client = new Client(this);
		client.useClient();
	}
	
	public static void main(String[] args) {
		ClientGui gui = new ClientGui();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = textField.getText()+"\n";
		client.sendMsg(msg);
		textArea.append(msg);
		textField.setText("");
	}

	public void appendMsg(String msg) {
		textArea.append(msg);
	}
}
