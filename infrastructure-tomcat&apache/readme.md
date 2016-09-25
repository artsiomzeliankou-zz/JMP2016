1. Download java 8.* and install it. Installation directory = ${JAVA_HOME}
2. Download apache server 2.4 and install it. Installation directory = ${APACHE_HOME}
3. Download apache toncat 8.* and install it. Installation directory = ${CATALINA_HOME}
4. Download mod_jk connector and copy mod_jk.so to ${APACHE_HOME}/modules folder
5. Download test war file. Download directory = ${WAR_DIR}
6. Replace configuretion files with attached. Replace all ${*_HOME} variables in config files by real values.
7. Run tomcat
8. To deploy with tomcat manager application (text/script mode) enter following command in browser:
	http://localhost:8487/manager/text/deploy?path=/todo&war=file:${WAR_DIR}/todo.war

9. Create folder "todo" under the ${APACHE_HOME}/htdocs.
	Move ${CATALINA_HOME}/webapps/todo/assets  folder into ${APACHE_HOME}/htdocs/todo/

10. Start apache server and test app by browsing URL:
	http://localhost/todo/main