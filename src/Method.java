import java.util.ArrayList;

public class Method {

    private String name;  // Nom de la méthode
    private ArrayList<String> args = new ArrayList<String>(); // Arguments de la méthode
    private int methode_LOC; // Nombre de lignes de code d’une méthode
    private int methode_CLOC; // Nombre de lignes de code d’une méthode qui contiennent des commentaires
    private float methode_DC; // Densité de commentaires pour une méthode : methode_DC = methode_CLOC / methode_LOC

    // Attributs utilisés pour le calcul de la complexité cyclomatique
    private int cyclomaticComplexity; // Complexité cyclomatique de McCabe
    private int noOfIfs; // Nombre de clauses if dans la méthode
    private int noOfSwitchCases; // Nombre de cases dans les blocs switch de la méthodes
    private int noOfWhileLoops; // Nombre de bloc while ou do-while de la méthode
    private int noOfForLoops; // Nombre de boucles for dans la méthode

    /**
     * Constructeur
     * @param name nom de la méthode
     * @param args arguments de la méthodes
     */
    public Method(String name, ArrayList<String> args) {
        this.name = name;
        this.args = args;
        this.cyclomaticComplexity = 0; // Complexité cyclomatique initialisée à 1
        this.noOfIfs = 0;
        this.noOfSwitchCases = 0;
        this.noOfWhileLoops = 0;
        this.noOfForLoops = 0;
    }

    /**
     * Calcule la densité de commentaires pour une méthode
     */
    public void computeMethode_DC() {
        this.methode_DC = (float)this.methode_CLOC / (float)this.methode_LOC;
    }

    /**
     * Calcule la complexité cyclomatique de la méthode
     * @param cyclomaticComplexity complexité cyclomatique de la méthode
     */
    public int computeCyclomaticComplexity(int cyclomaticComplexity) {
        this.cyclomaticComplexity = 1 + this.noOfIfs + this.noOfSwitchCases
                + this.noOfWhileLoops + this.noOfForLoops;
        return this.cyclomaticComplexity;
    }

    /**
     * @return Nom de la méthode
     */
    public String getName() {
        return name;
    }

    /**
     * @return Arguments de la méthode
     */
    public ArrayList<String> getArgs() {
        return args;
    }

    /**
     * @return methode_LOC Nombre de lignes de code de la méthode
     */
    public int getMethode_LOC() {
        return methode_LOC;
    }

    /*
     * @param methode_LOC Nombre de lignes de code de la méthode
     */
    public void setMethode_LOC(int methode_LOC) {
        this.methode_LOC = methode_LOC;
    }

    /**
     * @return methode_CLOC Nombre de lignes contenant des commentaires de la méthode
     */
    public int getMethode_CLOC() {
        return methode_CLOC;
    }

    /**
     * @param methode_CLOC Nombre de lignes contenant des commentaires de la méthode
     */
    public void setMethode_CLOC(int methode_CLOC) {
        this.methode_CLOC = methode_CLOC;
    }

    /**
     * @return methode_DC Densité de commentaire pour une méthode
     */
    public float getMethode_DC() {
        return methode_DC;
    }

    /**
     * @return La complexité cyclomatique de la méthode
     */
    public int getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    /**
     * @return Nombre de clauses if dans la méthode
     */
    public int getNoOfIfs() {
        return noOfIfs;
    }

    /**
     * @param noOfIfs Nombre de clauses if dans la méthode
     */
    public void setNoOfIfs(int noOfIfs) {
        this.noOfIfs = noOfIfs;
    }

    /**
     * @return Nombre de cases dans les blocs switch de la méthodes
     */
    public int getNoOfSwitchCases() {
        return noOfSwitchCases;
    }

    /**
     * @param noOfSwitchCases Nombre de cases dans les blocs switch de la méthodes
     */
    public void setNoOfSwitchCases(int noOfSwitchCases) {
        this.noOfSwitchCases = noOfSwitchCases;
    }

    /**
     * @return Nombre de bloc while ou do-while de la méthode
     */
    public int getNoOfWhileLoops() {
        return noOfWhileLoops;
    }

    /**
     * @param noOfWhileLoops Nombre de bloc while ou do-while de la méthode
     */
    public void setNoOfWhileLoops(int noOfWhileLoops) {
        this.noOfWhileLoops = noOfWhileLoops;
    }

    /**
     * @return Nombre de boucles for dans la méthode
     */
    public int getNoOfForLoops() {
        return noOfForLoops;
    }

    /**
     * @param noOfForLoops Nombre de boucles for dans la méthode
     */
    public void setNoOfForLoops(int noOfForLoops) {
        this.noOfForLoops = noOfForLoops;
    }
}
