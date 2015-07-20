package vn.shortclips.infrastructure.datasource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.Response;

import vn.shortclips.domain.video.entity.Video;

@Component
public class NineGagDataSource {

	private static String URL = "http://9gag.com/gif?ref=9nav";

	private HtmlParser htmlParser = new HtmlParser();

	AsyncHttpClient asyncHttpClient = new AsyncHttpClient(
			new AsyncHttpClientConfig.Builder().setMaxConnections(10).build());

	public List<Video> downloadVideos() {
		List<Video> videos = htmlParser.videos();
		for (Video video : videos) {
			asyncHttpClient.prepareGet(video.getMp4Url()).execute(new AsyncVideoHandler(video));
		}
		return videos;
	}

	private final class AsyncVideoHandler extends AsyncCompletionHandler<Response> {
		private Video video;

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

	static class HtmlParser {
		private Connection con = Jsoup.connect(URL);

		Document document() {
			try {
				return con.get();
			} catch (IOException e) {
				// TODO : log
				return null;
			}
		}

		public List<Video> videos() {
			List<Video> result = new ArrayList<>();
			Document doc = document();
			if (doc == null) {
				return result;
			}
			Elements articles = doc.select("article");
			for (Element article : articles) {
				result.add(video(article));
			}
			return result;
		}

		Video video(Element article) {
			String title = article.select("header > h2 > a").first().html();
			String mp4Url = article.select("div > a > div > video > source").first().attr("src");
			return new Video().setTitle(title).setMp4Url(mp4Url);
		}
	}
}
