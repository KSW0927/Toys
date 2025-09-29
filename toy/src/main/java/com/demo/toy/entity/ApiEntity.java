package com.demo.toy.entity;

import com.demo.toy.dto.ApiDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gallery_item")
public class ApiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String galContentId;
    private String galContentTypeId;
    private String galTitle;
    private String galWebImageUrl;
    private String galCreatedTime;
    private String galModifiedTime;
    private String galPhotographyMonth;
    private String galPhotographyLocation;
    private String galPhotographer;
    private String galSearchKeyword;

    // 기본 생성자
    public ApiEntity() {}

    // 모든 필드를 받는 생성자 (id 제외)
    public ApiEntity(String galContentId, String galContentTypeId, String galTitle, String galWebImageUrl,
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

    public Long getId() {
        return id;
    }

    public String getGalContentId() {
        return galContentId;
    }

    public String getGalContentTypeId() {
        return galContentTypeId;
    }

    public String getGalTitle() {
        return galTitle;
    }

    public String getGalWebImageUrl() {
        return galWebImageUrl;
    }

    public String getGalCreatedTime() {
        return galCreatedTime;
    }

    public String getGalModifiedTime() {
        return galModifiedTime;
    }

    public String getGalPhotographyMonth() {
        return galPhotographyMonth;
    }

    public String getGalPhotographyLocation() {
        return galPhotographyLocation;
    }

    public String getGalPhotographer() {
        return galPhotographer;
    }

    public String getGalSearchKeyword() {
        return galSearchKeyword;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGalContentId(String galContentId) {
        this.galContentId = galContentId;
    }

    public void setGalContentTypeId(String galContentTypeId) {
        this.galContentTypeId = galContentTypeId;
    }

    public void setGalTitle(String galTitle) {
        this.galTitle = galTitle;
    }

    public void setGalWebImageUrl(String galWebImageUrl) {
        this.galWebImageUrl = galWebImageUrl;
    }

    public void setGalCreatedTime(String galCreatedTime) {
        this.galCreatedTime = galCreatedTime;
    }

    public void setGalModifiedTime(String galModifiedTime) {
        this.galModifiedTime = galModifiedTime;
    }

    public void setGalPhotographyMonth(String galPhotographyMonth) {
        this.galPhotographyMonth = galPhotographyMonth;
    }

    public void setGalPhotographyLocation(String galPhotographyLocation) {
        this.galPhotographyLocation = galPhotographyLocation;
    }

    public void setGalPhotographer(String galPhotographer) {
        this.galPhotographer = galPhotographer;
    }

    public void setGalSearchKeyword(String galSearchKeyword) {
        this.galSearchKeyword = galSearchKeyword;
    }
    
    public void update(ApiDTO dto) {
        this.galTitle = dto.getGalTitle();
        this.galWebImageUrl = dto.getGalWebImageUrl();
        this.galPhotographyMonth = dto.getGalPhotographyMonth();
        this.galPhotographyLocation = dto.getGalPhotographyLocation();
        this.galPhotographer = dto.getGalPhotographer();
        this.galSearchKeyword = dto.getGalSearchKeyword();
        this.galModifiedTime = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
