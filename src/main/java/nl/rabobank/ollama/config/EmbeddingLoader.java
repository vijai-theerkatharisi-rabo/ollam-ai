package nl.rabobank.ollama.config;

import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.loader.github.GitHubDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.nio.file.FileSystems;

@Configuration
@RequiredArgsConstructor
public class EmbeddingLoader {

    private final InMemoryEmbeddingStore<TextSegment> embeddingStore;

    private final EmbeddingModel embeddingModel;

    private String gitHubToken;

    //@PostConstruct
    public void loadEmbeddings() {
        var pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
        var documents = FileSystemDocumentLoader.loadDocuments("/Users/vijaitheerkatharisi/Downloads/ollam" +
                "-ai/src/main/resources/", pathMatcher, new TextDocumentParser());
        var embeddingStoreIngestor=  EmbeddingStoreIngestor.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        embeddingStoreIngestor.ingest(documents);
    }
    // @PostConstruct
    public void loadCodeEmbeddings(){
        var pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**.java");
        var documents = FileSystemDocumentLoader.loadDocuments("/Users/vijaitheerkatharisi/IdeaProjects/ollam-ai/src/main/java/nl/rabobank/ollama/", pathMatcher, new ApacheTikaDocumentParser());
        var embeddingStoreIngestor=  EmbeddingStoreIngestor.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        embeddingStoreIngestor.ingest(documents);
    }
    @PostConstruct
    public void loadGitHubEmbeddings(){
        var gitHubDocumentLoader= GitHubDocumentLoader.builder()
                .gitHubToken(gitHubToken).build();
        var documents=gitHubDocumentLoader.loadDocuments("vijai-theerkatharisi-rabo", "ollam-ai", "main", "src/main/java", new ApacheTikaDocumentParser());
        var embeddingStoreIngestor=  EmbeddingStoreIngestor.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        embeddingStoreIngestor.ingest(documents);
    }
}