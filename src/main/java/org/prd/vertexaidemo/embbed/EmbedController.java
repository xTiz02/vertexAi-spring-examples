package org.prd.vertexaidemo.embbed;

import org.springframework.ai.embedding.Embedding;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/embed")
public class EmbedController {

    private final EmbedService ems;

    public EmbedController(EmbedService ems) {
        this.ems = ems;
    }

    @GetMapping("/text")
    public List<Embedding> getEmbedding(@RequestParam String message) {
        return ems.getEmbedding(message);
    }
}
