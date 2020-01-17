package dice.steps;

import common.Namespace;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

public class DiceSteps {

    private static final String API_HOST = "http://localhost:8888";

    @Step
    public int getDiceRollResult() {
        return Integer.parseInt(SerenityRest.given().get(API_HOST + Namespace.ROLL.getName())
                .then().assertThat().statusCode(200)
                .extract().response().asString());
    }

    @Step
    public List<Integer> rollsOfDice(final int rollsCount) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < rollsCount; i++) {
            result.add(getDiceRollResult());
        }

        return getListSortedResult(result);
    }

    @Step
    public void verifyGaussianDistribution(final List<Integer> sortedFacesResult) {
        double percentDiff = getDiff(sortedFacesResult.get(0),
                sortedFacesResult.get(sortedFacesResult.size() - 1));

        assertThat("The dice roll distribution more than 5% ", 5.0 < percentDiff);
    }

    @Step
    public double getDiff(final int a, final int b) {
        return (double) ((b - a) * 100) / a;
    }

    @NotNull
    private List<Integer> getCountOfFace(final List<Integer> result, final int face) {
        return result.stream()
                .filter(s -> s.equals(face))
                .collect(Collectors.toList());
    }

    private List<Integer> getListSortedResult(final List<Integer> diceResult) {
        List<Integer> countDiceFaces = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            countDiceFaces.add(getCountOfFace(diceResult, i).size());
        }
        Collections.sort(countDiceFaces);
        return countDiceFaces;
    }


}
