# 基于springboot开发的服务架构
<p>本项目是一个基于 Spring Boot、fastDFS等框架构建的服务项目</p>

# 应用架构
<p>3 个服务</p>
<ul>
<li>sx-context-data-import 话术数据库转换</li>
<li>sx-data-import 话术数据库转换</li>
<li>sx-context-web 话术服务接口</li>
</ul>
<div class="highlight highlight-source-shell">
<pre>
├── pom.xml
├── sx-context-data-import  (话术数据库转换)
├── sx-context-service  (话术数据库层)
├── sx-context-web  (话术服务接口)
├── README.md
</pre>
</div>