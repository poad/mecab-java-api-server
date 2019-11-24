FROM poad/docker-openjdk11:zulu-buster

WORKDIR /tmp

ENV LANG ja_JP.UTF-8
ENV LC_CTYPE ja_JP.UTF-8

RUN apt-get update -qq \
 && apt-get upgrade -qqy \
 && apt-get install --no-install-recommends -qqy locales curl wget ca-certificates gnupg \
 && echo "deb http://apt.llvm.org/buster/ llvm-toolchain-buster main" >> /etc/apt/sources.list.d/llvm-toolchain.list \
 && echo "deb http://apt.llvm.org/buster/ llvm-toolchain-buster-8 main" >> /etc/apt/sources.list.d/llvm-toolchain.list \
 && echo "deb http://apt.llvm.org/buster/ llvm-toolchain-buster-9 main" >> /etc/apt/sources.list.d/llvm-toolchain.list \
 && wget https://apt.llvm.org/llvm-snapshot.gpg.key \
 && apt-key add llvm-snapshot.gpg.key \
 && apt-get update -qq \
 && apt-get install -qqy --no-install-recommends clang-9 lld-9 make \
 && cp -p /etc/locale.gen /etc/locale.gen.bak \
 && sed -e "s/# ja_JP.UTF-8/ja_JP.UTF-8/g" /etc/locale.gen.bak | sed -e "s/# en_US.UTF-8/en_US.UTF-8/g" > /etc/locale.gen \
 && locale-gen \
 && update-locale LANG=ja_JP.UTF-8 \
 && wget "https://drive.google.com/uc?export=download&id=0B4y35FiV1wh7cENtOXlicTFaRUE" -O mecab-0.996.tar.gz \
 && tar xf mecab-0.996.tar.gz \
 && cd mecab-0.996 \
 && CC=clang++-9 CXX=clang++-9 ./configure --prefix=/usr --enable-utf8-only \
 && make \
 && make check \
 && make install \
 && cd .. \
 && wget "https://drive.google.com/uc?export=download&id=0B4y35FiV1wh7MWVlSDBCSXZMTXM" -O mecab-ipadic-2.7.0-20070801.tar.gz \
 && tar xf mecab-ipadic-2.7.0-20070801.tar.gz \
 && cd mecab-ipadic-2.7.0-20070801 \
 && CC=clang++-9 CXX=clang++-9 ./configure --with-charset=utf8 \
 && make \
 && make install \
 && cd .. \
 && wget "https://drive.google.com/uc?export=download&id=0B4y35FiV1wh7X2pESGlLREpxdXM" -O mecab-jumandic-7.0-20130310.tar.gz \
 && tar xf mecab-jumandic-7.0-20130310.tar.gz \
 && cd mecab-jumandic-7.0-20130310 \
 && CC=clang++-9 CXX=clang++-9 ./configure --with-charset=utf8 \
 && make \
 && make install \
 && cd / \
 && ln -s /usr/bin/clang++-9 /usr/bin/c++ \
 && wget "https://drive.google.com/uc?export=download&id=0B4y35FiV1wh7NHo1bEJxd0RnSzg" -O mecab-java-0.996.tar.gz \
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

CMD [ "mecab" ]