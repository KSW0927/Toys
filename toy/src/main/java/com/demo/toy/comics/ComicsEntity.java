package com.demo.toy.comics;

import java.math.BigDecimal;

import com.demo.toy.contents.ContentsEntity;

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

    /** 권(회차) PK */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comics_id", nullable = false, updatable = false)
    private Long comicsId;

    
    /** 부모 콘텐츠 (ManyToOne) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private ContentsEntity content;

    /** 몇 권인지 */
    @Column(nullable = false)
    private Long volume;

    /** 총 페이지 수 */
    @Column(nullable = false)
    private Long page;

    /** 권별 가격 */
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal volumePrice = BigDecimal.ZERO;

    /** 권별 이미지 */
    @Column(length = 300)
    private String volumeImageUrl;

    /** 파일 용량 */
    @Column(length = 50)
    private String volumeFileSize;

    @Column(length = 20)
    private String regDate;

	public Long getComicsId() {
		return comicsId;
	}

	public void setComicsId(Long comicsId) {
		this.comicsId = comicsId;
	}

	public ContentsEntity getContent() {
		return content;
	}

	public void setContent(ContentsEntity content) {
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

	@Override
	public String toString() {
		return "ComicsEntity [comicsId=" + comicsId + ", content=" + content + ", volume=" + volume + ", page=" + page
				+ ", volumePrice=" + volumePrice + ", volumeImageUrl=" + volumeImageUrl + ", volumeFileSize="
				+ volumeFileSize + ", regDate=" + regDate + "]";
	}
}
