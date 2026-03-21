
package net.engineeringdigest.journalApp.controller;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @PostMapping("/create/{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String username){
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/journal-list/{username}")
    public ResponseEntity<List<JournalEntry>> getAll(@PathVariable String username){
        User user = userService.findByUserName(username);
        List<JournalEntry> alllist =  user.getJournEntryList();
        if(alllist!= null && !alllist.isEmpty()){
            return new ResponseEntity<>(alllist, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/list/{journalId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId journalId){
        Optional<JournalEntry> journalEntity = journalEntryService.getJournalById(journalId);
        if(journalEntity.isPresent()){
            return new ResponseEntity<>(journalEntryService.getJournalById(journalId), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/list/{journalId}/{username}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId journalId, @PathVariable String username){
        journalEntryService.deleteById(journalId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/list/{journalId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId journalId, @RequestBody JournalEntry myEntry){
        JournalEntry old = journalEntryService.getJournalById(journalId).orElse(null);
        if(old != null){
            old.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : old.getTitle());
            old.setContent(myEntry.getContent() != null && !myEntry.equals("") ? myEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
            }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
