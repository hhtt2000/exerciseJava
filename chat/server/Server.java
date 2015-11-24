package example.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

	private ServerSocket serverSocket;
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private ServerGui serverGui;
	
	public void setServerGui(ServerGui serverGui) {
		this.serverGui = serverGui;
	}

	public Server(ServerGui serverGui) {
		try {
			serverSocket = new ServerSocket(8888);
			setServerGui(serverGui);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void useServer() {
		try {
			while(true) {
				serverGui.appendMsg("클라이언트 연결 대기중.\n");
				socket = serverSocket.accept();
				serverGui.appendMsg(socket.getInetAddress()+"이 연결됨.\n");
				
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				while(true) {
					try {
						String msg = dis.readUTF();
						serverGui.appendMsg(msg);
					} catch(SocketException e2) {
						serverGui.appendMsg(socket.getInetAddress()+" 연결 종료.\n");
						socket.close();
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dos.close();
				dis.close();
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
