package model;

public class TestThrowable {

	// java虚拟机是如何捕获异常的
	public static void main(String[] args) {
	  try {
	    mayThrowException();
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	}
	// 对应的Java字节码
//	public static void main(java.lang.String[]);
//	  Code:
//	    0: invokestatic mayThrowException:()V
//	    3: goto 11
//	    6: astore_1
//	    7: aload_1
//	    8: invokevirtual java.lang.Exception.printStackTrace
//	   11: return
//	  Exception table:
//	    from  to target type
//	      0   3   6  Class java/lang/Exception  // 异常表条目
      
}


//可以用 javap 工具来查看下面这段包含了 try-catch-finally 代码块的编译结果。
//为了更好地区分每个代码块，我定义了四个实例字段：tryBlock、catchBlock、finallyBlock、以及 methodExit，
//并且仅在对应的代码块中访问这些字段。
public class Foo {
  private int tryBlock;
  private int catchBlock;
  private int finallyBlock;
  private int methodExit;

  public void test() {
    try {
      tryBlock = 0;
    } catch (Exception e) {
      catchBlock = 1;
    } finally {
      finallyBlock = 2;
    }
    methodExit = 3;
  }
}


$ javap -c Foo
...
  public void test();
    Code:
       0: aload_0
       1: iconst_0
       2: putfield      #20                 // Field tryBlock:I
       5: goto          30
       8: astore_1
       9: aload_0
      10: iconst_1
      11: putfield      #22                 // Field catchBlock:I
      14: aload_0
      15: iconst_2
      16: putfield      #24                 // Field finallyBlock:I
      19: goto          35
      22: astore_2
      23: aload_0
      24: iconst_2
      25: putfield      #24                 // Field finallyBlock:I
      28: aload_2
      29: athrow
      30: aload_0
      31: iconst_2
      32: putfield      #24                 // Field finallyBlock:I
      35: aload_0
      36: iconst_3
      37: putfield      #26                 // Field methodExit:I
      40: return
    Exception table:
       from    to  target type
           0     5     8   Class java/lang/Exception
           0    14    22   any

  ...
  // 可以看到，编译结果包含三份 finally 代码块。
  //其中，前两份分别位于 try 代码块和 catch 代码块的正常执行路径出口。最后一份则作为异常处理器，监控 try 代码块以及 catch 代码块。
  //它将捕获 try 代码块触发的、未被 catch 代码块捕获的异常，以及 catch 代码块触发的异常
  
  
// 每个资源关闭需要单独try-finally代码块，代码变得十分繁琐
  FileInputStream in0 = null;
  FileInputStream in1 = null;
  FileInputStream in2 = null;
  ...
  try {
    in0 = new FileInputStream(new File("in0.txt"));
    ...
    try {
      in1 = new FileInputStream(new File("in1.txt"));
      ...
      try {
        in2 = new FileInputStream(new File("in2.txt"));
        ...
      } finally {
        if (in2 != null) in2.close();
      }
    } finally {
      if (in1 != null) in1.close();
    }
  } finally {
    if (in0 != null) in0.close();
  }
  
  

public class Foo implements AutoCloseable {
  private final String name;
  public Foo(String name) { this.name = name; }

  @Override
  public void close() {
    throw new RuntimeException(name);
  }

  public static void main(String[] args) {
    try (Foo foo0 = new Foo("Foo0"); // try-with-resources
         Foo foo1 = new Foo("Foo1");
         Foo foo2 = new Foo("Foo2")) {
      throw new RuntimeException("Initial");
    }
  }
}

// 运行结果：
Exception in thread "main" java.lang.RuntimeException: Initial
        at Foo.main(Foo.java:18)
        Suppressed: java.lang.RuntimeException: Foo2
                at Foo.close(Foo.java:13)
                at Foo.main(Foo.java:19)
        Suppressed: java.lang.RuntimeException: Foo1
                at Foo.close(Foo.java:13)
                at Foo.main(Foo.java:19)
        Suppressed: java.lang.RuntimeException: Foo0
                at Foo.close(Foo.java:13)
                at Foo.main(Foo.java:19)


  


