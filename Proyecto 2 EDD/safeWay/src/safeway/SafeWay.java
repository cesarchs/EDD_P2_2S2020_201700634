
package safeway;
import vistas.*;

public class SafeWay {

    public static ArbolB<UsuarioNodoB> arbolBNormal=new ArbolB<>(5);
    public static ArbolB<UsuarioNodoB> arbolBConductor=new ArbolB<>(5);
    
    public static void main(String[] args) {
        //COMENZAMOS CON LA CARATURA DEL PROGRAMA
//      vInicio inicio = new vInicio ();
//      inicio.setVisible(true);
//      inicio.setResizable(false);
//      inicio.setLocationRelativeTo(null);
      
      
      UsuarioNodoB nuevoUsu = new UsuarioNodoB(1,"cesar","chs","cesar@gmail.com","123",321,0);
      arbolBNormal.insert(nuevoUsu, nuevoUsu.id);
      arbolBNormal.inOrden();
      /*    FLUJO
      se crean las edds
      se abre el log in 
      se da la opcion de iniciar o crear usuario
      podes crear solo usuario de 
      */
    }
    
}
