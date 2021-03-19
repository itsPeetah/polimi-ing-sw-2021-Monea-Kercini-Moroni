import org.junit.Assert;
import org.junit.Test;

public class HelloWorldTest {
    @Test
    public void testHelloWorldString() {
        Assert.assertEquals(new HelloWorld().helloWorldString(), "Hello world");
    }
}
