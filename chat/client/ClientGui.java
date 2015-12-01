package example.chat.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientGui extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea = new JTextArea(40, 25);
	private JTextField textField = new JTextField(25);
	private Client client;
	private String name;
	
	public ClientGui(String name) {
		add(textArea, BorderLayout.CENTER);
		add(textField, BorderLayout.SOUTH);
		textField.addActionListener(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(600, 100, 400, 600);
		setTitle("chitchatForClient");
		
		this.name = name;
		client = new Client(this);
		client.useClient(name);
	}
	
	public static void main(String[] args) {
		System.out.print("사용 할 이름을 입력해 주세요: ");
		Scanner scanner = new Scanner(System.in);
		String name = scanner.nextLine();
		new ClientGui(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = "["+name+"] "+textField.getText()+"\n";
		client.sendMsg(msg);
		textField.setText("");
	}

	public void appendMsg(String msg) {
		textArea.append(msg);
	}
}
