package com.github.poad.examples.mecab.morphogical;

import org.chasen.mecab.Node;
import org.springframework.stereotype.Component;

@Component
public interface MeCab {

    String parse(String text);

    Node parseToNode(String text);

    String modelParse(String text);
}
