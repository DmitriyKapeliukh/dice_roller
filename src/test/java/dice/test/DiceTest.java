package dice.test;

import dice.steps.DiceSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Concurrent;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

@RunWith(SerenityRunner.class)
@Concurrent
public class DiceTest {

/*    @TestData
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {1001}, {5001}, {10001}
        });
    }

    private final int rolls;

    public DiceTest(final int count) {
        this.rolls = count;
    }*/

    @Steps
    private DiceSteps diceSteps;

    @Test
    public void verifyGaussianDistributionTest() {
        int[] facesResult = diceSteps.rollsOfDice(1001);
        diceSteps.verifyGaussianDistribution(facesResult);
    }

    @Test
    public void verifyGaussianDistributionForTwoDiceTest() {
        Map<Integer, Integer> result = diceSteps.rollsOfTwoDice(1001);
        diceSteps.addTwoDiceRollResultToReport(result);
    }
}
