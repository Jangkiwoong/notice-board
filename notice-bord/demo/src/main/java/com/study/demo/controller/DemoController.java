package com.study.demo.controller;

import com.study.demo.entity.Board;
import com.study.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DemoController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/demo/write")      //localhost:8080/demo/write
    public String DemoWriteForm(){

        return "demowrite";
    }

    @PostMapping("/demo/writepro")
    public String demoWritePro(Board board, Model model)  {

        boardService.write(board);

        model.addAttribute("message","글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl","/demo/list");

        return "message";
    }

    @GetMapping("/demo/list")
    public String demoList(Model model,
                           @PageableDefault(page = 0, size = 5, sort = "ID", direction = Sort.Direction.DESC) Pageable pageable,
                           String searchKeyword) {

        Page<Board> list = null;

        if (searchKeyword == null) {
            list = boardService.demoList(pageable);
        }
        else{
             list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage =  Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage+9, list.getTotalPages());


        model.addAttribute("list", list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "demolist";
    }


    @GetMapping("/demo/view")
    public String demoView(Model model, Integer id){
        model.addAttribute("board", boardService.demoView(id));
        return "demoview";
    }


    @GetMapping("/demo/delete")
    public String demoDelete(Integer id){

        boardService.demoDelete(id);

        return "redirect:/demo/list";
    }


    @GetMapping("/demo/modify/{id}")
    public String boardModify(@PathVariable("id")Integer id, Model model) {
        model.addAttribute("board", boardService.demoView(id));


        return "demomodify";
    }


    @PostMapping("/demo/update/{id}")
    public String demoupdate(@PathVariable("id") Integer id,Board board, Model model){
        Board boardTemp = boardService.demoView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setWriter(board.getWriter());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);

        model.addAttribute("message","글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl","/demo/list");

        return "message";
    }

}
