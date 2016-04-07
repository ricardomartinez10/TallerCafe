import java.io.File;

import processing.core.PApplet;
import processing.data.XML;

public class ControlXml {
	
	PApplet app;
	XML usuarios;
	File archivo;
	
	
	public ControlXml(PApplet app){
	this.app=app;
	
	archivo=new File("../data/usuarios.xml");
	
	if(archivo.exists()){
		usuarios=app.loadXML("../data/usuarios.xml");
	}else{
		usuarios=app.parseXML("<Usuarios></Usuarios>");
		
	}
	
	}
	
	public boolean asgregarUsuario(String usuario,String clave,String nombre,String correo){
		
		boolean existe=false;
		boolean agregado=false;
		
		XML []hijos=usuarios.getChildren("Usuario");
		for (int i = 0; i < hijos.length; i++) {
			if(hijos[i].getString("user").equals(usuario)){
				existe=true;
			}
			
		}
		
		if(!existe){
			
			
			XML hijo=usuarios.addChild("Usuario");
			hijo.setString("user", usuario);
			hijo.setString("clave",clave);
			hijo.setString("nombre", nombre);
			hijo.setString("correo",correo);
			app.saveXML(usuarios,"../data/usuarios.xml");

			agregado=true;
		}
		
		return agregado;
		
	}
	
	public int validarUsuario(String usuario,String clave){
		
		int estado=0;
		
		XML []hijo=usuarios.getChildren("Usuario");
		for (int i = 0; i < hijo.length; i++) {
			if(hijo[i].getString("user").equals(usuario)){
				if(hijo[i].getString("clave").equals(clave)){
					estado=1;
				}else{
					estado=2;
				}
			}
			
		}
		System.out.println(estado);
		return estado;
	}

}
