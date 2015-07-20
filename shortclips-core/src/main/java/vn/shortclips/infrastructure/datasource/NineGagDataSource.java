package vn.shortclips.infrastructure.datasource;

import java.util.List;

import org.springframework.stereotype.Component;

import vn.shortclips.domain.video.entity.Video;

@Component
public class NineGagDataSource {

	private static String URL = "http://9gag.com/gif?ref=9nav";

	public List<Video> downloadVideos() {
		return null;
	}

	static class HtmlParser {

	}

}
