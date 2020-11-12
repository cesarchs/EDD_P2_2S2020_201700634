/*
 * To change Tabla_Hashis license header, choose License Headers in Project Properties.
 * To change Tabla_Hashis template file, choose Tools | Templates
 * and open Tabla_Hashe template in Tabla_Hashe editor.
 */
package hash;

/**
 *
 * @autor Ghosth32
 */
public class T_HASH {

    //Atributo
    Object[] Tabla_Hash;

    //Constructor
    public T_HASH(int n) {
        Tabla_Hash = new Object[n];
        for (int i = 0; i < Tabla_Hash.length; i++) {
            Tabla_Hash[i] = null;
        }//******************
    }

    //Métodos
    public void creaTabla(Object[] a, int nmax) 
    {
        int posicion;
        for (int i = 0; i < a.length; i++) 
        {   
            //Genera la posición del elemento a insertar
            //Haciendo uso de una función hash
            posicion = a[i].hashCode() % nmax;
//            System.out.println("Elemento: " + a[i] + " Posicion: " + posicion);

            //cuando la función devuelve un valor negativo
            if (posicion < 0) 
            {
                posicion *= -1;
            }
            if (Tabla_Hash[posicion] == null) 
            {
                //Inserta en la tabla sin colisión
                Tabla_Hash[posicion] = a[i];
                System.out.println("Elemento: " + a[i] + " Posicion: " + posicion);
            } 
            else 
            {

                while (Tabla_Hash[posicion] != null) 
                {
                    posicion++;
                    if (posicion == Tabla_Hash.length) 
                    {
                        posicion = 0;
                    }
                }

                //Inserta el valor en la tabla hash despues de una colision
                Tabla_Hash[posicion] = a[i];
                System.out.println("Elemento: " + a[i] + " Posicion: " + posicion);
            }
        }
        presenta("TABLA HASH", Tabla_Hash);
    }
    
public void eliminar (Object elemb, int nmax){
    int e = elemb.hashCode();
        
        if (e < 0) 
        {
            e *= -1;
        }

        int posicion = e % nmax;

        if (Tabla_Hash[posicion] == null) 
        {
            System.out.println("Elemento "+elemb+" no encontrado");
        } 
        else 
        {
            if (Tabla_Hash[posicion]==elemb) 
            {
                Tabla_Hash[posicion]= null;
                System.out.println("Elemento eliminado " + elemb + " su posicion es : " + posicion);
                int posicionRemplazo = posicion;
                System.out.println("Elemento encontrado, " + elemb + " su posicion es : " + posicion);
                repeticiones ++;
                for (int i = posicionRemplazo+1;i<Tabla_Hash.length;i++){
                    if(Tabla_Hash[i]==elemb){
                        System.out.println("Elemento encontrado, " + elemb + " su posicion es : " + i);
                        repeticiones ++;
                    }
                }System.out.println("El elemento "+elemb+ " se repite " + repeticiones +" veces");
                repeticiones =0;
            } 
            else 
            {
                int j = posicion + 1;
                if (j == Tabla_Hash.length) 
                {
                    j = 0;
                }

                while ((Tabla_Hash[j] != elemb) && (Tabla_Hash[j] != null) && (j != posicion)) 
                {
                    j++;
                    if (j >= Tabla_Hash.length - 1) 
                    {
                        j = 0;
                    }
                }

                if (Tabla_Hash[j] == elemb) 
                {
                    int posicionRemplazo =j;
                    System.out.println("Elemento encontrado, " + elemb + " su posicion es : " + j);
                    repeticiones ++;
                    for (int i = posicionRemplazo+1;i<Tabla_Hash.length;i++){
                    if(Tabla_Hash[i]==elemb){
                        System.out.println("Elemento encontrado, " + elemb + " su posicion es : " + i);
                        repeticiones ++;
                    }
                }System.out.println("El elemento "+elemb+ " se repite " + repeticiones +" veces");
                repeticiones =0;
                } 
                else 
                {
                    System.out.println("Elemento "+elemb+" no encontrado");
                }
            }
        }
}
int repeticiones=0;
    public void buscarHash(Object elemb, int nmax)
    {
        int e = elemb.hashCode();
        
        if (e < 0) 
        {
            e *= -1;
        }

        int posicion = e % nmax;

        if (Tabla_Hash[posicion] == null) 
        {
            System.out.println("Elemento "+elemb+" no encontrado");
        } 
        else 
        {
            if (Tabla_Hash[posicion]==elemb) 
            {
                int posicionRemplazo = posicion;
                System.out.println("Elemento encontrado, " + elemb + " su posicion es : " + posicion);
                repeticiones ++;
                for (int i = posicionRemplazo+1;i<Tabla_Hash.length;i++){
                    if(Tabla_Hash[i]==elemb){
                        System.out.println("Elemento encontrado, " + elemb + " su posicion es : " + i);
                        repeticiones ++;
                    }
                }System.out.println("El elemento "+elemb+ " se repite " + repeticiones +" veces");
                repeticiones =0;
            } 
            else 
            {
                int j = posicion + 1;
                if (j == Tabla_Hash.length) 
                {
                    j = 0;
                }

                while ((Tabla_Hash[j] != elemb) && (Tabla_Hash[j] != null) && (j != posicion)) 
                {
                    j++;
                    if (j >= Tabla_Hash.length - 1) 
                    {
                        j = 0;
                    }
                }

                if (Tabla_Hash[j] == elemb) 
                {
                    int posicionRemplazo =j;
                    System.out.println("Elemento encontrado, " + elemb + " su posicion es : " + j);
                    repeticiones ++;
                    for (int i = posicionRemplazo+1;i<Tabla_Hash.length;i++){
                    if(Tabla_Hash[i]==elemb){
                        System.out.println("Elemento encontrado, " + elemb + " su posicion es : " + i);
                        repeticiones ++;
                    }
                }System.out.println("El elemento "+elemb+ " se repite " + repeticiones +" veces");
                repeticiones =0;
                } 
                else 
                {
                    System.out.println("Elemento "+elemb+" no encontrado");
                }
            }
        }
    }

    public void presenta(String msg, Object a[]) 
    {
        System.out.print(msg + " [");

        for (int i = 0; i <= a.length - 1; i++) 
        {
            if (i < a.length - 1) 
            { 
                System.out.print(a[i] + ",");
            } 
            else 
            {
                System.out.print(a[i] + "]");
            }
        }

        System.out.print("\n");
    }

}
