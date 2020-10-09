/* @Author Laura Bégin (p1164844, 20093040)
 * @Author Raphaël Guillemin (p1202392, 20129638)
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    // Ensemble des fichiers java du dossier
    static private ArrayList<JavaFile> javaFiles = new ArrayList<JavaFile>();

    /*
     * @param args A string of the path to the folder containing java files
     */
    public static void main(String[] args) throws Exception {
        // Si aucun argument passé, ne rien faire
        String folderPath;
        if (args.length != 0){
            folderPath = args[0];
        } else {
            System.err.println("Veuillez ajouter le chemin vers le dossier désiré.");
            return;
        }

        // Ouvrir le lien passé en paramètre
        File folder = new File(folderPath);
        recursiveParseFiles(folder);
        createCSVFiles();
    }

    public static void recursiveParseFiles(File dir) throws Exception {
        try {
            File[] files = dir.listFiles();
            if (files == null){
                System.err.println("Le dossier n'existe pas");
                return;
            } else if (files.length == 0) {
                System.err.println("Le dossier est vide");
                return;
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    recursiveParseFiles(file);
                } else {
                    if (file.getPath().endsWith("java")) {
                        File javaFile = new File(file.getPath());
                        javaFiles.add(parseNewFile(javaFile));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * @param fichier java souhaité
     * @return instance de JavaFile du fichier analysé
     */
    public static JavaFile parseNewFile(File file) throws Exception{
        JavaFile javaFile = new JavaFile(file.getPath());
        try {
            Scanner scanner = new Scanner(file);
            boolean inComment = false;
            boolean inSwitch = false;

            Class classe = null;
            Method method = null;
            Class outerClass = null;

            int ignoreCount = 0; // Nombre de fois que l'on rencontre un { dans une méthode
            int ignoreCountStartSwitch = 0; // Valeur de ignoreCount à l'entrée d'un bloc switch
            int untrackedLOC = 0;
            int untrackedCLOC = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String nonCommentLine = line.trim();
                // Ligne vide
                if(nonCommentLine.equals("")){
                    untrackedCLOC = 0;
                    if (classe != null){
                        untrackedLOC = 0;
                    }
                    continue;
                }
                // Compter le nombre de case dans un bloc switch
                if (inSwitch) {
                    if (nonCommentLine.startsWith("case")) {
                        method.incrementNoOfSwitchCases();
                    }
                    if (nonCommentLine.contains("}") && ignoreCountStartSwitch == ignoreCount) {
                        inSwitch = false;
                        ignoreCountStartSwitch = 0;
                    }
                }
                // Commentaire et javadoc
                if(inComment || nonCommentLine.startsWith("//") || nonCommentLine.startsWith("/*")
                        || nonCommentLine.startsWith("@")) {
                    if (nonCommentLine.startsWith("/*")) {
                        inComment = true;
                    }
                    if (classe != null) {
                        classe.incrementClasse_CLOC();
                        if(outerClass != null){
                            outerClass.incrementClasse_CLOC();
                        }
                        if (method != null) {
                            method.incrementMethode_CLOC();
                        } else {
                            untrackedCLOC++;
                        }
                    } else {
                        untrackedCLOC++;
                    }
                    if (nonCommentLine.contains("*/")) {
                        inComment = false;
                    }

                // Ligne de code
                } else {

                    // Incrementer les LOC de toutes les classes en cours et de la methode
                    if (classe != null){
                        if (outerClass != null){
                            outerClass.incrementClasse_LOC();
                        }
                        classe.incrementClasse_LOC();
                        if (method != null){
                            method.incrementMethode_LOC();
                        } else {
                            untrackedLOC++;
                        }
                    } else {
                        untrackedLOC++;
                    }

                    // Séparer la ligne en 2 blocs (avant le début du commentaire et après le début du commentaire)
                    if (nonCommentLine.contains("//") || nonCommentLine.contains("/*")) {
                        if((nonCommentLine.contains("/*") || inComment) && !nonCommentLine.contains("*/")){
                            inComment = true;
                        } else {
                            inComment = false;
                        }
                        nonCommentLine = getNonCommentLine(nonCommentLine, classe, outerClass, method);
                        if ((classe == null && outerClass == null && method == null) || (classe != null && method == null)){
                            untrackedCLOC++;
                        }
                    }

                    // cas où une ligne ne finit pas par ; ou { ou } ou :
                    if (!nonCommentLine.trim().endsWith(";") && !nonCommentLine.trim().endsWith("{") &&
                            !nonCommentLine.trim().endsWith("}") && !nonCommentLine.trim().endsWith(":")){
                        while(!nonCommentLine.trim().endsWith(";") && !nonCommentLine.trim().endsWith("{") &&
                                !nonCommentLine.trim().endsWith("}") && !nonCommentLine.trim().endsWith(":") && scanner.hasNextLine()){
                            if(nonCommentLine.endsWith("(")){
                                nonCommentLine = nonCommentLine.concat(scanner.nextLine().trim());
                            } else {
                                nonCommentLine = nonCommentLine.concat(" " + scanner.nextLine().trim());
                            }
                            if (nonCommentLine.contains("//") || nonCommentLine.contains("/*")) {
                                if((nonCommentLine.contains("/*") || inComment) && !nonCommentLine.contains("*/")){
                                    inComment = true;
                                } else {
                                    inComment = false;
                                }
                                nonCommentLine = getNonCommentLine(nonCommentLine, classe, outerClass, method);
                                if ((classe == null && outerClass == null && method == null) || (classe != null && method == null)){
                                    untrackedCLOC++;
                                }
                            }
                            if (classe != null){
                                if (outerClass != null){
                                    outerClass.incrementClasse_LOC();
                                }
                                classe.incrementClasse_LOC();
                                if (method != null){
                                    method.incrementMethode_LOC();
                                } else {
                                    untrackedLOC++;
                                }
                            } else {
                                untrackedLOC++;
                            }
                        }
                    }

                    // à partir d'ici toutes les lignes de code sont complètes

                    // nouvelle classe
                    if ((nonCommentLine.contains("class ") || nonCommentLine.contains("interface ") ||
                            nonCommentLine.contains("enum ")) && !nonCommentLine.trim().startsWith("}") &&
                            !nonCommentLine.contains("=") && !nonCommentLine.endsWith(";")){
                        if(classe != null){
                            outerClass = classe;
                        }
                        String name;
                        if (nonCommentLine.contains("enum ")) {
                            name = nonCommentLine.substring(nonCommentLine.indexOf("enum"), nonCommentLine.indexOf('{')).split(" ")[1];
                        } else if (nonCommentLine.contains("interface ")) {
                            name = nonCommentLine.substring(nonCommentLine.indexOf("interface"), nonCommentLine.indexOf('{')).split(" ")[1];
                            if(name.contains("<")){
                                name = name.split("<")[0];
                            }
                        } else {
                            name = nonCommentLine.substring(nonCommentLine.indexOf("class"), nonCommentLine.indexOf('{')).split(" ")[1];
                            if(name.contains("<")){
                                name = name.split("<")[0];
                            }
                        }
                        // Si commentaire juste avant la classe, il sera considéré dans le compte des commentaires de la classe
                        classe = new Class(name, untrackedLOC, untrackedCLOC);
                        untrackedCLOC = 0;
                        untrackedLOC = 0;

                        // Si une classe est sur une seule ligne, comme dans le cas d'un enum
                        if (nonCommentLine.contains("}")) {
                            classe.computeClasse_DC();
                            classe.computeWMC();
                            classe.computeClasse_BC();
                            javaFile.addClass(classe);
                            if (outerClass != null){
                                classe = outerClass;
                                outerClass = null;
                            } else {
                                classe = null;
                            }
                        }
                        continue;
                    }
                    // nouvelle méthode
                    if (nonCommentLine.contains("{") && nonCommentLine.contains("(") &&
                            classe != null && method == null && !nonCommentLine.contains("=") && !nonCommentLine.contains("->")
                            && !nonCommentLine.startsWith("if") && !nonCommentLine.startsWith("else") && !nonCommentLine.startsWith("for")){
                        ArrayList<String> args;
                        String name;
                        if (!nonCommentLine.contains("class")){
                            ArrayList<ArrayList<String>> nameAndArgs = getNameAndArgs(nonCommentLine);
                            name = nameAndArgs.get(0).get(0);
                            args = nameAndArgs.get(1);
                        } else {
                            name = nonCommentLine.substring(nonCommentLine.indexOf("class"), nonCommentLine.indexOf('{')).split(" ")[1];
                            args = new ArrayList<String>();
                        }
                        method = new Method(name, args, untrackedLOC, untrackedCLOC);
                        untrackedCLOC = 0;
                        untrackedLOC = 0;
                        // Si une méthode est sur une seule ligne, comme dans le cas d'un enum
                        if (nonCommentLine.contains("}")) {
                            method.computeMethode_DC();
                            method.computeCC();
                            method.computeMethode_BC();
                            classe.addMethod(method);
                            method = null;
                        }
                        continue;
                    }
                    // Cas de ligne de code contenant des {} sur la meme ligne
                    if (nonCommentLine.contains("{") && classe != null && method != null && nonCommentLine.contains("}") ){
                        if (nonCommentLine.startsWith("} else if")) {
                            method.incrementNoOfIfs();
                        }
                        continue;
                    // Cas ou la ligne de code contient un { (par exemple: for, while, switch, if, etc)
                    } else if (nonCommentLine.contains("{") && classe != null && method != null) {
                        ignoreCount++;
                        if (nonCommentLine.startsWith("if")) {
                            method.incrementNoOfIfs();
                        } else if (nonCommentLine.startsWith("switch")) {
                            inSwitch = true;
                            ignoreCountStartSwitch = ignoreCount;
                        } else if (nonCommentLine.startsWith("for")) {
                            method.incrementNoOfForLoops();
                        } else if (nonCommentLine.startsWith("while")) {
                            method.incrementNoOfWhileLoops();
                        }
                        continue;
                    }
                    // Cas ou l'on finit une boucle
                    if (nonCommentLine.contains("}") && ignoreCount > 0){
                        ignoreCount--;
                        continue;
                    }
                    // fin de classe
                    if (classe != null && method == null && nonCommentLine.contains("}") && ignoreCount == 0){
                        classe.computeClasse_DC();
                        classe.computeWMC();
                        classe.computeClasse_BC();
                        javaFile.addClass(classe);
                        untrackedLOC = 0;
                        if (outerClass != null){
                            classe = outerClass;
                            outerClass = null;
                        } else {
                            classe = null;
                        }
                        continue;
                    }
                    // fin de méthode
                    if (method != null && nonCommentLine.contains("}") && ignoreCount == 0){
                        method.computeMethode_DC();
                        method.computeCC();
                        method.computeMethode_BC();
                        classe.addMethod(method);
                        method = null;
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

    public static String getNonCommentLine(String line, Class classe, Class outterClass, Method method){
        String[] lineArray = line.trim().split("//{2}|//*");
        String nonCommentLine = lineArray[0].trim(); // Enlève le whitespace avant la ligne
        if (line.contains("*/")){
            String[] afterComment = line.trim().split("/*/");
            if(afterComment.length > 2 && afterComment[2].trim().length() > 0) {
                nonCommentLine = nonCommentLine.concat(" " + afterComment[2].trim());
            }
        }
        if (classe != null){
            if (outterClass != null){
                outterClass.incrementClasse_CLOC();
            }
            classe.incrementClasse_CLOC();
        }
        if (method != null){
            method.incrementMethode_CLOC();
        }
        return nonCommentLine;
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
        String[] arguments = line.substring(line.indexOf("(") + 1, line.indexOf(')')).split(",");
        for(int i=0; i < arguments.length; i++){
            args.add(arguments[i].trim().split(" ")[0]);
        }
        tab.add(name);
        tab.add(args);
        return tab;
    }

    /*
     * Créé les fichiers csv contenant toutes les informations des fichiers java
     */
    public static void createCSVFiles(){
        // Classes
        try (PrintWriter writer = new PrintWriter(new File("classes.csv"))) {
            StringBuilder output = new StringBuilder();
            output.append("chemin, class, classe_LOC, classe_CLOC, classe_DC, WMC, classe_BC\n");
            for (JavaFile javaFile: javaFiles){
                for (Class classe : javaFile.getClasses()) {
                    output.append(javaFile.getPath() + ", "
                            + classe.getName() + ", "
                            + classe.getClasse_LOC() + ", "
                            + classe.getClasse_CLOC() + ", "
                            + classe.getClasse_DC() + ", "
                            + classe.getWMC() + ", "
                            + classe.getClasse_BC() + "\n"
                    );
                }
            }

            writer.write(output.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Méthode
        try (PrintWriter writer = new PrintWriter(new File("methodes.csv"))) {
            StringBuilder output = new StringBuilder();
            output.append("chemin, class, methode, methode_LOC, methode_CLOC, methode_DC, CC, methode_BC\n");
            for (JavaFile javaFile: javaFiles){
                for (Class classe : javaFile.getClasses()) {
                    for (Method method : classe.getMethods()) {
                        output.append(javaFile.getPath() + ", ");
                        output.append(classe.getName() + ", ");
                        String name = method.getName();
                        for (String arg : method.getArgs()) {
                            name += ('_' + arg);
                        }
                        output.append(name + ", "
                                + method.getMethode_LOC() + ", "
                                + method.getMethode_CLOC() + ", "
                                + method.getMethode_DC() + ", "
                                + method.getCC() + ", "
                                + method.getMethode_BC() + "\n"
                        );
                    }
                }
            }
            writer.write(output.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * @return Ensemble des fichiers java du dossier
     */
    public static ArrayList<JavaFile> getJavaFiles() {
        return javaFiles;
    }

    /**
     * @param javaFiles Ensemble des fichiers java du dossier
     */
    public static void setJavaFiles(ArrayList<JavaFile> javaFiles) {
        Parser.javaFiles = javaFiles;
    }
}
