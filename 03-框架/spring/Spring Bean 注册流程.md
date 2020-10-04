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





















