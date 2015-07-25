package vn.shortclips.domain.video.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.shortclips.domain.video.entity.Video;
import vn.shortclips.domain.video.repository.VideoRepository;

@Service
public class DefaultVideoService implements VideoService {

	@Autowired
	VideoRepository videoRepository;

	@Override
	public List<Video> downloadNextVideos() {
		List<Video> shortenVideos = videoRepository.loadShortenVideos();
		List<Video> notExistingVidoes = videoRepository.notExist(shortenVideos);
		return videoRepository.loadFullVideos(notExistingVidoes);
	}

	@Override
	@Transactional
	public void uploadVideo(Video video) {
		videoRepository.save(videoRepository.youtubeUpload(video));
	}

}
