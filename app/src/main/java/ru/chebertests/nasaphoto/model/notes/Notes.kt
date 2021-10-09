package ru.chebertests.nasaphoto.model.notes

open class Notes (open var noteTitle: String, open var noteText: String)

data class Note(override var noteTitle: String, override var noteText: String) : Notes(noteTitle, noteText)

data class TodoNote(override var noteTitle: String, override var noteText: String) : Notes(noteTitle, noteText)