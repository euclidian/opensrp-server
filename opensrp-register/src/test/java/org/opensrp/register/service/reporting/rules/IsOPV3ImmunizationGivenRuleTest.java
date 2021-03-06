package org.opensrp.register.service.reporting.rules;

import junit.framework.Assert;
import org.opensrp.util.SafeMap;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.service.reporting.rules.IsOPV3ImmunizationGivenRule;

import static junit.framework.Assert.assertTrue;
import static org.opensrp.common.util.EasyMap.create;
import static org.junit.Assert.assertFalse;


public class IsOPV3ImmunizationGivenRuleTest {
    private IsOPV3ImmunizationGivenRule rule;

    @Before
    public void setUp() throws Exception {
        rule = new IsOPV3ImmunizationGivenRule();
    }

    @Test
    public void shouldReturnTrueIfImmunizationsGivenContainsOPV_3() {
        SafeMap safeMap = new SafeMap(create("immunizationsGiven", "pentavalent_1 opv_3 tt_1").map());

        boolean didRuleApply = rule.apply(safeMap);
        assertTrue(didRuleApply);

        didRuleApply = rule.apply(new SafeMap(create("immunizationsGiven", "pentavalent_1 opv_3 tt_1").put("previousImmunizations", "pentavalent_1 tt_1").map()));
        Assert.assertTrue(didRuleApply);

        didRuleApply = rule.apply(new SafeMap(create("immunizationsGiven", "pentavalent_1 opv_3 tt_1").put("previousImmunizations", "").map()));
        Assert.assertTrue(didRuleApply);

        didRuleApply = rule.apply(new SafeMap(create("immunizationsGiven", "pentavalent_1 opv_3 tt_1").put("previousImmunizations", null).map()));
        Assert.assertTrue(didRuleApply);
    }

    @Test
    public void shouldReturnFalseIfImmunizationsGivenDoesNotContainOPV_3() {
        boolean didRuleApply = rule.apply(new SafeMap(create("immunizationsGiven", "").map()));
        assertFalse(didRuleApply);

        didRuleApply = rule.apply(new SafeMap().put("immunizationsGiven", null));
        assertFalse(didRuleApply);

        didRuleApply = rule.apply(new SafeMap(create("immunizationsGiven", "pentavalent_1 tt_1").map()));
        assertFalse(didRuleApply);

        didRuleApply = rule.apply(new SafeMap(create("immunizationsGiven", "pentavalent_1 opv_3 tt_1").put("previousImmunizations", "opv_3").map()));
        Assert.assertFalse(didRuleApply);
    }
}
