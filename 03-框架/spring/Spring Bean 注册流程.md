Spring Bean 实例注册流程

```java
/** 1. 定义好 Spring 的配置文件 applicationContext.xml

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

