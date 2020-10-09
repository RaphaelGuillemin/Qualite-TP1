import java.util.ArrayList;

public class Method {

    private String name;  // Nom de la méthode
    private ArrayList<String> args = new ArrayList<String>(); // Arguments de la méthode
    private int methode_LOC; // Nombre de lignes de code d’une méthode
    private int methode_CLOC; // Nombre de lignes de code d’une méthode qui contiennent des commentaires
    private float methode_DC; // Densité de commentaires pour une méthode : methode_DC = methode_CLOC / methode_LOC
    private float methode_BC; // Degré selon lequel une méthode est bien commentée : methode_BC = methode_DC/CC

    // Attributs utilisés pour le calcul de la complexité cyclomatique (CC)
    private int CC; // Complexité cyclomatique de McCabe
    private int noOfIfs; // Nombre de clauses if dans la méthode
    private int noOfSwitchCases; // Nombre de cases dans les blocs switch de la méthodes
    private int noOfWhileLoops; // Nombre de bloc while ou do-while de la méthode
    private int noOfForLoops; // Nombre de boucles for dans la méthode

    /**
     * Constructeur
     * @param name nom de la méthode
     * @param args arguments de la méthodes
     */
    public Method(String name, ArrayList<String> args, int ini_LOC, int ini_CLOC) {
        this.name = name;
        this.args = args;
        this.methode_LOC = ini_LOC;
        this.methode_CLOC = ini_CLOC;
        this.methode_DC = 0;
        this.methode_BC = 0;
        this.CC = 0;
        this.noOfIfs = 0;
        this.noOfSwitchCases = 0;
        this.noOfWhileLoops = 0;
        this.noOfForLoops = 0;
    }

    /**
     * Calcule la densité de commentaires pour une méthode
     */
    public void computeMethode_DC() {
        this.methode_DC = (float) this.methode_CLOC / (float) this.methode_LOC;
    }

    /**
     * Calcule la complexité cyclomatique de la méthode
     */
    public void computeCC() {
        this.CC = 1 + this.noOfIfs + this.noOfSwitchCases
                + this.noOfWhileLoops + this.noOfForLoops;
    }

    /**
     * Calcule le degré selon lequel une méthode est bien commentée : methode_BC = methode_DC/CC
     */
    public void computeMethode_BC() {
        this.methode_BC = (float) this.methode_DC / (float) this.CC;
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

    /**
     * Incrémente le nombre de lignes de code de la méthode
     */
    public void incrementMethode_LOC() {
        this.methode_LOC++;
    }

    /**
     * @return methode_CLOC Nombre de lignes contenant des commentaires de la méthode
     */
    public int getMethode_CLOC() {
        return methode_CLOC;
    }

    /**
     * Incrémente le nombre de lignes contenant des commentaires de la méthode
     */
    public void incrementMethode_CLOC() {
        this.methode_CLOC++;
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
    public int getCC() {
        return CC;
    }

    /**
     * @return Nombre de clauses if dans la méthode
     */
    public int getNoOfIfs() {
        return noOfIfs;
    }

    /**
     * Incrémente noOfIfs
     */
    public void incrementNoOfIfs() {
        this.noOfIfs += 1;
    }

    /**
     * @return Nombre de cases dans les blocs switch de la méthodes
     */
    public int getNoOfSwitchCases() {
        return noOfSwitchCases;
    }

    /**
     * Incrémente noOfSwitchCases
     */
    public void incrementNoOfSwitchCases() {
        this.noOfSwitchCases += 1;
    }

    /**
     * @return Nombre de bloc while ou do-while de la méthode
     */
    public int getNoOfWhileLoops() {
        return noOfWhileLoops;
    }

    /**
     * Incrémente noOfWhileLoops
     */
    public void incrementNoOfWhileLoops() {
        this.noOfWhileLoops += 1;
    }


    /**
     * @return Nombre de boucles for dans la méthode
     */
    public int getNoOfForLoops() {
        return noOfForLoops;
    }

    /**
     * Incrémente noOfForLoops
     */
    public void incrementNoOfForLoops() {
        this.noOfForLoops += 1;
    }

    /**
     * @return Le degré selon lequel une méthode est bien commentée : methode_BC = methode_DC/CC
     */
    public float getMethode_BC() {
        return this.methode_BC;
    }
}
