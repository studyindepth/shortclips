package vn.shortclips.domain.video.entity;

import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import vn.shortclips.domain.video.exception.VideoException;

@Entity
public class Video {

	@Id
	private String id;

	private String title;

	@Column(name = "source_url")
	private String sourceUrl;

	@Column(name = "youtube_url")
	private String youtubeUrl;

	@Transient
	private InputStream inputStream;

	public Video() {
	}

	public Video(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public Video setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public Video setSourceUrl(String mp4Url) {
		this.sourceUrl = mp4Url;
		return this;
	}

	public String getId() {
		return id;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public Video setInputStream(InputStream inputStream) {
		if (this.inputStream == null) {
			this.inputStream = inputStream;
		} else {
			throw new VideoException("Input stream can only be set once.");
		}
		return this;
	}

	public boolean isReady() {
		return this.inputStream != null;
	}

	public String getYoutubeUrl() {
		return youtubeUrl;
	}

	public Video setYoutubeUrl(String youtubeUrl) {
		this.youtubeUrl = youtubeUrl;
		return this;
	}

	public Video setId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public String toString() {
		return "Video [id=" + id + ", title=" + title + ", sourceUrl=" + sourceUrl + ", youtubeUrl=" + youtubeUrl + "]";
	}

}
