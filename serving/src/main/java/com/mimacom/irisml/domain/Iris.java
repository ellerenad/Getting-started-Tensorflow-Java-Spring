package com.mimacom.irisml.domain;

public class Iris {

    private float petalLength;
    private float petalWidth;
    private float sepalLength;
    private float sepalWidth;

    public Iris() {
    }

    public Iris(float petalLength, float petalWidth, float sepalLength, float sepalWidth) {
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
    }

    public float getPetalLength() {
        return petalLength;
    }

    public void setPetalLength(float petalLength) {
        this.petalLength = petalLength;
    }

    public float getPetalWidth() {
        return petalWidth;
    }

    public void setPetalWidth(float petalWidth) {
        this.petalWidth = petalWidth;
    }

    public float getSepalLength() {
        return sepalLength;
    }

    public void setSepalLength(float sepalLength) {
        this.sepalLength = sepalLength;
    }

    public float getSepalWidth() {
        return sepalWidth;
    }

    public void setSepalWidth(float sepalWidth) {
        this.sepalWidth = sepalWidth;
    }


}
