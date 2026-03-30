package org.marlisson.restwithspringboot.file.importer.factory;

import org.marlisson.restwithspringboot.exception.BadRequestException;
import org.marlisson.restwithspringboot.file.importer.contract.FileImporter;
import org.marlisson.restwithspringboot.file.importer.impl.CsvImporter;
import org.marlisson.restwithspringboot.file.importer.impl.XlsxImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileImporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileImporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileImporter getImporter(String fileName) throws Exception {
        if (fileName.endsWith(".xlsx")) {
            // return new XlsxImporter(); não faz "new". usar injeção de dependência
            return context.getBean(XlsxImporter.class);
        } else if (fileName.endsWith(".csv")) {
            return context.getBean(CsvImporter.class);
        } else {
            throw new BadRequestException("Invalid File Format!");
        }
    }

}