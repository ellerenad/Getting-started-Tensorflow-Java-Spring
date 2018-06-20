package com.mimacom.irisml.web.controller;

import com.mimacom.irisml.domain.Iris;
import com.mimacom.irisml.domain.IrisType;
import com.mimacom.irisml.service.IrisClassifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class IrisController {

    @Autowired
    IrisClassifierService irisClassifierService;

    @GetMapping(value = "/iris/classify/class")
    public IrisType classify(Iris iris) {
        return irisClassifierService.classify(iris);
    }

    @GetMapping(value = "/iris/classify/probabilities")
    public Map<IrisType, Float> classificationProbabilities(Iris iris) {
        return irisClassifierService.classificationProbabilities(iris);
    }

}
