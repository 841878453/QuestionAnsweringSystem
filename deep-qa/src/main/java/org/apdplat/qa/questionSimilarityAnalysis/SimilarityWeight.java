package org.apdplat.qa.questionSimilarityAnalysis;

public class SimilarityWeight {
    private double cosSimilarity = 0.5;
    private double jaccardSimilarity = 0.4;

    private double  euclideanSimilarity= 0.3;

    public SimilarityWeight() {
    }

    public SimilarityWeight(double cosSimilarity) {
        this.cosSimilarity = cosSimilarity;
    }

    public double getCosSimilarity() {
        return cosSimilarity;
    }

    public void setCosSimilarity(double cosSimilarity) {
        this.cosSimilarity = cosSimilarity;
    }

    public double getEuclideanSimilarity() {
        return euclideanSimilarity;
    }

    public void setEuclideanSimilarity(double euclideanSimilarity) {
        this.euclideanSimilarity = euclideanSimilarity;
    }

    public double getJaccardSimilarity() {
        return jaccardSimilarity;
    }

    public void setJaccardSimilarity(double jaccardSimilarity) {
        this.jaccardSimilarity = jaccardSimilarity;
    }
}
