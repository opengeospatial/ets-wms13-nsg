FROM tomcat:10.1-jre17

RUN apt update && apt install -y unzip

# add TEAM Engine webapp
ADD maven/dependency/teamengine-web-*.war /root/
RUN cd /root/ && unzip -q teamengine-web-*.war -d /usr/local/tomcat/webapps/teamengine

# add TEAM Engine common libs
ADD maven/dependency/teamengine-web-*-common-libs.zip /root/
RUN cd /root/ && unzip -q teamengine-web-*-common-libs.zip -d /usr/local/tomcat/lib

# add TEAM Engine console
ADD maven/dependency/teamengine-console-*-base.zip /root/
RUN cd /root/ && unzip -q teamengine-console-*-base.zip -d /root/te_base

# set TE_BASE
ENV JAVA_OPTS="-Xms1024m -Xmx2048m -DTE_BASE=/root/te_base"

# add ETS
ADD maven/ets-wms13-nsg-*-ctl.zip /root/
RUN cd /root/ && unzip -q ets-wms13-nsg-*-ctl.zip -d /root/te_base/scripts
ADD maven/ets-wms13-nsg-*-deps.zip /root/
RUN cd /root/ && unzip -q -o ets-wms13-nsg-*-deps.zip -d /usr/local/tomcat/webapps/teamengine/WEB-INF/lib

RUN rm -R /root/te_base/scripts/note

# run tomcat
CMD ["catalina.sh", "run"]
