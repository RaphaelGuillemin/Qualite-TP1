import java.util.ArrayList;

public class ClassTest {

    /**
     * Happy path. Compute WMC from 2 methods.
     */
    public static void computeWMCTest() {
        // Prepare
        Method method1 = new Method("method1", new ArrayList<>(), 10, 10);
        Method method2 = new Method("method2", new ArrayList<>(), 20, 20);
        method1.incrementNoOfIfs();
        method1.incrementNoOfWhileLoops();

        method2.incrementNoOfSwitchCases();
        method2.incrementNoOfForLoops();
        method2.incrementNoOfForLoops();

        Class testClass = new Class("testClass", 30, 30);

        // Run
        method1.computeCC();
        testClass.addMethod(method1);
        method2.computeCC();
        testClass.addMethod(method2);
        testClass.computeWMC();

        // Assert
        assert testClass.getWMC() == 7;
    }

    public static void main(String[] args) throws Exception {
        computeWMCTest();
    }
}
