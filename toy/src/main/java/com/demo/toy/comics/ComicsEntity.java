package com.demo.toy.comics;

import java.math.BigDecimal;

import com.demo.toy.entity.ApiEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "COMICS")
public class ComicsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 비즈니스용 만화 ID (unique key)
    @Column(unique = true, nullable = false)
    private String comicsId;

    // 콘텐츠 (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", referencedColumnName = "contentId")
    private ApiEntity content;

    // 권 번호
    private Long volume;

    // 페이지 수
    private Long page;

    // 권별 가격
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal volumePrice = BigDecimal.ZERO;

    // 권별 이미지
    private String volumeImageUrl;

    // 파일 사이즈
    private String volumeFileSize;

    private String regDate;

    public ComicsEntity() {}

    public ComicsEntity(ApiEntity content, String comicsId,
                        Long volume, Long page, BigDecimal volumePrice,
                        String volumeImageUrl, String volumeFileSize, String regDate) {

        this.content = content;
        this.comicsId = comicsId;
        this.volume = volume;
        this.page = page;
        this.volumePrice = volumePrice;
        this.volumeImageUrl = volumeImageUrl;
        this.volumeFileSize = volumeFileSize;
        this.regDate = regDate;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComicsId() {
		return comicsId;
	}

	public void setComicsId(String comicsId) {
		this.comicsId = comicsId;
	}

	public ApiEntity getContent() {
		return content;
	}

	public void setContent(ApiEntity content) {
		this.content = content;
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
