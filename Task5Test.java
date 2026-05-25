import org.junit.Assert;
import org.junit.Test;

public class Task5Test {

    @Test
    public void shouldReturnInvalidResultWhenDocumentIsNull() {

        DocumentValidator validator = new DocumentValidator();

        ValidationResult result = validator.validate(null);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isValid());
    }
}
