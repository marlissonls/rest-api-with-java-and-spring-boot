package org.marlisson.restwithspringboot.file.exporter.contract;

import org.marlisson.restwithspringboot.data.dto.PersonDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileExporter {

    Resource exportFile(List<PersonDTO> people) throws Exception;
    //Resource exportPerson(PersonDTO person) throws Exception;
}
