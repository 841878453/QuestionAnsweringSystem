package org.apdplat.qa.questionSimilarityAnalysis;

public class SimilarityWeight {
    private double cosSimilarity = 0.7;
    private double jaccardSimilarity = 0.3;

    private double similarity = 0.5;

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

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public double getJaccardSimilarity() {
        return jaccardSimilarity;
    }

    public void setJaccardSimilarity(double jaccardSimilarity) {
        this.jaccardSimilarity = jaccardSimilarity;
    }
}
