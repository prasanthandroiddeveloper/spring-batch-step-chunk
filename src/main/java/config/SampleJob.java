package config;

import listener.FirstJobListener;
import listener.FirstStepListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import processor.FirstItemProcessor;
import reader.FirstItemReader;
import writer.FirstItemWriter;

@Configuration
public class SampleJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private FirstJobListener firstJobListener;

    @Autowired
    private FirstStepListener firstStepListener;

    @Autowired
    private FirstItemReader firstItemReader;

    @Autowired
    private FirstItemProcessor firstItemProcessor;

    @Autowired
    private FirstItemWriter firstItemWriter;

    @Bean
    public Job firstJob() {
        return jobBuilderFactory.get("First Job")
                .start(firstStep()).next(secondStep()).next(thirdStep()).listener(firstJobListener)
                .build();
    }

    @Bean
    public Job secondJob() {
        return jobBuilderFactory.get("Second Job").incrementer(new RunIdIncrementer()).
                start(firstChunkStep()).build();
    }

    public Step firstChunkStep() {
        return stepBuilderFactory.get("First Chunk step").<Integer,Long>chunk(2).reader(firstItemReader).
                processor(firstItemProcessor).writer(firstItemWriter).build();
    }

    private Step firstStep() {
        return stepBuilderFactory.get("First Step")
                .tasklet(firstTask()).listener(firstStepListener)
                .build();
    }

    private Step secondStep() {
        return stepBuilderFactory.get("Second Step")
                .tasklet(secondTask())
                .build();
    }
    private Step thirdStep() {
        return stepBuilderFactory.get("Third Step")
                .tasklet(thirdTask())
                .build();
    }

    private Tasklet firstTask() {
        return (contribution, chunkContext) -> {
            System.out.println("This is first tasklet step");
            return RepeatStatus.FINISHED;
        };
    }
    private Tasklet secondTask() {
        return (contribution, chunkContext) -> {
            System.out.println("This is second tasklet step");
            return RepeatStatus.FINISHED;
        };
    }

    private Tasklet thirdTask() {
        return (contribution, chunkContext) -> {
            System.out.println("This is second tasklet step");
            return RepeatStatus.FINISHED;
        };
    }
}
