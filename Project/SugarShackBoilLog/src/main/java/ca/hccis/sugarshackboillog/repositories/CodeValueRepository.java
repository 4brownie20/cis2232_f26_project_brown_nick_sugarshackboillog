package ca.hccis.sugarshackboillog.repositories;

import ca.hccis.sugarshackboillog.jpa.entity.CodeValue;
import ca.hccis.sugarshackboillog.jpa.entity.CodeValueId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeValueRepository extends CrudRepository<CodeValue, CodeValueId> {
}