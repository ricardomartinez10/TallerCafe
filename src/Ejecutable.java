import java.net.InetAddress;
import java.net.UnknownHostException;

import processing.core.*;
public class Ejecutable extends PApplet{
	
	Logica logica;
	public void setup(){
		size(200,200);
		logica =new Logica(this);
		
		try {
			System.out.println(InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void draw(){
		
	}
	public void mouseClicked(){
		logica.click();
	}

}
