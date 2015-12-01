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
			System.out.println("클라이언트 소켓 생성 에러.");
		}
	}
	
	public void useClient(String name) {
		try {
			this.name = name;
			clientGui.appendMsg("서버에 연결됨.\n");
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			sendMsg(name);
			clientGui.appendMsg("보낼 메세지 여기 작성\n");
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
