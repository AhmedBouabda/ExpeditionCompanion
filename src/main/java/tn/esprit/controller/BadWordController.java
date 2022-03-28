package tn.esprit.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.Config.BadWordConfig;

@RestController
@RequestMapping("/dict")

public class BadWordController {


	 BadWordConfig badWordConfig  = new BadWordConfig();
	 @PostMapping("/{in}")
	    public String badWord(@PathVariable("in") String input){
	    return  badWordConfig.filterText(input) ;}
	}



