package org.shakti;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * Created by ppoddar on 8/8/16.
 */
public class SimpleFileNameFilter implements FilenameFilter {
    String mRegex;

    public SimpleFileNameFilter(String regex) {
        mRegex = regex;
    }
    /**
     * Indicates if a specific filename matches this filter.
     *
     * @param dir      the directory in which the {@code filename} was found.
     * @param filename the name of the file in {@code dir} to test.
     * @return {@code true} if the filename matches the filter
     * and can be included in the list, {@code false}
     * otherwise.
     */
    @Override
    public boolean accept(File dir, String filename) {

        return Pattern.compile(mRegex).matcher(filename).matches();
    }
}
