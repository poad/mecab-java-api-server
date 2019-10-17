package com.github.poad.examples.mecab.morphogical;

import org.chasen.mecab.Node;
import org.springframework.stereotype.Component;

@Component
public interface MeCab {

    Node parseToNode(String text);
}
