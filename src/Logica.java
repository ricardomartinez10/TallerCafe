import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

public class Logica implements Observer {

	Comunicacion c;

	PApplet app;
	
	ControlXmlPost cp;

	public Logica(PApplet app) {
		this.app = app;
		c = new Comunicacion(app);
		Thread t = new Thread(c);
		t.start();
		
		c.addObserver(this);
		
		cp=new ControlXmlPost(app);

	}

	public void click() {
		c.enviar("log_resp:1");
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		String msg=(String)arg1;
		
	if(msg.contains("logueo")){	
			String separar[]=msg.split(":");
			c.enviar("log_resp:"+separar[1]);
	}
	
	if(msg.contains("registro")){
		String separar[]=msg.split(":");
		c.enviar("reg_resp:"+separar[1]);
	}
	if(msg.contains("post")){
		String separar[]=msg.split(":");
		c.enviarPublicacion("new",separar[1], separar[2],Integer.parseInt(separar[3]));
	}
	
	if (msg.equals("pido")) { 
		ArrayList<String> post = cp.arrayPost();
		for (int i = 0; i < post.size(); i++) {
			c.enviarPublicacion("Notas:" + post.get(i));
			System.out.println("envio_notas");
		}
	
	
}
			
	
		
		
	}

}
