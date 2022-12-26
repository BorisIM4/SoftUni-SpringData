package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.repository.JobRepository;
import softuni.exam.service.JobService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class JobServiceImpl implements JobService {
    public static final String JOB_FILE_PATH = "src/main/resources/files/xml/jobs.xml";

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public boolean areImported() {
        return this.jobRepository.count() > 0;
    }

    @Override
    public String readJobsFileContent() throws IOException {
        return Files.readString(Path.of(JOB_FILE_PATH));
    }

    @Override
    public String importJobs() throws IOException {
        return null;
    }

    @Override
    public String getBestJobs() {
        return null;
    }
}
