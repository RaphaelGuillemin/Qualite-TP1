import java.util.ArrayList;

public class Class {

    private int classe_LOC; // Nombre de lignes de code d’une classe
    private int classe_CLOC; // Nombre de lignes de code d’une classe qui contiennent des commentaires
    private float classe_DC; // Densité de commentaires pour une classe : classe_DC = classe_CLOC / classe_LOC
    private int WMC; // Weighted methods per class : somme pondérée des complexités des méthodes de la classe

    private ArrayList<Method> methods = new ArrayList<Method>(); // Méthodes de la classe
    private String name; // Nom de la classe

    /**
     * Constructeur
     * @param name Nom de la classe
     */
    public Class(String name) {
        this.name = name;
    }

    /**
     * Calcule la densité de commentaires pour une classe : classe_DC = classe_CLOC / classe_LOC
     */
    public void computeClasse_DC() {
        this.classe_DC = (float)this.classe_CLOC / (float)this.classe_LOC;
    }

    /**
     * Calcule WMC : somme pondérée des complexités des méthodes de la classe
     */
    public void computeWMC() {
        int sum = 0;
        for (Method method : this.methods) {
            sum += method.getCyclomaticComplexity();
        }
        this.WMC = sum;
    }

    /**
     * Ajoute une méthode à une classe
     * @param method Méthode de la classe
     */
    public void addMethod(Method method) {
        this.methods.add(method);
    }

    /**
     * @return Nombre de lignes de code d’une classe
     */
    public int getClasse_LOC() {
        return classe_LOC;
    }

    /**
     * @param classe_LOC Nombre de lignes de code d’une classe
     */
    public void setClasse_LOC(int classe_LOC) {
        this.classe_LOC = classe_LOC;
    }

    /**
     * @return Nombre de lignes de code d’une classe qui contiennent des commentaires
     */
    public int getClasse_CLOC() {
        return classe_CLOC;
    }

    /**
     * @param classe_CLOC Nombre de lignes de code d’une classe qui contiennent des commentaires
     */
    public void setClasse_CLOC(int classe_CLOC) {
        this.classe_CLOC = classe_CLOC;
    }

    /**
     * @return Densité de commentaires pour une classe : classe_DC = classe_CLOC / classe_LOC
     */
    public float getClasse_DC() {
        return classe_DC;
    }

    /**
     * @return Méthodes de la classe
     */
    public ArrayList<Method> getMethods(){
        return this.methods;
    }

    /**
     * @return Nom de la classe
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return Weighted methods per class : somme pondérée des complexités des méthodes de la classe
     */
    public int getWMC() {
        return this.WMC;
    }
}
