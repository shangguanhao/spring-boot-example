<div id="topics">
	<div class="post">
		<h1 class="postTitle">
			<a id="cb_post_title_url" class="postTitle2" href="https://www.cnblogs.com/sgh1023/p/10108416.html">Spring Boot实现仿博客园发送通知邮件</a>
		</h1>
		<div class="clear"></div>
		<div class="postBody">
			<div id="cnblogs_post_body" class="blogpost-body"><p><span>邮件服务已经是基础性服务了 ，是网站的必备功能之一，当注册了某些网站的时候，邮箱里通常会收到一封注册成功通知邮件或者点击激活账号的邮件，博客园也是如此。本文使用Spring Boot，通过QQ邮箱来模仿博客园发送一封通知邮件。</span></p>
<p><span>博客园发送的“欢迎您加入博客园”的主题邮件如图所示。这种通知邮件，只有登录用户名在变化，其它邮件内容均不变，很适合用邮件模板来处理。</span></p>
<p><span><img src="https://img2018.cnblogs.com/blog/1538609/201812/1538609-20181212133722681-510791467.png" alt=""></span></p>
<p>模板可以实现显示与数据分离，将模板文件和数据通过模板引擎生成最终的HTML代码。</p>
<p>Thymeleaf是一个适用于Web和独立环境的现代服务器端Java模板引擎，能够处理HTML，XML，JavaScript，CSS甚至纯文本。Thymeleaf由于使用了标签属性做为语法，模版页面直接用浏览器渲染，与其它模板引擎（比如Freemaker）相比，Thymeleaf最大的特点是能够直接在浏览器中打开并正确显示模板页面，而不需要启动整个Web应用。</p>
<p><span>Thymeleaf作为Spring官方推荐的模板引擎，Spring boot对Thymeleaf支持比较友好，配置简单，这里使用Thymeleaf作为模板引擎。</span></p>
<p><span>下面正式开始实现仿博客园发送通知邮件。</span></p>
<h2>1. pom.xml添加邮件和模板相关依赖</h2>
<div class="cnblogs_code"><div class="cnblogs_code_toolbar"></div>
<pre>        &lt;dependency&gt;
            &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;
            &lt;artifactId&gt;spring-boot-starter-mail&lt;/artifactId&gt;
        &lt;/dependency&gt;
        
        &lt;dependency&gt;
            &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;
             &lt;artifactId&gt;spring-boot-starter-thymeleaf&lt;/artifactId&gt;
        &lt;/dependency&gt;</pre>
<div class="cnblogs_code_toolbar"></div></div>
<h2>2. application.property配置邮箱和thymelea模板</h2>
<p>我使用的是QQ邮箱，需要获得QQ邮箱的授权码。</p>
<div>关于QQ邮箱生成授权码：进入QQ邮箱 --&gt; 邮箱设置 --&gt; 账户 --&gt;&nbsp; POP3/IMAP/SMTP/Exchange/CardDAV/CalDAV服务 --&gt; 生成授权码 --&gt; 手机发送验证短信 --&gt;得到授权码</div>
<div class="cnblogs_code"><div class="cnblogs_code_toolbar"></div>
<pre>spring.mail.host=<span style="color: #000000;">smtp.qq.com
spring.mail.username</span>=<span style="color: #000000;">QQ邮箱
spring.mail.password</span>=<span style="color: #000000;">授权码
spring.mail.properties.mail.smtp.auth</span>=<span style="color: #0000ff;">true</span><span style="color: #000000;">
spring.mail.properties.mail.smtp.starttls.enable</span>=<span style="color: #0000ff;">true</span><span style="color: #000000;">  
spring.mail.properties.mail.smtp.starttls.required</span>=<span style="color: #0000ff;">true</span><span style="color: #000000;">  

#thymelea模板配置
spring.thymeleaf.prefix</span>=classpath:/templates/<span style="color: #000000;">
spring.thymeleaf.suffix</span>=<span style="color: #000000;">.html
spring.thymeleaf.mode</span>=<span style="color: #000000;">HTML
spring.thymeleaf.encoding</span>=UTF-8<span style="color: #000000;">
spring.thymeleaf.servlet.content</span>-type:text/<span style="color: #000000;">html
#热部署文件，页面不产生缓存，及时更新
spring.thymeleaf.cache</span>=<span style="color: #0000ff;">false</span><span style="color: #000000;">
spring.resources.chain.strategy.content.enabled</span>=<span style="color: #0000ff;">true</span><span style="color: #000000;">
spring.resources.chain.strategy.content.paths</span>=<span style="color: #008000;">/**</span></pre>
<div class="cnblogs_code_toolbar"></a></span></div></div>
<h2>3. 编写Service及其实现</h2>
<p>Service中有两个方法：</p>
<p>sendSimpleMail用于发送简单的文本邮件，是一个比较基础的案例。</p>
<p>sendHtmlMail用于发送HTML邮件，发送通知邮件用的就是这个方法。其实模板邮件也就是HTML邮件中的一个子类。</p>
<p>MailService：</p>
<div class="cnblogs_code">
<pre><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">interface</span><span style="color: #000000;"> MailService {
    </span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">void</span><span style="color: #000000;"> sendSimpleMail(String to, String subject, String  content);
    </span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">void</span><span style="color: #000000;"> sendHtmlMail(String to, String subject, String  content);
}</span></pre>
</div>
<p>MailServiceImpl：</p>
<div class="cnblogs_code"><div class="cnblogs_code_toolbar"></div>
<pre><span style="color: #000000;">@Component
</span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">class</span> MailServiceImpl <span style="color: #0000ff;">implements</span><span style="color: #000000;"> MailService {

    </span><span style="color: #0000ff;">private</span> <span style="color: #0000ff;">final</span> Logger logger =  LoggerFactory.getLogger(<span style="color: #0000ff;">this</span><span style="color: #000000;">.getClass());

    @Autowired
    </span><span style="color: #0000ff;">private</span><span style="color: #000000;"> JavaMailSender mailSender;

    @Value(</span>"${spring.mail.username}"<span style="color: #000000;">)
    </span><span style="color: #0000ff;">private</span><span style="color: #000000;"> String from;

    @Override
    </span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">void</span><span style="color: #000000;"> sendSimpleMail(String to, String subject, String  content) {
        SimpleMailMessage message </span>= <span style="color: #0000ff;">new</span><span style="color: #000000;"> SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);</span><span style="color: #008000;">//</span><span style="color: #008000;">邮件接收者</span>
        message.setSubject(subject);<span style="color: #008000;">//</span><span style="color: #008000;">邮件主题</span>
        message.setText(content);<span style="color: #008000;">//</span><span style="color: #008000;">邮件内容</span>
        <span style="color: #0000ff;">try</span><span style="color: #000000;"> {
            mailSender.send(message);
            logger.info(</span>"发送简单邮件成功！"<span style="color: #000000;">);
        } </span><span style="color: #0000ff;">catch</span><span style="color: #000000;"> (Exception e) {
            logger.error(</span>"发送简单邮件时发生异常！"<span style="color: #000000;">, e);
        }
    }

    @Override
    </span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">void</span><span style="color: #000000;"> sendHtmlMail(String to, String subject, String  content) {
        MimeMessage message </span>=<span style="color: #000000;"> mailSender.createMimeMessage();
        </span><span style="color: #0000ff;">try</span><span style="color: #000000;"> {
            </span><span style="color: #008000;">//</span><span style="color: #008000;">true表示需要创建一个multipart message</span>
            MimeMessageHelper helper = <span style="color: #0000ff;">new</span>  MimeMessageHelper(message, <span style="color: #0000ff;">true</span><span style="color: #000000;">);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, </span><span style="color: #0000ff;">true</span><span style="color: #000000;">);
            mailSender.send(message);
            logger.info(</span>"发送HTML邮件成功！"<span style="color: #000000;">);
        } </span><span style="color: #0000ff;">catch</span><span style="color: #000000;"> (MessagingException e) {
            logger.error(</span>"发送HTML邮件时发生异常！"<span style="color: #000000;">, e);
        }
    }
}</span></pre>
<div class="cnblogs_code_toolbar"></div></div>
<h2>4. 创建模板</h2>
<p>在resorces/templates下创建emailTemplate.html模板，与模板配置中的spring.thymeleaf.prefix=classpath:/templates/对应，不然会找不到模板。</p>
<p>关于Thymeleaf的使用这里简单介绍一下：</p>
<p>引入命名空间：&lt;html xmlns:th="http://www.thymeleaf.org"&gt; 。不同的约束文档中，可能会出现不同含义的相同标记名称，引入名称空间可以避免混淆和冲突。</p>
<p>访问数据：#｛user.name｝</p>
<p>访问变量：${today}&nbsp;&nbsp;</p>
<p>输出URL：&nbsp;&lt;a href="#" th:href="@{https://www.cnblogs.com}" &gt;博客园&lt;/a&gt;</p>
<p>更多详情的说明和规则请参见：<a href="https://www.thymeleaf.org/documentation.html" target="_blank">Thymeleaf官方文档</a></p>
<p>emailTemplate.html：</p>
<div class="cnblogs_code"><div class="cnblogs_code_toolbar"></div>
<pre><span style="color: #0000ff;">&lt;!</span><span style="color: #ff00ff;">DOCTYPE html</span><span style="color: #0000ff;">&gt;</span>
<span style="color: #0000ff;">&lt;</span><span style="color: #800000;">html </span><span style="color: #ff0000;">lang</span><span style="color: #0000ff;">="zh"</span><span style="color: #ff0000;"> xmlns:th</span><span style="color: #0000ff;">="http://www.thymeleaf.org"</span><span style="color: #0000ff;">&gt;</span>
    <span style="color: #0000ff;">&lt;</span><span style="color: #800000;">head</span><span style="color: #0000ff;">&gt;</span>
        <span style="color: #0000ff;">&lt;</span><span style="color: #800000;">meta </span><span style="color: #ff0000;">charset</span><span style="color: #0000ff;">="UTF-8"</span> <span style="color: #0000ff;">/&gt;</span>
        <span style="color: #0000ff;">&lt;</span><span style="color: #800000;">title</span><span style="color: #0000ff;">&gt;</span>欢迎您加入博客园<span style="color: #0000ff;">&lt;/</span><span style="color: #800000;">title</span><span style="color: #0000ff;">&gt;</span>
    <span style="color: #0000ff;">&lt;/</span><span style="color: #800000;">head</span><span style="color: #0000ff;">&gt;</span>
    <span style="color: #0000ff;">&lt;</span><span style="color: #800000;">body</span><span style="color: #0000ff;">&gt;</span>
        <span style="color: #0000ff;">&lt;</span><span style="color: #800000;">p</span><span style="color: #0000ff;">&gt;</span>您好，您在博客园的帐户激活成功，您的登录用户名是：<span style="color: #0000ff;">&lt;</span><span style="color: #800000;">span  </span><span style="color: #ff0000;">th:text</span><span style="color: #0000ff;">="${username}"</span><span style="color: #0000ff;">&gt;&lt;/</span><span style="color: #800000;">span</span><span style="color: #0000ff;">&gt;</span>。<span style="color: #0000ff;">&lt;/</span><span style="color: #800000;">p</span><span style="color: #0000ff;">&gt;</span>
        <span style="color: #0000ff;">&lt;</span><span style="color: #800000;">p</span><span style="color: #0000ff;">&gt;</span>--<span style="color: #0000ff;">&lt;/</span><span style="color: #800000;">p</span><span style="color: #0000ff;">&gt;</span>
        <span style="color: #0000ff;">&lt;</span><span style="color: #800000;">div</span><span style="color: #0000ff;">&gt;</span><span style="color: #000000;">博客园(
            </span><span style="color: #0000ff;">&lt;</span><span style="color: #800000;">a </span><span style="color: #ff0000;">th:href</span><span style="color: #0000ff;">="@{https://www.cnblogs.com  }"</span><span style="color: #0000ff;">&gt;</span>www.cnblogs.com<span style="color: #0000ff;">&lt;/</span><span style="color: #800000;">a</span><span style="color: #0000ff;">&gt;</span><span style="color: #000000;">
            ) - 开发者的网上家园</span><span style="color: #0000ff;">&lt;/</span><span style="color: #800000;">div</span><span style="color: #0000ff;">&gt;</span>
        <span style="color: #0000ff;">&lt;</span><span style="color: #800000;">p</span><span style="color: #0000ff;">&gt;</span>代码改变世界！<span style="color: #0000ff;">&lt;/</span><span style="color: #800000;">p</span><span style="color: #0000ff;">&gt;</span>
    <span style="color: #0000ff;">&lt;/</span><span style="color: #800000;">body</span><span style="color: #0000ff;">&gt;</span>
<span style="color: #0000ff;">&lt;/</span><span style="color: #800000;">html</span><span style="color: #0000ff;">&gt;</span></pre>
<div class="cnblogs_code_toolbar"></div></div>
<h2>5. JUnit单元测试</h2>
<p>使用Junit进行单元测试，pom.xml中已经默认配置好了，需要编写测试类和测试方法。测试类以<code>xxxTest.java命名，测试方法上面加<code>@Test注解就可以使用了。具体代码如下：</code></code></p>
<div class="cnblogs_code"><div class="cnblogs_code_toolbar"></div>
<pre>@RunWith(SpringRunner.<span style="color: #0000ff;">class</span><span style="color: #000000;">)
@SpringBootTest
</span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">class</span><span style="color: #000000;"> EmailTest {

    @Autowired
    </span><span style="color: #0000ff;">private</span><span style="color: #000000;"> MailService mailService;

    @Autowired
    </span><span style="color: #0000ff;">private</span><span style="color: #000000;"> TemplateEngine templateEngine;

    @Test
    </span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">void</span> testSendSimpleMail() <span style="color: #0000ff;">throws</span><span style="color: #000000;"> Exception {
        mailService.sendSimpleMail(</span>"xxx@qq.com", "测试发送简单文本邮件", "测试发送简单文本邮件"<span style="color: #000000;">);
    }

    @Test
    </span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">void</span><span style="color: #000000;"> testSendTemplateMail() {
        Context context </span>= <span style="color: #0000ff;">new</span><span style="color: #000000;"> Context();
        context.setVariable(</span>"username", "shangguanhao"<span style="color: #000000;">);
        String emailContent </span>= templateEngine.process("emailTemplate"<span style="color: #000000;">, context);
        mailService.sendHtmlMail(</span>"xxx@qq.com", "欢迎您加入博客园"<span style="color: #000000;">, emailContent);
    }

}</span></pre>
<div class="cnblogs_code_toolbar"></div></div>
<p>进行Junit测试，就可以发送一个简答的文本邮件和一个HTML的模板邮件，几乎和博客园的一模一样（如下图所示）：</p>
<p><img src="https://img2018.cnblogs.com/blog/1538609/201812/1538609-20181212134759117-738859529.png" alt=""></p>
<div>完整代码：<a href="https://github.com/shangguanhao/spring-boot-example/tree/master/mail">https://github.com/shangguanhao/spring-boot-example/tree/master/mail</a></div>
<div>参考：<a href="https://www.cnblogs.com/ityouknow/p/6823356.html" target="_blank">springboot（十）：邮件服务</a>&nbsp;</div></div><div id="MySignature" style="display: block;">
