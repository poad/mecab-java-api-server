package com.github.poad.examples.mecab.service;

import com.github.poad.examples.mecab.morphogical.MeCab;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.chasen.mecab.Model;
import org.chasen.mecab.Node;
import org.chasen.mecab.Tagger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeCabService implements MorphologicalAnalysisService {

    private static final Logger logger = LogManager.getLogger(MeCabService.class);

    private final MeCab meCab;
    public MeCabService(@Autowired MeCab meCab) {
        this.meCab = meCab;
    }

    public String parse(String text) {
        String parsed = meCab.parse(text);
        logger.trace(parsed);
        return parsed;
    }

    public void parseToNode(String text) {
        Node node = meCab.parseToNode(text);
        for (;node != null; node = node.getNext()) {
            logger.trace(node.getSurface() + "\t" + node.getFeature());
        }
        meCab.modelParse(text);
    }
}
