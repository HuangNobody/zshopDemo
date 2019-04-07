# zshop项目搭建步骤

标签（空格分隔）： spring+springmvc+mybatis  idea  mysql

---

# 一 搭建环境

## 1.创建工程

 1. 按层次创建maven工程 
> * zshop_parent   父工程，所有的依赖都交给它管理
> * zshop_commmon   通用的东西，工具类，常量
> * zshop_pojo   普通的java对象
> * zshop_dao
> * zshop_service 这里serviece实际也可以分为前台后台两个部分，因为有些东西相同，这里就没细分
> * zshop_front_web   前台web工程
> * zshop_backend_web   后台web工程
（这里前面几个工程师quickstart，最后两个是web工程）

## 详细步骤

> * 1,创建一个空的工程File ——> new——>project ——> empty project (项目名zshopDemo)
> * 2,配置jdk和maven
> * 3,创建module，程File ——> new——>module ——>maven (看一下jdk有没有正确配置) ——>maven  ——>选中 create from archetype 前面的方框 ——> **org.apache.maven.archetype:maven-archetype-quickstart** ——> 点击next  ——>

> * 4,工程都创建好后，隐藏.iml文件（这个也没什么影响，就是看着不舒服，也可以跳过这一步）file-->setting --> 在搜索框中输入file type -->选中File types -- > 在ignore file and folders 中输入*.iml-->ok  .就隐藏成功

----------

# 2 配置依赖（添加jar包）
### **父工程zshop_parent的pom.xml添加依赖**

> **在父工程添加依赖，在子工程中引用它，每个子工程*根据自己的需要*引用相应的依赖（jar包）。**

**在zshop_parent的pom.xml文件中添加依赖**，去网站*[添加依赖的网站](https://mvnrepository.com/)*搜索，都可以搜到
> * 1,java ee相关依赖（因为有web工程）：servlet-api，jsp-api,jstl-api
> * 2,spring相关依赖： spring-core,spring-beans,spring-context,spring-expression,spring-aop,spring-aspects, spring-jdbc,spring-tx,spring-webmvc 
> * 3,mybatis相关依赖：mybatis,mybatis-spring,pagehelper(mybatis中特别好用的分页插件)
> * 4,mysql相关依赖:mysql-connector,druid(这和jdbc不一样，这是阿里的一个数据源)，
> * 5,工具相关依赖（比如文件的上传下载）：commons-fileupload(文件上传下载)，
fastjson(处理json)


----------

### (1) 子工程zshop_commmon的pom.xml
通用的东西往这里添加。java ee只有web项目才用，所以不要往里加；spring多个项目都要用，所以可以往里面添加引用（spring中的依赖不需要每个都添加引用个，根据需要）。注意，删除版本号那一行。
> * 添加引用：spring-core,spring-beans,spring-context,spring-expression,spring-aop,spring-aspects,    commons-fileupload(文件上传下载)，fastjson(处理json)


----------
### (2) 子工程zshop_pojo的pom.xml
就是一些实体来，没有什么依赖，可以不用添加引用。


----------
### (3) 子工程zshop_dao的pom.xml
dao操作数据库，这是与数据库相关的，所以mysql相关依赖(a)，mybatis相关依赖(b)都要引用，添加进来。spring相关依赖(c)中的spring-jdbc也与数据库相关，也可以放进来。子工程zshop_dao要调用子工程zshop_pojo中的对象(d)，所以依赖这个工程；也依赖子工程zshop_commmon(e)，因为这个工程放的东西都是公共的。
注意：对其他子项目的依赖（d）(e)，需要自己写，不是到网站上搜的。
> * **依赖的传递性：子工程dao依赖自动车common，那么common中所有的jar包（依赖关系）子工程dao中也有了。**

> * 添加引用：（a）mysql-connector,druid， (b)mybatis,mybatis-spring,pagehelpe，(c)spring-jdbc，(d) 和(e)添加的依赖如下

```xml
<dependency>
    <groupId> com.itany_zshopDemo</groupId>
    <artifactId>zshop_commmon</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.itany_zshopDemo</groupId>
    <artifactId>zshop_pojo</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```


----------


### (4) 子工程zshop_service的pom.xml

首先service层有事务（spirng-tx）（a），sercive 依赖dao的（b）,如下
```xml
<dependency>
    <groupId>com.itany_zshopDemo</groupId>
    <artifactId>zshop_dao</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
### (5) 子工程zshop_backend_web的pom.xml
 后台java ee 是要的（a）,web工程肯定是需要java ee的，java ee的所有依赖;  需要springmvc（b）;依赖于子项目service（c）;
```xml
<dependency>
    <groupId>com.itany_zshopDemo</groupId>
    <artifactId>zshop_service</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
``` 

### (6) 子工程zshop_front_web的pom.xml
 后台java ee 是要的（a）,web工程肯定是需要java ee的，java ee的所有依赖;  需要springmvc（b）;依赖于子项目service（c）;
 **注意：在这个项目中这里前台和后台是一样的，在其他的项目就不一定是这样。**
 
a

----------
### **父工程zshop_parent的pom.xml添加tomcat插件**
去网站*[添加依赖的网站](https://mvnrepository.com/)*搜索，tomcat7-maven-plugin,
上面添加依赖是在 `<dependencies> </ependencies>`里面，这里添加插件是在`<build>在这里添加</build>`里面。在父工程添加插件，每个子工程都要引用它
```xml
<pluginManagement>
    <plugins>
        <plugin>
            <groupId>org.apache.tomcat.maven</groupId>
            <artifactId>tomcat7-maven-plugin</artifactId>
            <version>${tomcat7-maven-plugin.version}</version>
        </plugin>
    </plugins>
</pluginManagement>
```


----------
### (1) 子工程zshop_backend_web的pom.xml
后台要使用tomcat插件，所以要添加引用。添加引用是在`<build>在这里添加</build>`里面。后台端口号`<port>9001</port>`设置为9001，前台设置另外一个端口号。
```xml
<plugins>
    <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat7-maven-plugin</artifactId>
        <configuration>
            <path>/</path>
            <port>9001</port>
        </configuration>
    </plugin>
</plugins>
```
 


----------
### (2) 子工程zshop_front_web的pom.xml
前台要使用tomcat插件，所以要添加引用。添加引用是在`<build>在这里添加</build>`里面。后台端口号`<port>9002</port>`设置为9002，端口号要与后端端口号不一样。
```xml
<plugins>
    <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat7-maven-plugin</artifactId>
        <configuration>
            <path>/</path>
            <port>9002</port>
        </configuration>
    </plugin>
</plugins>
```


----------
目前所有依赖加完了，后面还可以再添加，若是需要。完整的父工程zshop_parent的pom.xml（最前面一点没粘贴）
```xml
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.7</maven.compiler.source>
    <maven.compiler.target>1.7</maven.compiler.target>
    <javax.servlet-api.version>3.1.0</javax.servlet-api.version>
    <junit.version>4.11</junit.version>
    <jsp-api.version>2.2</jsp-api.version>
      <jstl.version>1.2</jstl.version>
    <spring-core.version>4.3.20.RELEASE</spring-core.version>
    <mybatis.version>3.4.6</mybatis.version>
    <mybatis-spring.version>1.3.2</mybatis-spring.version>
    <pagehelper.version>5.1.8</pagehelper.version>
    <mysql-connector-java.version>5.1.38</mysql-connector-java.version>
    <druid.version>1.1.12</druid.version>
    <commons-fileupload.version>1.3.1</commons-fileupload.version>
    <fastjson.version>1.2.56</fastjson.version>
    <tomcat7-maven-plugin.version>2.2</tomcat7-maven-plugin.version>
  </properties>

  <!--进行依赖管理，加下面一句-->
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>${junit.version}</version>
      <scope>test</scope>
    </dependency>

    <!-- java ee (因为是web工程，所以肯定有这个依赖)-->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>${javax.servlet-api.version}</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>javax.servlet.jsp</groupId>
      <artifactId>jsp-api</artifactId>
      <version>${jsp-api.version}</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>jstl</artifactId>
      <version>${jstl.version}</version>
    </dependency>

    <!-- spring 相关依赖)-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-core</artifactId>
      <version>${spring-core.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-beans</artifactId>
      <version>${spring-core.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>${spring-core.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-expression</artifactId>
      <version>${spring-core.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aop</artifactId>
      <version>${spring-core.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aspects</artifactId>
      <version>${spring-core.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>${spring-core.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-tx</artifactId>
      <version>${spring-core.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>${spring-core.version}</version>
    </dependency>

    <!--mybatis相关依赖-->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>${mybatis.version}</version>
    </dependency>
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>${mybatis-spring.version}</version>
    </dependency>
    <dependency>
      <groupId>com.github.pagehelper</groupId>
      <artifactId>pagehelper</artifactId>
      <version>${pagehelper.version}</version>
    </dependency>

    <!--mysql相关依赖-->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>${mysql-connector-java.version}</version>
    </dependency>
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>${druid.version}</version>
    </dependency>

    <!--工具（比如文件的上传下载）-->
    <dependency>
      <groupId>commons-fileupload</groupId>
      <artifactId>commons-fileupload</artifactId>
      <version>${commons-fileupload.version}</version>
    </dependency>
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>fastjson</artifactId>
      <version>${fastjson.version}</version>
    </dependency>
  </dependencies>
</dependencyManagement>

  <build>
    <pluginManagement>
      <plugins>
        <plugin>
            <groupId>org.apache.tomcat.maven</groupId>
            <artifactId>tomcat7-maven-plugin</artifactId>
            <version>${tomcat7-maven-plugin.version}</version>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>
</project>
```


----------
# 用tomcat跑一下zshop_backend_web测试一下
> * 1，点击idea右边的maven界面-->双击zshop_parent中Lifecycle中install（是为了将依赖关系导入到maven本地仓库中，不然会报错）
> * 2,点击zshop_backend_web中plugins中tomcat7中的tomcat7：run。也可以add-configuration,点击出来界面的左上角的“+”，选择maven，在Name：中输入名字，在working directory中选择要运行tomcat的项目zshop_backend_web的路径






