package com.secondprojinitiumback.admin.extracurricular.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Log4j2
public class ExtracurricularImageFileService {

    @Value("${extracurricularImage}")
    private String extracurricularImagePath;

    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("빈 파일은 저장할 수 없습니다.");
        }
        try {
            Path rootLocation = Paths.get(extracurricularImagePath);
            Files.createDirectories(rootLocation);

            String originalFilename = file.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID() + ext;

            Path destination = rootLocation.resolve(filename);
            file.transferTo(destination.toFile());

            log.info("파일 저장 완료 (절대경로): {}", destination.toAbsolutePath());
            // 💡 클라이언트가 접근 가능한 경로로 반환해야
            return "/images/" + filename;

        } catch (IOException e) {
            log.error("파일 저장 실패", e);
            throw new RuntimeException("파일 저장 실패", e);
        }
    }


    public void deleteFile(String filePath) {
        try {
            String filename = Paths.get(filePath).getFileName().toString();
            Path pathToDelete = Paths.get(extracurricularImagePath).resolve(filename);

            log.info("삭제 대상 파일 이름: {}", filename);
            log.info("삭제 절대 경로: {}", pathToDelete.toAbsolutePath());

            Files.deleteIfExists(pathToDelete);
            log.info("파일 삭제 성공: {}", filePath);
        } catch (IOException e) {
            log.error("파일 삭제 실패", e);
            throw new RuntimeException("파일 삭제 실패", e);
        }
    }
}
