package example.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

	private ServerSocket serverSocket;
	private Socket socket;
	private ServerGui serverGui;
	private static ConcurrentHashMap<String, DataOutputStream> clients;
	
	public void setServerGui(ServerGui serverGui) {
		this.serverGui = serverGui;
	}

	public Server(ServerGui serverGui) {
		try {
			clients = new ConcurrentHashMap<String, DataOutputStream>();
			serverSocket = new ServerSocket(8888);
			setServerGui(serverGui);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void useServer() {
		try {
			while(true) {
				socket = serverSocket.accept();
				serverGui.appendMsg(socket.getInetAddress()+"이 연결됨.\n");
				Receiver receiver = new Receiver(socket, serverGui);
				receiver.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
//	public void sendMsg(String msg) {
//		try {
//			dos.writeUTF(msg);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void addClient(String name, DataOutputStream dos) {
		clients.put(name, dos);
	}
	public static void delClient(String name) {
		clients.remove(name);
	}
	public static void sendToAll(String msg) {
		Iterator<String> it = clients.keySet().iterator();
		while(it.hasNext()) {
			DataOutputStream dos = clients.get(it.next());
			try {
				dos.writeUTF(msg);
			} catch (IOException e) {
			}
		}
	}
}

class Receiver extends Thread {
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private ServerGui serverGui;
	private String name;
	
	public Receiver(Socket socket, ServerGui serverGui) {
		this.socket = socket;
		this.serverGui = serverGui;
		try {
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			name = dis.readUTF();
			Server.sendToAll("["+name+"] 님이 참여하였습니다.\n");
			Server.addClient(name, dos);
			while(dis != null) {
				try {
					String msg = dis.readUTF();
					Server.sendToAll(msg);
					serverGui.appendMsg(msg);
				} catch(IOException e) {
					serverGui.appendMsg(socket.getInetAddress()+" 연결 종료.\n");
					Server.sendToAll("["+name+"] 님이 나가셨습니다.\n");
					Server.delClient(name);
					break;
				}
			}
		} catch(IOException e) {
		}
	}
}
