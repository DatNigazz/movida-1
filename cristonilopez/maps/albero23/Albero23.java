package movida.cristonilopez.maps.albero23;

import java.lang.reflect.Array;
import movida.cristonilopez.maps.Dizionario;

public class Albero23<T> implements Dizionario<T>{
    private Nodo radice;
    private int nNodi;  //numero di foglie presenti
    final Class<T> parameterClass;
    
    public Albero23(Class<T> parameterClass){
        radice = null;
        nNodi  = 0; 
        this.parameterClass = parameterClass;
    }
    /**
     * Il metodo insert permette di inserire un nuovo elemento all'interno del nostro albero
     * @param data
     * @param key
     */
    public void insert(T data, String key){
        if(radice == null){                 //Controlliamo che il nostro albero non sia vuoto
            nNodi = nNodi + 1;
            Foglia23<T> nuovaFoglia = new Foglia23<T>(data, key);
            radice = nuovaFoglia;
        }
        else if(radice instanceof Foglia23){
            if(radice.getKey().compareTo(key) == 0){    //Se la chiave è già presente aggiorno le informazioni
                ((Foglia23<T>)radice).setInfo(data);
            }
            else{
                nNodi = nNodi+1;
                Nodo23 nuovaRadice = new Nodo23();
                nuovaRadice.figli[0] = radice;
                radice = nuovaRadice;
                ((Nodo23)radice).addChild(new Foglia23<T>(data, key));
            }
        }
        else{
            Nodo23 padre = searchPosition(key);                 
            Foglia23<T> foglia = (Foglia23<T>)padre.searchNode(key);          //Controllo se è presente una foglia con tale chiave
            if(foglia == null || foglia.getKey().compareToIgnoreCase(key) != 0){  //Se non è presente aggiungo normalmente la nuova foglia
                nNodi = nNodi+1;
                Foglia23<T> nuovaFoglia = new Foglia23<T>(data, key);
                padre.addChild(nuovaFoglia);
                if(padre.grado()>3)                                      //Se il nostro nodo non è ancora saturo possiamo aggiungere un nuovo figlio
                    split(padre);
            }
            else{                                                        //Se è presente aggiorno solamente il valore
                foglia.setInfo(data);
            }
        }
    }
    /**
     * Il metodo delte permette l'eliminazione di un elemento attraverso la sua chiave,
     * se tale chiave non è presente viene sollevata un'eccezione che viene gestita attraverso una stampa di 
     * "Chiave non presente".
     * @param key
     */
    public boolean delete(String key){
        if(radice == null){
            return false;
        }
        else if(radice instanceof Foglia23){
            if(key.compareToIgnoreCase(((Foglia23<T>)radice).getKey()) == 0){
                radice = null;
                nNodi = 0;
                return true;
            }
            else
                return false;
        }
        else{
            Nodo23 nodo = searchPosition(key);
            String max = nodo.getKey();
            //Gestisco chiave non è presente
            try {
                nodo.deleteChild(key);
                if (max.compareToIgnoreCase(key) == 0) {
                    fixKeys(nodo.padre, key, nodo.getKey());
                }
            } catch (ExceptionKeyNotFound e) {
                return false;
            }
            nNodi = nNodi - 1;
            if(nodo.grado()<2){
                if(nodo == radice)
                    radice = nodo.figli[0];
                else{
                    fuse(nodo);
                }
            }
            return true;
        }
    }

    public T search(String key){
        if(radice == null)
            return null;
        else if(radice instanceof Foglia23)
            if(radice.getKey().compareToIgnoreCase(key) == 0)
                return ((Foglia23<T>)radice).getInfo();
            else
                return null;
        else{
            Nodo foglia =  searchPosition(key).searchNode(key);
            if(foglia.getKey().compareToIgnoreCase(key) == 0)
                return ((Foglia23<T>)foglia).getInfo();
            else
                return null;
        }
    }

    /**
     * Il metodo split gestisce il caso in cui il nostro nodo sia già pieno.
     * Viene creato un nuovo nodo con all'interno i due sottoalberi minori in modo tale che questo diventi il sottoalbero
     * sinistro del nodo padre.
     * Viene poi aggiunto al padre del nodo splittato, la chiave usata nell'aggiunta è quella del massimo valore presente sotto tale nodo
     * @param nodo
     */
    private void split(Nodo23 nodo){
        //Creo il nuovo nodo
        Nodo23 nodoSplit = new Nodo23();    //Nel nostro nuovo nodo andiamo sempre a porre la chiave e i due sottoalberi minori
        nodoSplit.addChild(nodo.figli[0]);  //Aggiungiamo due figli al nuovo nodo
        nodoSplit.addChild(nodo.figli[1]);
        nodo.deleteChild(nodo.figli[0]);    //Rimuoviamoli dal vecchio
        nodo.deleteChild(nodo.figli[0]);
        if(nodo == radice){                 //Nel caso siamo arrivati alla radice creiamo una nuova radice
            Nodo23 oldRoot = (Nodo23)radice;
            radice = new Nodo23();
            nodoSplit.padre =(Nodo23) radice;
            nodo.padre = (Nodo23)radice;
            ((Nodo23) radice).addChild(nodoSplit);
            ((Nodo23) radice).addChild(oldRoot);
        }
        else{
        nodo.padre.addChild(nodoSplit);                      //Andiamo ad inserire il nuovo nodo nel padre del nodo splittato
        if (nodo.padre.grado() > 3)
            split(nodo.padre);
        }
    } 

    /**
     * Il metodo fuse si preoccupa di ripristinare le caratteristiche di un albero 2-3 eseguendo una fusione tra nodi
     * o una ripartizione degli elementi tra nodi fratelli, il metodo può essere chiamato dopo una delete
     * @param nodo
     */

    private void fuse(Nodo23 nodo){
        Nodo23 rb = nodo.rightBrother();            //Fratello destro
        Nodo23 lb = nodo.leftBrother();             //Fratello sinistro
        if(rb != null && rb.grado() == 3 ){         //Controllo che il fratello destro abbia abbastanza nodi per eseguire unos cambio
            nodo.addChild(rb.figli[0]);
            fixKeys(nodo.padre, nodo.keys[0], rb.keys[0]);      //Sistemiamo le chiavi degli antenati
            rb.deleteChild(rb.figli[0]);              //eliminiamo il nodo che abbiamo spostato
        }
        else if(lb != null && lb.grado() == 3 ){         // Controllo che il fratello sinistro abbia abbastanza nodi per eseguire uno scambio
            nodo.addChild(lb.figli[2]);
            fixKeys(lb, nodo.keys[0], lb.figli[1].getKey());      //Aggiorniamo i valori degli antenati del fratello sinistro
            lb.deleteChild(lb.figli[2]);
        }
        else if(lb != null){                               //Fusione con il fratello sinistro
            lb.addChild(nodo.figli[0]);
            String newMax = lb.figli[2].getKey();
            fixKeys(nodo.padre, lb.keys[1], newMax);        //Aggiorniamo le chiavi degli antenati sostituendo il vecchio massimo al nuovo
            nodo.padre.deleteChild(nodo);                   //Eliminiamo il nodo dal padre
        }
        else if(rb != null){                                //Fusione con il fratello destro
            rb.addChild(nodo.figli[0]);
            nodo.padre.deleteChild(nodo);
        }
        if(nodo.padre.grado()<2)
            if(nodo.padre == radice){                       //Se siamo arrivati alla radice e questa ha un  grado minore di 2
                radice = ((Nodo23)radice).figli[0];         //Cambiamo la radice
                radice.padre = null;
            }
            else
                fuse(nodo.padre);
    }
    /**
     * Il metodo ci fornisce il padre della nostra nuova foglia
     * @param key
     * @return Padre della nuova foglia
     */
    private Nodo23 searchPosition(String key){
        Nodo23 padre =  ((Nodo23)radice);
        Nodo tmp = ((Nodo23) radice);
        while(tmp!=null && !tmp.isLeaf()){
            padre = (Nodo23)tmp;
            tmp = padre.searchNode(key);
        }
        return padre;
    }

    int toArrayRec(Nodo radice, T[]A, int n){
        Nodo tmp = radice;
        if(tmp.isLeaf()){
            //System.out.println(((Foglia23)tmp).key.toString());
            A[n] = (T)((Foglia23<T>)tmp).getInfo();
            n = n+1;
            return n;
        }
        else{
            for (Nodo nodo : ((Nodo23)tmp).figli) {
                if(nodo!=null)
                    n = toArrayRec(nodo, A, n);
            }  
            return n;
        }
    }
 
    /**
     * Il metodo fixKeys data una chiave eliminata controlla se all'interno di un nodo è presente tale chiave
     * se è presente la sostituisce col nuovo massimo
     * @param nodo
     * @param oldKey chiave da sostituire
     * @param newKey nuova chiave
     */
    private void fixKeys(Nodo23 nodo, String oldKey, String newKey){
        if(nodo == null)
            return;
        else{
            nodo.updateKey(oldKey, newKey);
            fixKeys(nodo.padre, oldKey, newKey);
        }
    }
    
    /**
     * Il metodo toArray() fornisce un array ordinato di tutti gli elementi presenti nell'albero
     * @return  Array ordinato
     */

    public T[] toArray(){
        T[] A = null;
        A = (T[]) Array.newInstance(parameterClass, nNodi);
        if(nNodi != 0)
            toArrayRec(radice, A, 0);
        return A;
    }


    public int count(){
        return nNodi;
    }
}