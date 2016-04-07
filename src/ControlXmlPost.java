import java.io.File;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.XML;

public class ControlXmlPost {
	
	PApplet app;	
	XML publicaciones;
	File file;
	
	public ControlXmlPost(PApplet app){
		this.app=app;
		
		
		file=new File("../data/publicaciones.xml");
		
		if(file.exists()){
			publicaciones=app.loadXML("../data/publicaciones.xml");
		}else{
			publicaciones=app.parseXML("<Publicaciones></Publicaciones>");
			
		}
		
		
	}
	
	public void agregarPublicacion(String user,String desc,int imagen){
		
		XML hijo=publicaciones.addChild("Publicacion");
		hijo.setString("user", user);
		hijo.setString("dercripcion", desc);
		hijo.setInt("imagen",imagen);
		app.saveXML(publicaciones,"../data/publicaciones.xml");
		
	}
	
	public ArrayList<String> arrayPost(){
		
		ArrayList <String> misNotas= new ArrayList<String>();
		XML []hijos = publicaciones.getChildren("Publicacion");
		for (int i = 0; i < hijos.length; i++) {
			misNotas.add(hijos[i].getString("user") + ":" + hijos[i].getString("dercripcion") + ":" + hijos[i].getString("imagen"));
		}
		
		return misNotas;
		
	}

}
