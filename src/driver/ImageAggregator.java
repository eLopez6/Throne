package driver;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
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

    public List<File> shuffledImages() {
        List<File> shuffledList = new LinkedList<>(images);
        Collections.shuffle(shuffledList);
        return shuffledList;
    }

    static class Builder {
        static ImageAggregator build(List<String> extensions, String directory)
        throws Exception {
            verifyList(extensions);
            verifyDirectory(directory);
            List<String> verifiedExtensions = filterInvalidExtensions(extensions);

            return new ImageAggregator(filterInvalidFilesFromDirectories(verifiedExtensions, directory));
        }

        private static List<String> filterInvalidExtensions(List<String> extensions)
        throws Exception {
            assert extensions.size() >= 1;
            List<String> validExtensions = new LinkedList<>(extensions);

            for (String extension : validExtensions) {
                if (extension.charAt(0) != '.') {
                    validExtensions.remove(extension);
                }
            }

            if (validExtensions.size() < 1) {
                throw new Exception("Invalid format for extensions. They must start with '.' ");
            }

            // Pretty sure this isn't a safe operation
            return validExtensions;
        }

        private static List<File> filterInvalidFilesFromDirectories(List<String> extensions, String directory)
        throws FileNotFoundException {
            List<File> validImages = new LinkedList<>();

            File[] listOfFiles = (new File(directory)).listFiles();
            if (listOfFiles == null) {
                throw new FileNotFoundException("Directory does not exist");
            }
            for (File file : listOfFiles) {
                if (fileHasValidExtension(file, extensions)) {
                    validImages.add(file);
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

        private static void verifyList(List<String> extensions)
        throws Exception {
            if (extensions == null || extensions.size() < 1) {
                throw new Exception("List of extensions cannot be null or empty");
            }
        }

        private static void verifyDirectory(String directoryPath)
                throws Exception {
            if (!Files.isDirectory(Paths.get(directoryPath))) {
                throw new Exception("No valid directories");
            }
        }
    }
}
