package vn.shortclips.infrastructure.datasource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;

import vn.shortclips.domain.video.exception.VideoException;

@Component
public class YoutubeDataSource {

	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	public vn.shortclips.domain.video.entity.Video upload(vn.shortclips.domain.video.entity.Video video) {
		Video youtubeVideo = new Video();
		youtubeVideo.setStatus(new VideoStatus().setPrivacyStatus("public"));
		VideoSnippet snippet = new VideoSnippet();
		snippet.setTitle(video.getTitle());
		snippet.setDescription(video.getTitle());
		List<String> tags = new ArrayList<String>();
		tags.add("hài");
		tags.add("hai");
		tags.add("fun");
		tags.add("funny");
		tags.add("clip");
		snippet.setTags(tags);
		youtubeVideo.setSnippet(snippet);
		InputStreamContent mediaContent = new InputStreamContent("video/*", video.getInputStream());
		try {
			YouTube.Videos.Insert videoInsert = youtube().videos().insert("snippet,statistics,status", youtubeVideo,
					mediaContent);

			MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();

			uploader.setDirectUploadEnabled(false);

			MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
				public void progressChanged(MediaHttpUploader uploader) throws IOException {
					switch (uploader.getUploadState()) {
					case INITIATION_STARTED:
						System.out.println("Initiation Started");
						break;
					case INITIATION_COMPLETE:
						System.out.println("Initiation Completed");
						break;
					case MEDIA_IN_PROGRESS:
						System.out.println("Upload in progress");
						System.out.println("Upload percentage: " + uploader.getProgress());
						break;
					case MEDIA_COMPLETE:
						System.out.println("Upload Completed!");
						break;
					case NOT_STARTED:
						System.out.println("Upload Not Started!");
						break;
					}
				}
			};
			uploader.setProgressListener(progressListener);

			youtubeVideo = videoInsert.execute();
		} catch (Exception e) {
			throw new VideoException("Uploading to youtube failed.", e);
		}
		return video.setYoutubeUrl(youtubeVideo.getPlayer().getEmbedHtml());
	}

	private YouTube youtube() throws Exception {
		TokenResponse tokenResponse = new GoogleRefreshTokenRequest(HTTP_TRANSPORT, JSON_FACTORY,
				"1/8U3YNmpIMkDaDSFremfhfdiR0sEjPjMKOgO_5YuFV5tIgOrJDtdun6zK6XiATCKT",
				"10244675421-62gmc2ufgedjov3ta90oe64rpcd57ise.apps.googleusercontent.com", "fGRpZpZfyagZppmpyIh1OzoX")
						.execute();
		return new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				new GoogleCredential().setAccessToken(tokenResponse.getAccessToken())).setApplicationName("shortclips")
						.build();
	}

}
