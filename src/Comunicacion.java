import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectInputValidation;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Observable;

import comun.Publicacion;
import comun.Registro;
import processing.core.PApplet;

public class Comunicacion extends Observable implements Runnable {

	DatagramSocket ds;
	InetAddress ip;
	boolean validar;
	ControlXml control;
	ControlXmlPost controlPost;

	int id;

	PApplet app;

	public Comunicacion(PApplet app) {
		this.app = app;
		validar = false;

		try {
			ds = new DatagramSocket(5000);
			ip = InetAddress.getByName("192.168.0.18");
			System.out.println("Se inicia el Socket");
		} catch (IOException e) {
			e.printStackTrace();
		}

		control = new ControlXml(app);
		controlPost=new ControlXmlPost(app);

	}

	public void run() {
		while (true) {
			try {
				recibir();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void validarContra() throws IOException {
		byte[] datos = new byte[1000];
		DatagramPacket dp = new DatagramPacket(datos, datos.length);
		ds.receive(dp);
		Object r = deserializar(datos);

	}

	public void saludar() {

	}

	public void recibir() throws IOException {

		byte[] datos = new byte[1000];
		DatagramPacket dp = new DatagramPacket(datos, datos.length);
		ds.receive(dp);
		Object m = deserializar(datos);

		if (m instanceof Registro) {

			String estado = ((Registro) m).getEstado();
			if (estado.equals("log")) {

				String user = ((Registro) m).getNombre();
				String pass = ((Registro) m).getPass();
				
				System.out.println(user);
				
				if(control.validarUsuario(user, pass)==0){
					setChanged();
					notifyObservers("logueo:0");
					clearChanged();
				}

				if (control.validarUsuario(user, pass) == 1) {
					setChanged();
					notifyObservers("logueo:1");
					clearChanged();
				}
				if (control.validarUsuario(user, pass) == 2) {
					setChanged();
					notifyObservers("logueo:2");
					clearChanged();
				}

			}

			if (estado.equals("reg")) {

				String user = ((Registro) m).getNombre();
				System.out.println(user);
				String pass = ((Registro) m).getPass();
				String nombre = ((Registro) m).getUsuaio();
				String correo = ((Registro) m).getCorreo();
 
				
				if(control.asgregarUsuario(user, pass, nombre, correo)==true){
					setChanged();
					notifyObservers("registro:1");
					clearChanged();
				}else{
					setChanged();
					notifyObservers("registro:2");
					clearChanged();
				}
				
				

			}

		}
		if(m instanceof Publicacion){
			String estado=((Publicacion)m).getEstado();
			String usuario=((Publicacion)m).getUsuario();
			String comentario=((Publicacion)m).getComent();
			System.out.println(comentario);
			int image=((Publicacion)m).getImg();
			
			if(estado.equals("nueva")){
				
				controlPost.agregarPublicacion(usuario, comentario,image);
				setChanged();
				notifyObservers("post"+":"+usuario+":"+comentario+":"+image);
				clearChanged();
			}
			
			if(estado.equals("pido")){
				System.out.println(estado);
				setChanged();
				notifyObservers(estado);
				clearChanged();
			}

		}
			

	}
	
	 public void enviarPublicacion(String estado,String usuario,String coment,int imagen){
	        if(ds!=null){
	            Publicacion p= new Publicacion(estado,usuario,coment,imagen);
	            byte[]datos=serializar(p);
	            DatagramPacket dp=new DatagramPacket(datos,datos.length,ip,5000);

	            try {
	                ds.send(dp);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	public void enviar(String estado) {
		Registro r = new Registro(null, null, estado, null, null);
		byte[] datos = serializar(r);
		DatagramPacket dp = new DatagramPacket(datos, datos.length, ip, 5000);
		try {
			ds.send(dp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public byte[] serializar(Object c) {

		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(bs);
			os.writeObject(c);
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bs.toByteArray();
	}

	public Object deserializar(byte[] datos) {

		ByteArrayInputStream buff = new ByteArrayInputStream(datos);
		ObjectInputStream oi;
		Object c = null;
		try {
			oi = new ObjectInputStream(buff);
			c = (Object) oi.readObject();
			oi.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException f) {
			f.printStackTrace();
		}

		return c;
	}

	public void enviarPublicacion(String estado) {
		if(ds!=null){
            Publicacion p= new Publicacion(estado,null,null,2);
            byte[]datos=serializar(p);
            DatagramPacket dp=new DatagramPacket(datos,datos.length,ip,5000);

            try {
                ds.send(dp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
	}

		
	

	

}
