package com.demo.toy.entity;

import java.math.BigDecimal;

import com.demo.toy.common.response.ContentType;
import com.demo.toy.dto.ApiDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "CONTENTS")
public class ApiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String contentId; 
    
    // 제목 (API의 galTitle)
    @Column(length = 255, nullable = false)
    private String title; 
    
    // 커버 이미지 URL (API의 galWebImageUrl)
    private String coverImageUrl; 
    
    // 콘텐츠 유형 (BOOK, WEBTOON, COMICS 등을 구분)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentType contentType; 

    // 가격
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price = BigDecimal.ZERO; 
    
    // 작가 ID (Authors 테이블의 FK)
    private Long authorId; 
    
    // 콘텐츠 요약/소개 글
    @Lob // 데이터베이스에서 TEXT 타입으로 매핑
    private String description;
    
    // 사용자 평점 평균
    @Column(precision = 3, scale = 2)
    private BigDecimal ratingAvg = BigDecimal.ZERO; 

    // 성인 콘텐츠 여부
    private Boolean isAdult = false; 
    
    private String regDate;

    public ApiEntity() {}

    public ApiEntity(String contentId, String title, String coverImageUrl, String regDate) {
        this.contentId = contentId;
        this.title = title;
        this.coverImageUrl = coverImageUrl;
        this.regDate = regDate;
        
        // 필수 기본값 설정
        this.contentType = ContentType.BOOK;
        this.price = BigDecimal.ZERO;
        this.authorId = 0L;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getRatingAvg() {
		return ratingAvg;
	}

	public void setRatingAvg(BigDecimal ratingAvg) {
		this.ratingAvg = ratingAvg;
	}

	public Boolean getIsAdult() {
		return isAdult;
	}

	public void setIsAdult(Boolean isAdult) {
		this.isAdult = isAdult;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public void update(ApiDTO dto) {
        this.title = dto.getTitle();
        this.price = dto.getPrice();
        this.description = dto.getDescription();
        this.contentType = dto.getContentType();
        this.isAdult = dto.getIsAdult();
        this.coverImageUrl = dto.getCoverImageUrl();
    }
}
