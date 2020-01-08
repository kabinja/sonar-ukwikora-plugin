package org.ikora.checks;

import java.util.Arrays;
import java.util.List;

public class CheckRepository {

    private CheckRepository(){}

    private static final List<Class<? extends IkoraCheck>> CHECK_CLASSES = Arrays.asList(
            SyntaxErrorCheck.class,
            SymbolErrorCheck.class,
            KeywordDocumentationCheck.class,
            KeywordHasMinimumOneStepCheck.class,
            MaximumNumberOfArgumentsCheck.class,
            NoOperationCheck.class,
            TestHasLessThan10StepsCheck.class,
            DocumentationDifferentThanNameCheck.class,
            SleepCheck.class
    );

    public static List<Class<? extends IkoraCheck>> getCheckClasses(){
        return CHECK_CLASSES;
    }
}
