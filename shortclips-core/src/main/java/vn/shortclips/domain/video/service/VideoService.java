package vn.shortclips.domain.video.service;

import java.util.List;

import vn.shortclips.domain.video.entity.Video;

public interface VideoService {
	List<Video> downloadNextVideos();

	void uploadVideo(Video video);
}
