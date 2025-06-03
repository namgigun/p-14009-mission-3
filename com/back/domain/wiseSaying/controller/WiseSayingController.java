package com.back.domain.wiseSaying.controller;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.service.WiseSayingService;

import java.util.*;

public class WiseSayingController {
    private final Scanner sc;
    private final WiseSayingService wiseSayingService;
    public WiseSayingController() {
        sc = new Scanner(System.in);
        wiseSayingService = new WiseSayingService();
    }

    public void actionEnroll() {
        // 삽입할 명언 정보를 입력
        String wiseSay;
        do {
            System.out.print("명언 : ");
            wiseSay = sc.nextLine().trim();
        }  while(wiseSayingService.isAsterisk(wiseSay));

        String writer;
        do {
            System.out.print("작가 : ");
            writer = sc.nextLine().trim();
        } while(wiseSayingService.isAsterisk(writer));

        // 입력 정보를 저장
        WiseSaying wiseSaying = wiseSayingService.save(new WiseSaying(wiseSay, writer));
        System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
    }

    public void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        List<WiseSaying> list = wiseSayingService.findForList();

        // 리스트 출력
        for(WiseSaying wiseSaying : list) {
            System.out.println(wiseSaying.getId() + " / "+ wiseSaying.getWriter() + " / "
                    +  wiseSaying.getWiseSay());
        }
    }

//    public void actionBuild() {
//
//    }

    public void actionDelete(String cmd) {
        StringTokenizer st = new StringTokenizer(cmd,"삭제?id=");
        int deleteId = Integer.parseInt(st.nextToken());

        // id 값에 해당하는 레포지토리로부터 명언정보를 삭제한다.
        boolean isSuccess = wiseSayingService.delete(deleteId);

        if(!isSuccess) {
            System.out.println(deleteId + "번 명언은 존재하지 않습니다.");
            return;
        }

        System.out.println(deleteId + "번 명언이 삭제되었습니다.");
    }

    public void actionRevise(String cmd) {
        // 명령어로부터 id 값을 추출
        StringTokenizer st = new StringTokenizer(cmd,"수정?id=");
        int reviseId = Integer.parseInt(st.nextToken());

        WiseSaying findOne = wiseSayingService.findById(reviseId);
        // id 값에 해당하는 정보가 없는 경우
        if(findOne == null) {
            System.out.println(reviseId + "번 명언은 존재하지 않습니다.");
            return;
        }

        // 수정할 정보를 입력
        System.out.println("명언(기존) : " + findOne.getWiseSay());

        String wiseSay;
        do {
            System.out.print("명언 : ");
            wiseSay = sc.nextLine().trim();
        }  while(wiseSayingService.isAsterisk(wiseSay));

        System.out.println("작가(기존) : " + findOne.getWriter());

        String writer;
        do {
            System.out.print("작가 : ");
            writer = sc.nextLine().trim();
        } while(wiseSayingService.isAsterisk(writer));

        wiseSayingService.revise(reviseId, wiseSay, writer);
    }

    public void actionSaveInFile() {
        wiseSayingService.saveInFile();
    }

    public void actionBuild() {
        wiseSayingService.build();
    }
}
