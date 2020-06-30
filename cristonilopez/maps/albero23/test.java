package movida.cristonilopez.maps.albero23;

public class test {
    public static void main(String[] args) {
        Albero23 albero = new Albero23();
        String[] alfabeto = { "p", "b", "c", "d", "a", "f", "g", "x", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
                "s", "t", "u", "v", "w", "h", "y", "z" };
        Integer data = new Integer(1); 
        for (int i = 0; i < 25; i++) {
            albero.insert(data, alfabeto[i]);
        }
        albero.insert(new Integer(10), "c");   
        albero.delete("a");
        albero.delete("c");
        albero.delete("f");
        albero.delete("s");
        albero.delete("j");
        albero.delete("k");
        albero.delete("l");
        albero.delete("r");
        albero.delete("q");
        albero.delete("p");
        albero.delete("n");
        albero.printTree();
        System.out.println(albero.search("c"));

        //System.out.println(Arrays.toString(albero.toArray()));
    }
    
}