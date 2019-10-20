package com.github.poad.examples.mecab.morphogical;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.chasen.mecab.Node;
import org.chasen.mecab.Tagger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class MeCabImpl implements MeCab {
    private static final Logger logger = LogManager.getLogger(MeCab.class);

    static {
        try {
            System.loadLibrary("MeCab");
        } catch (UnsatisfiedLinkError e) {
            logger.fatal("Cannot load the example native code.\nMake sure your LD_LIBRARY_PATH contains \'.\'\n" + e);
            System.exit(1);
        }
    }

    @Override
    public Node parseToNode(String text) {
        Tagger tagger = new Tagger();
        return tagger.parseToNode(text);
    }
}
