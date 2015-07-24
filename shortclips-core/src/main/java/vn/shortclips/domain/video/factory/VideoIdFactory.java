package vn.shortclips.domain.video.factory;

public class VideoIdFactory {
	private VideoIdFactory() {

	}

	public static String videoId(String videoUrl) {
		String[] parts = videoUrl.split("/");
		return parts[parts.length - 1];
	}
}
