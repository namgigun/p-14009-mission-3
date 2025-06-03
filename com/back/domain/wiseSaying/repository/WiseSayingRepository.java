package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WiseSayingRepository {
    private final List<WiseSaying> wiseSayings;

    private int lastId;

    private String filePath = "db/wiseSaying/";

    public WiseSayingRepository() {
        wiseSayings = new ArrayList<>();

        // 파일로부터 데이터 조회
        init();
    }

    private void init() {
        String regex = "id:\\s*\"([^\"]+)\"\\s*,\\s*content:\\s*\"([^\"]+)\"\\s*,\\s*author:\\s*\"([^\"]+)\"";

        // lastId 읽어오기
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath + "lastId.txt"))) {
            lastId = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            lastId = 0;
        }

        for(int i = lastId; i >= 1; i--) {
            StringBuilder jsonReader = new StringBuilder();
            String line;

            // {id}.json 으로 부터 데이터를 받아온 후, StringBuilder 에 모든 정보를 저장한다.
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath + i + ".json"))) {
                jsonReader.append(reader.readLine());
                while ((line = reader.readLine()) != null) {
                    jsonReader.append(line.trim());
                }
            } catch (IOException e) {
                continue;
            }

            // StringBuilder 에 저장한 모든 정보를 추출하여 list 에 정보를 추가한다.
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(jsonReader.toString());

            if(matcher.find()) {
                int id = Integer.parseInt(matcher.group(1));
                String wiseSay = matcher.group(2);
                String writer = matcher.group(3);

                wiseSayings.add(new WiseSaying(id, wiseSay, writer));
            }
        }
    }

    public WiseSaying findById(int id) {
        for(WiseSaying wiseSaying : wiseSayings) {
            if(wiseSaying.getId() == id) {
                return wiseSaying;
            }
        }

        return null;
    }

    public WiseSaying save(WiseSaying wiseSaying) {
        wiseSaying.setId(++lastId);
        wiseSayings.add(wiseSaying);
        return wiseSaying;
    }

    public boolean delete(int id) {
       for(WiseSaying wiseSaying : wiseSayings) {
           if(wiseSaying.getId() == id) {
               wiseSayings.remove(wiseSaying);
               return true;
           }
       }

       return false;
    }

    public List<WiseSaying> findAll() {
        return wiseSayings;
    }

    public void revise(int id, String wiseSay, String writer) {
        int idx = 0;

        for(WiseSaying wiseSaying : wiseSayings) {
            if(wiseSaying.getId() == id) {
                wiseSayings.set(idx, new WiseSaying(id, wiseSay, writer));
                return;
            }
            idx++;
        }
    }

    /*
             다음과 같은 형식으로 StringBuilder 를 형성
             {
                 id: "1",
                 content: "현재",
                 author: "홍길동"
             }
    */
    public void saveInFile() {
        for (WiseSaying wiseSaying : wiseSayings) {
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("{\n");

            jsonBuilder
                    .append("\tid")
                    .append(": ")
                    .append("\"" + wiseSaying.getId() + "\"")
                    .append(",\n")

                    .append("\tcontent")
                    .append(": ")
                    .append("\"" + wiseSaying.getWiseSay() + "\"")
                    .append(",\n")

                    .append("\tauthor")
                    .append(": ")
                    .append("\"" + wiseSaying.getWriter() + "\"")
                    .append("\n");

            jsonBuilder.append("}");

            /*
            {id}.json 에 {id}에 해당하는 정보 저장
             */
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + wiseSaying.getId() + ".json"))) {
                writer.write(jsonBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*
        lastId.txt 파일에 가장 최신으로 사용한 lastId를 저장.
         */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "lastId.txt"))) {
            writer.write(Integer.toString(lastId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildFiles() {
        StringBuilder jsonBuilder = new StringBuilder();

        jsonBuilder.append("[\n");
        int count = wiseSayings.size();

        for (WiseSaying wiseSaying : wiseSayings) {
            count--;
            jsonBuilder.append("\t{\n");

            jsonBuilder
                    .append("\t\tid")
                    .append(": ")
                    .append("\"" + wiseSaying.getId() + "\"")
                    .append(",\n")

                    .append("\t\tcontent")
                    .append(": ")
                    .append("\"" + wiseSaying.getWiseSay() + "\"")
                    .append(",\n")

                    .append("\t\tauthor")
                    .append(": ")
                    .append("\"" + wiseSaying.getWriter() + "\"")
                    .append("\n");

            if(count == 0) {
                jsonBuilder.append("\t}\n");
                break;
            }

            jsonBuilder.append("\t},");
        }

        jsonBuilder.append("]");

        // data.json 에 StringBuilder 에 기록한 데이터를 저장
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "data.json"))) {
            writer.write(jsonBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}