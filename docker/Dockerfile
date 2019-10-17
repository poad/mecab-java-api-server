FROM buildpack-deps:buster-curl

WORKDIR /tmp

RUN echo "deb http://apt.llvm.org/buster/ llvm-toolchain-buster main" >> /etc/apt/sources.list.d/llvm-toolchain.list \
 && echo "deb http://apt.llvm.org/buster/ llvm-toolchain-buster-8 main" >> /etc/apt/sources.list.d/llvm-toolchain.list \
 && echo "deb http://apt.llvm.org/buster/ llvm-toolchain-buster-9 main" >> /etc/apt/sources.list.d/llvm-toolchain.list \
 && wget https://apt.llvm.org/llvm-snapshot.gpg.key \
 && apt-key add llvm-snapshot.gpg.key \
 && apt-get update -qq \
 && apt-get install -qqy --no-install-recommends clang-9 lld-9 make \
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
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/* /tmp/*

CMD [ "mecab" ]
