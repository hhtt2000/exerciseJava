package example.chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private ClientGui clientGui;
	private String name;
	
	public void setClientGui(ClientGui clientGui) {
		this.clientGui = clientGui;
	}

	public Client(ClientGui clientGui) {
		try {
			socket = new Socket("127.0.0.1", 8888);
			setClientGui(clientGui);
		} catch (IOException e) {
			System.out.println("Ŭ���̾�Ʈ ���� ���� ����.");
		}
	}
	
	public void useClient(String name) {
		try {
			this.name = name;
			clientGui.appendMsg("������ �����.\n");
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			sendMsg(name);
			clientGui.appendMsg("���� �޼��� ���� �ۼ�\n");
			while(dis != null) {
				String msg = dis.readUTF();
				clientGui.appendMsg(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dos.close();
				dis.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	public void sendMsg(String msg) {
		try{
			dos.writeUTF(msg);			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
