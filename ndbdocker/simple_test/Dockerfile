From centos

Workdir /app/bundle

#RUN dnf --disablerepo '*' --enablerepo extras swap centos-linux-repos centos-stream-repos -y &&\
#	dnf distro-sync -y &&\ 
#	yum install java-11-openjdk-devel -y

USER root

COPY run.sh /run.sh

COPY simple.jar simple.jar

RUN chmod 644 /run.sh

ENTRYPOINT ["/run.sh"]