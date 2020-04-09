# CrudTemplate

### Introduce
Using CrudTemplate,you can create crud interfaces withot write any code but a POJO and a path.And implementing specialization crud by rewrite is alse in the plan.  

通过使用CrudTemplate，你只需要输入一个POJO和接口地址，就可以获得一套增删改查的接口。后续计划通过重写的方式实现特殊要求的增删改查。

### Guide
CrudTemplate used SpringBoot and some maven dependencies.
So it will be really easy if you used the SpringBoot too.
Using SpringBoot,all you need to do is delete the SpringBootMainClass directory
and add "com.Boyce.CrudTemplate" to your @SpringBootApplication's(or @ComponentScan's) scanBasePackages. 

CrudTemplate使用了SpringBoot框架以及一些maven依赖。所以如果你也使用SpringBoot的话用起来会很方便。
如果你需要使用CrudTemplate，你只需要删除我的SpringBootMainClass启动类，并在你的SpringBoot启动类上，通过@SpringBootApplication或者@ComponentScan，
将"com.Boyce.CrudTemplate"包添加到扫描路径下即可。

### Warning
1.If you add a crud interface that has a path containing another crud interface's path,or being contained,
only one crud interface will work.And the answer to the question that witch one will work depend on their path's hash code.

1.如果添加的增删改查接口路径中包含其他增删改查接口的路径，或被其他接口路径包含，
那么只有一个接口会起作用。哪个接口会起作用取决于接口路径的hash code。