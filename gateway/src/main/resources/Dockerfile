FROM java:8
MAINTAINER "songhs"<songhs@bytter.com>
ADD gateway-1.0.0.jar gateway.jar
RUN mkdir -p /usr/local/zuul
VOLUME /usr/local/zuul
EXPOSE 7000
#COPY ./agent/ /usr/local/zuul/agent/
CMD java -javaagent:/usr/local/zuul/agent/skywalking-agent.jar -jar gateway.jar