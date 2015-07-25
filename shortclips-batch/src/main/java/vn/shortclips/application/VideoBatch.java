package vn.shortclips.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.shortclips.domain.video.entity.Video;
import vn.shortclips.domain.video.service.VideoService;

@Component
public class VideoBatch extends Batch {

	@Autowired
	VideoService service;

	@Override
	public void run() {
		while (true) {
			List<Video> vidoes = service.downloadNextVideos();
			for (Video video : vidoes) {
				try {
					service.uploadVideo(video);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
