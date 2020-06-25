package projekti.fileObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projekti.account.Account;

import java.io.IOException;

@Service
public class FileObjectService {

    @Autowired
    FileObjectRepository fileObjectRepository;

    public FileObject save(MultipartFile file, Account account) throws IOException {
        FileObject fo = fileObjectRepository.findByAccountId(account.getId());

        if (fo == null) {
            fo = new FileObject();
        }

        fo.setContent(file.getBytes());
        fo.setAccount(account);

        return fileObjectRepository.save(fo);
    }

    public FileObject findByAccountId(Long id) {
        return fileObjectRepository.findByAccountId(id);
    }
}
