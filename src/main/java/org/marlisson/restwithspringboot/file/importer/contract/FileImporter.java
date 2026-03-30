package org.marlisson.restwithspringboot.file.importer.contract;

import org.marlisson.restwithspringboot.data.dto.PersonDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<PersonDTO> importFile(InputStream inputStream) throws Exception;
}
