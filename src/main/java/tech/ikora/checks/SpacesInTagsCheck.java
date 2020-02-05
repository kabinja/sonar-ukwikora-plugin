package tech.ikora.checks;

import tech.ikora.model.*;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.Rule;

@Rule(key = SpacesInTagsCheck.RULE_KEY)
public class SpacesInTagsCheck extends IkoraLintCheck {
    public static final String RULE_KEY = "SpacesInTagsCheck";

    private static final Logger LOG = Loggers.get(SpacesInTagsCheck.class);

    @Override
    public void validate() {
        super.validate();

        SourceFile sourceFile = ikoraSourceCode.getSourceFile();

        for(TestCase testCase: sourceFile.getTestCases()){
            checkSpaces(testCase);
        }

        for(UserKeyword userKeyword: sourceFile.getUserKeywords()){
            checkSpaces(userKeyword);
        }
    }

    private void checkSpaces(KeywordDefinition keyword) {
        for(Token tag: keyword.getTags()){
            if(tag.toString().contains(" ")){
                LOG.debug(String.format("Add issue 'found space' found in '%s'", keyword.toString()));

                IkoraIssue issue = new IkoraIssue(ruleKey,
                        "Sleep keyword should be avoided in production",
                        tag.getLine() + 1,
                        tag.getStartOffset());

                ikoraSourceCode.addViolation(issue);
            }
        }
    }
}
