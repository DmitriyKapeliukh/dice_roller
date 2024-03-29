package dice.steps;

import common.Namespace;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class DiceSteps {

    /**
     * get rolls of dice result
     *
     * @param rollsCount rolls count
     * @return array with result
     */

    @Step
    public int[] rollsOfDice(final int rollsCount) {
        int[] result = new int[rollsCount];
        for (int i = 0; i < rollsCount; i++) {
            result[i] = Integer.parseInt(getDiceRollResult(Namespace.ROLL.getName()).asString());
        }
        return getListSortedResult(6, result);
    }

    /**
     * verify that result of one dice distribution with in 5%
     *
     * @param sortedFacesResult sorted array with dice result
     */

    @Step
    public void verifyGaussianDistribution(final int[] sortedFacesResult) {
        int percentDiff = getDiff(sortedFacesResult[0], sortedFacesResult[sortedFacesResult.length - 1]);

        assertThat("The dice roll distribution more than 5% ", 5, is(greaterThan(percentDiff)));
    }

    /**
     * get difference between two int in percent
     *
     * @param a num a
     * @param b num b
     * @return difference in percent
     */

    @Step
    public int getDiff(final int a, final int b) {
        return ((b - a) * 100) / a;
    }

    /**
     * @param rollsCount get rolls of tow dice result
     * @return map of roll two side sum result
     */

    @Step
    public Map<Integer, Integer> rollsOfTwoDice(final int rollsCount) {
        int[] result = new int[rollsCount];
        for (int i = 0; i < rollsCount; i++) {
            Response response = getDiceRollResult(getMultiDiceNamespace(2, 6, 1));

            result[i] = getSumFromMultipleDices(response);
        }
        return getSortedResult(result);
    }

    /**
     * add two dice rolls result into the report
     *
     * @param twoDiceResult two dice rolls result
     */

    @Step
    public void addTwoDiceRollResultToReport(final Map<Integer, Integer> twoDiceResult) {
        Serenity.recordReportData().withTitle("Two Dice Result").andContents(twoDiceResult.toString());
    }

    private Response getDiceRollResult(final String namespace) {
        return given().get(System.getProperty("apiHost") + namespace)
                .then().assertThat().statusCode(200)
                .extract().response();
    }

    private int getSumFromMultipleDices(final Response response) {
        return Arrays.stream(response.asString().split(StringUtils.SPACE))
                .mapToInt(Integer::parseInt)
                .sum();
    }

    private String getMultiDiceNamespace(final int dices, final int faces, final int rolls) {
        return String.format(Namespace.MULTI_ROLL.getName(), dices, faces, rolls);
    }

    @NotNull
    private int getCountOfFace(final int[] result, final int face) {
        return IntStream.of(result)
                .filter(s -> s == face)
                .toArray().length;
    }

    private Map<Integer, Integer> getSortedResult(final int[] diceResult) {
        Map<Integer, Integer> result = new HashMap<>();
        for (int i = 2; i < 13; i++) {
            result.put(i, getCountOfFace(diceResult, i));
        }
        return result;
    }

    private int[] getListSortedResult(final int sumFaces, final int[] diceResult) {
        int[] countDiceFaces = new int[sumFaces];
        for (int i = 0; i < sumFaces; i++) {
            countDiceFaces[i] = getCountOfFace(diceResult, i + 1);
        }
        Arrays.sort(countDiceFaces);
        return countDiceFaces;
    }


}
