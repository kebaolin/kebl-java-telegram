package model;

public class TestInvokeDynamic {
	
	

class Horse {
  public void race() {
    System.out.println("Horse.race()"); 
  }
}

class Deer {
  public void race() {
    System.out.println("Deer.race()");
  }
}

class Cobra {
  public void race() {
    System.out.println("How do you turn this on?");
  }
}

// (如何用同一种方式调用他们的赛跑方法？)

//为了解答这个问题，我们先来回顾一下 Java 里的方法调用。在 Java 中，方法调用会被编译为 invokestatic，invokespecial，invokevirtual 以及 invokeinterface 四种指令。
//这些指令与包含目标方法类名、方法名以及方法描述符的符号引用捆绑。在实际运行之前，Java 虚拟机将根据这个符号引用链接到具体的目标方法。

//可以看到，在这四种调用指令中，Java 虚拟机明确要求方法调用需要提供目标方法的类名。在这种体系下，我们有两个解决方案。
//一是调用其中一种类型的赛跑方法，比如说马类的赛跑方法。对于非马的类型，则给它套一层马甲，当成马来赛跑。
//另外一种解决方式，是通过反射机制，来查找并且调用各个类型中的赛跑方法，以此模拟真正的赛跑。

//显然，比起直接调用，这两种方法都相当复杂，执行效率也可想而知。为了解决这个问题，Java 7 引入了一条新的指令 invokedynamic。
//该指令的调用机制抽象出调用点这一个概念，并允许应用程序将调用点链接至任意符合条件的方法上。

public static void startRace(java.lang.Object)
       0: aload_0                // 加载一个任意对象
       1: invokedynamic race     // 调用赛跑方法
       
//作为 invokedynamic 的准备工作，Java 7 引入了更加底层、更加灵活的方法抽象 ：方法句柄（MethodHandle）。

// 一、方法句柄的概念
//方法句柄是一个强类型的，能够被直接执行的引用https://docs.oracle.com/javase/10/docs/api/java/lang/invoke/MethodHandle.html。
//该引用可以指向常规的静态方法或者实例方法，也可以指向构造器或者字段。当指向字段时，方法句柄实则指向包含字段访问字节码的虚构方法，语义上等价于目标字段的 getter 或者 setter 方法。
//这里需要注意的是，它并不会直接指向目标字段所在类中的 getter/setter，毕竟你无法保证已有的 getter/setter 方法就是在访问目标字段。
       
//方法句柄的类型（MethodType）是由所指向方法的参数类型以及返回类型组成的。它是用来确认方法句柄是否适配的唯一关键。当使用方法句柄时，我们其实并不关心方法句柄所指向方法的类名或者方法名。
//打个比方，如果兔子的“赛跑”方法和“睡觉”方法的参数类型以及返回类型一致，那么对于兔子递过来的一个方法句柄，我们并不知道会是哪一个方法。

//方法句柄的创建是通过 MethodHandles.Lookup 类来完成的。它提供了多个 API，既可以使用反射 API 中的 Method 来查找，也可以根据类、方法名以及方法句柄类型来查找。
//当使用后者这种查找方式时，用户需要区分具体的调用类型，比如说对于用 invokestatic 调用的静态方法，我们需要使用 Lookup.findStatic 方法；
//对于用 invokevirtual 调用的实例方法，以及用 invokeinterface 调用的接口方法，我们需要使用 findVirtual 方法；对于用 invokespecial 调用的实例方法，我们则需要使用 findSpecial 方法。
       
//调用方法句柄，和原本对应的调用指令是一致的。也就是说，对于原本用 invokevirtual 调用的方法句柄，它也会采用动态绑定；而对于原本用 invkespecial 调用的方法句柄，它会采用静态绑定。

class Foo {
  private static void bar(Object o) {
    ..
  }
  public static Lookup lookup() {
    return MethodHandles.lookup();
  }
}

// 获取方法句柄的不同方式
MethodHandles.Lookup l = Foo.lookup(); // 具备Foo类的访问权限
Method m = Foo.class.getDeclaredMethod("bar", Object.class);
MethodHandle mh0 = l.unreflect(m);

MethodType t = MethodType.methodType(void.class, Object.class);
MethodHandle mh1 = l.findStatic(Foo.class, "bar", t);

//方法句柄同样也有权限问题。但它与反射 API 不同，其权限检查是在句柄的创建阶段完成的。在实际调用过程中，Java 虚拟机并不会检查方法句柄的权限。如果该句柄被多次调用的话，那么与反射调用相比，它将省下重复权限检查的开销。
//需要注意的是，方法句柄的访问权限不取决于方法句柄的创建位置，而是取决于 Lookup 对象的创建位置。
//举个例子，对于一个私有字段，如果 Lookup 对象是在私有字段所在类中获取的，那么这个 Lookup 对象便拥有对该私有字段的访问权限，即使是在所在类的外边，也能够通过该 Lookup 对象创建该私有字段的 getter 或者 setter。
//由于方法句柄没有运行时权限检查，因此，应用程序需要负责方法句柄的管理。一旦它发布了某些指向私有方法的方法句柄，那么这些私有方法便被暴露出去了。

//二、方法句柄的操作
//方法句柄的调用可分为两种，一是需要严格匹配参数类型的 invokeExact。它有多严格呢？假设一个方法句柄将接收一个 Object 类型的参数，如果你直接传入 String 作为实际参数，
//那么方法句柄的调用会在运行时抛出方法类型不匹配的异常。正确的调用方式是将该 String 显式转化为 Object 类型。

//在普通 Java 方法调用中，我们只有在选择重载方法时，才会用到这种显式转化。这是因为经过显式转化后，参数的声明类型发生了改变，因此有可能匹配到不同的方法描述符，从而选取不同的目标方法。
//调用方法句柄也是利用同样的原理，并且涉及了一个签名多态性（signature polymorphism）的概念。（在这里我们暂且认为签名等同于方法描述符。）

public final native @PolymorphicSignature Object invokeExact(Object... args) throws Throwable;

//方法句柄 API 有一个特殊的注解类 @PolymorphicSignature。在碰到被它注解的方法调用时，Java 编译器会根据所传入参数的声明类型来生成方法描述符，而不是采用目标方法所声明的描述符。

//在刚才的例子中，当传入的参数是 String 时，对应的方法描述符包含 String 类；而当我们转化为 Object 时，对应的方法描述符则包含 Object 类。

public void test(MethodHandle mh, String s) throws Throwable {
  mh.invokeExact(s);
  mh.invokeExact((Object) s);
}

// 对应的Java字节码
public void test(MethodHandle, String) throws java.lang.Throwable;
  Code:
     0: aload_1
     1: aload_2
     2: invokevirtual MethodHandle.invokeExact:(Ljava/lang/String;)V
     5: aload_1
     6: aload_2
     7: invokevirtual MethodHandle.invokeExact:(Ljava/lang/Object;)V
    10: return
    		
//invokeExact 会确认该 invokevirtual 指令对应的方法描述符，和该方法句柄的类型是否严格匹配。在不匹配的情况下，便会在运行时抛出异常。
//如果你需要自动适配参数类型，那么你可以选取方法句柄的第二种调用方式 invoke。它同样是一个签名多态性的方法。
//invoke 会调用 MethodHandle.asType 方法，生成一个适配器方法句柄，对传入的参数进行适配，再调用原方法句柄。调用原方法句柄的返回值同样也会先进行适配，然后再返回给调用者。 
    		
//方法句柄还支持增删改参数的操作，这些操作都是通过生成另一个方法句柄来实现的。
//这其中，改操作就是刚刚介绍的 MethodHandle.asType 方法。
//删操作指的是将传入的部分参数就地抛弃，再调用另一个方法句柄。它对应的 API 是 MethodHandles.dropArguments 方法。
//增操作则非常有意思。它会往传入的参数中插入额外的参数，再调用另一个方法句柄，它对应的 API 是 MethodHandle.bindTo 方法。Java 8 中捕获类型的 Lambda 表达式便是用这种操作来实现的，下一篇我会详细进行解释。
//增操作还可以用来实现方法的柯里化[3]https://en.wikipedia.org/wiki/Currying。
//举个例子，有一个指向 f(x, y) 的方法句柄，我们可以通过将 x 绑定为 4，生成另一个方法句柄 g(y) = f(4, y)。在执行过程中，每当调用 g(y) 的方法句柄，它会在参数列表最前面插入一个 4，再调用指向 f(x, y) 的方法句柄。
    		
//三、方法句柄的实现
//下面我们来看看 HotSpot 虚拟机中方法句柄调用的具体实现。（由于篇幅原因，这里只讨论 DirectMethodHandle。）	
//前面提到，调用方法句柄所使用的 invokeExact 或者 invoke 方法具备签名多态性的特性。它们会根据具体的传入参数来生成方法描述符。
//那么，拥有这个描述符的方法实际存在吗？对 invokeExact 或者 invoke 的调用具体会进入哪个方法呢？

import java.lang.invoke.*;

public class Foo {
  public static void bar(Object o) {
    new Exception().printStackTrace();
  }

  public static void main(String[] args) throws Throwable {
    MethodHandles.Lookup l = MethodHandles.lookup();
    MethodType t = MethodType.methodType(void.class, Object.class);
    MethodHandle mh = l.findStatic(Foo.class, "bar", t);
    mh.invokeExact(new Object());
  }
}
//和查阅反射调用的方式一样，我们可以通过新建异常实例来查看栈轨迹。打印出来的占轨迹如下所示：
$ java Foo
java.lang.Exception
        at Foo.bar(Foo.java:128)
        at Foo.main(Foo.java:135)
//也就是说，invokeExact 的目标方法竟然就是方法句柄指向的方法。  		

//先别高兴太早。我刚刚提到过，invokeExact 会对参数的类型进行校验，并在不匹配的情况下抛出异常。
//如果它直接调用了方法句柄所指向的方法，那么这部分参数类型校验的逻辑将无处安放。因此，唯一的可能便是 Java 虚拟机隐藏了部分栈信息。
//当我们启用了 -XX:+ShowHiddenFrames 这个参数来打印被 Java 虚拟机隐藏了的栈信息时，你会发现 main 方法和目标方法中间隔着两个貌似是生成的方法。

$ java -XX:+UnlockDiagnosticVMOptions -XX:+ShowHiddenFrames Foo
java.lang.Exception
        at Foo.bar(Foo.java:128)
        at java.base/java.lang.invoke.DirectMethodHandle$Holder. invokeStatic(DirectMethodHandle$Holder:1000010)
        at java.base/java.lang.invoke.LambdaForm$MH000/766572210. invokeExact_MT000_LLL_V(LambdaForm$MH000:1000019)
        at Foo.main(Foo.java:135)
//实际上，Java 虚拟机会对 invokeExact 调用做特殊处理，调用至一个共享的、与方法句柄类型相关的特殊适配器中。
//这个适配器是一个 LambdaForm，我们可以通过添加虚拟机参数将之导出成 class 文件（-Djava.lang.invoke.MethodHandle.DUMP_CLASS_FILES=true）。    

final class java.lang.invoke.LambdaForm$MH000 {  static void invokeExact_MT000_LLLLV(jeava.lang.bject, jjava.lang.bject, jjava.lang.bject);
    Code:
        : aload_0
      1 : checkcast      #14                 //Mclass java/lang/invoke/ethodHandle
        : dup
      5 : astore_0
        : aload_32        : checkcast      #16                 //Mclass java/lang/invoke/ethodType
      10: invokestatic  I#22                 // Method java/lang/invoke/nvokers.checkExactType:(MLjava/lang/invoke/ethodHandle,;Ljava/lang/invoke/ethodType);V
      13: aload_0
      14: invokestatic   #26     I           // Method java/lang/invoke/nvokers.checkCustomized:(MLjava/lang/invoke/ethodHandle);V
      17: aload_0
      18: aload_1
      19: ainvakevirtudl #30             2   // Methodijava/lang/nvokev/ethodHandle.invokeBasic:(LLeava/lang/bject;;V
       23 return
//可以看到，在这个适配器中，它会调用 Invokers.checkExactType 方法来检查参数类型，然后调用 Invokers.checkCustomized 方法。
//后者会在方法句柄的执行次数超过一个阈值时进行优化（对应参数 -Djava.lang.invoke.MethodHandle.CUSTOMIZE_THRESHOLD，默认值为 127）。最后，它会调用方法句柄的 invokeBasic 方法。
//Java 虚拟机同样会对 invokeBasic 调用做特殊处理，这会将调用至方法句柄本身所持有的适配器中。这个适配器同样是一个 LambdaForm，你可以通过反射机制将其打印出来。

    		// 该方法句柄持有的LambdaForm实例的toString()结果
    		DMH.invokeStatic_L_V=Lambda(a0:L,a1:L)=>{
    		  t2:L=DirectMethodHandle.internalMemberName(a0:L);
    		  t3:V=MethodHandle.linkToStatic(a1:L,t2:L);void}
//这个适配器将获取方法句柄中的 MemberName 类型的字段，并且以它为参数调用 linkToStatic 方法。
//估计你已经猜到了，Java 虚拟机也会对 linkToStatic 调用做特殊处理，它将根据传入的 MemberName 参数所存储的方法地址或者方法表索引，直接跳转至目标方法。

final class MemberName implements Member, Cloneable {
...
    //@Injected JVM_Method* vmtarget;
    //@Injected int         vmindex;
...
//那么前面那个适配器中的优化又是怎么回事？实际上，方法句柄一开始持有的适配器是共享的。
//当它被多次调用之后，Invokers.checkCustomized 方法会为该方法句柄生成一个特有的适配器。这个特有的适配器会将方法句柄作为常量，直接获取其 MemberName 类型的字段，并继续后面的 linkToStatic 调用。

final class java.lang.invoke.LambdaForm$DMH000 {
  static void invokeStatic000_LL_V(java.lang.Object, java.lang.Object);
    Code:
       0: ldc           #14                 // String CONSTANT_PLACEHOLDER_1 <<Foo.bar(Object)void/invokeStatic>>
       2: checkcast     #16                 // class java/lang/invoke/MethodHandle
       5: astore_0     // 上面的优化代码覆盖了传入的方法句柄
       6: aload_0      // 从这里开始跟初始版本一致
       7: invokestatic  #22                 // Method java/lang/invoke/DirectMethodHandle.internalMemberName:(Ljava/lang/Object;)Ljava/lang/Object;
      10: astore_2
      11: aload_1
      12: aload_2
      13: checkcast     #24                 // class java/lang/invoke/MemberName
      16: invokestatic  #28                 // Method java/lang/invoke/MethodHandle.linkToStatic:(Ljava/lang/Object;Ljava/lang/invoke/MemberName;)V
      19: return    		   
//可以看到，方法句柄的调用和反射调用一样，都是间接调用。因此，它也会面临无法内联的问题。不过，与反射调用不同的是，方法句柄的内联瓶颈在于即时编译器能否将该方法句柄识别为常量。具体内容我会在下一篇中进行详细的解释。

    		  
    		  
//上回讲到，为了让所有的动物都能参加赛马，Java 7 引入了 invokedynamic 机制，允许调用任意类的“赛跑”方法。
//不过，我们并没有讲解 invokedynamic，而是深入地探讨了它所依赖的方法句柄。今天，我便来正式地介绍 invokedynamic 指令，讲讲它是如何生成调用点，并且允许应用程序自己决定链接至哪一个方法中的。
    		  
//一、invokedynamic 指令
//invokedynamic 是 Java 7 引入的一条新指令，用以支持动态语言的方法调用。具体来说，它将调用点（CallSite）抽象成一个 Java 类，并且将原本由 Java 虚拟机控制的方法调用以及方法链接暴露给了应用程序。
//在运行过程中，每一条 invokedynamic 指令将捆绑一个调用点，并且会调用该调用点所链接的方法句柄。
    		  
//在第一次执行 invokedynamic 指令时，Java 虚拟机会调用该指令所对应的启动方法（BootStrap Method），来生成前面提到的调用点，并且将之绑定至该 invokedynamic 指令中。
//在之后的运行过程中，Java 虚拟机则会直接调用绑定的调用点所链接的方法句柄。
//在字节码中，启动方法是用方法句柄来指定的。这个方法句柄指向一个返回类型为调用点的静态方法。该方法必须接收三个固定的参数，分别为一个 Lookup 类实例，一个用来指代目标方法名字的字符串，以及该调用点能够链接的方法句柄的类型。
//除了这三个必需参数之外，启动方法还可以接收若干个其他的参数，用来辅助生成调用点，或者定位所要链接的目标方法。

import java.lang.invoke.*;

class Horse {
  public void race() {
    System.out.println("Horse.race()"); 
  }
}

class Deer {
  public void race() {
    System.out.println("Deer.race()");
  }
}

// javac Circuit.java
// java Circuit
public class Circuit {

  public static void startRace(Object obj) {
    // aload obj
    // invokedynamic race()
  }

  public static void main(String[] args) {
    startRace(new Horse());
    // startRace(new Deer());
  }
  
  public static CallSite bootstrap(MethodHandles.Lookup l, String name, MethodType callSiteType) throws Throwable {
    MethodHandle mh = l.findVirtual(Horse.class, name, MethodType.methodType(void.class));
    return new ConstantCallSite(mh.asType(callSiteType));
  }
}
//我在文稿中贴了一段代码，其中便包含一个启动方法。它将接收前面提到的三个固定参数，并且返回一个链接至 Horse.race 方法的 ConstantCallSite。
//这里的 ConstantCallSite 是一种不可以更改链接对象的调用点。
//除此之外，Java 核心类库还提供多种可以更改链接对象的调用点，比如 MutableCallSite 和 VolatileCallSite。这两者的区别就好比正常字段和 volatile 字段之间的区别。
//此外，应用程序还可以自定义调用点类，来满足特定的重链接需求。

//由于 Java 暂不支持直接生成 invokedynamic 指令[1]，所以接下来我会借助之前介绍过的字节码工具 ASM 来实现这一目的。
import java.io.IOException;
import java.lang.invoke.*;
import java.nio.file.*;

import org.objectweb.asm.*;

// javac -cp /path/to/asm-all-6.0_BETA.jar:. ASMHelper.java
// java -cp /path/to/asm-all-6.0_BETA.jar:. ASMHelper
// java Circuit
public class ASMHelper implements Opcodes {

  private static class MyMethodVisitor extends MethodVisitor {

    private static final String BOOTSTRAP_CLASS_NAME = Circuit.class.getName().replace('.', '/');
    private static final String BOOTSTRAP_METHOD_NAME = "bootstrap";
    private static final String BOOTSTRAP_METHOD_DESC = MethodType
        .methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class)
        .toMethodDescriptorString();

    private static final String TARGET_METHOD_NAME = "race";
    private static final String TARGET_METHOD_DESC = "(Ljava/lang/Object;)V";

    public final MethodVisitor mv;

    public MyMethodVisitor(int api, MethodVisitor mv) {
      super(api);
      this.mv = mv;
    }

    @Override
    public void visitCode() {
      mv.visitCode();
      mv.visitVarInsn(ALOAD, 0);
      Handle h = new Handle(H_INVOKESTATIC, BOOTSTRAP_CLASS_NAME, BOOTSTRAP_METHOD_NAME, BOOTSTRAP_METHOD_DESC, false);
      mv.visitInvokeDynamicInsn(TARGET_METHOD_NAME, TARGET_METHOD_DESC, h);
      mv.visitInsn(RETURN);
      mv.visitMaxs(1, 1);
      mv.visitEnd();
    }
  }

  public static void main(String[] args) throws IOException {
    ClassReader cr = new ClassReader("Circuit");
    ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
    ClassVisitor cv = new ClassVisitor(ASM6, cw) {
      @Override
      public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
          String[] exceptions) {
        MethodVisitor visitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if ("startRace".equals(name)) {
          return new MyMethodVisitor(ASM6, visitor);
        }
        return visitor;
      }
    };
    cr.accept(cv, ClassReader.SKIP_FRAMES);

    Files.write(Paths.get("Circuit.class"), cw.toByteArray());
  }
}
//你无需理解上面这段代码的具体含义，只须了解它会更改同一目录下 Circuit 类的 startRace(Object) 方法，使之包含 invokedynamic 指令，执行所谓的赛跑方法。

public static void startRace(java.lang.Object);
        0: aload_0
        1: invokedynamic #80,  0 // race:(Ljava/lang/Object;)V
        6: return
//如果你足够细心的话，你会发现该指令所调用的赛跑方法的描述符，和 Horse.race 方法或者 Deer.race 方法的描述符并不一致。
//这是因为 invokedynamic 指令最终调用的是方法句柄，而方法句柄会将调用者当成第一个参数。因此，刚刚提到的那两个方法恰恰符合这个描述符所对应的方法句柄类型。

        		
//到目前为止，我们已经可以通过 invokedynamic 调用 Horse.race 方法了。为了支持调用任意类的 race 方法，我实现了一个简单的单态内联缓存。如果调用者的类型命中缓存中的类型，便直接调用缓存中的方法句柄，否则便更新缓存。

        		// 需要更改ASMHelper.MyMethodVisitor中的BOOTSTRAP_CLASS_NAME
        		import java.lang.invoke.*;

        		public class MonomorphicInlineCache {

        		  private final MethodHandles.Lookup lookup;
        		  private final String name;

        		  public MonomorphicInlineCache(MethodHandles.Lookup lookup, String name) {
        		    this.lookup = lookup;
        		    this.name = name;
        		  }

        		  private Class<?> cachedClass = null;
        		  private MethodHandle mh = null;

        		  public void invoke(Object receiver) throws Throwable {
        		    if (cachedClass != receiver.getClass()) {
        		      cachedClass = receiver.getClass();
        		      mh = lookup.findVirtual(cachedClass, name, MethodType.methodType(void.class));
        		    }
        		    mh.invoke(receiver);
        		  }

        		  public static CallSite bootstrap(MethodHandles.Lookup l, String name, MethodType callSiteType) throws Throwable {
        		    MonomorphicInlineCache ic = new MonomorphicInlineCache(l, name);
        		    MethodHandle mh = l.findVirtual(MonomorphicInlineCache.class, "invoke", MethodType.methodType(void.class, Object.class));
        		    return new ConstantCallSite(mh.bindTo(ic));
        		  }
        		}
//可以看到，尽管 invokedynamic 指令调用的是所谓的 race 方法，但是实际上我返回了一个链接至名为“invoke”的方法的调用点。由于调用点仅要求方法句柄的类型能够匹配，因此这个链接是合法的。
//不过，这正是 invokedynamic 的目的，也就是将调用点与目标方法的链接交由应用程序来做，并且依赖于应用程序对目标方法进行验证。所以，如果应用程序将赛跑方法链接至兔子的睡觉方法，那也只能怪应用程序自己了。

//二、Java 8 的 Lambda 表达式
//在 Java 8 中，Lambda 表达式也是借助 invokedynamic 来实现的。具体来说，Java 编译器利用 invokedynamic 指令来生成实现了函数式接口的适配器。
//这里的函数式接口指的是仅包括一个非 default 接口方法的接口，一般通过 @FunctionalInterface 注解。不过就算是没有使用该注解，Java 编译器也会将符合条件的接口辨认为函数式接口。

int x = ..
IntStream.of(1, 2, 3).map(i -> i * 2).map(i -> i * x);
//举个例子，上面这段代码会对 IntStream 中的元素进行两次映射。我们知道，映射方法 map 所接收的参数是 IntUnaryOperator（这是一个函数式接口）。
//也就是说，在运行过程中我们需要将 i->i2 和 i->ix 这两个 Lambda 表达式转化成 IntUnaryOperator 的实例。这个转化过程便是由 invokedynamic 来实现的。

//在编译过程中，Java 编译器会对 Lambda 表达式进行解语法糖（desugar），生成一个方法来保存 Lambda 表达式的内容。
//该方法的参数列表不仅包含原本 Lambda 表达式的参数，还包含它所捕获的变量。(注：方法引用，如 Horse::race，则不会生成生成额外的方法。)
//在上面那个例子中，第一个 Lambda 表达式没有捕获其他变量，而第二个 Lambda 表达式（也就是 i->i*x）则会捕获局部变量 x。
//这两个 Lambda 表达式对应的方法如下所示。可以看到，所捕获的变量同样也会作为参数传入生成的方法之中。

// i -> i * 2
private static int lambda$0(int);
  Code:
     0: iload_0
     1: iconst_2
     2: imul
     3: ireturn

// i -> i * x
private static int lambda$1(int, int);
  Code:
     0: iload_1
     1: iload_0
     2: imul
     3: ireturn
//第一次执行 invokedynamic 指令时，它所对应的启动方法会通过 ASM 来生成一个适配器类。
//这个适配器类实现了对应的函数式接口，在我们的例子中，也就是 IntUnaryOperator。启动方法的返回值是一个 ConstantCallSite，其链接对象为一个返回适配器类实例的方法句柄。
//根据 Lambda 表达式是否捕获其他变量，启动方法生成的适配器类以及所链接的方法句柄皆不同。
//如果该 Lambda 表达式没有捕获其他变量，那么可以认为它是上下文无关的。因此，启动方法将新建一个适配器类的实例，并且生成一个特殊的方法句柄，始终返回该实例。
//如果该 Lambda 表达式捕获了其他变量，那么每次执行该 invokedynamic 指令，我们都要更新这些捕获了的变量，以防止它们发生了变化。
//另外，为了保证 Lambda 表达式的线程安全，我们无法共享同一个适配器类的实例。因此，在每次执行 invokedynamic 指令时，所调用的方法句柄都需要新建一个适配器类实例。
//在这种情况下，启动方法生成的适配器类将包含一个额外的静态方法，来构造适配器类的实例。该方法将接收这些捕获的参数，并且将它们保存为适配器类实例的实例字段。
//你可以通过虚拟机参数 -Djdk.internal.lambda.dumpProxyClasses=/DUMP/PATH 导出这些具体的适配器类。这里我导出了上面这个例子中两个 Lambda 表达式对应的适配器类。

  // i->i*2 对应的适配器类
  final class LambdaTest$$Lambda$1 implements IntUnaryOperator {
   private LambdaTest$$Lambda$1();
    Code:
      0: aload_0
      1: invokespecial java/lang/Object."<init>":()V
      4: return

   public int applyAsInt(int);
    Code:
      0: iload_1
      1: invokestatic LambdaTest.lambda$0:(I)I
      4: ireturn
  }

  // i->i*x 对应的适配器类
  final class LambdaTest$$Lambda$2 implements IntUnaryOperator {
   private final int arg$1;

   private LambdaTest$$Lambda$2(int);
    Code:
      0: aload_0
      1: invokespecial java/lang/Object."<init>":()V
      4: aload_0
      5: iload_1
      6: putfield arg$1:I
      9: return

   private static java.util.function.IntUnaryOperator get$Lambda(int);
    Code:
      0: new LambdaTest$$Lambda$2
      3: dup
      4: iload_0
      5: invokespecial "<init>":(I)V
      8: areturn

   public int applyAsInt(int);
    Code:
      0: aload_0
      1: getfield arg$1:I
      4: iload_1
      5: invokestatic LambdaTest.lambda$1:(II)I
      8: ireturn
  }
//可以看到，捕获了局部变量的 Lambda 表达式多出了一个 get$Lambda 的方法。
//启动方法便会所返回的调用点链接至指向该方法的方法句柄。也就是说，每次执行 invokedynamic 指令时，都会调用至这个方法中，并构造一个新的适配器类实例。
//这个多出来的新建实例会对程序性能造成影响吗？

//三、Lambda 以及方法句柄的性能分析
//我再次请出测试反射调用性能开销的那段代码，并将其改造成使用 Lambda 表达式的 v6 版本。

//v6版本
import java.util.function.IntConsumer;

public class Test {
 public static void target(int i) { }

 public static void main(String[] args) throws Exception {
   long current = System.currentTimeMillis();
   for (int i = 1; i <= 2_000_000_000; i++) {
     if (i % 100_000_000 == 0) {
       long temp = System.currentTimeMillis();
       System.out.println(temp - current);
       current = temp;
     }

     ((IntConsumer) j -> Test.target(j)).accept(128);
     // ((IntConsumer) Test::target.accept(128);
   }
 }
}
//测量结果显示，它与直接调用的性能并无太大的区别。也就是说，即时编译器能够将转换 Lambda 表达式所使用的 invokedynamic，以及对 IntConsumer.accept 方法的调用统统内联进来，最终优化为空操作。

//这个其实不难理解：Lambda 表达式所使用的 invokedynamic 将绑定一个 ConstantCallSite，其链接的目标方法无法改变。因此，即时编译器会将该目标方法直接内联进来。
//对于这类没有捕获变量的 Lambda 表达式而言，目标方法只完成了一个动作，便是加载缓存的适配器类常量。

//如果你查看了 accept 方法对应的字节码的话，你会发现它仅包含一个方法调用，调用至 Java 编译器在解 Lambda 语法糖时生成的方法。
//该方法的内容便是 Lambda 表达式的内容，也就是直接调用目标方法 Test.target。将这几个方法调用内联进来之后，原本对 accept 方法的调用则会被优化为空操作。
     
//下面我将之前的代码更改为带捕获变量的 v7 版本。理论上，每次调用 invokedynamic 指令，Java 虚拟机都会新建一个适配器类的实例。然而，实际运行结果还是与直接调用的性能一致。

//v7版本
import java.util.function.IntConsumer;

public class Test {
public static void target(int i) { }

public static void main(String[] args) throws Exception {
 int x = 2;

 long current = System.currentTimeMillis();
 for (int i = 1; i <= 2_000_000_000; i++) {
   if (i % 100_000_000 == 0) {
     long temp = System.currentTimeMillis();
     System.out.println(temp - current);
     current = temp;
   }

   ((IntConsumer) j -> Test.target(x + j)).accept(128);
 }
}
}
//显然，即时编译器的逃逸分析又将该新建实例给优化掉了。我们可以通过虚拟机参数 -XX:-DoEscapeAnalysis 来关闭逃逸分析。果然，这时候测得的值约为直接调用的 2.5 倍。
//尽管逃逸分析能够去除这些额外的新建实例开销，但是它也不是时时奏效。它需要同时满足两件事：invokedynamic 指令所执行的方法句柄能够内联，和接下来的对 accept 方法的调用也能内联。
//只有这样，逃逸分析才能判定该适配器实例不逃逸。否则，我们会在运行过程中不停地生成适配器类实例。所以，我们应当尽量使用非捕获的 Lambda 表达式。
     
实践环节
//你应该测过这一段代码的性能开销了。我这边测得的结果约为直接调用的 3.5 倍。

//v8版本
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class Test {
public static void target(int i) { }

public static void main(String[] args) throws Exception {
 MethodHandles.Lookup l = MethodHandles.lookup();
 MethodType t = MethodType.methodType(void.class, int.class);
 MethodHandle mh = l.findStatic(Test.class, "target", t);

 long current = System.currentTimeMillis();
 for (int i = 1; i <= 2_000_000_000; i++) {
   if (i % 100_000_000 == 0) {
     long temp = System.currentTimeMillis();
     System.out.println(temp - current);
     current = temp;
   }

   mh.invokeExact(128);
 }
}
}
//实际上，它与使用 Lambda 表达式或者方法引用的差别在于，即时编译器无法将该方法句柄识别为常量，从而无法进行内联。那么如果将它变成常量行不行呢？
//一种方法便是将其赋值给 final 的静态变量，如下面的 v9 版本所示：

//v9版本
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class Test {
public static void target(int i) { }

static final MethodHandle mh;
static {
 try {
   MethodHandles.Lookup l = MethodHandles.lookup();
   MethodType t = MethodType.methodType(void.class, int.class);
   mh = l.findStatic(Test.class, "target", t);
 } catch (Throwable e) {
   throw new RuntimeException(e);
 }
}

public static void main(String[] args) throws Throwable {
 long current = System.currentTimeMillis();
 for (int i = 1; i <= 2_000_000_000; i++) {
   if (i % 100_000_000 == 0) {
     long temp = System.currentTimeMillis();
     System.out.println(temp - current);
     current = temp;
   }

   mh.invokeExact(128);
 }
}
}
//这个版本测得的数据和直接调用的性能数据一致。也就是说，即时编译器能够将该方法句柄完全内联进来，成为空操作。

//我们来继续探索方法句柄的性能。运行下面的 v10 版本以及 v11 版本，比较它们的性能并思考为什么。

//v10版本
import java.lang.invoke.*;

public class Test {
public static void target(int i) {
}

public static class MyCallSite {

 public final MethodHandle mh;

 public MyCallSite() {
   mh = findTarget();
 }

 private static MethodHandle findTarget() {
   try {
     MethodHandles.Lookup l = MethodHandles.lookup();
     MethodType t = MethodType.methodType(void.class, int.class);
     return l.findStatic(Test.class, "target", t);
   } catch (Throwable e) {
     throw new RuntimeException(e);
   }
 }
}

private static final MyCallSite myCallSite = new MyCallSite();

public static void main(String[] args) throws Throwable {
 long current = System.currentTimeMillis();
 for (int i = 1; i <= 2_000_000_000; i++) {
   if (i % 100_000_000 == 0) {
     long temp = System.currentTimeMillis();
     System.out.println(temp - current);
     current = temp;
   }

   myCallSite.mh.invokeExact(128);
 }
}
}

//v11版本
import java.lang.invoke.*;

public class Test {
public static void target(int i) {
}

public static class MyCallSite extends ConstantCallSite {

 public MyCallSite() {
   super(findTarget());
 }

 private static MethodHandle findTarget() {
   try {
     MethodHandles.Lookup l = MethodHandles.lookup();
     MethodType t = MethodType.methodType(void.class, int.class);
     return l.findStatic(Test.class, "target", t);
   } catch (Throwable e) {
     throw new RuntimeException(e);
   }
 }
}

public static final MyCallSite myCallSite = new MyCallSite();

public static void main(String[] args) throws Throwable {
 long current = System.currentTimeMillis();
 for (int i = 1; i <= 2_000_000_000; i++) {
   if (i % 100_000_000 == 0) {
     long temp = System.currentTimeMillis();
     System.out.println(temp - current);
     current = temp;
   }

   myCallSite.getTarget().invokeExact(128);
 }
}
}
     
     
}
