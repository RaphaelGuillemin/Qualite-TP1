import java.io.*;
import java.util.ArrayList;

public class ParserTest {

    static String folderPath = "resources";

    /**
     * Happy path. Test JavaFile name.
     * @throws Exception
     */
    public static void parseNewFileTest() throws Exception {
        // Prepare
        String filePath = "/testfolder/Main.java";
        File testFile = new File(folderPath + filePath);

        // Run
        JavaFile testJavaFile = Parser.parseNewFile(testFile);

        // Assert
        assert testJavaFile.getPath().equals(folderPath + filePath);
    }

    /**
     * Test basic file parsing.
     * @throws Exception
     */
    public static void parseMainFileTest() throws Exception {
        // Prepare
        String filePath = "/testfolder/Main.java";
        String className = "Main";
        int classLOC = 43;
        int classCLOC = 26;
        double classDC = 0.60465115;
        double classWMC = 8;
        double classBC = 0.075581394;
        File testFile = new File(folderPath + filePath);

        // Run
        JavaFile testJavaFile = Parser.parseNewFile(testFile);

        // Assert
        ArrayList<Class> classes = testJavaFile.getClasses();
        Class testClass = classes.get(0);
        assert testClass.getName().equals(className);
        assert testClass.getClasse_LOC() == classLOC;
        assert testClass.getClasse_CLOC() == classCLOC;
        assert testClass.getClasse_DC() == (float) classDC;
        assert testClass.getWMC() == classWMC;
        assert testClass.getClasse_BC() == (float) classBC;
    }

    /**
     * Test interface parsing. Inteface should be considered as a class.
     * @throws Exception
     */
    public static void parseInterfaceTest() throws Exception {
        // Prepare
        String filePath = "/testfolder/interface.java";
        File testFile = new File(folderPath + filePath);
        String interfaceName = "Animal";
        String className = "Pig";
        int interfaceLOC = 4;
        int interfaceCLOC = 3;
        double interfaceDC = 0.75;
        double interfaceWMC = 0;


        // Run
        JavaFile testJavaFile = Parser.parseNewFile(testFile);

        // Assert
        Class firstClass = testJavaFile.getClasses().get(0);
        Class SecondClass = testJavaFile.getClasses().get(1);
        assert (testJavaFile.getClasses().size() == 2);
        assert firstClass.getName().equals(interfaceName);
        assert SecondClass.getName().equals(className);

        assert firstClass.getName().equals(interfaceName);
        assert firstClass.getClasse_LOC() == interfaceLOC;
        assert firstClass.getClasse_CLOC() == interfaceCLOC;
        assert firstClass.getClasse_DC() == interfaceDC;
        assert firstClass.getWMC() == interfaceWMC;
        assert Float.isInfinite(firstClass.getClasse_BC());
    }

    /**
     * Test enum parsing. Enum should be considered as a class.
     * @throws Exception
     */
    public static void parseEnumTest() throws Exception {
        // Prepare
        String filePath = "/testfolder/enum.java";
        File testFile = new File(folderPath + filePath);
        String enumName = "ScaleName";
        int enumLOC = 1;
        int enumCLOC = 0;
        double enumDC = 0;
        double enumWMC = 0;

        // Run
        JavaFile testJavaFile = Parser.parseNewFile(testFile);

        // Assert
        Class enumClass = testJavaFile.getClasses().get(0);
        assert enumClass.getName().equals(enumName);
        assert enumClass.getName().equals(enumName);
        assert enumClass.getClasse_LOC() == enumLOC;
        assert enumClass.getClasse_CLOC() == enumCLOC;
        assert enumClass.getClasse_DC() == enumDC;
        assert enumClass.getWMC() == enumWMC;
        assert Float.isNaN(enumClass.getClasse_BC());
    }

    /**
     * Test method parsing.
     * @throws Exception
     */
    public static void parseMethodsTest() throws Exception {
        // Prepare
        String filePath = "/testfolder/Main.java";
        String methodName = "somme";
        int methodLOC = 15;
        int methodCLOC = 2;
        double methodDC = 0.13333334;
        double methodBC = 0.026666667;
        int methodCC = 5;
        File testFile = new File(folderPath + filePath);

        // Run
        JavaFile testJavaFile = Parser.parseNewFile(testFile);

        // Assert
        ArrayList<Class> classes = testJavaFile.getClasses();
        Class testClass = classes.get(0);
        Method firstMethod = testClass.getMethods().get(0);
        assert firstMethod.getName().equals(methodName);
        assert firstMethod.getMethode_LOC() == methodLOC;
        assert firstMethod.getMethode_CLOC() == methodCLOC;
        assert firstMethod.getMethode_DC() == (float) methodDC;
        assert firstMethod.getMethode_BC() == (float) methodBC;
        assert firstMethod.getCC() ==  methodCC;
    }

    public static void notJavaFileTest() throws Exception {
        // Prepare
        String filePath = "/thisissupposedtobeignored.txt";
        File testFile = new File(folderPath + filePath);
        Parser.setJavaFiles(new ArrayList<>());

        // Run
        Parser.recursiveParseFiles(testFile);

        // Assert
        assert Parser.getJavaFiles().isEmpty();
    }

    public static void main(String[] args) throws Exception {
        parseNewFileTest();
        parseMainFileTest();
        parseInterfaceTest();
        parseEnumTest();
        parseMethodsTest();
        notJavaFileTest();
    }
}
