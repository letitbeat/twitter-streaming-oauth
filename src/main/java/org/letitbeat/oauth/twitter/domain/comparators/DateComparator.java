package org.letitbeat.oauth.twitter.domain.comparators;

import org.letitbeat.oauth.twitter.domain.BaseTwitterObject;

import java.util.Comparator;

/**
 * Comparator used to sort {@link BaseTwitterObject} by creation date in
 * chronological order.
 *
 * @author Jose Reyes
 * @version 1.0 05/2017.
 */
public class DateComparator implements Comparator<BaseTwitterObject> {

	public int compare(BaseTwitterObject u1, BaseTwitterObject u2) {
		if (u1.getCreatedAt().before(u2.getCreatedAt())) {
			return -1;
		} else if (u1.getCreatedAt().after(u2.getCreatedAt())) {
			return 1;
		} else {
			return 0;
		}
	}
}
