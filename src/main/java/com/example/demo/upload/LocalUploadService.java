package com.example.demo.upload;

import com.example.demo.utils.UploadUtill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LocalUploadService implements UploadService {
    private final UploadUtill uploadUtill;
    public String saveFile(MultipartFile file){
        String uploadPath = uploadUtill.makeFolder(); // 오늘 날짜로 파일 생기게 하기

        String filePath = uploadPath + File.separator + UUID.randomUUID() + "_" + file.getOriginalFilename();
        File saveFile = new File(filePath);
        try {
            file.transferTo(saveFile);
        }catch (Exception e){
            e.printStackTrace();
        }

        return uploadPath;


    }

    @Override
    public List<String> upload(List<MultipartFile> fileList){
//        List<String> uploadPathList = new ArrayList<>();
//        for (MultipartFile file : fileList){
//            String uploadPath = saveFile(file);
//            uploadPathList.add(uploadPath);
//        }
//
//        return uploadPathList;

        return fileList.stream().map(this::saveFile).toList();
    }
}
