package movida.cristonilopez;

<<<<<<< HEAD
import movida.commons.*;
import movida.cristonilopez.maps.Dizionario;
import movida.cristonilopez.maps.albero23.Albero23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MovidaCore implements IMovidaDB, IMovidaConfig {

    SortingAlgorithm sort;
    MapImplementation map;
    Dizionario movies;
    Dizionario actors;

    public MovidaCore(){
        this.sort = SortingAlgorithm.InsertionSort;
        this.map = MapImplementation.Alberi23;
        this.movies = null;
        this.actors = null;
    }

    @Override
    public boolean setSort(SortingAlgorithm a) {
        if(a != sort){
            if(a == SortingAlgorithm.InsertionSort || a == SortingAlgorithm.HeapSort){
                sort = a;
                return true;
            }
            else
                return false;
        }
        return false;
    }

    @Override
    public boolean setMap(MapImplementation m) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void loadFromFile(File f) { //TODO gestite le omonimie
        if(!f.exists())
        {
            throw new MovidaFileException();
        }

        Scanner file = null;
        try
        {
            file = new Scanner(f);
        } 
        catch (FileNotFoundException e)
        {
            throw new MovidaFileException();
        }
<<<<<<< HEAD

        struttura = new Albero23(); //aggiungere controllo se esiste già na struttura e aggiungere selezione di struttura

=======
        //Aggiiungere controllo sul tipo di implementazione attiva
        movies = new Albero23(); //aggiungere controllo se esiste già na struttura e aggiungere selezione di struttura
>>>>>>> 330e819eb3904e29331020b9670a26ec3b4528e3
        boolean continua = true;
        while(continua)
        {
            String title = FileEngine.getTitle(file); //potrebbe essere più utile accorpare tutti questi metodi in un solo
            
            Integer year = FileEngine.getYear(file);  //metodo dato che questi metodi non possono assolutamente essere 
            
            Person director = FileEngine.getDirector(file);//autilizzati in un ordine diverso
            
            Person[] cast = FileEngine.getCast(file);

            Integer votes = FileEngine.getVotes(file);
            
            Movie result = new Movie(title, year, votes, cast, director);

            Test.stampa(result);
            movies.insert(result, title);
            //far smaltire la riga vuota, TODO da sistemare
            if(!file.hasNextLine())
            {
                continua = false;
            }
            else
            {
                String temp = file.nextLine();
                if(temp.length() >= 4 || !file.hasNextLine()) //TODO qua prevede che sia formattata male, ma anzichè fermarmi io devo dare err
                {
                    continua = false;
                }
            }
        } 
        file.close();
    }



    @Override
    public void saveToFile(File f) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

    @Override
    public int countMovies() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int countPeople() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean deleteMovieByTitle(String title) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Movie getMovieByTitle(String title) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Person getPersonByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Movie[] getAllMovies() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Person[] getAllPeople() {
        // TODO Auto-generated method stub
        return null;
    }
    
=======
public class MovidaCore{

>>>>>>> 8cadd4d20323955b5c685fce6613a3a2247f8af0
}