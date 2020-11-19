
package safeway;
import vistas.*;

public class SafeWay {

    public static ArbolB<UsuarioNodoB> arbolBNormal=new ArbolB<>(5);
    public static ArbolB<UsuarioNodoB> arbolBConductor=new ArbolB<>(5);
    public static Arbol_B arbolB=new Arbol_B(5);
    
    public static void main(String[] args) {
        //COMENZAMOS CON LA CARATURA DEL PROGRAMA
      vInicio inicio = new vInicio ();
      inicio.setVisible(true);
      inicio.setResizable(false);
      inicio.setLocationRelativeTo(null);
      
      
//        System.out.println( nuevoUsu.correo);
      
//      System.out.println(arbolBNormal.getKeys()[0].password  +" xd");
//        System.out.println(arbolBNormal.print());
//        System.out.println(arbolBNormal.getKeys()[0]);
        
        
      /*    FLUJO
      se crean las edds
      se abre el log in 
      se da la opcion de iniciar o crear usuario
      podes crear solo usuario de 
      */
      
       for(int i=1;i<=50;i++)
            arbolB.insert(i,"nombre", "Usuario "+i, "correo", "password", i, i);
        System.out.println("antes de borrar");

        
        arbolB.delete(27);
        System.out.println(arbolB.print());

        System.out.println("recorrer");
        arbolB.inOrden();
        arbolB.graficar();
        arbolB.buscar(17);
    }
    
}
