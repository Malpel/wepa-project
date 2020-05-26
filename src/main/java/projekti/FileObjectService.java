package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileObjectService {

    @Autowired
    FileObjectRepository fileObjectRepository;

    public List<byte[]> getAll() {

        List<byte[]> images = fileObjectRepository.findAll()
                                .stream()
                                .map(i -> i.getContent())
                                .collect(Collectors.toList());

        return images;
    }

    public FileObject save(MultipartFile file, Account account) throws IOException {

        FileObject fo = new FileObject();
        fo.setContent(file.getBytes());
        fo.setAccount(account);

        return fileObjectRepository.save(fo);
    }

    public FileObject findByAccountId(Long id) {
        return fileObjectRepository.findByAccountId(id);
    }

    public void deleteOne(FileObject fo) {
        fileObjectRepository.delete(fo);
    }
}
