import java.util.ArrayList;

public class Method {
    // Nombre de lignes de code d’une méthode
    private int methode_LOC;
    // Nombre de lignes de code d’une méthode qui contiennent des commentaires
    private int methode_CLOC;
    // Densité de commentaires pour une méthode : methode_DC = methode_CLOC / methode_LOC
    private float methode_DC;
    // Nom de la méthode
    private String name;
    // Arguments de la méthode
    private ArrayList<String> args = new ArrayList<String>();
    /*
     * @returns methode_LOC
     */
    public int getMethode_LOC() {
        return methode_LOC;
    }

    /*
     * @param methode_LOC
     */
    public void setMethode_LOC(int methode_LOC) {
        this.methode_LOC = methode_LOC;
    }

    /*
     * @returns methode_CLOC
     */
    public int getMethode_CLOC() {
        return methode_CLOC;
    }

    /*
     * @param methode_CLOC
     */
    public void setMethode_CLOC(int methode_CLOC) {
        this.methode_CLOC = methode_CLOC;
    }

    /*
     * @returns methode_DC
     */
    public float getMethode_DC() {
        return methode_DC;
    }

    /*
     * Calcule la densité de commentaires pour une méthode
     */
    public void setMethode_DC() {
        this.methode_DC = (float)this.methode_CLOC / (float)this.methode_LOC;
    }

    public String getName() {
        return name;
    }

    public Method(String name) {
        this.name = name;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public void addArgs(String arg) {
        this.args.add(arg);
    }
}
