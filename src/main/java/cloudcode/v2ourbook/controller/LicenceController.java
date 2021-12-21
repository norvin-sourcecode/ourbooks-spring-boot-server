package cloudcode.v2ourbook.controller;

import cloudcode.v2ourbook.error.ExceptionBlueprint;
import cloudcode.v2ourbook.dto.LicenceDto;
import cloudcode.v2ourbook.repositories.LicenceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LicenceController {

    LicenceRepository licenceRepository;

    public LicenceController(LicenceRepository licenceRepository) {
        this.licenceRepository = licenceRepository;
    }

    @GetMapping("/licence/{id}")
    public LicenceDto getById(@PathVariable(name = "id") Long id) throws ExceptionBlueprint {
        return new LicenceDto(licenceRepository.findById(id)
                .orElseThrow(() -> new ExceptionBlueprint("book not found","no",1)));
    }

}
