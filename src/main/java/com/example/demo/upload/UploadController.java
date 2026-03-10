package com.example.demo.upload;

    import com.example.demo.board.model.BoardDto;
import com.example.demo.common.model.BaseResponse;
import lombok.RequiredArgsConstructor;
    import org.springframework.context.annotation.Primary;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;
    import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/upload")
@RequiredArgsConstructor
@RestController
// 원하는 클래스 앞에 @Primary를 달아두면 해당 객체가 주입된다.
//@Primary
public class UploadController {
//    의존성 주입할 객체가 프로젝트 내에 똑같은 타입이 여러개 존재할 때
//    @Qualifier(변수앞) or @Primary(클래스앞)를 달아두면 해당 객체가 주입된다.
//    @Qualifier(value = "cloudUploadService")
    private final UploadService uploadService;

    @PostMapping("/image")
    // 파일을 매개변수로 받아서 실행하는 메소드
    public ResponseEntity upload(List<MultipartFile> images) {
        // 메소드를 실행해서 최종 경로를 반환한다.
        List<String> result = uploadService.upload(images);
        // 응답
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @PostMapping("/imageWithDto")
    public ResponseEntity upload(
            @RequestPart BoardDto.RegReq dto,
            @RequestPart List<MultipartFile> images
    ) {
        System.out.println(dto.getTitle());
        System.out.println(images.get(0).getOriginalFilename());

        return ResponseEntity.ok("");
    }
}
