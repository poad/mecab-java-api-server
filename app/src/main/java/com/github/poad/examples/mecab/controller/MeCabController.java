package com.github.poad.examples.mecab.controller;

import com.github.poad.examples.mecab.model.AnalyzeRequest;
import com.github.poad.examples.mecab.model.AnalyzeResponse;
import com.github.poad.examples.mecab.service.MeCabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/mecab", produces = {MediaType.APPLICATION_JSON_VALUE})
public class MeCabController {
    private final MeCabService service;

    public MeCabController(@Autowired MeCabService service) {
        this.service = service;
    }

    @GetMapping
    public AnalyzeResponse analyze(@RequestParam(name = "text") String text) {
        return analyzeInternal(text);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public AnalyzeResponse analyze(@Valid @RequestBody AnalyzeRequest request) {
        return analyzeInternal(request.getText());
    }

    private AnalyzeResponse analyzeInternal(String text) {
        return new AnalyzeResponse(service.parseToNode(text).stream()
                .filter(item -> !item.getKeyword().isBlank())
                .map(item -> new AnalyzeResponse.Result(
                        item.getKeyword(),
                        new AnalyzeResponse.Result.Attributes(
                                item.getAttributes().getPos(),
                                item.getAttributes().getWord()
                        )
                ))
                .collect(Collectors.toList()));
    }
}
