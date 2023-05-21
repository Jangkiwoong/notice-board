package com.study.demo.service;

import com.study.demo.entity.Board;
import com.study.demo.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public void write(Board board)  {
        boardRepository.save(board);
    }



    public Page<Board> demoList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }


    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }


    public Board demoView(Integer id){

        return boardRepository.findById(id).get();
    }


    public void demoDelete(Integer id){

        boardRepository.deleteById(id);
    }



}
