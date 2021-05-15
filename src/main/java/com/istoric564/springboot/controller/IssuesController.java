package com.istoric564.springboot.controller;

import com.istoric564.springboot.exception.ResourceNotFoundException;
import com.istoric564.springboot.model.Issue;
import com.istoric564.springboot.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class IssuesController {

    private final IssueRepository issueRepository;

    public IssuesController(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @GetMapping("/issues")
    public List<Issue> getAllIssues(){
        return issueRepository.findAll();
    }

    @PostMapping("/issues")
    public Issue createIssue(@RequestBody Issue issue){
        return issueRepository.save(issue);
    }

    @GetMapping("/issues/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Integer id){
        Issue issue = issueRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Issue not exist with id: " + id));
        return ResponseEntity.ok(issue);
    }

    @PutMapping("/issues/{id}")
    public ResponseEntity<Issue> updateIssue(@PathVariable Integer id,@RequestBody Issue issueDetails){
        Issue issue = issueRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Issue not exist with id: " + id));
        issue.setId(issueDetails.getId());
        issue.setCreatedBy(issueDetails.getCreatedBy());
        issue.setIssueSummary(issueDetails.getIssueSummary());
        issue.setIssueDescription(issueDetails.getIssueDescription());
        issue.setCreatedOn(issueDetails.getCreatedOn());
        issue.setStatus(issueDetails.getStatus());
        issue.setTitle(issueDetails.getTitle());
        Issue updateIssue = issueRepository.save(issue);
        return ResponseEntity.ok(updateIssue);
    }
}