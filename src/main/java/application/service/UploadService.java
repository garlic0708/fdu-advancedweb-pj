package application.service;

import java.io.IOException;

public interface UploadService {

    void uploadFile(byte[] file, String filePath, String fileName) throws IOException;
}
