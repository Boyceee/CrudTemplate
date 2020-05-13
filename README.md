# CrudTemplate

### Introduce
Using CrudTemplate,you can create crud interfaces withot write any code but a POJO and a path.And implementing specialization crud is also in the plan.  

通过使用CrudTemplate，你只需要输入一个POJO和接口地址，就可以获得一套增删改查的接口。后续计划实现特殊要求的增删改查。

### Guide
CrudTemplate used SpringBoot and some maven dependencies.
So it will be really easy if you used the SpringBoot too.
Using SpringBoot,all you need to do is delete the SpringBootMainClass directory
and add "com.boyce.CrudTemplate" to your @SpringBootApplication's(or @ComponentScan's) scanBasePackages. 

CrudTemplate使用了SpringBoot框架以及一些maven依赖。所以如果你也使用SpringBoot的话用起来会很方便。
如果你需要使用CrudTemplate，你只需要删除我的SpringBootMainClass启动类，并在你的SpringBoot启动类上，通过@SpringBootApplication或者@ComponentScan，
将"com.boyce.CrudTemplate"包添加到扫描路径下即可。

### Warning
1.If you add a crud interface that has a path containing another crud interface's path,or being contained,
only one crud interface will work.And the answer to the question that witch one will work depend on their path's hashCode() & (map's capacity -1) and the order of addition.  
2.Make sure that you are using POJO's field name as the request parameter's key,not database's column name.  
3."/query" interface basically support all of the https's methods.  
4.Up to now,"/add" interface only support the content type of "application/x-www-form-urlencoded" and "application/json".
As for http's methods,as long as they supported these two content type,it will be OK.

1.如果添加的增删改查接口路径中包含其他增删改查接口的路径，或被其他接口路径包含，
那么只有一个接口会起作用。哪个接口会起作用取决于接口路径的hashCode() & (map容量 -1)和接口被添加的顺序。  
2.确保在发送的请求中，参数的key是POJO的属性名，而不是数据库的字段名。  
3."/query"接口支持基本上所有http的method方法。  
4."/add"接口目前只支持content type为"application/x-www-form-urlencoded"和"application/json"的请求，至于http的method，
只要该method支持这两种content type，就可以使用。  