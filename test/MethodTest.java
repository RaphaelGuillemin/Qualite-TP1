import java.util.ArrayList;

public class MethodTest {

    /**
     * Happy path. Compute CC.
     */
    public static void computeCCTest() {
        // Prepare
        Method testMethod = new Method("testMethod", new ArrayList<>(), 10, 10);
        testMethod.incrementNoOfIfs();
        testMethod.incrementNoOfWhileLoops();
        testMethod.incrementNoOfSwitchCases();
        testMethod.incrementNoOfForLoops();
        testMethod.incrementNoOfForLoops();


        // Run
        testMethod.computeCC();

        // Assert
        assert testMethod.getCC() == 6;
    }

    public static void main(String[] args) throws Exception {
        computeCCTest();
    }
}
