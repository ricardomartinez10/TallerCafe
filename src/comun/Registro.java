package comun;
import java.io.Serializable;


public class Registro implements Serializable {

    String nombre;
    String pass;
    String estado;
    String usuario;
    String correo;

    public Registro(String nombre,String pass,String estado,String usuario,String correo){
        this.nombre=nombre;
        this.pass=pass;
        this.estado=estado;
        this.usuario=usuario;
        this.correo=correo;
    }

    public String getNombre(){
        return nombre;
    }
    public String getPass(){
        return pass;
    }
    public String getEstado(){
        return estado;
    }

    public String getUsuaio(){
        return usuario;
    }
    public String getCorreo(){
        return correo;
    }


}
