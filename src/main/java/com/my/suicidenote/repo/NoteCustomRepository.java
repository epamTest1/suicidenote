package com.my.suicidenote.repo;

import com.my.suicidenote.dto.Note;

public interface NoteCustomRepository {
	public void moveNoteToSent(Note note);
}
