package com.back;

import com.back.domain.system.controller.SystemController;
import com.back.domain.wiseSaying.controller.WiseSayingController;

import java.util.Scanner;

public class App {
    private final Scanner sc = new Scanner(System.in);
    private final WiseSayingController wiseSayingController;

    private final SystemController systemController;

    public App() {
        wiseSayingController = new WiseSayingController();
        systemController = new SystemController();
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        // 명령입력
        label:
        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine().trim();

            switch (cmd) {
                case "종료":
                    systemController.actionExit();
                    break label;
                case "등록":
                    wiseSayingController.actionEnroll();
                    break;
                case "목록":
                    wiseSayingController.actionList();
                    break;
                case "빌드":
                    wiseSayingController.actionBuild();
                    System.out.println("data.json 파일의 내용이 갱신되었습니다.");
                    break;
                default:
                    if (cmd.startsWith("삭제")) {
                        wiseSayingController.actionDelete(cmd);
                    } else if (cmd.startsWith("수정")) {
                        wiseSayingController.actionRevise(cmd);
                    }
                    break;
            }
        }

        // 파일에 정보 저장
        wiseSayingController.actionSaveInFile();
    }
}
