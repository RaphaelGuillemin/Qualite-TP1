import java.util.ArrayList;

/**
 * Représente une classe Java.
 */
public class Class {
    /** Nombre de lignes de code d’une classe */
    private int classe_LOC;
    /** Nombre de lignes de code d’une classe qui contiennent des commentaires */
    private int classe_CLOC;
    /** Densité de commentaires pour une classe : classe_DC = classe_CLOC / classe_LOC */
    private float classe_DC;
    /** Weighted methods per class : somme pondérée des complexités des méthodes de la classe */
    private int WMC;
    /** Degré selon lequel une méthode est bien commentée : classe_BC = classe_DC/WMC */
    private float classe_BC;

    /** Méthodes de la classe */
    private ArrayList<Method> methods = new ArrayList<Method>();
    /** Nom de la classe */
    private String name;

    /**
     * Constructeur
     * @param name Nom de la classe
     */
    public Class(String name, int ini_LOC, int ini_CLOC) {
        this.name = name;
        this.classe_LOC = ini_LOC;
        this.classe_CLOC = ini_CLOC;
        this.classe_DC = 0;
        this.WMC = 0;
        this.classe_BC = 0;
    }

    /**
     * Calcule la densité de commentaires pour une classe : classe_DC = classe_CLOC / classe_LOC
     */
    public void computeClasse_DC() {
        this.classe_DC = (float) this.classe_CLOC / (float) this.classe_LOC;
    }

    /**
     * Calcule WMC : somme pondérée des complexités des méthodes de la classe
     */
    public void computeWMC() {
        int sum = 0;
        for (Method method : this.methods) {
            sum += method.getCC();
        }
        this.WMC = sum;
    }

    /**
     * Calcule le degré selon lequel une méthode est bien commentée : classe_BC = classe_DC/WMC
     */
    public void computeClasse_BC() {
        this.classe_BC = (float) this.classe_DC / (float) this.WMC;
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
     * Incrémente le ombre de lignes de code d’une classe
     */
    public void incrementClasse_LOC() {
        this.classe_LOC++;
    }

    /**
     * @return Nombre de lignes de code d’une classe qui contiennent des commentaires
     */
    public int getClasse_CLOC() {
        return classe_CLOC;
    }

    /**
     * Incrémente le nombre de lignes de code d’une classe qui contiennent des commentaires
     */
    public void incrementClasse_CLOC() {
        this.classe_CLOC++;
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

    /**
     * @return Degré selon lequel une méthode est bien commentée : classe_BC = classe_DC/WMC
     */
    public float getClasse_BC() {
        return this.classe_BC;
    }
}
