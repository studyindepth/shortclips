package vn.shortclips.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gs.collections.impl.list.mutable.FastList;

import vn.shortclips.domain.video.entity.Video;
import vn.shortclips.domain.video.service.VideoService;

@Component
public class VideoBatch extends Batch {

	@Autowired
	VideoService service;

	@Override
	public void run() {
		List<Video> delay = new FastList<>();
		List<Video> vidoes = service.downloadNextVideos();
		try {
			Thread.sleep(50_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (Video video : vidoes) {
			if (video.isReady()) {
				service.uploadVideo(video);
			} else {
				delay.add(video);
			}
		}

	}

}
