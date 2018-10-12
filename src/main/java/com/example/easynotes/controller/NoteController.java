package com.example.easynotes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;

@RestController
@RequestMapping("/api")
public class NoteController {
	@Autowired
	NoteRepository noteRepository;
	//Get all values
	@GetMapping("/notes")
	public List<Note> getAllNotes(){
		return noteRepository.findAll();
	}
	//Get values by id
	@GetMapping("/notes/{id}")
	public Note getNoteById(@PathVariable(value="id") Long noteId) {
		return noteRepository.findById(noteId).orElseThrow(()->new ResourceNotFoundException("Note", "id", noteId));
		
	}
	//Add new Notes
	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note) {
		return noteRepository.save(note);
	}
	@DeleteMapping("notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId){
		Note note = noteRepository.findById(noteId).orElseThrow(()->new ResourceNotFoundException("Note", "id", noteId));
		noteRepository.delete(note);
		return ResponseEntity.ok().build();
	}
	@PutMapping("/notes/{id}")
	public Note updateNote(@PathVariable(value = "id") Long noteId,@Valid @RequestBody Note noteDetails) {
		Note note = noteRepository.findById(noteId).orElseThrow(()->new ResourceNotFoundException("Note", "id", noteId));
		note.setContent(noteDetails.getContent());
		note.setTitle(noteDetails.getTitle());
		Note updateNote =noteRepository.save(note);
		return updateNote;
	}
}
