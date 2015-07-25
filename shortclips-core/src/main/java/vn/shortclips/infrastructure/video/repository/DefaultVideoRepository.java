package vn.shortclips.infrastructure.video.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gs.collections.impl.list.mutable.FastList;

import vn.shortclips.domain.video.entity.Video;
import vn.shortclips.domain.video.repository.VideoRepository;
import vn.shortclips.infrastructure.datasource.NineGagDataSource;
import vn.shortclips.infrastructure.datasource.YoutubeDataSource;

@Repository
public class DefaultVideoRepository implements VideoRepository {

	@PersistenceContext
	EntityManager em;

	@Autowired
	NineGagDataSource nineGagDataSource;

	@Autowired
	YoutubeDataSource youtubeDataSource;

	@Override
	@Transactional
	public void save(Video video) {
		em.persist(video);
	}

	@Override
	public Video findOne(String id) {
		return em.find(Video.class, id);
	}

	@Override
	public boolean exists(String id) {
		return findOne(id) != null;
	}

	@Override
	public List<Video> loadShortenVideos() {
		return nineGagDataSource.nextVideos();
	}

	@Override
	public List<Video> loadFullVideos(List<Video> videos) {
		return nineGagDataSource.downloadVideos(videos);
	}

	@Override
	public List<Video> loadNewestShortenVideos() {
		return nineGagDataSource.newestVideos();
	}

	@Override
	public List<Video> notExist(List<Video> videos) {
		List<Video> result = new FastList<>();
		for (Video video : videos) {
			if (!exists(video.getId())) {
				result.add(video);
			}
		}
		return result;
	}

	@Override
	public void save(Iterable<Video> videos) {
		for (Video video : videos) {
			save(video);
		}
	}

	@Override
	public Video youtubeUpload(Video video) {
		return youtubeDataSource.upload(video);
	}

}
