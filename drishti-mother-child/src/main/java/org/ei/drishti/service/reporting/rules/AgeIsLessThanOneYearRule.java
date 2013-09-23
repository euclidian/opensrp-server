package org.ei.drishti.service.reporting.rules;

import org.ei.drishti.common.AllConstants;
import org.ei.drishti.domain.Child;
import org.ei.drishti.repository.AllChildren;
import org.ei.drishti.util.SafeMap;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.ei.drishti.common.AllConstants.CommonFormFields.SUBMISSION_DATE_FIELD_NAME;

@Component
public class AgeIsLessThanOneYearRule implements IRule {

    private AllChildren allChildren;

    @Autowired
    public AgeIsLessThanOneYearRule(AllChildren allChildren) {
        this.allChildren = allChildren;
    }

    @Override
    public boolean apply(SafeMap safeMap) {
        Child child = allChildren.findByCaseId(safeMap.get(AllConstants.Form.ENTITY_ID));
        LocalDate dateOfBirth = LocalDate.parse(child.dateOfBirth());
        LocalDate submissionDate = LocalDate.parse(safeMap.get(SUBMISSION_DATE_FIELD_NAME));

        return dateOfBirth.plusYears(1).isAfter(submissionDate);
    }
}