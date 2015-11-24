package example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FileDownloadFromUrl {

	private static void download(String urlStr) {
		// TODO Auto-generated method stub
		try {
			URL url = new URL(urlStr);
			URLConnection urlConn = url.openConnection();
			String[] splitedUrl = urlStr.split("/");
			String fileName = splitedUrl[splitedUrl.length - 1];
			
			int ch = 0;
			InputStream is = urlConn.getInputStream();
			FileOutputStream os = new FileOutputStream("d:\\"+fileName);
			
			while((ch = is.read()) != -1){
				os.write(ch);
			}
			is.close();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String url = "이미지가 위치한 url";
		FileDownloadFromUrl.download(url);
	}

}
