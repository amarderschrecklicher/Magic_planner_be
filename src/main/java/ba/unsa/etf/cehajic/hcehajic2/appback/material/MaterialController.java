package ba.unsa.etf.cehajic.hcehajic2.appback.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/material")
@CrossOrigin
class MaterialController {

    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService= materialService;
    }

    @PostMapping("/save")
    public ResponseEntity<Material> createMaterial(@RequestBody Material requestDTO) throws Exception {
        Material createdMaterial = materialService.saveMaterial(requestDTO);
        return ResponseEntity.ok().body(createdMaterial);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> getMaterialById(@PathVariable Long id) throws Exception {
        Material material = materialService.getMaterialById(id);
        return ResponseEntity.ok().body(material);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Material> updateMaterial(@PathVariable Long id, @RequestBody Material requestDTO) throws Exception {
        Material material = new Material(requestDTO.getName(),requestDTO.getContentType(),requestDTO.getData());
        Material updatedMaterial = materialService.updateMaterial(id, material);
        return ResponseEntity.ok().body(updatedMaterial);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) throws Exception {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }
}