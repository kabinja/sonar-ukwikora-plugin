package org.ikora.checks;

import org.ikora.libraries.builtin.NoOperation;
import org.ikora.model.*;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.Rule;

@Rule(key = NoOperationCheck.RULE_KEY)
public class NoOperationCheck extends IkoraLintCheck {
    public static final String RULE_KEY = "NoOperationCheck";

    private static final Logger LOG = Loggers.get(NoOperationCheck.class);

    @Override
    public void validate() {
        super.validate();

        SourceFile sourceFile = ikoraSourceCode.getSourceFile();

        for(TestCase testCase: sourceFile.getTestCases()){
            checkNoOperation(testCase);
        }

        for(UserKeyword userKeyword: sourceFile.getUserKeywords()){
            checkNoOperation(userKeyword);
        }
    }

    private void checkNoOperation(KeywordDefinition keyword) {
        for(Step step: keyword.getSteps()){
            step.getKeywordCall().flatMap(KeywordCall::getKeyword).ifPresent(k -> {
                if(k instanceof NoOperation){
                    LOG.debug(String.format("Add issue no operation found in '%s'", keyword.toString()));

                    IkoraIssue issue = new IkoraIssue(ruleKey,
                            "No operation keyword should be avoided in production",
                            step.getPosition().getStartMark().getLine(),
                            step.getPosition().getStartMark().getColumn());

                    ikoraSourceCode.addViolation(issue);
                }
            });
        }
    }
}
