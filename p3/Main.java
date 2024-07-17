package p3;

public

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// Define the @LogExecutionTime annotation
@Retention(RetentionPolicy.RUNTIME) // Annotation is available at runtime
@Target(ElementType.METHOD) // Annotation can only be applied to methods
@interface LogExecutionTime {
}

// Invocation handler to log execution time of methods
class MethodLogger implements InvocationHandler {
    private final Object target; // The target object being proxied

    // Constructor to initialize the target object
    public MethodLogger(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Check if the method is annotated with @LogExecutionTime
        if (method.isAnnotationPresent(LogExecutionTime.class)) {
            long startTime = System.nanoTime(); // Record start time
            Object result = method.invoke(target, args); // Invoke the original method
            long endTime = System.nanoTime(); // Record end time
            System.out.println("Execution time of " + method.getName() + ": " + (endTime - startTime) + " nanoseconds");
            return result;
        } else {
            return method.invoke(target, args); // Invoke the method without logging
        }
    }

    // Create a proxy instance for the target object
    public static <T> T createProxy(T target) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(), // ClassLoader of the target class
                target.getClass().getInterfaces(), // Interfaces implemented by the target class
                new MethodLogger(target) // Invocation handler instance
        );
    }
}

// Service interface
interface SampleService {
    void fastMethod();

    void slowMethod();
}

// Service implementation
class SampleServiceImpl implements SampleService {
    @Override
    @LogExecutionTime
    public void fastMethod() {
        // Simulate a fast method
        System.out.println("Executing fast method...");
    }

    @Override
    @LogExecutionTime
    public void slowMethod() {
        // Simulate a slow method
        System.out.println("Executing slow method...");
        try {
            Thread.sleep(1000); // Sleep for 1 second to simulate slow execution
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Main application to demonstrate the usage of the annotation and proxy
public class Main {
    public static void main(String[] args) {
        // Create a proxy for the sample service
        SampleService service = MethodLogger.createProxy(new SampleServiceImpl());

        // Call the methods
        service.fastMethod();
        service.slowMethod();
    }
}{

}
