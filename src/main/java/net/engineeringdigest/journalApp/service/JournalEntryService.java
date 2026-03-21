package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;
    public List<JournalEntry> getList(){
        return journalEntryRepository.findAll();
    }

    @Transactional
    public void saveEntry(JournalEntry myEntry, String username){
        User user = userService.findByUserName(username);
        JournalEntry save = journalEntryRepository.save(myEntry);
        user.getJournEntryList().add(save);
        userService.saveEntry(user);
    }

    public void saveEntry(JournalEntry myEntry){
        journalEntryRepository.save(myEntry);
    }

    public Optional<JournalEntry> getJournalById(ObjectId journalId){
        return journalEntryRepository.findById(journalId);
    }

    public void deleteById(ObjectId journalId, String username){
        User user = userService.findByUserName(username);
        user.getJournEntryList().removeIf(x->x.getId().equals(journalId));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(journalId);
    }
}
