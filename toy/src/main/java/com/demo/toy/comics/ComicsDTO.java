package com.demo.toy.comics;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComicsDTO {

    @JsonProperty("contentId")
    private String contentId;

    @JsonProperty("comicsId")
    private String comicsId; 

    @JsonProperty("volume")
    private Long volume;
    
    @JsonProperty("page")
    private Long page;
    
    @JsonProperty("volumePrice")
    private BigDecimal volumePrice; 

    @JsonProperty("volumeImageUrl")
    private String volumeImageUrl;

    @JsonProperty("volumeFileSize")
    private String volumeFileSize;

    @JsonProperty("regDate")
    private String regDate;

    public ComicsDTO() {}

    public ComicsDTO(String contentId, String comicsId, Long volume, Long page, 
                     BigDecimal volumePrice, String volumeImageUrl, String volumeFileSize, 
                     String regDate) {
        this.contentId = contentId;
        this.comicsId = comicsId;
        this.volume = volume;
        this.page = page;
        this.volumePrice = volumePrice;  // 전달값 그대로
        this.volumeImageUrl = volumeImageUrl;
        this.volumeFileSize = volumeFileSize;
        this.regDate = regDate;
    }

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getComicsId() {
		return comicsId;
	}

	public void setComicsId(String comicsId) {
		this.comicsId = comicsId;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public BigDecimal getVolumePrice() {
		return volumePrice;
	}

	public void setVolumePrice(BigDecimal volumePrice) {
		this.volumePrice = volumePrice;
	}

	public String getVolumeImageUrl() {
		return volumeImageUrl;
	}

	public void setVolumeImageUrl(String volumeImageUrl) {
		this.volumeImageUrl = volumeImageUrl;
	}

	public String getVolumeFileSize() {
		return volumeFileSize;
	}

	public void setVolumeFileSize(String volumeFileSize) {
		this.volumeFileSize = volumeFileSize;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
}