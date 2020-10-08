import java.io.*;
import java.util.ArrayList;

public class ParserTest {

    static String folderPath = "resources";

    public static void parseNewFileTest() throws Exception {
        // Prepare
        String filePath = "/testfolder/Main.java";
        File testFile = new File(folderPath + filePath);

        // Run
        JavaFile testJavaFile = Parser.parseNewFile(testFile);

        // Assert
        assert testJavaFile.getPath().equals(filePath);
    }

    public static void parseMainFileTest() throws Exception {
        // Prepare
        String filePath = "/testfolder/Main.java";
        String className = "Main";
        int classLOC = 40;
        int classCLOC = 25;
        double classDC = 0.625;
        double classWMC = 8;
        double classBC = 0.078125;
        File testFile = new File(folderPath + filePath);

        // Run
        JavaFile testJavaFile = Parser.parseNewFile(testFile);

        // Assert
        ArrayList<Class> classes = testJavaFile.getClasses();
        Class testClass = classes.get(0);
        assert testClass.getName().equals(className);
        assert testClass.getClasse_LOC() == classLOC;
        assert testClass.getClasse_CLOC() == classCLOC;
        assert testClass.getClasse_DC() == classDC;
        assert testClass.getWMC() == classWMC;
        assert testClass.getClasse_BC() == classBC;
    }

    public static void parseInterfaceTest() throws Exception {
        // Prepare
        String filePath = "/testfolder/interface.java";
        File testFile = new File(folderPath + filePath);
        String interfaceName = "Animal";
        String className = "Pig";
        int interfaceLOC = 2;
        int interfaceCLOC = 3;
        double interfaceDC = 1.5;
        double interfaceWMC = 0;


        // Run
        JavaFile testJavaFile = Parser.parseNewFile(testFile);

        // Assert
        Class firstClass = testJavaFile.getClasses().get(0);
        Class SecondClass = testJavaFile.getClasses().get(1);
        assert (testJavaFile.getClasses().size() == 2);
        assert firstClass.getName().equals(interfaceName);
        assert SecondClass.getName().equals(className);

        assert firstClass.getName().equals(className);
        assert firstClass.getClasse_LOC() == interfaceLOC;
        assert firstClass.getClasse_CLOC() == interfaceCLOC;
        assert firstClass.getClasse_DC() == interfaceDC;
        assert firstClass.getWMC() == interfaceWMC;
        assert Float.isFinite(firstClass.getClasse_BC());
    }

    public static void parseMethodsTest() throws Exception {

    }

    public static void main(String[] args) throws Exception {
        parseNewFileTest();
        parseMainFileTest();
        parseInterfaceTest();

    }
}
