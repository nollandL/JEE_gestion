package com.example.jee_gestion.repository;

import com.example.jee_gestion.Model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n WHERE n.etudiant.id = :etudiantId AND n.matiere.id = :matiereId")
    List<Note> findByEtudiantAndMatiere(@Param("etudiantId") Long etudiantId, @Param("matiereId") Long matiereId);

    @Query("SELECT AVG(n.note) FROM Note n WHERE n.etudiant.id = :etudiantId AND n.matiere.id = :matiereId")
    Double findAverageByEtudiantAndMatiere(@Param("etudiantId") Long etudiantId, @Param("matiereId") Long matiereId);

    @Query("SELECT AVG(n.note) FROM Note n WHERE n.matiere.id = :matiereId")
    Double findGeneralAverageByMatiere(@Param("matiereId") Long matiereId);

    @Query("SELECT DISTINCT n.matiere.id FROM Note n WHERE n.etudiant.id = :etudiantId")
    List<Long> findDistinctMatiereIdsByEtudiant(@Param("etudiantId") Long etudiantId);
    
    @Query("SELECT MAX(noteCount) FROM (SELECT COUNT(n.id) as noteCount FROM Note n WHERE n.etudiant.id = :etudiantId GROUP BY n.matiere.id) subquery")
    Integer findMaxNoteCountForEtudiant(@Param("etudiantId") Long etudiantId);

    @Query("SELECT n FROM Note n WHERE n.etudiant.id = :etudiantId AND n.matiere.id = :matiereId ORDER BY n.id DESC")
    List<Note> findLastNoteForEtudiantAndMatiere(@Param("etudiantId") Long etudiantId, @Param("matiereId") Long matiereId);
    
    void deleteAllByEtudiantId(Long etudiantId);
}
