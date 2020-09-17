/* @Author Laura Bégin ()
 * @Author Raphaël Guillemin (p1202392, 20129638)
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintWriter;
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
                        // Vérifier si ligne contient un commentaire
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
                        ArrayList<ArrayList<String>> nameAndArgs = getNameAndArgs(line.trim());
                        String name = nameAndArgs.get(0).get(0);
                        ArrayList<String> args = nameAndArgs.get(1);
                        classCount++;
                        if(commentCount > 0){
                            methodCommentCount = commentCount;
                            commentCount = 0;
                        }
                        // Vérifier si ligne contient un commentaire
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
                        method = new Method(name,args);
                        continue;
                    // Cas de ligne de code contenant des {} sur la meme ligne
                    } else if (line.contains("{") && inClass && inMethod && line.contains("}") ){
                        classCount++;
                        methodCount++;
                        continue;
                    // Cas ou la ligne de code contient uniquement un { (par exemple: for et while)
                    } else if (line.contains("{") && inClass && inMethod){
                        ignoreCount++;
                        methodCount++;
                        classCount++;
                        continue;
                    }
                    // Cas ou l'on fini une boucle
                    if (line.contains("}") && ignoreCount > 0){
                        ignoreCount--;
                        methodCount++;
                        classCount++;
                        continue;
                    }
                    // fin de classe
                    if (inClass && !inMethod && line.contains("}") && ignoreCount == 0){
                        inClass = false;
                        classe.setClasse_LOC(classCount);
                        classCount = 0;
                        // Vérifier si ligne contient un commentaire
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
                    // fin de méthode
                    if (inMethod && line.contains("}") && ignoreCount == 0){
                        inMethod = false;
                        method.setMethode_LOC(methodCount);
                        methodCount = 0;
                        // Vérifier si ligne contient un commentaire
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
                    // ligne de code d'une classe
                    if (inClass){
                        classCount++;
                        // Vérifier si ligne contient un commentaire
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
                    // ligne de code d'une méthode
                    if (inMethod){
                        methodCount++;
                        // Vérifier si ligne contient un commentaire
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
        return javaFile;
    }

    /*
     * @param ligne qui contient le nom et les arguments de la méthode
     * @return Arraylist de [[nom], [arguments]]
     */
    public static ArrayList<ArrayList<String>> getNameAndArgs(String line){
        ArrayList<ArrayList<String>> tab = new ArrayList<ArrayList<String>>();
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<String> args = new ArrayList<String>();

        String[] parts = line.split(" ");
        for (String part : parts){
            if(part.contains("(")){
                name.add(part.split("[(]")[0]);
            }
        }

        String[] arguments = line.substring(line.indexOf("(") + 1, line.indexOf(')')).split(" ");
        for(int i=0; i < arguments.length; i+=2){
            args.add(arguments[i]);
        }
        tab.add(name);
        tab.add(args);
        return tab;
    }

    /*
     * Créé les fichiers csv contenant toutes les informations des fichiers java
     */
    public static void createCSVFile(){
        // Classes
        try (PrintWriter writer = new PrintWriter(new File("classes.csv"))) {
            StringBuilder output = new StringBuilder();
            output.append("chemin, class, classe_LOC, classe_CLOC, classe_DC\n");
            for(JavaFile javaFile: javaFiles){
                output.append(folderPath + '/' + javaFile.getName() + ", ");
                Class classe = javaFile.getClasses().get(0);
                output.append(classe.getName() + ", " +  classe.getClasse_LOC() + ", " + classe.getClasse_CLOC() + ", " + classe.getClasse_DC());
                output.append('\n');
            }

            writer.write(output.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Méthode
        try (PrintWriter writer = new PrintWriter(new File("methodes.csv"))) {
            StringBuilder output = new StringBuilder();
            output.append("chemin, class, methode1, methode_LOC, methode_CLOC, methode_DC\n");
            for(JavaFile javaFile: javaFiles){
                Class classe = javaFile.getClasses().get(0);
                ArrayList<Method> methods = classe.getMethods();
                for(Method method : methods){
                    output.append(folderPath + '/' + javaFile.getName() + ", ");
                    output.append(classe.getName() + ", ");
                    String name = method.getName();
                    for (String arg : method.getArgs()){
                        name += ('_' + arg);
                    }
                    output.append(name + ", " +  method.getMethode_LOC() + ", " + method.getMethode_CLOC() + ", " + method.getMethode_DC() + '\n');
                }
            }
            writer.write(output.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
