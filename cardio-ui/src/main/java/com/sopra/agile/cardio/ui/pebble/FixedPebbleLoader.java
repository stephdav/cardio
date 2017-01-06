package com.sopra.agile.cardio.ui.pebble;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mitchellbosecke.pebble.error.LoaderException;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.utils.PathUtils;

public class FixedPebbleLoader implements Loader<String> {

    private static final Logger logger = LoggerFactory.getLogger(FixedPebbleLoader.class);

    private String prefix;

    private String suffix;

    private String charset = "UTF-8";

    private char expectedSeparator = '/';

    private final ClassLoader rcl;

    public FixedPebbleLoader(ClassLoader classLoader) {
        rcl = classLoader;
    }

    public FixedPebbleLoader() {
        this(FixedPebbleLoader.class.getClassLoader());
    }

    @Override
    public Reader getReader(String templateName) throws LoaderException {

        InputStreamReader isr = null;
        Reader reader = null;

        InputStream is = null;

        // append the prefix and make sure prefix ends with a separator
        // character
        StringBuilder path = new StringBuilder(128);
        if (getPrefix() != null) {

            path.append(getPrefix());

            // we do NOT use OS dependent separators here; getResourceAsStream
            // explicitly requires forward slashes.
            if (!getPrefix().endsWith(Character.toString(expectedSeparator))) {
                path.append(expectedSeparator);
            }
        }
        path.append(templateName);
        if (getSuffix() != null)
            path.append(getSuffix());
        String location = path.toString();
        logger.debug("Looking for template in {}.", location);

        // perform the lookup
        is = rcl.getResourceAsStream(location);

        if (is == null) {
            throw new LoaderException(null, "Could not find template \"" + location + "\"");
        }

        try {
            isr = new InputStreamReader(is, charset);
            reader = new BufferedReader(isr);
        } catch (UnsupportedEncodingException e) {
        }

        return reader;
    }

    public String getSuffix() {
        return suffix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getCharset() {
        return charset;
    }

    @Override
    public void setCharset(String charset) {
        this.charset = charset;
    }

    @Override
    public String resolveRelativePath(String relativePath, String anchorPath) {
        return PathUtils.resolveRelativePath(relativePath, anchorPath, expectedSeparator);
    }

    @Override
    public String createCacheKey(String templateName) {
        return templateName;
    }
}
