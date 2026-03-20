package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public List<JournalEntry> getList(){
        return journalEntryRepository.findAll();
    }

    public void saveEntry(JournalEntry myEntry){
         journalEntryRepository.save(myEntry);
    }

    public Optional<JournalEntry> getJournalById(ObjectId journalId){
        return journalEntryRepository.findById(journalId);
    }

    public void deleteById(ObjectId journalId){
         journalEntryRepository.deleteById(journalId);
    }
}
