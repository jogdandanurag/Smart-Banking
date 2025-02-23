package com.aj.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;


public class FilesUtils {
    // Base directory for storing uploaded files (can be configured differently for production)
    public static String FILES_DIR = "./uploadFiles/";

    // Default profile image in case no image is provided
    public static String DEFAULT_PROFILE = "./uploadFiles/profile/default.jpg";

    // Store an image and return the relative URL path to access it
    public static String storeImage(MultipartFile image, String folder, String itemName) {
        if (image != null && !image.isEmpty()) {
            if (folder == null) {
                folder = "profile_images"; // Default folder name if none provided
            }
            Path directoryPath = Paths.get(FILES_DIR + folder);
            try {
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath); // Create directory if not present
                }
            } catch (IOException e) {
                System.out.println("Error creating directory: " + e.getMessage());
            }
            String fileName = itemName + new Date().getTime() + ".jpg";
            Path filePath = Paths.get(FILES_DIR + folder + "/" + fileName);
            try {
                Files.copy(image.getInputStream(), filePath);
            } catch (IOException e) {
                System.out.println("Error saving file: " + e.getMessage());
                return null; // Return null if there was an error
            }
            return FILES_DIR+folder + "/" + fileName;  // relative path
        }
        System.out.println("No image provided.");
        return null;
    }

    // Retrieve an image as a byte array (used for returning profile images or other resources)
    public static byte[] getImage(String path) {
        if (path == null) {
            path = DEFAULT_PROFILE;
        } else {
            path = path.replace("\\", "/"); // Normalize path separators
        }

        Path imagePath = Paths.get(path);
        try {
            // Read the image as a byte array
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return imageBytes;
        } catch (IOException e) {
            System.out.println("Error reading image: " + e.getMessage());
            return new byte[0]; // Return an empty byte array if there's an error
        }
    }

	public static String storeFile(MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}
}