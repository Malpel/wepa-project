package projekti.fileObject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface FileObjectRepository extends JpaRepository<FileObject, Long> {
    FileObject findByAccountId(Long id);
}
