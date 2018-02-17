package driver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class ImageAggregator {

    private List<File> images;

    private ImageAggregator(List<File> images) {
        this.images = images;
    }


    public List<File> getImages() {
        return images;
    }

    static class Builder {
        static ImageAggregator build(List<String> extensions, List<String> directories)
        throws Exception {
            verifyLists(extensions, directories);
            List<String> verifiedExtensions = filterInvalidExtensions(extensions);
            List<String> verifiedDirectories = filterInvalidDirectories(directories);


            return new ImageAggregator(filterInvalidFilesFromDirectories(verifiedExtensions, verifiedDirectories));
        }

        private static void verifyLists(List<String> extensions, List<String> directories)
        throws Exception {
            if (extensions == null || extensions.size() < 1) {
                throw new Exception("List of extensions cannot be null or empty");
            }

            if (directories == null || directories.size() < 1) {
                throw new Exception("List of directories cannot be null or empty");
            }
        }

        private static List<String> filterInvalidExtensions(List<String> extensions)
        throws Exception {
            assert extensions.size() >= 1;

            for (String extension : extensions) {
                if (extension.charAt(0) != '.') {
                    extensions.remove(extension);
                }
            }

            if (extensions.size() < 1) {
                throw new Exception("Invalid format for extensions. They must start with '.' ");
            }

            // Pretty sure this isn't a safe operation
            return extensions;
        }

        private static List<String> filterInvalidDirectories(List<String> directories)
        throws Exception {
            assert directories.size() >= 1;

            for (String path : directories) {
                if (!Files.isDirectory(Paths.get(path))) {
                    directories.remove(path);
                }
            }

            if (directories.size() < 1) {
                throw new Exception("No valid directories");
            }

            return directories;
        }

        private static List<File> filterInvalidFilesFromDirectories(List<String> extensions, List<String> directories) {
            List<File> validImages = new LinkedList<>();

            // O(N^2)
            for (String directory : directories) {
                File[] listOfFiles = (new File(directory)).listFiles();

                for (File file : listOfFiles) {
                    if (!fileHasValidExtension(file, extensions)) {
                        validImages.add(file);
                    }
                }
            }

            return validImages;
        }

        private static boolean fileHasValidExtension(File file, List<String> extensions) {
            String filename = file.getName();

            if (!filename.contains(".")) {
                return false;
            }

            int extensionMarker = filename.lastIndexOf('.');
            String fileExtension = filename.substring(extensionMarker);

            for (String extension : extensions) {
                if (extension.equals(fileExtension)) {
                    return true;
                }
            }
            return false;
        }
    }
}
