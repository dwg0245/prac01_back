package com.example.demo.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// 오늘 날짜 별로 파일이 생기게 메소드 만들기
// 파일 중복 X 설정
@Component
public class UploadUtil {
    // yml 파일에 지정해 놓은 기본 경로 설정을 가지고 온다.
    @Value("${project.upload.path}")
    private String defaultUploadPath;

    public String makeFolder() {
        // 오늘 날짜로 변수에 저장하기
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        //윈도우 / , 리눅스 \\ 로 알아서 설정됨
        // 위에 저장한 날짜 파일에 있는 / 를 운영체제에 맞춰서 설정해준다.
        String folderPath = date.replace("/", File.separator);

        // 기본 저장 경로 + 날짜 경로를 합쳐서 최종 파일을 생성한다.
        File uploadPath = new File(defaultUploadPath + File.separator + folderPath);

        // 만약에 최종 파일의 경로가 없다면
        if (!uploadPath.exists()) {
            // 파일을 생성해라
            uploadPath.mkdirs();

        }

        // 최종적으로 만들어진 파일 경로를 반환
        return uploadPath.getPath();
    }
}
