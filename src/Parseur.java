/* @Author Laura Bégin ()
 * @Author Raphaël Guillemin (p1202392, 20129638)
 */

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Scanner;

public class Parseur {
    // Ensemble des fichiers java du dossier
    static private ArrayList<JavaFile> javaFiles = new ArrayList<JavaFile>();
    // Name of the folder to be read from
    static private String folderPath;

    //TODO REMOVE THIS BEFORE SENDING
    public static void print(String str){
        System.out.println(str);
    }

    /*
     * @param args A string of the path to the folder containing java files
     */
    public static void main(String[] args) throws Exception {
        // Si aucun argument passé, ne rien faire
        if (args.length != 0){
            folderPath = args[0];
        } else {
            System.err.println("Veuillez ajouter le chemin vers le dossier désiré.");
            return;
        }

        // Ouvrir le lien passé en paramètre
        File folder = new File(folderPath);
        // Séléctionner les fichiers java uniquement
        File[] filesFolder = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.endsWith("java");
            }
        });

        // Déterminer si le dossier existe
        if (filesFolder == null){
            System.err.println("Ce dossier n'existe pas.");
            return;
        }
        // Déterminer si le dossier est vide
        else if (filesFolder.length == 0){
            System.err.println("Ce dossier ne contient aucun fichier java");
            return;
        }

        // Analyser chaque fichier java du dossier
        for(File file : filesFolder) {
            File javaFile = new File(folderPath + '/' + file.getName());
            javaFiles.add(parseNewFile(javaFile));
        }
        createCSVFile();
    }

    /*
     * @param fichier java souhaité
     * @return instance de JavaFile du fichier analysé
     */
    public static JavaFile parseNewFile(File file) throws Exception{
        JavaFile javaFile = new JavaFile(file.getName());
        try {
            Scanner scanner = new Scanner(file);
            boolean inClass = false;
            boolean inMethod = false;
            boolean inComment = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.equals("")){
                    continue;
                } else {
                    print(line);
                }

            }
            scanner.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return javaFile;
    }

    /*
     * Créé le fichier csv contenant toutes les informations des fichiers java
     */
    public static void createCSVFile(){

    }
}
