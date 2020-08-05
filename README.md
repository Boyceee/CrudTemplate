# CrudTemplate

### Introduce
Using CrudTemplate,you can create crud interfaces without write any code but a POJO and a path.

通过使用CrudTemplate，你只需要输入一个POJO和接口地址，就可以获得一套增删改查的接口。

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
use a database supporting the automatic transfer if you want to use this project.For example,MySQL,which I used in my test project,is highly recommended.  

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
7.这个项目非常依赖数据库的数据类型自动转换，所以如果你想使用这个项目，非常推荐使用具有自动转换功能的数据库。例如，强烈推荐使用MySQL数据库，这也是我在测试项目时所使用的数据库。  

### Guide
##### CrudTemplate used SpringBoot and some maven dependencies,so it will be really easy if you used the SpringBoot too,which is also highly recommended.
##### 这个项目使用了SpringBoot和一些maven依赖，所以强烈建议你也使用一个基于SpringBoot的项目，这会非常容易兼容。
If you are using the source code,here are all the things you need to do:  
1.Write your own POJO according to your database table.You can refer to my TestTable.  
2.Change 'CrudController.addCrudInterface("/test",new TestTable());' to your path and POJO in the CrudTemplateApplication.  
3.Request "http://ip:port/test/query","http://ip:port/test/add","http://ip:port/test/update","http://ip:port/test/delete".  

If you are using the jar of this project,here are all the things you need to do:  
1.Get the jar of this project in my maven repository web:https://mvnrepository.com/artifact/com.github.Boyceee.  
2.Add "com.boyce.crud.template" and your own package path to your @SpringBootApplication's scanBasePackages.  
3.Write your own POJO according to your database table.You can refer to my TestTable.  
4.Write 'CrudController.addCrudInterface("/test",new POJO());' at wherever it can be executed.  
5.Make sure that there is a datasource in your application.properties.  
6.If your maven report an error that the jar of this project cannot be found,
check that if you set a mirror mirroring of central(like aliyun) in your settings.xml.
If you did,then you need to add a default mirror mirroring of * at the last of the mirrors.
Because sometimes aliyun(or other) won't synchronize there repository in time,
so you need to add a default mirror to make sure maven can find this jar in their own repository at last.   
7.Request "http://ip:port/test/query","http://ip:port/test/add","http://ip:port/test/update","http://ip:port/test/delete".  

如果你想要使用源码，以下是你需要做的事：  
1.根据你的数据库表结构编写你自己的POJO。你可以参考我的TestTable。  
2.修改CrudTemplateApplication中的'CrudController.addCrudInterface("/test",new TestTable());'，将路径与POJO修改为你自己的内容。  
3.请求"http://ip:port/test/query","http://ip:port/test/add","http://ip:port/test/update","http://ip:port/test/delete"。  

如果你想要使用jar包，以下是你需要做的事：  
1.在我的maven仓库获取这个项目的jar包，地址为：https://mvnrepository.com/artifact/com.github.Boyceee.  
2.在你的@SpringBootApplication的scanBasePackages中，添加"com.boyce.crud.template"路径和你自己的包路径。  
3.根据你的数据库表结构编写你自己的POJO。你可以参考我的TestTable。  
4.在任何可被执行到的地方写'CrudController.addCrudInterface("/test",new POJO());'。  
5.确保在你的application.properties中有一个datasource数据源。  
6.如果你的maven报错找不到这个jar包，检查一下在你的settings.xml中有没有配置一个映射了central的镜像（比如阿里云）。
如果你做了这个映射，你需要在mirrors的最后添加一个映射*的默认镜像。因为有时候阿里云（或者其他）不会实时同步他们的仓库，
所以你需要添加一个默认镜像来保证maven最终可以在自己的仓库中找到这个jar包。  
7.请求"http://ip:port/test/query","http://ip:port/test/add","http://ip:port/test/update","http://ip:port/test/delete"。  
