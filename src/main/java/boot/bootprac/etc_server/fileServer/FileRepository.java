package boot.bootprac.etc_server.fileServer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    @Query("select f from FileEntity f where f.id in :ids")
    List<FileEntity> findAllById(List<Long> ids);

}
