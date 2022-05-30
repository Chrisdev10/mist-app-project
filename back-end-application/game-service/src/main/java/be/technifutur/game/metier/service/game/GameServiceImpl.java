package be.technifutur.game.metier.service.game;

import be.technifutur.game.exceptions.ElementNotFoundException;
import be.technifutur.game.metier.mapper.GameMapper;
import be.technifutur.game.models.dto.GameDTO;
import be.technifutur.game.models.entities.Developer;
import be.technifutur.game.models.entities.Editor;
import be.technifutur.game.models.entities.Game;
import be.technifutur.game.models.forms.GameForm;
import be.technifutur.game.repository.DeveloperRepository;
import be.technifutur.game.repository.EditorRepository;
import be.technifutur.game.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GameServiceImpl implements GameService{

    private final GameRepository repository;
    private final GameMapper mapper;
    private final DeveloperRepository developerRepository;
    private final EditorRepository editorRepository;

    public GameServiceImpl(GameRepository repository, GameMapper mapper, DeveloperRepository developerRepository, EditorRepository editorRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.developerRepository = developerRepository;
        this.editorRepository = editorRepository;
    }

    @Override
    public List<GameDTO> getGames() {
        return repository.findAll()
                .stream()
                .map(mapper::entityToDTO)
                .toList();
    }

    @Override
    public GameDTO getGameByReference(UUID reference) {
        GameDTO gameDTO = repository.findByReference(reference)
                .map(mapper::entityToDTO)
                .orElseThrow(() -> new ElementNotFoundException(reference, Game.class));
        return gameDTO;
    }

    @Override
    public GameDTO getGameByTitle(String title) {
        GameDTO gameDTO = repository.findByTitle(title)
                .map(mapper::entityToDTO)
                .orElseThrow(() -> new ElementNotFoundException(title, Game.class));
        return gameDTO;
    }

    @Override
    public GameDTO insertGame(GameForm gameForm) {
        Game entity = mapper.formToEntity(gameForm);
        Developer dev = developerRepository.findById(gameForm.getDeveloper().getId())
                .orElseThrow(()-> new ElementNotFoundException(gameForm.getDeveloper().getReference(), Developer.class));
        entity.setDeveloper(dev);
        Editor editor = editorRepository.findById(gameForm.getEditor().getId())
                .orElseThrow(()-> new ElementNotFoundException(gameForm.getEditor().getId(), Editor.class));
        entity.setEditor(editor);
        entity = repository.save(entity);
        return mapper.entityToDTO(entity);
    }

    @Override
    public GameDTO updateGame(UUID reference, GameForm gameForm) {
        Game entity = repository.findByReference(reference)
                .orElseThrow(() -> new ElementNotFoundException(reference, Game.class));
        entity.setTitle(gameForm.getTitle());
        entity.setReleaseDate(gameForm.getReleaseDate());
        entity.setGenre(gameForm.getGenre());
        entity = repository.save(entity);
        return mapper.entityToDTO(entity);
    }

    @Override
    public GameDTO deleteGame(UUID reference) {
        GameDTO dto = getGameByReference(reference);
        repository.deleteByReference(reference);
        return dto;
    }

    @Override
    public GameDTO updateDeveloperofGame(UUID gameReference, UUID devReference) {
        Game game = repository.findByReference(gameReference)
                .orElseThrow(() -> new ElementNotFoundException(gameReference, Game.class));
        Developer developer = developerRepository.findByReference(devReference)
                .orElseThrow(() -> new ElementNotFoundException(devReference, Developer.class));
        game.setDeveloper(developer);
        return mapper.entityToDTO(game);
    }

    @Override
    public GameDTO updateEditorOfGame(UUID gameReference, UUID edReference) {
        Game game = repository.findByReference(gameReference)
                .orElseThrow(() -> new ElementNotFoundException(gameReference, Game.class));
        Editor editor = editorRepository.findByReference(edReference)
                .orElseThrow(() -> new ElementNotFoundException(edReference, Developer.class));
        game.setEditor(editor);
        return mapper.entityToDTO(game);
    }
}