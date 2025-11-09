package vn.longlee.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.longlee.jobhunter.domain.Job;
import vn.longlee.jobhunter.domain.response.job.ResCreateJobDTO;
import vn.longlee.jobhunter.domain.response.job.ResUpdateJobDTO;
import vn.longlee.jobhunter.domain.response.ResultPaginationDTO;
import vn.longlee.jobhunter.service.JobService;
import vn.longlee.jobhunter.util.annotation.ApiMessage;
import vn.longlee.jobhunter.util.error.IdInvalidException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping("/jobs")
    @ApiMessage("Create a job")
    public ResponseEntity<ResCreateJobDTO> create(@Valid @RequestBody Job job) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.jobService.create(job));
    }


    @PutMapping("/jobs")
    @ApiMessage("Update a job")
    public ResponseEntity<ResUpdateJobDTO> update(@Valid @RequestBody Job job) throws IdInvalidException {
        Optional<Job> currentJob = this.jobService.fetchJobById(job.getId());
        if (!currentJob.isPresent()) {
            throw new IdInvalidException("Job not found");
        }

        return ResponseEntity.ok()
                .body(this.jobService.update(job, currentJob.get()));
    }

    @DeleteMapping("/jobs/{id}")
    @ApiMessage("Delete a job by id")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) throws IdInvalidException {
        Optional<Job> currentJob = this.jobService.fetchJobById(id);
        if (!currentJob.isPresent()) {
            throw new IdInvalidException("Job not found");
        }
        this.jobService.delete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/jobs/{id}")
    @ApiMessage("Get a job by id")
    public ResponseEntity<Job> getJob(@PathVariable("id") long id) throws IdInvalidException {
        Optional<Job> currentJob = this.jobService.fetchJobById(id);
        if (!currentJob.isPresent()) {
            throw new IdInvalidException("Job not found");
        }

        return ResponseEntity.ok().body(currentJob.get());
    }

    @GetMapping("/jobs")
    @ApiMessage("Get job with pagination")
    public ResponseEntity<ResultPaginationDTO> getAllJob(
            @Filter Specification<Job> spec,
            Pageable pageable) {

        return ResponseEntity.ok().body(this.jobService.fetchAll(spec, pageable));
    }

}
