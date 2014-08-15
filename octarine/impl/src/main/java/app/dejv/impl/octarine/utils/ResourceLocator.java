package app.dejv.impl.octarine.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * <br/>
 * Author: dejv (www.dejv.info)
 */
public class ResourceLocator {


    public static final String JAVA_CLASS_PATH = "java.class.path";


    /**
     * for all elements of java.class.path get a Collection of resources Pattern
     * pattern = Pattern.compile(".*"); gets all resources
     *
     * @param pattern the pattern to match
     * @return the resources in the order they are found
     */
    public static Collection<String> getResources(final Pattern pattern) {
        final ArrayList<String> retval = new ArrayList<>();
        final String classPath = System.getProperty(JAVA_CLASS_PATH, ".");
        final String[] classPathElements = classPath.split(":");

        for (final String element : classPathElements) {
            retval.addAll(getResources(element, pattern));
        }
        return retval;
    }


    private static Collection<String> getResources(final String element, final Pattern pattern) {
        final ArrayList<String> result = new ArrayList<>();
        final File file = new File(element);

        if (file.isDirectory()) {
            result.addAll(getResourcesFromDirectory(file, pattern));
        } else {
            result.addAll(getResourcesFromJarFile(file, pattern));
        }

        return result;
    }


    private static Collection<String> getResourcesFromJarFile(final File file, final Pattern pattern) {
        final ArrayList<String> result = new ArrayList<>();

        ZipFile zf;

        try {
            zf = new ZipFile(file);
        } catch (final IOException e) {
            throw new Error(e);
        }

        final Enumeration e = zf.entries();

        while (e.hasMoreElements()) {
            final ZipEntry ze = (ZipEntry) e.nextElement();
            final String fileName = ze.getName();
            final boolean accept = pattern.matcher(fileName).matches();

            if (accept) {
                result.add(fileName);
            }
        }

        try {
            zf.close();
        } catch (final IOException e1) {
            throw new Error(e1);
        }
        return result;
    }


    private static Collection<String> getResourcesFromDirectory(
            final File directory,
            final Pattern pattern) {

        final ArrayList<String> result = new ArrayList<>();
        final File[] fileList = directory.listFiles();

        assert fileList != null;

        for (final File file : fileList) {
            if (file.isDirectory()) {
                result.addAll(getResourcesFromDirectory(file, pattern));
            } else {
                try {
                    final String fileName = file.getCanonicalPath();
                    final boolean accept = pattern.matcher(fileName).matches();
                    if (accept) {
                        result.add(fileName);
                    }
                } catch (final IOException e) {
                    throw new Error(e);
                }
            }
        }
        return result;
    }


    private void main() {
        Pattern pattern = Pattern.compile(".*");
        final Collection<String> list = getResources(pattern);
        for (final String name : list) {
            System.out.println("R: " + name);
        }
    }

}
