## Spring Bean 实例注册流程

1. 定义好 Spring 的配置文件 applicationContext.xml

```java
/** 

* 2. 通过 Resource 对象将 Spring 配置文件进行抽象，抽象成一个具体的 Resource 对象，比如 ClassPathResource
	Resource resource = new ClasspathResource("applicationContext.xml");
	
* 3. 定义好将要使用的 Bean 工厂（各种 BeanFactory）
	DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
	
* 4. 定义好 XMLBeanDefinitionReader 对象，并将工厂对象作为参数传递进去，从而构建好二者之间的关联关系
	BeanDefinitionReader beanDefinitionReader = new XMLBeanDefinitionReader(defaultListableBeanFactory);


* 5. 定义好 XMLBeanDefinitionReader 对象读取之前所抽取出的 Resource 对象
* 6. 流程开始解析
	beanDefinitionReader.loadBeanDefinitionReader(resource);

* 7. 针对 XML 文件进行各种元素以及元素属性的解析，这里面，真正的解析是通过 BeanDefinitionParserDelegate 对象来完成的（委托模式）
* 8. 通过 BeanDefinitionParserDelegate 对象在解析 XML 文件是，又使用到了模板方法设计模式（pre， process， post）
* 9. 当所有的 bean 标签元素都解析完毕后，开始定义一个 BeanDefinition 对象，该对象是一个非常重要的对象，里面容纳了一个 Bean 相关的所有属性，包括 scope，abstract，init-method, destory-method....

* 10. BeanDefinition 对象创建完毕后，Spring 又会创建一个 BeanDefinitionHolder 对象来持有这个 BeanDefinition 对象。
		
	BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanName, beanDefinition);
	
* 11. BeanDefinitionHolder 主要包含两部分内容：beanName 和 BeanDefinition。
* 12. 工厂会将解析出来的 Bean 信息存放到内部的一个 ConcurrentHashMap 中，该 Map 的键是 beanName（唯一），值是 BeanDefinition 对象
* 13. 调用 Bean 解析完毕的触发动作，从而触发相应的监听器的方法执行（观察者模式）
**/
```



## 关于 Spring Bean 的创建流程

1. Spring 所管理的 Bean 实际上是缓存在一个 ConcurrentHashMap 中的（singletonObjects 对象中）
2. 该对象本质是一个 key-value 对的形式，key 指的是 bean 的名字（id）， value 是一个 object 对象，就是所创建的对象
3. 在创建 bean 之前，首先需要将该 Bean 的创建标识指定好，表示该 bean 已经或是即将被创建，目的是增强缓存的效率
4. 根据 bean 的 scope 属性来确定当前的这个 bean 是一个 singleton 还是一个 prototype 的 bean，然后创建相应的对象
5. 无论是 singleton 还是 prototype 的 bean，其创建过程是一致的
6. 通过 Java 的反射机制来创建 Bean 的实例，在创建之前需要检查构造方法的访问修饰符，如果不是 public 的，则会调用 setAccessible（true）来突破 Java 语法的限制，使得可以通过非 public 构造方法来完成对象实例的创建
7. 当对象创建完毕后，开始对对象属性的注入
8. 在对象属性注入的过程中，Spring 除去使用之前通过 BeanDefinition 对象获取 Bean 的信息外，还会通过反射的方式获取到上面所创建 Bean 的真实信息（还包括一个 class 属性，表示该 Bean 所对应的 Class 类型）
9. 完成 Bean 属性的注入（或者抛出异常）
10. 如果 Bean 是一个单例的，那么将所创建出来的 bean 添加到 singletonObjects 对象中（缓存中），供程序后续再次使用。





## ProxyFactoryBean 的构成

- target
  - 目标对象，需要对其进行切面增强
- proxyInterfaces
  - 代理对象所实现的接口
- interceptorNames
  - 通知器（Advisor）列表，通知器中包含了通知（Advice）与切点（Pointcut）

```java
public class MyAdvisor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("before MyAdvisor invoke");

        Object result = invocation.proceed();

        System.out.println("after MyAdvisor invoked");
        return result;
    }
}
```



## BeanFactory 和 FactoryBean 的区别

- BeanFactory 是 IOC 的工厂，它里面管理着 Spring 所创建出来的各种 Bean 对象，当我们在配置文件中（注解）中声明了某个 bean 的 id 后，通过这个 id 就可以获取到该 id 所对应的 class 对象的实例（可能新创建，也可能从缓存中获取，BeanFactory 的缓存就是一个 ConcurrentHashMap）
- FactoryBean 本质上也是一个 Bean，和其他的 Bean 一样，也是由 BeanFactory 所管理和维护的，当然他的实例也会缓存到 Spring 的工厂中（如果是单例），它与普通的 Bean 的唯一区别就在于，当 Spring 创建另一个 FactoryBean 的实例后，它接下来会判断当前所创建的 Bean 是否是一个 FactoryBean 实例。如果不是，那么就将直接创建出来的 Bean 返回给客户端；如果是，那么它会对其进行进一步处理，根据配置文件所配置的 target，advisor 与 interfaces 等信息在运行期间动态构建出一个类，并生成该类的实例，最后将该实例返回给客户端；因此我们在声明一个 FactoryBean 时，通过 id 获取到的并非是这个 FactoryBean 的实例，而是它动态生成的一个代理对象。（通过三种方式来生成）
  - JDK 动态代理
  - CGLIB
  - ObjenesisCglibAopProxy（Spring4.0 引入）





## 关于 Spring AOP 代理的生成过程

1. 通过 ProxyFactoryBean（FactoryBean 接口的实现）来去配置相应的代理对象相关的信息
2. 在获取 ProxyFactoryBean 实例时，本质上并不是获取到 ProxyFactoryBean 的对象，而是获取到了由 ProxyFactoryBean 所返回的那个对象实例
3. 在整个 ProxyFactoryBean 实例的构建与缓存的过程中，Spring 会判断当前所创建的对象是否是一个 FactoryBean 的实例。
4. 差别在于，当创建了 ProxyFactoryBean 对象后，Spring 会判断当前所创建的对象是否是一个 FactoryBean 实例。
5. 如果不是，那么 Spring 就直接将所创建的对象返回。
6. 如果是，Spring 则会进入到一个新的流程分支中。
7. Spring 会根据我们在配置信息中所指定的各种元素，如目标对象是否实现了接口以及 Advisor 等信息，使用动态代理或者 CGLIB 等方式来为目标对象创建响应的代理对象。
8. 当相应的代理对象创建完毕后，Spring 就会通过 ProxyFactoryBean 的 getObject 方法将所创建的对象返回。
9. 对象返回到调用端（客户端），他本质上是一个代理对象。可以代理对目标对象的访问与调用；这个代理对象对用户来说，就像一个目标对象一样。
10. 客户在使用代理对象时，可以正常调用目标对象的方法，同时在执行过程中，会根据我们在配置文件中所配置的信息来调用前后执行额外的附加逻辑。



## 针对数据库事务的操作

1. setAutoCommit(false)
2. target.method() [1. 执行成功  2. 执行失败]
3. conn.commit() or conn.rollback();





## 基于注解与基于 XML 配置的 Spring Bean 在创建时机存在的唯一的不同之处

1. 基于 XML 配置的方式，Bean 对象的创建是在程序首次从工厂中获取该对象时才创建的。
2. 基于注解配置的方式，Bean 对象的创建是在注解处理器解析相应的 @Bean 注解时调用了该注解所修饰的方法，当该方法执行后，相应的对象自然就已经被创建出来了，这时候，Spring 就会将该对象纳入到工厂所管理的范围之内，当我们首次尝试从工厂中获取到该 Bean 对象时，这时，该 Bean 对象实际上已经完成了创建并且已经被纳入到工厂的管理范围之内。

### 





























































