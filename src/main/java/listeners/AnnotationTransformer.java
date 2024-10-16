package listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * AnnotationTransformer class implements {@link IAnnotationTransformer} to dynamically
 * set data providers and retry analyzers at runtime for test methods.
 */
public class AnnotationTransformer implements IAnnotationTransformer {

    /**
     * Modifies the annotations of test methods at runtime, including setting the retry analyzer.
     *
     * @param annotation The annotation of the test method.
     * @param testClass The test class (can be null if not applicable).
     * @param testConstructor The test constructor (can be null if not applicable).
     * @param testMethod The test method (can be null if not applicable).
     */
    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        // Dynamically set retry analyzer for test methods
        if (annotation != null) {
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        }
    }
}
