package dice.test;

import dice.steps.DiceSteps;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Concurrent;
import net.thucydides.junit.annotations.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RunWith(SerenityParameterizedRunner.class)
@Concurrent
public class DiceTest {

    @TestData
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {1001}, {5001}, {10001}
        });
    }

    private final int rolls;

    public DiceTest(final int count) {
        this.rolls = count;
    }

    @Steps
    private DiceSteps diceSteps;

    @Test
    public void verifyGaussianDistributionTest() {
        int[] facesResult = diceSteps.rollsOfDice(rolls);
        diceSteps.verifyGaussianDistribution(facesResult);
    }

    @Test
    public void verifyGaussianDistributionForTwoDiceTest() {
        Map<Integer, Integer> result = diceSteps.rollsOfTwoDice(rolls);
        diceSteps.addTwoDiceRollResultToReport(result);
    }
}
