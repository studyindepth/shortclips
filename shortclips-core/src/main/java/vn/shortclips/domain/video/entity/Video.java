package vn.shortclips.domain.video.entity;

import java.io.InputStream;

import vn.shortclips.domain.video.exception.VideoException;

public class Video {

	private String title;

	private String mp4Url;

	private InputStream inputStream;

	public String getTitle() {
		return title;
	}

	public Video setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getMp4Url() {
		return mp4Url;
	}

	public Video setMp4Url(String mp4Url) {
		this.mp4Url = mp4Url;
		return this;
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
}
