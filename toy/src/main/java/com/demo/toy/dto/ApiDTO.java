package com.demo.toy.dto;

import java.math.BigDecimal;
import com.demo.toy.common.response.ContentType; // ContentType Enum 임포트
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiDTO {

    @JsonProperty("contentId")
    private String contentId; 

    // ContentType Enum 타입으로 변경
    @JsonProperty("contentType")
    private ContentType contentType; 

    @JsonProperty("title")
    private String title;

    @JsonProperty("coverImageUrl")
    private String coverImageUrl;

    // 가격은 BigDecimal 타입으로 변경 (정확한 금액 처리)
    @JsonProperty("price")
    private BigDecimal price; 

    // ID는 Long 타입으로 변경
    @JsonProperty("authorId")
    private Long authorId; 

    @JsonProperty("description")
    private String description;

    // 평점도 BigDecimal 타입으로 변경
    @JsonProperty("ratingAvg")
    private BigDecimal ratingAvg;

    // 성인 여부는 Boolean 타입으로 변경
    @JsonProperty("isAdult")
    private Boolean isAdult;
    
    @JsonProperty("regDate")
    private String regDate;

    // --------------------------------------------------
    // ❌ 생성자 오류 수정 및 로직 정리
    // --------------------------------------------------
    
    public ApiDTO() {}

    public ApiDTO(String contentId, ContentType contentType, String title, 
                  String coverImageUrl, BigDecimal price, Long authorId, 
                  String description, BigDecimal ratingAvg, Boolean isAdult, String regDate) {
        this.contentId = contentId;
        this.contentType = contentType;
        this.title = title;
        this.coverImageUrl = coverImageUrl;
        this.price = price;
        this.authorId = authorId;
        this.description = description;
        this.ratingAvg = ratingAvg;
        this.isAdult = isAdult;
        this.regDate = regDate;
    }

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
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
}