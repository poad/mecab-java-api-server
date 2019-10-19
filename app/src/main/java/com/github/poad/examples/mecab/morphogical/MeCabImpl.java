package com.github.poad.examples.mecab.morphogical;

import com.github.poad.examples.mecab.service.MeCabService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.chasen.mecab.Lattice;
import org.chasen.mecab.Model;
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
    public String parse(String text) {
        Tagger tagger = new Tagger();
        return tagger.parse(text);
    }

    @Override
    public Node parseToNode(String text) {
        Tagger tagger = new Tagger();
        return tagger.parseToNode(text);
    }

    @Override
    public String modelParse(String text) {
        Tagger tagger = new Tagger();
        Node node = tagger.parseToNode(text);

        Model model = new Model();
        Tagger tagger2 = model.createTagger();
        Lattice lattice = model.createLattice();

        lattice.set_sentence(text);
        if (tagger2.parse(lattice)) {
            logger.trace(lattice.toString());
            for (node = lattice.bos_node(); node != null; node = node.getNext()) {
                logger.trace(node.getSurface() + "\t" + node.getFeature());
            }
            logger.trace("EOS\n");
        }

        lattice.add_request_type(org.chasen.mecab.MeCab.MECAB_NBEST);
        lattice.set_sentence(text);
        tagger2.parse(lattice);
        for (int i = 0; i < 10; ++i) {
            if (lattice.next()) {
                logger.trace("nbest:" + i + "\n" +
                        lattice.toString());
            }
        }

        return tagger.parse(text);
    }
}
