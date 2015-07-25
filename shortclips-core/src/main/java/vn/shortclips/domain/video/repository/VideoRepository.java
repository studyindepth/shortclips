package vn.shortclips.domain.video.repository;

import java.util.List;

import vn.shortclips.domain.video.entity.Video;

public interface VideoRepository {
	void save(Video video);

	void save(Iterable<Video> videos);

	Video findOne(String id);

	boolean exists(String id);

	List<Video> notExist(List<Video> videos);

	List<Video> loadNewestShortenVideos();

	List<Video> loadShortenVideos();

	List<Video> loadFullVideos(List<Video> videos);

	Video youtubeUpload(Video video);
}
