package com.demo.toy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiDTO {

    @JsonProperty("galContentId")
    private String galContentId;

    @JsonProperty("galContentTypeId")
    private String galContentTypeId;

    @JsonProperty("galTitle")
    private String galTitle;

    @JsonProperty("galWebImageUrl")
    private String galWebImageUrl;

    @JsonProperty("galCreatedtime")
    private String galCreatedTime;

    @JsonProperty("galModifiedtime")
    private String galModifiedTime;

    @JsonProperty("galPhotographyMonth")
    private String galPhotographyMonth;

    @JsonProperty("galPhotographyLocation")
    private String galPhotographyLocation;

    @JsonProperty("galPhotographer")
    private String galPhotographer;

    @JsonProperty("galSearchKeyword")
    private String galSearchKeyword;

    // 기본 생성자
    public ApiDTO() {}

    // 모든 필드를 받는 생성자
    public ApiDTO(String galContentId, String galContentTypeId, String galTitle, String galWebImageUrl,
                  String galCreatedTime, String galModifiedTime, String galPhotographyMonth,
                  String galPhotographyLocation, String galPhotographer, String galSearchKeyword) {
        this.galContentId = galContentId;
        this.galContentTypeId = galContentTypeId;
        this.galTitle = galTitle;
        this.galWebImageUrl = galWebImageUrl;
        this.galCreatedTime = galCreatedTime;
        this.galModifiedTime = galModifiedTime;
        this.galPhotographyMonth = galPhotographyMonth;
        this.galPhotographyLocation = galPhotographyLocation;
        this.galPhotographer = galPhotographer;
        this.galSearchKeyword = galSearchKeyword;
    }

    public String getGalContentId() {
        return galContentId;
    }

    public void setGalContentId(String galContentId) {
        this.galContentId = galContentId;
    }

    public String getGalContentTypeId() {
        return galContentTypeId;
    }

    public void setGalContentTypeId(String galContentTypeId) {
        this.galContentTypeId = galContentTypeId;
    }

    public String getGalTitle() {
        return galTitle;
    }

    public void setGalTitle(String galTitle) {
        this.galTitle = galTitle;
    }

    public String getGalWebImageUrl() {
        return galWebImageUrl;
    }

    public void setGalWebImageUrl(String galWebImageUrl) {
        this.galWebImageUrl = galWebImageUrl;
    }

    public String getGalCreatedTime() {
        return galCreatedTime;
    }

    public void setGalCreatedTime(String galCreatedTime) {
        this.galCreatedTime = galCreatedTime;
    }

    public String getGalModifiedTime() {
        return galModifiedTime;
    }

    public void setGalModifiedTime(String galModifiedTime) {
        this.galModifiedTime = galModifiedTime;
    }

    public String getGalPhotographyMonth() {
        return galPhotographyMonth;
    }

    public void setGalPhotographyMonth(String galPhotographyMonth) {
        this.galPhotographyMonth = galPhotographyMonth;
    }

    public String getGalPhotographyLocation() {
        return galPhotographyLocation;
    }

    public void setGalPhotographyLocation(String galPhotographyLocation) {
        this.galPhotographyLocation = galPhotographyLocation;
    }

    public String getGalPhotographer() {
        return galPhotographer;
    }

    public void setGalPhotographer(String galPhotographer) {
        this.galPhotographer = galPhotographer;
    }

    public String getGalSearchKeyword() {
        return galSearchKeyword;
    }

    public void setGalSearchKeyword(String galSearchKeyword) {
        this.galSearchKeyword = galSearchKeyword;
    }
}
