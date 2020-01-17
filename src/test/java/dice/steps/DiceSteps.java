package dice.steps;

import common.Namespace;
import io.vavr.collection.Array;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;

public class DiceSteps {

    private static final String API_HOST = "http://localhost:8888";

    private int getDiceRollResult() {
        return Integer.parseInt(SerenityRest.given().get(API_HOST + Namespace.ROLL.getName())
                .then().assertThat().statusCode(200)
                .extract().response().asString());
    }

    @Step
    public int[] rollsOfDice(final int rollsCount) {
        int[] result = new int[rollsCount];
        for (int i = 0; i < rollsCount; i++) {
            result[i] = getDiceRollResult();
        }

        return getListSortedResult(result);
    }

    @Step
    public void verifyGaussianDistribution(final int[] sortedFacesResult) {
        double percentDiff = getDiff(sortedFacesResult[0], sortedFacesResult.length - 1);

        assertThat("The dice roll distribution more than 5% ", 5.0 < percentDiff);
    }

    @Step
    public double getDiff(final int a, final int b) {
        return (double) ((b - a) * 100) / a;
    }

    @NotNull
    private int getCountOfFace(final int[] result, final int face) {
        return Arrays.stream(result)
                .filter(s -> s == face)
                .boxed()
                .toArray(Integer[]::new).length;

    }

    private int[] getListSortedResult(final int[] diceResult) {
        int[] countDiceFaces = new int[6];
        for (int i = 0; i < 6; i++) {
            countDiceFaces[i] = getCountOfFace(diceResult, i);
        }
        Arrays.sort(countDiceFaces);
        return countDiceFaces;
    }


}
