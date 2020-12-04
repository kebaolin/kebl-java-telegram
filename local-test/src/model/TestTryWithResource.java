package model;

public class TestTryWithResource implements AutoCloseable {
  private final String name;
  public TestTryWithResource(String name) { this.name = name; }

  @Override
  public void close() {
    throw new RuntimeException(name);
  }

  public static void main(String[] args) {

	// 在同一catch代码块中捕获多种异常
	try {
	  String abc="abc";
	} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
	  System.out.println(e.getStackTrace());
	}
	  
    try (TestTryWithResource TestTryWithResource0 = new TestTryWithResource("TestTryWithResource0"); // try-with-resources
         TestTryWithResource TestTryWithResource1 = new TestTryWithResource("TestTryWithResource1");
         TestTryWithResource TestTryWithResource2 = new TestTryWithResource("TestTryWithResource2")) {
      throw new RuntimeException("Initial");
    }
    
    
  }
}

//执行结果
Exception in thread "main" java.lang.RuntimeException: Initial at model.TestTryWithResource.main(TestTryWithResource.java:16)
Suppressed: java.lang.RuntimeException: TestTryWithResource2
	at model.TestTryWithResource.close(TestTryWithResource.java:9)
	at model.TestTryWithResource.main(TestTryWithResource.java:17)
Suppressed: java.lang.RuntimeException: TestTryWithResource1
	at model.TestTryWithResource.close(TestTryWithResource.java:9)
	at model.TestTryWithResource.main(TestTryWithResource.java:17)
Suppressed: java.lang.RuntimeException: TestTryWithResource0
	at model.TestTryWithResource.close(TestTryWithResource.java:9)
	at model.TestTryWithResource.main(TestTryWithResource.java:17)

