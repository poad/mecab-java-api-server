package com.github.poad.examples.mecab.service;

import java.util.List;

public interface MorphologicalAnalysisService {

    List<AnalyzeResult> parseToNode(String text);

    class AnalyzeResult {
        private final String keyword;
        private final Attributes attributes;

        public AnalyzeResult(String keyword, Attributes attributes) {
            this.keyword = keyword;
            this.attributes = attributes;
        }

        public String getKeyword() {
            return keyword;
        }

        public Attributes getAttributes() {
            return attributes;
        }

        public static class Attributes {
            private final List<String> pos;
            private final List<String> word;

            Attributes(List<String> pos, List<String> word) {
                this.pos = pos;
                this.word = word;
            }

            public List<String> getPos() {
                return pos;
            }

            public List<String> getWord() {
                return word;
            }
        }
    }
}
