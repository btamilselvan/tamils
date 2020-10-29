From openjdk:8
RUN curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
RUN unzip awscliv2.zip
RUN ./aws/install
RUN wget https://mirrors.ocf.berkeley.edu/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
RUN rm apache-maven-3.6.3-bin.tar.gz awscliv2.zip
ENV PATH="/apache-maven-3.6.3/bin:${PATH}"