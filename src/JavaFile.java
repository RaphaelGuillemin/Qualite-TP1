import java.util.ArrayList;

public class JavaFile {
    // Ensemble des classes du fichier java
    private ArrayList<Class> classes = new ArrayList<Class>();
    // Nom du fichier java
    private String name;

    public void addClass(Class classe){
        classes.add(classe);
    }

    public ArrayList<Class> getClasses(){
        return this.classes;
    }

    public String getName() {
        return this.name;
    }

    public JavaFile(String name){
        this.name = name;
    }
}
