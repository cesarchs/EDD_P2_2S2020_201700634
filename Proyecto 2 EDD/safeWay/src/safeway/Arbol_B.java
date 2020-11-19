package safeway;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author cesarchs
 */
public class Arbol_B {
    //llaves

    private UsuarioNodoB[] keys;
    private int load;
    //cardinalidad numero de valores por nodo = grade-1
    private int grade;
    //apuntador padre
    private Arbol_B father;
    private Arbol_B[] children;

    private boolean hasChildren() {
        for (int i = 0; i < grade; i++) {
            return (children[0] != null);
        }
        return false;
    }

    public Arbol_B(int grade) {
        load = 0;
        this.grade = grade;
        //se le da una casilla extra al array de llaves y de hijos para poder guardar temporalmente el valor antes de separar
        keys = new UsuarioNodoB[grade];
        children = new Arbol_B[grade + 1];
        father = null;
    }

    public Arbol_B insert(int val, String nombre, String usuario, String correo, String password, int telefono, int rolint) {
        //si es un nodo sin hijos se inserta aqui

        if (!hasChildren()) {
            addSorted(val, nombre, usuario, correo, password, telefono, rolint, null, null);
        } else {
            ///se debe determinar en que apuntador hijo se debe insertar el nuevo valor

            findPositionOfValue(val).insert(val, nombre, usuario, correo, password, telefono, rolint);

        }
        return this;

    }

    private Arbol_B findPositionOfValue(int val) {
        int position = load;
        for (int i = 0; i < load; i++) {
            if (val < keys[i].id) {
                position = i;
                break;
            }
        }
        return children[position];
    }

    public String print() {
        String sout = "no. llaves " + load + "\n";
        sout += "[";
        for (int i = 0; i < load; i++) {
            sout += keys[i].id + ", ";
        }
        sout += "]\n";
        for (int i = 0; i <= load; i++) {
            if (children[i] != null) {
                sout += "hijo " + i + " de " + keys[0].id + "\n";
                sout += children[i].print();
            }
        }
        return sout;
    }

//metodo recursivo
    private void addSorted(int val, String nombre, String usuario, String correo, String password, int telefono, int rolint, Arbol_B pleft, Arbol_B pright) {
        //se recorre de atras para adelante porque los numeros se van llenando del indece 0,1..load-1
        //se tiene una bandera para saber si se inserto el valor al terminar el ciclo
        boolean isInserted = false;

        for (int i = load - 1; i >= 0; i--) //si el valor es menor quiere decir que se debe correr el valor actual a la siguiente posicion del array
        {
            if (val < keys[i].id) {
                keys[i + 1] = keys[i];
                //tambien se acarrean los punteros
                children[i + 2] = children[i + 1];
            } else {
                UsuarioNodoB actual = new UsuarioNodoB(val, nombre, usuario, correo, password, telefono, rolint);
                keys[i + 1] = actual;
                children[i + 1] = pleft;
                children[i + 2] = pright;
                isInserted = true;
                break;
            }
        }
        if (!isInserted) {
            UsuarioNodoB actual = new UsuarioNodoB(val, nombre, usuario, correo, password, telefono, rolint);
            keys[0] = actual;
            children[0] = pleft;
            children[1] = pright;
        }
        load++;
        //si la carga es menor a la cantidad maxima de llaves permitidas no se hace la siguiente parte de codigo
        //se hace la separacion
        if (load == grade) {
            // se calcula posicion de valor medio para separar
            int med = (grade - 1) / 2;
            //se crea arbol izquierdo
            Arbol_B left = new Arbol_B(grade);

            for (int i = 0; i < med; i++) {
                left.keys[i] = keys[i];
                left.children[i] = children[i];
                if (left.children[i] != null) {
                    left.children[i].father = left;
                }
            }
            //se asigna el hijo de en medio
            left.children[med] = children[med];
            if (left.children[med] != null) {
                left.children[med].father = left;
            }
            left.setLoad(med);
            //se crea arbol derecho
            Arbol_B right = new Arbol_B(grade);

            for (int i = med + 1; i < grade; i++) {
                right.keys[i - med - 1] = keys[i];
                right.children[i - med - 1] = children[i];
                if (right.children[i - med - 1] != null) {
                    right.children[i - med - 1].father = right;
                }
            }
            right.children[grade - med - 1] = children[grade];
            if (right.children[grade - med - 1] != null) {
                right.children[grade - med - 1].father = right;
            }
            right.setLoad(grade - med - 1);

            //si el padre es null el nodo actual  se le pondra el valor medio y se le colocan como hijos los dos nuevos nodos
            // ,sino solo se agrega el valor medio al padre
            if (this.father == null) {
                UsuarioNodoB[] keys1 = new UsuarioNodoB[grade];
                keys1[0] = keys[med];
                keys = keys1;
                load = 1;
                children[0] = left;
                children[1] = right;
                for (int i = 2; i <= grade; i++) {
                    children[i] = null;
                }
                left.father = this;
                right.father = this;
            } else {
                left.father = this.father;
                right.father = this.father;
                father.addSorted(keys[med].id, keys[med].nombre, keys[med].usuario, keys[med].correo, keys[med].password, keys[med].telefono, keys[med].rolInt, left, right);
            }

        }
    }

    public Arbol_B delete(int val) {
        boolean deleted = false;
        for (int i = 0; i < load; i++) {
            if (keys[i].id == val) {
                deleted = true;
                deleteKey(i);
                break;
            }
        }

        if (!deleted && hasChildren()) {
            findPositionOfValue(val).delete(val);
        }

        return this;
    }

    private void deleteKey(int pos) {
        //si tiene hijos se tiene que buscar entre las hojas el mayor de los hijos de lado izquierdo y ese valor tomara el lugar del elemento elimando

        if (hasChildren()) {
            //selecciona el valor mayor de su hijo izquierdo tomado de la última hoja (el mero mayor)
            //esto se hace por un recorrido recursivo y solo se obtiene al apuntador al nodo con el valor
            Arbol_B point = children[pos].getGreatest();

            UsuarioNodoB newVal = point.keys[point.load - 1];
            //la llave encontrada en su hoja mayor a la izquierda sera tratada como una eliminacion en hoja y sera balanceado en su llamada recursiva
            keys[pos] = newVal;
            point.deleteKey(point.load - 1);

        } else {

            remove(pos);
            //HACER rebalanceo siempre y cuando tenga padre si es la raiz = hoja y no esta balanceado pues ni modo asi se queda

        }

    }

    private void remove(int pos) {
        for (int i = pos; i < load - 1; i++) {
            keys[i] = keys[i + 1];
            children[i + 1] = children[i + 2];
        }
        keys[load - 1] = null;
        children[load] = null;
        load--;

        if (load < (grade - 1) / 2 && father != null) {
            father.rebalance(this);
        }
    }

    private void rebalance(Arbol_B childWithKeyDeleted) {
        //hay dos tipos de rebalanceos
        //1) la cantidad total de llaves de todos los hijos se puede redestribuir para que todos tengan al menos el minimo
        //2) la cantidad total de llaves de todos los hijos no es suficiente para la cantidad de hijos
        //    en este caso hay que unir hijos y estoy puede llevar a un rebalanceo  el cual llamaremos recursivamente
        // for(int i=0;i<=load;i++
        // para el caso 1) el algoritmo de la pagina Btree solo toma los hermanos contiguos no analiza todo el paquete debo saber que numero de hijo es el afectado
        //se recorre los hijos buscando el indice del hijo
        int i = findIndexChild(childWithKeyDeleted);
        if (i == -1) {
            return; //no se encontro el nodo del cual se quito un key
        }        //primero se verifica que el nodo anterior al modificado tenga mas del minimo
        if (i > 0 && children[i - 1].load > (grade - 1) / 2) {
            UsuarioNodoB val = this.keys[i - 1];
            this.keys[i - 1] = children[i - 1].keys[load - 1];
            children[i - 1].load--;
            childWithKeyDeleted.insert(val.id, val.nombre, val.usuario, val.correo, val.password, val.telefono, val.rolInt);

        } else if (i < load && children[i + 1].load > (grade - 1) / 2) {

            UsuarioNodoB val = this.keys[i];
            this.keys[i] = children[i + 1].keys[0];
            children[i + 1].remove(0);
            childWithKeyDeleted.keys[childWithKeyDeleted.load] = val;
            childWithKeyDeleted.load++;

        } else //los vecinos estan en el minimo de carga
        if (i == 0) {
            join(0, 1);
        } else {
            join(i - 1, i);
        }

    }

    private void join(int h1, int h2) {
        int load1 = children[h1].load;
        int load2 = children[h2].load;
        if (father == null && load == 1) {
            UsuarioNodoB val = keys[0];

            for (int i = load2 - 1; i >= 0; i--) {
                keys[load1 + i + 1] = children[h2].keys[i];
                children[load1 + i + 1] = children[h2].children[i];
            }
            children[load1 + load2 + 1] = children[h2].children[load2];
            keys[load1] = val;

            children[load1] = children[h1].children[load1];
            for (int i = load1 - 1; i >= 0; i--) {
                keys[i] = children[h1].keys[i];
                children[i] = children[h1].children[i];
            }
            this.load = load1 + load2 + 1;
            for (int i = 0; i <= load; i++) {
                children[i].father = this;
            }
        } else {

            children[h1].keys[load1] = this.keys[h1];
            for (int i = 0; i < children[h2].load; i++) {
                children[h1].keys[load1 + i + 1] = children[h2].keys[i];
                children[h1].children[load1 + i + 1] = children[h2].children[i];
            }
            children[h1].children[load1 + load2 + 1] = children[h2].children[load2];
            children[h1].load += children[h2].load + 1;

            this.remove(h1);
        }
    }

    private int findIndexChild(Arbol_B childWithKeyDeleted) {
        for (int i = 0; i <= load; i++) {
            if (children[i] == childWithKeyDeleted) {
                return i;
            }
        }
        return -1;
    }

    private Arbol_B getGreatest() {
        if (!hasChildren()) {
            return this;
        } else {
            return children[load].getGreatest();
        }
    }

    public UsuarioNodoB[] getKeys() {
        return keys;
    }

    public void setKeys(UsuarioNodoB[] keys) {
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

    public Arbol_B getFather() {
        return father;
    }

    public void setFather(Arbol_B father) {
        this.father = father;
    }

    public Arbol_B[] getChildren() {
        return children;
    }

    public void setChildren(Arbol_B[] children) {
        this.children = children;
    }

    public String getDotName() {
        return "Node" + this.hashCode();
    }

    public void inOrden() {
        if (hasChildren()) {
            for (int i = 0; i <= load; i++) {

                children[i].inOrden();             // vamos recorriendo el arbol hasta llegar al ultimo hijo.
                if (i < load) {
                    System.out.println(", " + keys[i].id);
                }
            }
        } else {
            new ArrayList<>(Arrays.asList(keys)).forEach(
                    a -> {
                        if (a != null) {
                            System.out.println("," + a.id);
                        }
                    }
            );
        }
    }

    public String escribirDot() {

        StringBuilder b = new StringBuilder();
        b.append(getDotName());
        b.append("[label=\"<P0>");
        for (int i = 0; i < grade; i++) {
            if (keys[i] != null) {
                b.append("|" + "Id: " + keys[i].id + " " + "user: " + keys[i].usuario);
                b.append("|<P" + (i) + ">");
            }
        }
        b.append("\"];\n");
        for (int i = 0; i <= load; i++) {
            if (children[i] != null) {
                b.append(children[i].escribirDot());
                b.append(getDotName() + ":P" + i + " -> " + this.children[i].getDotName() + ";\n");
            }
        }
        return b.toString();

    }

    public void graficar() {
        String path = "D:\\escritorio\\Grafica P2 EDD";

        try {
            FileWriter fw = new FileWriter(path + ".dot");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(" digraph G {\n"
                    + "" + " node[ shape=record,  style=filled ,fillcolor=green, fontcolor=black];  \n"
                    + this.escribirDot()
            );
            bw.write("\n" + "}");
            bw.close();
            fw.close();
            System.out.println("hola checha");
            ProcessBuilder pbuilder;
            pbuilder = new ProcessBuilder("dot", "-Tjpg", "-o", path + ".jpg", path + ".dot");
            pbuilder.redirectErrorStream(true);
            //Ejecuta el proceso
            pbuilder.start();

            pbuilder = new ProcessBuilder("eog", path + ".jpg");
            pbuilder.redirectErrorStream(true);
            //Ejecuta el proceso
            pbuilder.start();
        } catch (IOException e) {
            System.out.println(e + " XD");
        }
    }

    public void buscar(int id) {
        for (int i = 0; i <= load; i++) {
            if (this.children[i] == null) {
                if (this.keys[i] == null) {
                } else if (this.keys[i].id == id) {
                    System.out.print(this.keys[i].id + ", ");
                    System.out.println("lo encontro ");
                    break;
                }
            } else if (children[i] != null) {
                children[i].buscar(id);
                if (keys[i] == null) {
                } else if (this.keys[i].id == id) {
                    System.out.print(keys[i].id + ", ");
                    System.out.println("lo encontro ");
                    break;
                }
            }
        }

    }

//    
//        public static void main(String[] arg){
//        Arbol_B arbolB=new Arbol_B(5);
//
//        for(int i=1;i<=7;i++)
//            arbolB.insert(i,"nombre", "Usuario", "correo", "password", i, i);
//        System.out.println("antes de borrar");
//       
////      arbolB.delete(16);
//        System.out.println(arbolB.print());
//        
//        arbolB.graficar();
//        System.out.println("recorrer");
//        arbolB.inOrden();
//        
//
//    }   
}
