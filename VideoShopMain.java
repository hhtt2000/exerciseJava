package example;

import java.util.Vector;

class VideoShop {
	private Vector<String> list = new Vector<String>();
	
	public VideoShop() {
		list.add("������ ����1");
		list.add("������ ����2");
		list.add("������ ����3");
	}
	
	public synchronized String lendVideo() throws InterruptedException {
		if(list.size() == 0){
			System.out.println(Thread.currentThread().getName()+" : ���");
			this.wait();
		}
		String video = list.remove(0);
		return video;
	}
	
	public synchronized void returnVideo(String video) {
		list.add(video);
		System.out.println(Thread.currentThread().getName()+" : ����");
		this.notify();
	}
}

class Person extends Thread {
	
	public void run() {
		try {
			String video = VideoShopMain.videoShop.lendVideo();
			System.out.println(this.getName()+" : "+video+" �뿩");
			
			System.out.println(this.getName()+" : "+video+" ������");
			this.sleep(5000);
			
			System.out.println(this.getName()+" : "+video+" �ݳ�");
			VideoShopMain.videoShop.returnVideo(video);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

public class VideoShopMain {

	public static VideoShop videoShop = new VideoShop();
	
	public static void main(String[] args) {
		Person p1 = new Person();
		Person p2 = new Person();
		Person p3 = new Person();
		Person p4 = new Person();
		
		p1.start();
		p2.start();
		p3.start();
		p4.start();
		
	}
}
