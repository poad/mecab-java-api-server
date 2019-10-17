package com.github.poad.examples.mecab.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.chasen.mecab.Lattice;
import org.chasen.mecab.Model;
import org.chasen.mecab.Node;
import org.chasen.mecab.Tagger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MeCabService implements MorphologicalAnalysisService {

    private static final Logger logger = LogManager.getLogger(MeCabService.class);

    @Override
    public List<AnalyzeResult> parseToNode(String text) {
        List<AnalyzeResult> results = new ArrayList<>();
        Model model = new Model();
        Tagger tagger = model.createTagger();

        Lattice lattice = model.createLattice();

        lattice.set_sentence(text);
        if (tagger.parse(lattice)) {
            logger.trace(lattice.toString());
            for (Node node = lattice.bos_node(); node != null; node = node.getNext()) {
                String[] attributes = node.getFeature().split(",");
                List<String> pos = IntStream.range(0, 6)
                        .mapToObj(i -> attributes[i])
                        .filter(attr -> !attr.equals("*"))
                        .collect(Collectors.toList());
                List<String> word = IntStream.range(6, attributes.length)
                        .mapToObj(i -> attributes[i])
                        .collect(Collectors.toList());
                results.add(new AnalyzeResult(
                        node.getSurface(),
                        new AnalyzeResult.Attributes(pos, word))
                );
                logger.trace(node.getSurface() + "\t" + node.getFeature());
            }
        }
        return results;
    }
}
