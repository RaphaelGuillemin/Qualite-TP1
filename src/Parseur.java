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
    public static void print(Object str){
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
            Class classe = null;
            Method method = null;
            int classCount = 0;
            int methodCount = 0;
            int classCommentCount = 0;
            int methodCommentCount = 0;
            int ignoreCount = 0;
            int commentCount = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Ligne vide
                if(line.equals("")){
                    commentCount = 0;
                    continue;
                // Commentaire et javadoc
                } else if(inComment || line.trim().startsWith("//") || line.trim().startsWith("/*")
                        || line.trim().startsWith("@")) {
                    if(line.trim().startsWith("/*")){
                        inComment = true;
                    }
                    if(inClass){
                        classCommentCount++;
                        if(inMethod){
                            methodCommentCount++;
                        } else {
                            commentCount++;
                        }
                    } else {
                        commentCount++;
                    }
                    if(line.contains("*/")){
                        inComment = false;
                    }
                // Ligne de code
                } else {
                    // nouvelle classe
                    if (line.contains("class")){
                        inClass = true;
                        String name = line.substring(line.indexOf("class"), line.indexOf('{')).split(" ")[1];
                        classe = new Class(name);
                        if(commentCount > 0){
                            classCommentCount = commentCount;
                            commentCount = 0;
                        }
                        if(line.contains("//")){
                            classCommentCount++;
                        }
                        if(line.contains("/*")){
                            inComment = true;
                            classCommentCount++;
                            if (line.contains("*/")){
                                inComment = false;
                            }
                        }
                        continue;
                    }
                    // nouvelle méthode
                    if (line.contains("{") && inClass && !inMethod){
                        inMethod = true;
                        String nameAndArgs = line.trim().split(" ")[3];
                        String name = nameAndArgs.split("[(]")[0];
                        classCount++;
                        if(commentCount > 0){
                            methodCommentCount = commentCount;
                            commentCount = 0;
                        }
                        if(line.contains("//")){
                            methodCommentCount++;
                        }
                        if(line.contains("/*")){
                            inComment = true;
                            methodCommentCount++;
                            classCommentCount++;
                            if (line.contains("*/")){
                                inComment = false;
                            }
                        }
                        method = new Method(name);
                        continue;
                    }
                    else if (line.contains("{") && inClass && inMethod && line.contains("}") ){
                        classCount++;
                        methodCount++;
                        continue;
                    }
                    else if (line.contains("{") && inClass && inMethod){
                        ignoreCount++;
                        methodCount++;
                        classCount++;
                        continue;
                    }
                    if (line.contains("}") && ignoreCount > 0){
                        ignoreCount--;
                        methodCount++;
                        classCount++;
                        continue;
                    }
                    if (inClass && !inMethod && line.contains("}") && ignoreCount == 0){
                        inClass = false;
                        classe.setClasse_LOC(classCount);
                        classCount = 0;
                        if(line.contains("//")){
                            classCommentCount++;
                        }
                        if(line.contains("/*")){
                            inComment = true;
                            classCommentCount++;
                            if (line.contains("*/")){
                                inComment = false;
                            }
                        }
                        classe.setClasse_CLOC(classCommentCount);
                        classCommentCount = 0;
                        classe.setClasse_DC();
                        javaFile.addClass(classe);
                        classe = null;
                    }
                    if (inMethod && line.contains("}") && ignoreCount == 0){
                        inMethod = false;
                        method.setMethode_LOC(methodCount);
                        methodCount = 0;
                        if(line.contains("//")){
                            methodCommentCount++;
                        }
                        if(line.contains("/*")){
                            inComment = true;
                            methodCommentCount++;
                            if (line.contains("*/")){
                                inComment = false;
                            }
                        }
                        method.setMethode_CLOC(methodCommentCount);
                        methodCommentCount = 0;
                        method.setMethode_DC();
                        classe.addMethod(method);
                        method = null;
                    }
                    if (inClass){
                        classCount++;
                        if(line.contains("//")){
                            classCommentCount++;
                        }
                        if(line.contains("/*")){
                            inComment = true;
                            classCommentCount++;
                            if (line.contains("*/")){
                                inComment = false;
                            }
                        }
                    }
                    if (inMethod){
                        methodCount++;
                        if(line.contains("//")){
                            methodCommentCount++;
                        }
                        if(line.contains("/*")){
                            inComment = true;
                            methodCommentCount++;
                            if (line.contains("*/")){
                                inComment = false;
                            }
                        }
                    }
                }

            }
            scanner.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        print(javaFile.getClasses().get(0).getClasse_LOC());
        return javaFile;
    }

    /*
     * Créé le fichier csv contenant toutes les informations des fichiers java
     */
    public static void createCSVFile(){

    }
}
