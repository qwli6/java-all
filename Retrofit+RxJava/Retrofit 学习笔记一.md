# Retrofit 
说起 Retrofit，我真是头大，工作了一年了，还只会用 OKHttp 去做网络请求，由于自己注解没学好，所以对 Retrofit 一直都有点畏惧，但是没办法，现在 Retrofit 都是主流的网络请求了，所以难啃也得啃。毕竟工作了一年多了，说自己不会 Retrofit + RxJava 好像不太合适，那就来吧。

对 Retrofit 是什么东西，就不介绍了。反正大家都说好用，不信你用着试试。

# 入门

先来个简单的入门吧。首先定义一个接口，在接口里面定义一个方法。你只需要知道，Retrofit 所有的网络请求方法都是定义在接口内部的。

	public interface RetrofitApi {
	    @GET("users/{user}/repos")
	    Call<List<Repo>> listRepos(@Path("user") String user);
	}

这里有一个 @GET 注解，表示该方法请求是一个 Get 请求，我们都知道常用的请求方式有两种， Get 请求和 Post 请求，那么这里的 @GET 就代表的 Get 请求。

你可能还注意到了，@GET 注解后面带有一大串狗屎一样的东西，别急，我们来慢慢解释，“users/{user}/repos” 这实际上是一个子路径， 它最后是要和基路径拼接在一起才能形成一个完整的请求路径的，可能有人会问，我这里没有看到基路径啊，嘿，小伙子，别急，基路径你会看到的，只不过不是这里。

既然能够理解 “users/{user}/repos ” 是一个子路径，那么你肯定会觉得 "{user}" 这个东西不正常，因为正常的请求怎么可能会带有这个东西？没错，这个东西实际上是一个占位符。知道什么是占位符吧？举个例子，如果我们请求的基路径是 “http://www.baidu.com” ，那么我们调用 listRepos 这个方法实际上请求的完整路径就是 "http://www.baidu.com/users/{user}/repos" ，这里的占位符用 listRepos 这个方法传进来的参数来替代，也就是 listRepos(@Path("user") String user) 中的 user，先暂时忽略 @Path("user") 这个注解。如果我们传递进来的 user 参数是 tom， 那么完整请求路径就是

	http://www.baidu.com/users/tom/repos

如果我们传递进来的 user 参数是 jack，那么完整的请求路径就是

	http://www.baidu.com/users/jack/repos
	
说道这里了，我想你应该明白了。那么我们接着说一下 @Path("user") 这个注解，你上面也看到了，我们的请求路径中有一个 {user} 是占位符，因为是请求路径中有占位符，所以我们这里需要用注解 @Path 来标明我们的路径中的占位符是用这里的参数来替代的，@Path("user") 中的 “user” 就代表我们路径中的占位符 {user} 中的 user。

好了，说到这里，方法的定义就说完了，下面我们说说方法的实现。

	Retrofit retrofit = new Retrofit.Builder()
		.baseUrl("http://api.github.com/")
		.addConverterFactory(GsonConverterFactory.create())
		.build();
		
	RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
	
	Call<List<Repo>> call = retrofitApi.listRepos("octocat");
	
	call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
          		 String name = response.body().get(0).getName();
                  Toast.makeText(RxJavaActivity.this, "第一个列表中的用户名为：" + name, 
                  	Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });

我们上面不是说了基路径和子路径拼接起来就成了一个完整的路径么？从上面的代码我们可以知道，基路径就是 baseUrl("http://api.github.com/") 中的 http://api.github.com/

> **注意：基路径必须以 “/” 结尾，否则会报错。**

综合我们上面的讲解来看，完整的请求路径应该是

	http://api.github.com/users/{user}/repos

因为我们传入的 user 参数是 “octocat”， 所以最后我们应用程序请求的完整路径就出来了

	http://api.github.com/users/octocat/repos
	
这就是一个最简单，最基础的 Retrofit 网络请求。演示结果如下：

<img src="http://obyg3yq9k.bkt.clouddn.com/Retrofit%20Get%E4%B8%8D%E5%B8%A6%E5%8F%82%E6%95%B0%E8%AF%B7%E6%B1%82.gif">


我们轻而易举的拿到了列表中第一个人的用户名。你肯定看到了，我们对拿到的结果并没有做任何的处理，就可以直接根据 response 对象直接拿到实体类中的属性值，为什么呢？当然这都要得益于我们在创建 Retrofit 对象的时候添加了（Gson 解析转换工厂，这里用到了工厂模式）

	.addConverterFactory(GsonConverterFactory.create())

这样一行代码，这行代码直接指定了 Retrofit 在拿到结果后，直接跟我们对结果进行解析，返回给我们的是实体类对象。是不是很 6 ？ 帮我们把解析的过程都做了，简直不能太 6。当然你也可以在后面添加其他的转换工厂，链式编程就是这么炸，至于其他工厂，我们在这里暂时不做过多说明。

# 请求附带参数
上面我们说了一个最简单的 GET 请求，不带任何参数说明，万一我们的请求需要携带参数呢？举个例子，比如登录的请求

	http://www.baidu.com/login?username='xxx'&password='123456'
	
像这样的请求，我们对参数如何处理呢？
这里我们需要添加另外一个注解 Query

那我们就来试试怎么样使用 Query 注解，并且结合我们上面所讲的 Path 注解一起使用

首先在接口中定义一个方法

	 @GET("users/{user}/repos")
	 Call<List<Repo>> getGroupList(
	 			@Path("user") String user, 
	 			@Query("sort") String sort);
	 			

@Query("sort") 中的 sort 就是在路径后面拼接的查询参数，假如基路径为：
	
	http://api.github.com
Path 替代参数是 octcota，Query 拼接传入的参数是 20，所以我们上面的完整请求路径应该是 

	http://api.github.com/users/octcota/repos?sort=20
	
这是拼接一个参数的请求，那么加入我要拼接多个参数呢？那我们上面说的登录的案例就可以写成

	@GET("login")
	Call<String> login(@Query("username") String username, @Query("password") String password)

是不是贼 6 ？
这里的 Call<String> 可以先理解成返回值，等到时候和 RxJava 结合起来一起使用的时候，我们的返回值将不再是 Call。

















