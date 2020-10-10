import java.util.ArrayList;

/**
 * Représente un fichier Java.
 */
public class JavaFile {

    /** Ensemble des classes du fichier java */
    private ArrayList<Class> classes = new ArrayList<Class>();
    /** Path du fichier java */
    private String path;

    /**
     * Ajoute une classe du fichier Java
     * @param classe Classe du fichier Java
     */
    public void addClass(Class classe){
        classes.add(classe);
    }

    /**
     * @return Ensemble des classes du fichier java
     */
    public ArrayList<Class> getClasses(){
        return this.classes;
    }

    /**
     * @return // Path du fichier java
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Constructeur.
     * @param path Path du fichier java
     */
    public JavaFile(String path){
        this.path = path;
    }
}
