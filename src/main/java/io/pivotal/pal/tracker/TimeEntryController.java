package io.pivotal.pal.tracker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private final TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        this.timeEntryRepository.create(timeEntryToCreate);
        String idOfTimeEntry = String.valueOf(timeEntryToCreate.getId());
        URI location = URI.create(idOfTimeEntry);
        return ResponseEntity.created(location).body(timeEntryToCreate);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry = this.timeEntryRepository.find(id);

        return getResponseEntity(timeEntry);
    }
    private ResponseEntity getResponseEntity(TimeEntry timeEntry) {
        if (timeEntry != null) {
            return ResponseEntity.ok(timeEntry);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return ResponseEntity.ok(this.timeEntryRepository.list());
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry expected) {
        TimeEntry timeEntry = this.timeEntryRepository.update(id, expected);
        return getResponseEntity(timeEntry);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) {
        this.timeEntryRepository.delete(id);
        return ResponseEntity.noContent().build();
    }


}