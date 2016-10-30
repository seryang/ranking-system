package com.example.controller;

import com.example.entity.User;
import com.example.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seryang on 2016. 10. 30..
 */
@Controller
@RequestMapping("/rank")
public class RankController {

    @Autowired
    private RankService rankService;

    @RequestMapping(value = "/inputScore")
    @ResponseBody
    public boolean inputScore(@RequestParam(value="ptop[]")String [] ptop){
        List<User> userList = new ArrayList<>();
        for(String userArray : ptop){
            String [] userAndScore = userArray.split(":");
            String myId = userAndScore[0];
            int score = Integer.parseInt(userAndScore[1]);
            userList.add(new User(myId, score));
        }
        return rankService.bulkInsertUserAndScore(userList);
    }

    @RequestMapping(value = "/all")
    public String totalRank(Model model){
        model.addAttribute("list", rankService.selectAllRank());
        return "rank";
    }

    @RequestMapping(value = "/my")
    public String myRank(String myId, Model model){
        model.addAttribute("list", rankService.selectMyRank(myId));
        return "rank";
    }

    @RequestMapping(value = "/friend")
    public String friendRank(@RequestParam String myId, Model model){
        model.addAttribute("list", rankService.selectFriendRank(myId.trim()));
        return "rank";
    }
}
