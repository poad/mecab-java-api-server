ARG LLVM_VERSION=15
ARG DIST_VERSION_CODE_NAME=jammy

FROM buildpack-deps:${DIST_VERSION_CODE_NAME}-curl AS download

RUN apt-get update -qq \
 && apt-get upgrade -qqy \
 && apt-get install --no-install-recommends -qqy locales unzip ca-certificates gnupg lsb-release \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /tmp

RUN curl -sSLo mecab-0.996.tar.gz "https://drive.google.com/uc?export=download&id=0B4y35FiV1wh7cENtOXlicTFaRUE" \
    && curl -sSLo mecab-ipadic-2.7.0-20070801.tar.gz "https://drive.google.com/uc?export=download&id=0B4y35FiV1wh7MWVlSDBCSXZMTXM" \
    && curl -SLo mecab-java-0.996.tar.gz "https://drive.google.com/uc?export=download&id=0B4y35FiV1wh7NHo1bEJxd0RnSzg"

FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu 

ARG LLVM_VERSION
ARG DIST_VERSION_CODE_NAME

WORKDIR /tmp

ENV LANG ja_JP.UTF-8
ENV LC_CTYPE ja_JP.UTF-8

USER root

RUN apt-get update -qq \
 && apt-get upgrade -qqy \
 && apt-get install --no-install-recommends -qqy locales curl unzip ca-certificates gnupg lsb-release \
 && DIST_VERSION_CODE_NAME=$(lsb_release -cs) \
 && echo "deb http://apt.llvm.org/${DIST_VERSION_CODE_NAME}/ llvm-toolchain-${DIST_VERSION_CODE_NAME} main" >> /etc/apt/sources.list.d/llvm-toolchain.list \
 && echo "deb http://apt.llvm.org/${DIST_VERSION_CODE_NAME}/ llvm-toolchain-${DIST_VERSION_CODE_NAME}-${LLVM_VERSION} main" >> /etc/apt/sources.list.d/llvm-toolchain.list \
 && curl -SL https://apt.llvm.org/llvm-snapshot.gpg.key | apt-key add - \
 && apt-get update -qq \
 && apt-get install -qqy --no-install-recommends "clang-${LLVM_VERSION}" "lld-${LLVM_VERSION}" make \
 && cp -p /etc/locale.gen /etc/locale.gen.bak \
 && sed -e "s/# ja_JP.UTF-8/ja_JP.UTF-8/g" /etc/locale.gen.bak | sed -e "s/# en_US.UTF-8/en_US.UTF-8/g" > /etc/locale.gen \
 && locale-gen \
 && update-locale LANG=ja_JP.UTF-8 \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

COPY --from=download /tmp/mecab-0.996.tar.gz /tmp/mecab-0.996.tar.gz
COPY --from=download /tmp/mecab-ipadic-2.7.0-20070801.tar.gz /tmp/mecab-ipadic-2.7.0-20070801.tar.gz
COPY --from=download /tmp/mecab-java-0.996.tar.gz /tmp/mecab-java-0.996.tar.gz

RUN tar xf mecab-0.996.tar.gz \
 && cd mecab-0.996 \
 && CC=clang++-${LLVM_VERSION} CXX=clang++-${LLVM_VERSION} ./configure --prefix=/usr --enable-utf8-only \
 && make \
 && make check \
 && make install

RUN tar xf mecab-ipadic-2.7.0-20070801.tar.gz \
 && cd mecab-ipadic-2.7.0-20070801 \
 && CC=clang++-${LLVM_VERSION} CXX=clang++-${LLVM_VERSION} ./configure --with-charset=utf8 \
 && make \
 && make install \
 && cd / \
 && ln -s /usr/bin/clang++-${LLVM_VERSION} /usr/bin/c++

RUN tar xf mecab-java-0.996.tar.gz \
 && cd mecab-java-0.996 \
 && mv Makefile Makefile.bak \
 && cat Makefile.bak | sed -e "s/java-6-openjdk/$(basename ${JAVA_HOME})/g" | sed -e "s/\$(JAVAC)/\$(JAVAC) -encoding UTF-8/g" > Makefile \
 && make \
 && cp -p libMeCab.so /usr/lib \
 && mkdir -p /work \
 && cp -p libMeCab.so /work \
 && cp -p MeCab.jar /work

RUN rm -rf * \
 && apt-get update -qq \
 && apt-get autoremove --purge -y make clang-${LLVM_VERSION} \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

USER java

CMD [ "mecab" ]
