
package safeway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author cesarchs
 */
public class ArbolB<Template> {
    //llaves
    private Template[] keys;
    private int load;
    //cardinalidad numero de valores por nodo = grade-1
    private int grade;
    //apuntador padre
    private ArbolB father;
    private ArbolB[] children;

    private boolean hasChildren(){
        for(int i=0;i<grade;i++)
            return (children[0] != null);
        return false;
    }

    public ArbolB(int grade){
        load=0;
        this.grade=grade;
        //se le da una casilla extra al array de llaves y de hijos para poder guardar temporalmente el valor antes de separar
        keys=(Template[])new Object[grade];
        children=new ArbolB[grade+1];
        father=null;
    }

    public ArbolB<Template> insert(Template val){
        //si es un nodo sin hijos se inserta aqui

        if(! hasChildren())
            
            addSorted(val,null,null);
        else {
            ///se debe determinar en que apuntador hijo se debe insertar el nuevo valor

            findPositionOfValue(val).insert(val);

        }
        return this;

    }

    private ArbolB<Template> findPositionOfValue(Template val){
        int position=load;
        for (int i = 0; i < load; i++)
            if (val.hashCode() < keys[i].hashCode()) {
                position = i;
                break;
            }
        return children[position];
    }

    public String print() {
        String sout="no. llaves "+load+"\n";
        sout+="[";
        for(int i=0;i<load;i++)
            sout+=keys[i]+", ";
        sout+="]\n";
        for(int i=0;i<=load;i++)
            if(children[i] != null) {
                sout+= "hijo"+i+" de "+keys[0]+"\n";
                sout+=children[i].print();
            }
        return sout;
    }

//metodo recursivo
    private void addSorted(Template val,ArbolB pleft,ArbolB pright) {
        //se recorre de atras para adelante porque los numeros se van llenando del indece 0,1..load-1
        //se tiene una bandera para saber si se inserto el valor al terminar el ciclo
        boolean isInserted=false;

        for(int i=load-1;i>=0;i--)
            //si el valor es menor quiere decir que se debe correr el valor actual a la siguiente posicion del array
            if(val.hashCode() < keys[i].hashCode()) {
                keys[i + 1] = keys[i];
                //tambien se acarrean los punteros
                children[i+2] = children[i+1];
            }
            else {
                keys[i + 1] = val;
                children[i+1] = pleft;
                children[i+2] = pright;
                isInserted=true;
                break;
            }
         if(!isInserted) {
             keys[0] = val;
             children[0]=pleft;
             children[1]=pright;
         }
         load++;
        //si la carga es menor a la cantidad maxima de llaves permitidas no se hace la siguiente parte de codigo
         //se hace la separacion
         if(load == grade){
             // se calcula posicion de valor medio para separar
             int med = (grade-1)/2;
             //se crea arbol izquierdo
             ArbolB left=new ArbolB(grade);

             for(int i=0;i<med;i++) {
                 left.keys[i] = keys[i];
                 left.children[i]=children[i];
                 if(left.children[i]!=null){
                     left.children[i].father=left;
                 }
             }
             //se asigna el hijo de en medio
             left.children[med]=children[med];
             if(left.children[med]!=null)
                 left.children[med].father=left;
             left.setLoad(med);
             //se crea arbol derecho
             ArbolB right=new ArbolB(grade);

             for(int i=med+1;i<grade;i++) {
                 right.keys[i - med - 1] = keys[i];
                 right.children[i-med-1] =children[i];
                 if(right.children[i-med-1]!=null){
                     right.children[i-med-1].father=right;
                 }
             }
             right.children[grade-med-1] =children[grade];
             if(right.children[grade-med-1]!=null)
                 right.children[grade-med-1].father=right;
             right.setLoad(grade-med-1);

             //si el padre es null el nodo actual  se le pondra el valor medio y se le colocan como hijos los dos nuevos nodos
             // ,sino solo se agrega el valor medio al padre

             if(this.father==null) {
                 Template[] keys1=(Template[])new Object[grade];
                 keys1[0]=keys[med];
                 keys=keys1;
                 load=1;
                 children[0]=left;
                 children[1]=right;
                 for(int i=2;i<=grade;i++)
                     children[i]=null;
                 left.father=this;
                 right.father=this;
             } else {
                 left.father=this.father;
                 right.father=this.father;
                 father.addSorted(keys[med],left,right);
             }

         }
    }

    public ArbolB<Template> delete(Template val){
        boolean deleted=false;
        for(int i=0;i<load;i++)
            if (keys[i].hashCode() == val.hashCode()) {
                deleted = true;
                deleteKey(i);
                break;
            }

        if(!deleted  && hasChildren())
            findPositionOfValue(val).delete(val);

        return this;
    }

    private void deleteKey(int pos) {
        //si tiene hijos se tiene que buscar entre las hojas el mayor de los hijos de lado izquierdo y ese valor tomara el lugar del elemento elimando

        if(hasChildren()){
            //selecciona el valor mayor de su hijo izquierdo tomado de la última hoja (el mero mayor)
            //esto se hace por un recorrido recursivo y solo se obtiene al apuntador al nodo con el valor
            ArbolB point =children[pos].getGreatest();

            Template newVal=(Template)point.keys[point.load-1];
            //la llave encontrada en su hoja mayor a la izquierda sera tratada como una eliminacion en hoja y sera balanceado en su llamada recursiva
            keys[pos] =newVal;
            point.deleteKey(point.load-1);

        } else {

            remove(pos);
            //HACER rebalanceo siempre y cuando tenga padre si es la raiz = hoja y no esta balanceado pues ni modo asi se queda


        }

    }

    private void remove(int pos) {
        for (int i = pos; i < load-1; i++) {
            keys[i] = keys[i + 1];
            children[i+1]=children[i+2];
        }
        keys[load-1]=null;
        children[load]=null;
        load--;

        if(load < (grade-1)/2 && father != null)
            father.rebalance(this);
    }

    private void rebalance(ArbolB childWithKeyDeleted) {
        //hay dos tipos de rebalanceos
        //1) la cantidad total de llaves de todos los hijos se puede redestribuir para que todos tengan al menos el minimo
        //2) la cantidad total de llaves de todos los hijos no es suficiente para la cantidad de hijos
        //    en este caso hay que unir hijos y estoy puede llevar a un rebalanceo  el cual llamaremos recursivamente
       // for(int i=0;i<=load;i++
        // para el caso 1) el algoritmo de la pagina Btree solo toma los hermanos contiguos no analiza todo el paquete debo saber que numero de hijo es el afectado
        //se recorre los hijos buscando el indice del hijo
        int i=findIndexChild(childWithKeyDeleted);
        if(i ==-1)
            return ; //no se encontro el nodo del cual se quito un key
        //primero se verifica que el nodo anterior al modificado tenga mas del minimo
        if(i>0 && children[i-1].load > (grade-1)/2){
            Template val=this.keys[i-1];
            this.keys[i-1] =(Template) children[i-1].keys[load-1];
            children[i-1].load--;
            childWithKeyDeleted.insert(val);

        }
        else if(i<load && children[i+1].load > (grade-1)/2  ){

            Template val=this.keys[i];
            this.keys[i] =(Template) children[i+1].keys[0];
            children[i+1].remove(0);
            childWithKeyDeleted.keys[childWithKeyDeleted.load]=val;
            childWithKeyDeleted.load ++;

        } else  //los vecinos estan en el minimo de carga
            if (i == 0)
                join(0, 1);

            else
                join(i - 1, i);



    }

    private void join(int h1, int h2) {
        int load1 =children[h1].load;
        int load2= children[h2].load;
        if(father==null && load==1){
            Template val=keys[0];

            for(int i=load2-1;i>=0;i--){
                keys[load1+i+1]=(Template)children[h2].keys[i];
                children[load1+i+1]=children[h2].children[i];
            }
            children[load1+load2+1]=children[h2].children[load2];
            keys[load1]=val;

            children[load1] = children[h1].children[load1];
            for(int i=load1-1;i>=0;i--){
                keys[i] =(Template) children[h1].keys[i];
                children[i] = children[h1].children[i];
            }
            this.load =load1+load2+1;
            for(int i=0;i<=load;i++)
                children[i].father=this;
        } else {

            children[h1].keys[load1] = this.keys[h1];
            for (int i = 0; i < children[h2].load; i++) {
                children[h1].keys[load1 + i + 1] = children[h2].keys[i];
                children[h1].children[load1 + i + 1] = children[h2].children[i];
            }
            children[h1].children[load1+load2+1]=children[h2].children[load2];
            children[h1].load += children[h2].load + 1;


            this.remove(h1);
        }
    }



    private int findIndexChild(ArbolB childWithKeyDeleted) {
        for(int i=0;i<=load;i++)
          if(children[i] == childWithKeyDeleted)
              return i;
          return -1;
    }

    private ArbolB getGreatest() {
        if(!hasChildren())
            return this;
        else
            return children[load].getGreatest();
    }

    public Template[] getKeys() {
        return keys;
    }

    public void setKeys(Template[] keys) {
        this.keys = keys;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public ArbolB getFather() {
        return father;
    }

    public void setFather(ArbolB father) {
        this.father = father;
    }

    public ArbolB[] getChildren() {
        return children;
    }

    public void setChildren(ArbolB[] children) {
        this.children = children;
    }

    public void inOrden(){
       if(hasChildren())
        for(int i = 0; i <= load; i++){


                children[i].inOrden();             // vamos recorriendo el arbol hasta llegar al ultimo hijo.
                if(i<load) {
                    System.out.println(", " + keys[i]);
                }
        }
       else
           new ArrayList<>(Arrays.asList(keys)).forEach(
                   a-> {
                       if(a != null)
                       System.out.println("," + a);
                   }
           );
//
//           for(int i=0;i<load;i++)
//               System.out.println(", " + keys[i]);

    }
//
//    public static void main(String[] arg){
//         ///Timer timer=new Timer();
//        ArbolB<Integer> arbolB=new ArbolB<>(5);
//
//       //timer.start();
//
//        for(int i=1;i<=123;i++)
//            arbolB.insert(i);
//       // System.out.println(arbolB.print());
//        System.out.println("antes de borrar");
//
//        arbolB.delete(51);
////        System.out.println("borro 51");
////        System.out.println(arbolB.print());
//        arbolB.delete(52);
//     arbolB.delete(44);
////
//      arbolB.delete(16);
////        System.out.println("impresion de datos");
//        System.out.println(arbolB.print());
//
//        System.out.println("recorrer");
//        arbolB.inOrden();
//
//    }



}