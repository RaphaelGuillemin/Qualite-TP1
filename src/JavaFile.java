import java.util.ArrayList;

public class JavaFile {
    // Ensemble des classes du fichier java
    private ArrayList<Class> classes = new ArrayList<Class>();
    // Path du fichier java
    private String path;


    public void addClass(Class classe){
        classes.add(classe);
    }

    public ArrayList<Class> getClasses(){
        return this.classes;
    }

    public String getPath() {
        return this.path;
    }

    public JavaFile(String path){
        this.path = path;
    }
}
