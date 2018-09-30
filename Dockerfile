FROM centos

WORKDIR /webserver/templateMgr
COPY ./sx-context-web/target/sx-context-web.jar app.jar
COPY ./sx-context-web/dir/sqlcipher-master.zip sqlcipher-master.zip

ENV LANG 'en_US.UTF-8'
ENV LC_ALL 'en_US.UTF-8'
ENV TZ 'Asia/Shanghai'

RUN mkdir -p /root/app/shell
COPY ./sx-context-web/dir/sa.bin /root/app/shell/sa.bin
RUN chmod 777 /root/app/shell/*
RUN  yum install -y gcc gcc-c++ glibc make autoconf openssl openssl-devel libtool tcl unzip json-c java-1.8.0-openjdk.x86_64
RUN unzip sqlcipher-master.zip

WORKDIR /webserver/templateMgr/sqlcipher-master
RUN  ./configure --enable-tempstore=yes CFLAGS="-DSQLITE_HAS_CODEC" LDFLAGS="-lcrypto" && make && make install
WORKDIR /webserver/templateMgr
RUN  ln -s /usr/lib64/libjson-c.so.2 /usr/local/lib/libjson-c.so.2 && printf "/usr/local/lib" > /etc/ld.so.conf && /sbin/ldconfig -v
RUN printf "nameserver 8.8.8.8\nnameserver 8.8.4.4\nnameserver 114.114.114.114\n" > /etc/resolv.conf \
    && ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone