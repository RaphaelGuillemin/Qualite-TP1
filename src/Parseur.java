/* @Author Laura Bégin ()
 * @Author Raphaël Guillemin (p1202392, 20129638)
 */

import java.io.File;
import java.util.Scanner;

public class Parseur {
    // Nombre de lignes de code d’une classe
    private int classe_LOC;
    // Nombre de lignes de code d’une méthode
    private int methode_LOC;
    // Nombre de lignes de code d’une classe qui contiennent des commentaires
    private int classe_CLOC;
    // Nombre de lignes de code d’une méthode qui contiennent des commentaires
    private int methode_CLOC;
    // Densité de commentaires pour une classe : classe_DC = classe_CLOC / classe_LOC
    private int classe_DC;
    // Densité de commentaires pour une méthode : methode_DC = methode_CLOC / methode_LOC
    private int methode_DC;


    //TODO REMOVE THIS BEFORE SENDING
    public static void print(String str){
        System.out.println(str);
    }
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
     * @returns classe_DC
     */
    public int getClasse_DC() {
        return classe_DC;
    }

    /*
     * @param classe_DC
     */
    public void setClasse_DC(int classe_DC) {
        this.classe_DC = classe_DC;
    }

    /*
     * @returns methode_DC
     */
    public int getMethode_DC() {
        return methode_DC;
    }

    /*
     * @param methode_DC
     */
    public void setMethode_DC(int methode_DC) {
        this.methode_DC = methode_DC;
    }

    /*
     * @param args A string of the path to the java file to read
     */
    public static void main(String[] args) throws Exception {
        String filePath;
        if (args.length != 0){
            filePath = args[0];
        } else {
            System.err.println("Veuillez ajouter le chemin vers le fichier java désiré.");
            return;
        }

        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
