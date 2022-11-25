package hello.upload.domain;

import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName; // 사용자가 지정한 이름
    private String storeFileName; // UUID.확장자

    public UploadFile(String originalFileName, String storeFileName) {
        this.storeFileName = storeFileName;
        this.uploadFileName = originalFileName;
    }
}
