import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DocumentValidator {

    private static final Logger logger = LoggerFactory.getLogger(DocumentValidator.class);

    public ValidationResult validate(Document doc) {

        try {

            // FIX: Use specific validation exception for expected validation failures
            if (doc == null) {
                throw new IllegalArgumentException("Document is null");
            }

            String content = doc.extractContent();

            // FIX: Handle null/empty content safely
            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("Empty content");
            }

            return runValidationRules(content);

        } catch (IllegalArgumentException e) {

            // FIX: Expected validation failures should not flood logs with stack traces
            logger.warn("Validation failed: {}", e.getMessage());

            // FIX: Avoid returning null because caller uses r.isValid()
            return ValidationResult.invalid(e.getMessage());

        } catch (Exception e) {
            // e.printStackTrace(); //issue 1
            // return null; //issue 2
            // FIX: Log unexpected runtime errors properly
            logger.error("Unexpected validation error", e);

            return ValidationResult.invalid("Unexpected validation error");
        }
    }

    public void validateBatch(List<Document> docs) {

        for (Document doc : docs) {

            try {

                ValidationResult r = validate(doc);

                // FIX: Prevent NullPointerException before calling isValid()
                if (r != null && r.isValid()) { // issue 3
                    saveResult(r);
                }

            } catch (Exception e) {

                // silent -swallowed completely //isue4
                // FIX: Do not silently swallow exceptions
                logger.error("Batch validation failed", e);
            }
        }
    }
}