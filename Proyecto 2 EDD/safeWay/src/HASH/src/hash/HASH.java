/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash;

/**
 *
 * @author Ghosth32
 */
public class HASH {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Object l[]= {"diego","juan","diego","diego","carlos","diego","juan","karla","karla","karla",1,"diego",1};
        Object l[]= {"carlos","alberto","juan","diego","diego","karla","maria",1,"diego",3,4,"diego",1,"diego","juan","diego","diego","carlos","diego","juan","karla","karla","karla",1,"diego",1};
        T_HASH bh=new T_HASH(l.length*2);
        bh.presenta("Elementos a insertar en la Tabla Hash",l);
        bh.creaTabla(l,l.length);
        //BUSQUEDAS
        bh.buscarHash("carlos",l.length);
        bh.buscarHash(1,l.length);
        bh.buscarHash("diego",l.length);
        bh.buscarHash("armando",l.length);
        bh.buscarHash("@",l.length);
        bh.buscarHash("armando",l.length);
        bh.eliminar("carlos",l.length );
    }
    
}
