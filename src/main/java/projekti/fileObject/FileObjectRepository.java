package projekti.fileObject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileObjectRepository extends JpaRepository<FileObject, Long> {
    FileObject findByAccountId(Long id);
}
