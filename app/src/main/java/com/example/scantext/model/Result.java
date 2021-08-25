
package com.example.scantext.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("partOfSpeech")
    @Expose
    private Object partOfSpeech;
    @SerializedName("synonyms")
    @Expose
    private List<String> synonyms = null;
    @SerializedName("similarTo")
    @Expose
    private List<String> similarTo = null;
    @SerializedName("examples")
    @Expose
    private List<String> examples = null;
    @SerializedName("derivation")
    @Expose
    private List<String> derivation = null;
    @SerializedName("typeOf")
    @Expose
    private List<String> typeOf = null;
    @SerializedName("hasTypes")
    @Expose
    private List<String> hasTypes = null;
    @SerializedName("also")
    @Expose
    private List<String> also = null;
    @SerializedName("attribute")
    @Expose
    private List<String> attribute = null;
    @SerializedName("antonyms")
    @Expose
    private List<String> antonyms = null;
    @SerializedName("inCategory")
    @Expose
    private List<String> inCategory = null;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Object getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(Object partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getSimilarTo() {
        return similarTo;
    }

    public void setSimilarTo(List<String> similarTo) {
        this.similarTo = similarTo;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public List<String> getDerivation() {
        return derivation;
    }

    public void setDerivation(List<String> derivation) {
        this.derivation = derivation;
    }

    public List<String> getTypeOf() {
        return typeOf;
    }

    public void setTypeOf(List<String> typeOf) {
        this.typeOf = typeOf;
    }

    public List<String> getHasTypes() {
        return hasTypes;
    }

    public void setHasTypes(List<String> hasTypes) {
        this.hasTypes = hasTypes;
    }

    public List<String> getAlso() {
        return also;
    }

    public void setAlso(List<String> also) {
        this.also = also;
    }

    public List<String> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<String> attribute) {
        this.attribute = attribute;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }

    public List<String> getInCategory() {
        return inCategory;
    }

    public void setInCategory(List<String> inCategory) {
        this.inCategory = inCategory;
    }

}
