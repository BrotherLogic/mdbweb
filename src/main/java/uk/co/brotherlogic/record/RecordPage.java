package uk.co.brotherlogic.mdbweb.record;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import uk.co.brotherlogic.jarpur.Page;
import uk.co.brotherlogic.mdb.artist.Artist;
import uk.co.brotherlogic.mdb.record.GetRecords;
import uk.co.brotherlogic.mdb.record.Record;
import uk.co.brotherlogic.mdb.record.Track;

public class RecordPage extends Page {
	@Override
	public String buildPage(Map<String, String> params) throws IOException {
		try {
			int recordID = Integer.parseInt(params.get("id"));
			Record record = GetRecords.create().getRecord(recordID);

			Map<String, Object> paramMap = new TreeMap<String, Object>();
			paramMap.put("record", record);
			paramMap.put("artistmap", splitArtists(record));

			return buildPageFromTemplate(paramMap);
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	public Boolean relatedExists(Track track) {
		try {

			return GetRecords.create().getRecordsWithTrack(track.getTitle())
					.size() > 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private Map<String, List<Artist>> splitArtists(Record rec)
			throws SQLException {
		Map<String, List<Artist>> ret = new TreeMap<String, List<Artist>>();

		// First create a map from personnel to tracks
		Map<Artist, String> persToTracks = new HashMap<Artist, String>();
		for (Track track : rec.getTracks())
			for (Artist art : track.getPersonnel())
				if (persToTracks.containsKey(art))
					persToTracks.put(art, persToTracks.get(art)
							+ Integer.toString(track.getTrackNumber()));
				else
					persToTracks.put(art, Integer.toString(track
							.getTrackNumber()));

		// Now transform into the return set
		for (Entry<Artist, String> arts : persToTracks.entrySet()) {
			if (!ret.containsKey(arts.getValue()))
				ret.put(arts.getValue(), new LinkedList<Artist>());
			ret.get(arts.getValue()).add(arts.getKey());
		}

		return ret;
	}

}
