package uk.co.brotherlogic.mdbweb.artist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.Page;
import uk.co.brotherlogic.mdb.artist.Artist;
import uk.co.brotherlogic.mdb.artist.GetArtists;
import uk.co.brotherlogic.mdb.label.GetLabels;
import uk.co.brotherlogic.mdb.label.Label;

public class ArtistPage extends Page {
	@Override
	public String buildPage(Map<String, String> params) throws IOException {
		try {
			int labelID = Integer.parseInt(params.get("id"));
			Artist artist = GetArtists.create().getArtist(labelID);

			Map<String, Object> paramMap = new TreeMap<String, Object>();
			paramMap.put("artist", artist);

			return buildPageFromTemplate(paramMap);
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	public Boolean sized(Collection col) {
		System.err.println("COL: " + col);
		return col.size() > 0;
	}

	public Boolean unequal(String one, String two) {
		return !one.equalsIgnoreCase(two);
	}

	public static void main(String[] args) throws Exception {
		Label label = GetLabels.create().getLabel(9);
		System.err.println("Storing label = " + label.getName() + " [" + label
				+ "]");
	}
}
