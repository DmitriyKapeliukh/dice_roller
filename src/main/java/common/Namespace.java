package common;

import com.google.inject.internal.cglib.core.$DuplicatesPredicate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Namespace {

    ROLL("/api/roll"),
    MULTI_ROLL("/api/roll?dices=%d&faces=%d&rolls=%d");

    private String name;
}
