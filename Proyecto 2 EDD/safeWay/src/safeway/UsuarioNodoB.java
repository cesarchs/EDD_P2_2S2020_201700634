
package safeway;

/**
 *
 * @author cesarchs
 */
public class UsuarioNodoB {
    /* ESTE ES EL NODO PARA EL ARBOL B EN EL LLEVAREMOS EL CONTROL LOS USUARIOS */ 
    public int id;
    public String nombre;
    public String usuario;
    public String correo;
    public String password;
    public int telefono;
    public int rolInt;
    public String rolString;
    
    //constructor para crear usuarios
    public UsuarioNodoB (int id, String nombre, String usuario, String correo, String password, int telefono, int rolint){
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.correo = correo;
        this.password = password;
        this.telefono = telefono;
        this.rolInt= rolint;
    }
    
    public String getDotName(){
        return "Nodo "+this.id;
    }
}
