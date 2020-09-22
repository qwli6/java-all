线性查找并不总是 O(N) 的，当元素在数组的末尾，确实是需要 O(N) 才能找到。但是如果在开头，1 步就能找到的话，那么它的时间复杂度理论上可以成为 O(1)。

所以一句话概括，线性查找最好的情况是 O(1)，最坏的情况是 O(N)。



> 大 O 标记法一般都是指**最坏的情况**





SpringBoot 静态资源处理见 `org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry#getHandlerMapping`

```java
@Nullable
@SuppressWarnings("deprecation")
protected AbstractHandlerMapping getHandlerMapping() {
   if (this.registrations.isEmpty()) {
      return null;
   }

   Map<String, HttpRequestHandler> urlMap = new LinkedHashMap<>();
   for (ResourceHandlerRegistration registration : this.registrations) {
      for (String pathPattern : registration.getPathPatterns()) {
         ResourceHttpRequestHandler handler = registration.getRequestHandler();
         if (this.pathHelper != null) {
            handler.setUrlPathHelper(this.pathHelper);
         }
         if (this.contentNegotiationManager != null) {
            handler.setContentNegotiationManager(this.contentNegotiationManager);
         }
         handler.setServletContext(this.servletContext);
         handler.setApplicationContext(this.applicationContext);
         try {
            handler.afterPropertiesSet();
         }
         catch (Throwable ex) {
            throw new BeanInitializationException("Failed to init ResourceHttpRequestHandler", ex);
         }
         urlMap.put(pathPattern, handler);
      }
   }

   return new SimpleUrlHandlerMapping(urlMap, this.order);
}
```

这里实际上就是返回了一个 SimpleUrlHandlerMapping，可以自定义一个用来处理静态资源