# CrudTemplate

### Introduce
Using CrudTemplate,you can create crud interfaces without write any code but a POJO and a path.And implementing specialization crud is also in the plan.  

通过使用CrudTemplate，你只需要输入一个POJO和接口地址，就可以获得一套增删改查的接口。后续计划实现特殊要求的增删改查。

### Guide
CrudTemplate used SpringBoot and some maven dependencies.
So it will be really easy if you used the SpringBoot too.
Using SpringBoot,all you need to do is importing the jar of this project
and adding "com.boyce.crud.template" to your @SpringBootApplication's(or @ComponentScan's) scanBasePackages. 

CrudTemplate使用了SpringBoot框架以及一些maven依赖。所以如果你也使用SpringBoot的话用起来会很方便。
如果你需要使用CrudTemplate，你只需要导入这个项目的jar包，并在你的SpringBoot启动类上，通过@SpringBootApplication或者@ComponentScan，
将"com.boyce.CrudTemplate"包添加到扫描路径下即可。

### Warning
1.If you add a crud interface that has a path containing another crud interface's path,or being contained,
only one crud interface will work.And the answer to the question that witch one will work depend on their path's hashCode() & (map's capacity -1) and the order of addition.  
2.Make sure that you are using POJO's field name as the request parameter's key,not database's column name.  
3."/query" interface basically support all of the http's methods.  
4.Up to now,"/add" interface only support the content type of "application/x-www-form-urlencoded" and "application/json".
As for http's methods,as long as they supported these two content type,it will be OK.  
5.Up to now,"/update" interface only support the content type of "application/x-www-form-urlencoded" and "application/json".
As for http's methods,as long as they supported these two content type,it will be OK.  
6.Up to now,"/delete" interface only support the content type of "application/x-www-form-urlencoded" and "application/json".
As for http's methods,as long as they supported these two content type,it will be OK.  
7.This project is very dependent on database's automatic transfer of datatype,so it's highly recommended to 
use a database supporting the automatic transfer if you want to use this project.  

1.如果添加的增删改查接口路径中包含其他增删改查接口的路径，或被其他接口路径包含，
那么只有一个接口会起作用。哪个接口会起作用取决于接口路径的hashCode() & (map容量 -1)和接口被添加的顺序。  
2.确保在发送的请求中，参数的key是POJO的属性名，而不是数据库的字段名。  
3."/query"接口支持基本上所有http的method方法。  
4."/add"接口目前只支持content type为"application/x-www-form-urlencoded"和"application/json"的请求，至于http的method，
只要该method支持这两种content type，就可以使用。  
5."/update"接口目前只支持content type为"application/x-www-form-urlencoded"和"application/json"的请求，至于http的method，
只要该method支持这两种content type，就可以使用。  
6."/delete"接口目前只支持content type为"application/x-www-form-urlencoded"和"application/json"的请求，至于http的method，
只要该method支持这两种content type，就可以使用。  
7.这个项目非常依赖数据库的数据类型自动转换，所以如果你想使用这个项目，非常推荐使用具有自动转换功能的数据库。  

### Demo
If you are using the source code,here are all the things you need to do:  
1.Write your own POJO according to your database table.You can refer to my TestTable.  
2.Change 'CrudController.addCrudInterface("/test",new TestTable());' to your path and POJO in the CrudTemplateApplication.  
3.Request "http://ip:port/test/query","http://ip:port/test/add","http://ip:port/test/update","http://ip:port/test/delete".  

If you are using the jar of this project,here are all the things you need to do:
1.Get the jar of this project .  
2.Add "com.boyce.crud.template" to your @SpringBootApplication's scanBasePackages.  
3.Write your own POJO according to your database table.You can refer to my TestTable.  
4.Do 'CrudController.addCrudInterface("/test",new POJO());' before run your SpringApplication.  
5.Request "http://ip:port/test/query","http://ip:port/test/add","http://ip:port/test/update","http://ip:port/test/delete".  

如果你想要使用源码，以下是你需要做的事：  
1.根据你的数据库表结构编写你自己的POJO。你可以参考我的TestTable。  
2.修改CrudTemplateApplication中的'CrudController.addCrudInterface("/test",new TestTable());'，将路径与POJO修改为你自己的内容。  
3.请求"http://ip:port/test/query","http://ip:port/test/add","http://ip:port/test/update","http://ip:port/test/delete"。  

如果你想要使用jar包，以下是你需要做的事：  
1.获取这个项目的jar包。  
2.在你的@SpringBootApplication的scanBasePackages中，添加"com.boyce.crud.template"路径。  
3.根据你的数据库表结构编写你自己的POJO。你可以参考我的TestTable。  
4.在运行你的SpringApplication前，执行'CrudController.addCrudInterface("/test",new POJO());'。  
5.请求"http://ip:port/test/query","http://ip:port/test/add","http://ip:port/test/update","http://ip:port/test/delete"。  