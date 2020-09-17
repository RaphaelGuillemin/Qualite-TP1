import java.util.ArrayList;

public class Class {
    // Nombre de lignes de code d’une classe
    private int classe_LOC;
    // Nombre de lignes de code d’une classe qui contiennent des commentaires
    private int classe_CLOC;
    // Densité de commentaires pour une classe : classe_DC = classe_CLOC / classe_LOC
    private float classe_DC;
    // Méthodes de la classe
    private ArrayList<Method> methods = new ArrayList<Method>();
    // Nom de la classe
    private String name;

    /*
     * @returns classe_LOC
     */
    public int getClasse_LOC() {
        return classe_LOC;
    }

    /*
     * @param classe_LOC
     */
    public void setClasse_LOC(int classe_LOC) {
        this.classe_LOC = classe_LOC;
    }

    /*
     * @returns classe_CLOC
     */
    public int getClasse_CLOC() {
        return classe_CLOC;
    }

    /*
     * @param classe_CLOC
     */
    public void setClasse_CLOC(int classe_CLOC) {
        this.classe_CLOC = classe_CLOC;
    }

    /*
     * @returns classe_DC
     */
    public float getClasse_DC() {
        return classe_DC;
    }

    /*
     * @param classe_DC
     */
    public void setClasse_DC() {
        this.classe_DC = (float)this.classe_CLOC / (float)this.classe_LOC;
    }

    public void addMethod(Method method) {
        this.methods.add(method);
    }

    public ArrayList<Method> getMethods(){
        return this.methods;
    }

    public String getName() {
        return name;
    }

    public Class(String name) {
        this.name = name;
    }
}
