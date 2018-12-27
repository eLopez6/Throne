package driver;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ImageManager {

    private ArrayList<Image> images;
    private int curImageIndex = 0;
    private int IMAGES_LENGTH;

    public Image firstImage() {
        return images.get(0);
    }

    public Image nextImage() {
        curImageIndex = Math.floorMod(++curImageIndex, IMAGES_LENGTH);
        return images.get(curImageIndex);
    }


    public Image previousImage() {
        curImageIndex = Math.floorMod(--curImageIndex, IMAGES_LENGTH);
        return images.get(curImageIndex);
    }


    public void shuffleImages() {
        Collections.shuffle(images);
    }


    private ImageManager(ArrayList<Image> images) {
        this.images = images;
        this.IMAGES_LENGTH = images.size();
    }

    public static class Builder {
        public static ImageManager build(List<String> extensions, String directory)
        throws Exception {
            verifyList(extensions);
            verifyDirectory(directory);
            List<String> verifiedExtensions = filterInvalidExtensions(extensions);

            return new ImageManager(filterInvalidFilesFromDirectories(verifiedExtensions, directory));
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

        private static ArrayList<Image> filterInvalidFilesFromDirectories(List<String> extensions, String directory)
        throws FileNotFoundException {
            ArrayList<Image> validImages = new ArrayList<>();

            File[] listOfFiles = (new File(directory)).listFiles();
            if (listOfFiles == null) {
                throw new FileNotFoundException("Directory does not exist");
            }
            for (File file : listOfFiles) {
                if (fileHasValidExtension(file, extensions)) {
                    validImages.add(new Image(file.toURI().toString()));
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
