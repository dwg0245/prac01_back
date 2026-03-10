package com.example.demo.upload;

import com.example.demo.utils.UploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LocalUploadService implements UploadService {
    private final UploadUtil uploadUtil;
    @Value("${project.upload.path}")
    private String defaultUploadPath;

    // 사용자가 보낸 파일을 개별로 받아 저장하고 저장된 경로를 문자열로 반환하는 메소드
    private String saveFile(MultipartFile file) {
        // 날짜별로 파일을 생성해서 변수에 넣기
        String uploadPath = uploadUtil.makeFolder();

        // 저장될 파일 이름 앞에 중복이 되지 않도록 하기 위해 고유ID(UUID)를 붙여준다.
        // C:\ upload\2024\05\22\550e8400-e29b..._my_photo.jpg
        String filePath = uploadPath + File.separator + UUID.randomUUID() + "_" +file.getOriginalFilename();
        // 저장할 정보를 가진 File 객체를 생성한다.
        File saveFile = new File(filePath);
        try {
            // 메모리에 임시 저장된 파일 데이터를 실제 하드 디스크의 경로로 복사/ 이동
            file.transferTo(saveFile);
        }catch (Exception e) {
            e.printStackTrace();
        }

        // 파일 경로 반환
        // C:\ upload\2024\05\22\550e8400-e29b..._my_photo.jpg
        return filePath;
    }

    @Override
    // 여러개의 파일을 한번에 처리하는 메소드
    public List<String> upload(List<MultipartFile> fileList) {
        // 여러 파일을 저장할 List 만들어주기
        // List<String> uploadPathList = new ArrayList<>();
        // 매개변수로 받아왔던 파일들을 하나씩 꺼내서 만들어주었던 list에 넣어준다.
        // for(MultipartFile file : fileList) {
        // 위에 만들어 주었던 메소드를 이용해서 경로를 반환하여 변수에 넣어준다.
        // String uploadPath = saveFile(file);
        // List에 넣는다.
        // uploadPathList.add(uploadPath);
        // }
        // List를 반환한다.
        // return uploadPathList;

        // 위에 실행하고 있는 반복문을 스트림을 이용해 간단하게 작성
        // 위에 메소드를 실행하여 저장된 경로들이 담긴 결과물을 리스트로 묶는다.

        // Stream의 .map(): "A라는 형태의 데이터를 B라는 형태로 **변형(Mapping)**한다"는 뜻의 동사
        // 매개변수 타입을 메소드의 타입으로 바꾸고 반환해 줄때마다 list에 넣어라
        return fileList.stream().map(this::saveFile).toList();
    }
}
