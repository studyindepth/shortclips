package vn.shortclips.infrastructure.datasource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.gs.collections.impl.list.mutable.FastList;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.Response;

import vn.shortclips.domain.video.entity.Video;
import vn.shortclips.domain.video.factory.VideoIdFactory;

@Component
public class NineGagDataSource {

	private static String BASE_URL = "http://9gag.com";

	private static String GIF_URL = "http://9gag.com/gif";

	private VideoParser videoParser = new VideoParser();

	AsyncHttpClient asyncHttpClient = new AsyncHttpClient(
			new AsyncHttpClientConfig.Builder().setMaxConnections(1).build());

	public List<Video> newestVideos() {
		return videoParser.newestVideos();
	}

	public List<Video> nextVideos() {
		return videoParser.nextVideos();
	}

	public List<Video> downloadVideosAsync(List<Video> videos) {
		for (Video video : videos) {
			asyncHttpClient.prepareGet(video.getSourceUrl()).execute(new AsyncVideoHandler(video));
		}
		return videos;
	}

	public List<Video> downloadVideos(List<Video> videos) {
		List<Video> result = new FastList<>();
		for (Video video : videos) {
			Future<Response> f = asyncHttpClient.prepareGet(video.getSourceUrl()).execute();
			try {
				video.setInputStream(f.get().getResponseBodyAsStream());
			} catch (IOException | InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			if (video.isReady()) {
				result.add(video);
			}
		}
		return result;
	}

	private final class AsyncVideoHandler extends AsyncCompletionHandler<Response> {
		Video video;

		public AsyncVideoHandler(Video video) {
			this.video = video;
		}

		@Override
		public Response onCompleted(Response response) throws Exception {
			video.setInputStream(response.getResponseBodyAsStream());
			return response;
		}

		@Override
		public void onThrowable(Throwable t) {
			// TODO : log here
		}
	}

	private final static class VideoParser {
		Connection con = Jsoup.connect(BASE_URL);
		String nextUrl = GIF_URL;

		Document document(String url) {
			try {
				return con.url(url).get();
			} catch (IOException e) {
				// TODO : log
				return null;
			}
		}

		List<Video> videos(String url) {
			Document doc = document(url);
			parseNextUrl(doc);
			return parseVideos(doc);
		}

		private List<Video> parseVideos(Document doc) {
			List<Video> result = new ArrayList<>();
			if (doc == null) {
				return result;
			}
			Elements articles = doc.select("article");
			for (Element article : articles) {
				Video video = video(article);
				if (video != null) {
					result.add(video);
				}
			}
			return result;
		}

		private void parseNextUrl(Document doc) {
			Element loadMore = doc.select("div.loading > a").first();
			if (loadMore != null) {
				this.nextUrl = BASE_URL + loadMore.attr("href");
			}
		}

		List<Video> newestVideos() {
			return videos(GIF_URL);
		}

		List<Video> nextVideos() {
			return videos(nextUrl);
		}

		Video video(Element article) {
			Element firstTitle = article.select("header > h2 > a").first();
			if (firstTitle == null) {
				return null;
			}
			String title = firstTitle.html();
			Element firstVideo = article.select("div > a > div > video > source").first();
			if (firstVideo == null) {
				return null;
			}
			String mp4Url = firstVideo.attr("src");
			if (mp4Url == null || title == null) {
				return null;
			}
			return new Video().setId(VideoIdFactory.videoId(mp4Url)).setTitle(title).setSourceUrl(mp4Url);
		}
	}

	public static void main(String[] args) {
		NineGagDataSource source = new NineGagDataSource();
		List<Video> v = source.nextVideos();
		System.out.println(v);

	}
}
