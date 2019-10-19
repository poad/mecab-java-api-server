package com.github.poad.examples.mecab.controller;

import com.github.poad.examples.mecab.service.MeCabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
public class MeCabController {
    private final MeCabService service;

    public MeCabController(@Autowired MeCabService service) {
        this.service = service;
    }

    @GetMapping
    public String parse(@RequestParam(name = "text", required = true) String text) {
        service.parseToNode(text);

        return service.parse(text);
    }
}
