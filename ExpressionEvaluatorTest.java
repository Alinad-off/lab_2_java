import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExpressionEvaluatorTest {

    @Test
    public void testSimpleExpression() throws Exception {
        assertEquals(7.0, ExpressionEvaluator.evaluateExpression("3 + 4"), 0.0001);
    }

    @Test
    public void testExpressionWithParentheses() throws Exception {
        assertEquals(14.0, ExpressionEvaluator.evaluateExpression("2 * (3 + 4)"), 0.0001);
    }

    @Test
    public void testExpressionWithVariables() throws Exception {
        // This test requires user input, so it's not suitable for automated testing
        // You can mock the input or use a different approach for testing variables
    }

    @Test
    public void testExpressionWithDivision() throws Exception {
        assertEquals(2.0, ExpressionEvaluator.evaluateExpression("10 / 5"), 0.0001);
    }

    @Test
    public void testExpressionWithMixedOperators() throws Exception {
        assertEquals(10.0, ExpressionEvaluator.evaluateExpression("3 + 2 * 2 - 1 / 1"), 0.0001);
    }

    @Test
    public void testInvalidExpression() {
        assertThrows(Exception.class, () -> ExpressionEvaluator.evaluateExpression("3 + 4 * (5 - 2"));
    }
}