package com.mimacom.irisml.service.impl;

import com.mimacom.irisml.domain.Iris;
import com.mimacom.irisml.domain.IrisType;
import com.mimacom.irisml.service.IrisClassifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.util.HashMap;
import java.util.Map;

@Service
public class IrisTensorflowClassifierService implements IrisClassifierService {

    private final Session modelBundleSession;
    private final IrisType[] irisTypes;

    private final static String FEED_OPERATION = "dnn/input_from_feature_columns/input_layer/concat";
    private final static String FETCH_OPERATION_PROBABILITIES = "dnn/head/predictions/probabilities";
    private final static String FETCH_OPERATION_CLASS_ID = "dnn/head/predictions/class_ids";

    @Autowired
    public IrisTensorflowClassifierService(@Value("${irisml.savedModel.path}") String savedModelPath,
                                           @Value("${irisml.savedModel.tags}") String savedModelTags) {
        this.modelBundleSession = SavedModelBundle.load(savedModelPath, savedModelTags).session();
        this.irisTypes = IrisType.values();
    }

    @Override
    public IrisType classify(Iris iris) {
        int category = this.fetchClassFromModel(iris);
        return this.irisTypes[category];
    }

    @Override
    public Map<IrisType, Float> classificationProbabilities(Iris iris){
        Map<IrisType, Float> results = new HashMap<>(irisTypes.length);
        float[][] vector = this.fetchProbabilitiesFromModel(iris);
        int resultsCount = vector[0].length;
        for (int i=0; i < resultsCount; i++){
            results.put(irisTypes[i],vector[0][i]);
        }
        return results;
    }

    private float[][] fetchProbabilitiesFromModel(Iris iris) {
        Tensor inputTensor = IrisTensorflowClassifierService.createInputTensor(iris);

        Tensor result = this.modelBundleSession.runner()
                .feed(IrisTensorflowClassifierService.FEED_OPERATION, inputTensor)
                .fetch(IrisTensorflowClassifierService.FETCH_OPERATION_PROBABILITIES)
                .run().get(0);

        float[][] buffer = new float[1][3];
        result.copyTo(buffer);
        return buffer;

    }

    private int fetchClassFromModel(Iris iris){
        Tensor inputTensor = IrisTensorflowClassifierService.createInputTensor(iris);

        Tensor result = this.modelBundleSession.runner()
                .feed(IrisTensorflowClassifierService.FEED_OPERATION, inputTensor)
                .fetch(IrisTensorflowClassifierService.FETCH_OPERATION_CLASS_ID)
                .run().get(0);

        long[] buffer = new long[1];
        result.copyTo(buffer);

        return (int)buffer[0];
    }

    private static Tensor createInputTensor(Iris iris){
        // order of the data on the input: PetalLength, PetalWidth, SepalLength, SepalWidth
        // (taken from the saved_model, node dnn/input_from_feature_columns/input_layer/concat)
        float[] input = {iris.getPetalLength(), iris.getPetalWidth(), iris.getSepalLength(), iris.getSepalWidth()};
        float[][] data = new float[1][4];
        data[0] = input;
        return Tensor.create(data);
    }
}
