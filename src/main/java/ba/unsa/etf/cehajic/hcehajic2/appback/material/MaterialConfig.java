package ba.unsa.etf.cehajic.hcehajic2.appback.material;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
    @Bean
    CommandLineRunner commandLineRunnerMaterial(MaterialRepository repository) {
        return args -> {
            // Add photo
            String photoName = "example_photo.jpg";
            String photoContentType = "image/jpeg";
            byte[] photoData = readMediaFileBytes("path/to/photo.jpg");
            Material photoMaterial = new Material(photoName, photoContentType, photoData);
            repository.save(photoMaterial);

            // Add video
            String videoName = "example_video.mp4";
            String videoContentType = "video/mp4";
            byte[] videoData = readMediaFileBytes("path/to/video.mp4");
            Material videoMaterial = new Material(videoName, videoContentType, videoData);
            repository.save(videoMaterial);
        };
    }

    private byte[] readMediaFileBytes(String filePath) throws IOException {
        // Read the media file as bytes
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }*/