package com.back.domain.wiseSaying.service;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;

import java.util.Collections;
import java.util.List;

public class WiseSayingService {
    private final String regex;

    private final WiseSayingRepository wiseSayingRepository;
    public WiseSayingService() {
        regex = ".*[!@#$%^&*()\\-_=+\\[\\]{}|\\\\;:'\",<>/?].*";
        wiseSayingRepository = new WiseSayingRepository();
    }

    // 특수문자 체크
    public boolean isAsterisk(String wiseSay) {
        if (wiseSay.matches(regex)) {
            System.out.println("특수문자가 포함되었습니다. 다시 입력해주세요.");
            return true;
        }

        return false;
    }

    public WiseSaying save(WiseSaying wiseSaying) {
        return wiseSayingRepository.save(wiseSaying);
    }

    public boolean delete(int deleteId) {
        return wiseSayingRepository.delete(deleteId);
    }

    public WiseSaying findById(int id) {
        return wiseSayingRepository.findById(id);
    }

    public void revise(int reviseId, String wiseSay, String writer) {
        wiseSayingRepository.revise(reviseId, wiseSay, writer);
    }

    public List<WiseSaying> findForList() {
        List<WiseSaying> findForList = wiseSayingRepository.findAll();
        Collections.reverse(findForList);
        return findForList;
    }

    public void saveInFile() {
        wiseSayingRepository.saveInFile();
    }

    public void build() {
        wiseSayingRepository.buildFiles();
    }
}
