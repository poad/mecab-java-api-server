FROM poad/docker-openjdk11:zulu-buster

WORKDIR /tmp

ENV LANG ja_JP.UTF-8
ENV LC_CTYPE ja_JP.UTF-8

USER root

COPY docker/assets/mecab-0.996.tar.gz /tmp/mecab-0.996.tar.gz
COPY docker/assets/mecab-ipadic-2.7.0-20070801.tar.gz /tmp/mecab-ipadic-2.7.0-20070801.tar.gz
COPY docker/assets/mecab-jumandic-7.0-20130310.tar.gz /tmp/mecab-jumandic-7.0-20130310.tar.gz
COPY docker/assets/unidic-mecab-2.1.2_src.zip /tmp/unidic-mecab-2.1.2_src.zip
COPY docker/assets/mecab-java-0.996.tar.gz /tmp/mecab-java-0.996.tar.gz

RUN apt-get update -qq \
 && apt-get upgrade -qqy \
 && apt-get install --no-install-recommends -qqy locales curl unzip ca-certificates gnupg \
 && echo "deb http://apt.llvm.org/buster/ llvm-toolchain-buster main" >> /etc/apt/sources.list.d/llvm-toolchain.list \
 && echo "deb http://apt.llvm.org/buster/ llvm-toolchain-buster-8 main" >> /etc/apt/sources.list.d/llvm-toolchain.list \
 && echo "deb http://apt.llvm.org/buster/ llvm-toolchain-buster-9 main" >> /etc/apt/sources.list.d/llvm-toolchain.list \
 && curl https://apt.llvm.org/llvm-snapshot.gpg.key | apt-key add - \
 && apt-get update -qq \
 && apt-get install -qqy --no-install-recommends clang-9 lld-9 make \
 && cp -p /etc/locale.gen /etc/locale.gen.bak \
 && sed -e "s/# ja_JP.UTF-8/ja_JP.UTF-8/g" /etc/locale.gen.bak | sed -e "s/# en_US.UTF-8/en_US.UTF-8/g" > /etc/locale.gen \
 && locale-gen \
 && update-locale LANG=ja_JP.UTF-8 \
 && tar xf mecab-0.996.tar.gz \
 && cd mecab-0.996 \
 && CC=clang++-9 CXX=clang++-9 ./configure --prefix=/usr --enable-utf8-only \
 && make \
 && make check \
 && make install \
 && cd .. \
 && tar xf mecab-ipadic-2.7.0-20070801.tar.gz \
 && cd mecab-ipadic-2.7.0-20070801 \
 && CC=clang++-9 CXX=clang++-9 ./configure --with-charset=utf8 \
 && make \
 && make install \
 && cd .. \
 && tar xf mecab-jumandic-7.0-20130310.tar.gz \
 && cd mecab-jumandic-7.0-20130310 \
 && CC=clang++-9 CXX=clang++-9 ./configure --with-charset=utf8 \
 && make \
 && make install \
 && cd .. \
 && unzip unidic-mecab-2.1.2_src.zip \
 && cd unidic-mecab-2.1.2_src \
 && ./configure \
 && make \
 && make install \
 && cd .. \
 && ln -s /usr/bin/clang++-9 /usr/bin/c++ \
 && tar xf mecab-java-0.996.tar.gz \
 && cd mecab-java-0.996 \
 && mv Makefile Makefile.bak \
 && cat Makefile.bak | sed -e "s/java-6-openjdk/$(basename ${JAVA_HOME})/g" | sed -e "s/\$(JAVAC)/\$(JAVAC) -encoding UTF-8/g" > Makefile \
 && make \
 && cp -p libMeCab.so /usr/lib \
 && mkdir -p /work \
 && cp -p libMeCab.so /work \
 && cp -p MeCab.jar /work \
 && cd /tmp \
 && rm -rf * \
 && apt-get autoremove --purge -y make clang-9 \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

USER java

CMD [ "mecab" ]
