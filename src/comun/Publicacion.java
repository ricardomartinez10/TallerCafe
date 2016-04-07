package comun;

import java.io.Serializable;

public class Publicacion  implements Serializable{

	
	private String estado;
    private String usuario;
    private String coment;
    private int img;
    



    public  Publicacion(String estado,String usuario,String coment,int img){
    	this.estado=estado;
        this.usuario=usuario;
        this.coment=coment;
        this.img=img;

    }

    public String getEstado(){
        return estado;
    }

    public String getUsuario(){
        return usuario;
    }
    public String getComent(){
        return coment;
    }
    public int getImg(){
        return  img;
    }

}
