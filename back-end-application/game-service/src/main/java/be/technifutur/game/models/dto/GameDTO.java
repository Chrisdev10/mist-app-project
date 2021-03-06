package be.technifutur.game.models.dto;

import be.technifutur.game.models.entities.Genre;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Getter @Setter
public class GameDTO {

    private Long id;
    private UUID reference;
    private String title;
    private String imageUrl;
    private LocalDate releaseDate;
    private List<Genre> genres;
    private DeveloperDTO developer;
    private EditorDTO editor;


    @Builder
    @Data
    @AllArgsConstructor
    public static class DeveloperDTO{
        private UUID reference;
        private String name;
    }

    @Builder
    @Data
    @AllArgsConstructor
    public static class EditorDTO{
        private UUID reference;
        private String name;
    }
}
