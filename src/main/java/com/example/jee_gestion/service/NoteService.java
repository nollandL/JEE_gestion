package com.example.jee_gestion.service;

import com.example.jee_gestion.Model.Note;
import com.example.jee_gestion.Model.Etudiant;
import com.example.jee_gestion.Model.Matiere;
import com.example.jee_gestion.repository.NoteRepository;

import jakarta.transaction.Transactional;

import com.example.jee_gestion.repository.EtudiantRepository;
import com.example.jee_gestion.repository.MatiereRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final EtudiantRepository etudiantRepository;
    private final MatiereRepository matiereRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, EtudiantRepository etudiantRepository, MatiereRepository matiereRepository) {
        this.noteRepository = noteRepository;
        this.etudiantRepository = etudiantRepository;
        this.matiereRepository = matiereRepository;
    }

    public void saveNoteForEtudiant(Note note, Long etudiantId, Long matiereId) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new IllegalArgumentException("Étudiant non trouvé"));
        Matiere matiere = matiereRepository.findById(matiereId)
                .orElseThrow(() -> new IllegalArgumentException("Matière non trouvée"));

        note.setEtudiant(etudiant);
        note.setMatiere(matiere);
        noteRepository.save(note);
    }
    
    public Integer getMaxNoteCountForEtudiant(Long etudiantId) {
        return noteRepository.findMaxNoteCountForEtudiant(etudiantId);
    }

    public void deleteLastNoteForEtudiantAndMatiere(Long etudiantId, Long matiereId) {
        List<Note> notes = noteRepository.findLastNoteForEtudiantAndMatiere(etudiantId, matiereId);
        if (!notes.isEmpty()) {
            noteRepository.delete(notes.get(0)); // Supprime la première (dernière) note
        }
    }
    
    
    public Map<String, Object> getNotesAndAverages(Long etudiantId) {
        Map<String, Object> data = new HashMap<>();

        // Récupérer toutes les matières pour lesquelles l'étudiant a des notes
        List<Long> matiereIds = noteRepository.findDistinctMatiereIdsByEtudiant(etudiantId);

        for (Long matiereId : matiereIds) {
            // Obtenir le nom de la matière
            Matiere matiere = matiereRepository.findById(matiereId)
                    .orElseThrow(() -> new IllegalArgumentException("Matière non trouvée"));

            // Récupérer les notes pour l'étudiant dans cette matière
            List<Note> notes = noteRepository.findByEtudiantAndMatiere(etudiantId, matiereId);

            // Calculer la moyenne des notes pour cet étudiant dans cette matière
            Double moyenneEtudiant = noteRepository.findAverageByEtudiantAndMatiere(etudiantId, matiereId);

            // Calculer la moyenne générale de la matière
            Double moyenneGenerale = noteRepository.findGeneralAverageByMatiere(matiereId);

            // Structure pour stocker les informations par matière
            Map<String, Object> matiereData = new HashMap<>();
            matiereData.put("matiereNom", matiere.getNom());
            matiereData.put("notes", notes);
            matiereData.put("moyenneEtudiant", moyenneEtudiant);
            matiereData.put("moyenneGenerale", moyenneGenerale);

            data.put(matiereId.toString(), matiereData);
        }

        return data;
    }

    @Transactional
    public void ajouterNotePourEtudiant(Long etudiantId, Long matiereId, Float noteValeur) {
        // Vérifier si l'étudiant existe
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new IllegalArgumentException("Étudiant non trouvé avec l'ID : " + etudiantId));

        // Vérifier si la matière existe
        Matiere matiere = matiereRepository.findById(matiereId)
                .orElseThrow(() -> new IllegalArgumentException("Matière non trouvée avec l'ID : " + matiereId));

        // Créer une nouvelle instance de Note
        Note note = new Note();
        note.setEtudiant(etudiant);
        note.setMatiere(matiere);
        note.setNote(noteValeur);

        // Sauvegarder la note dans la base de données
        noteRepository.save(note);
    }
}
