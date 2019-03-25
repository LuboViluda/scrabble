package com.srable.corporation.scrabble;

import com.srable.corporation.scrabble.player.Player;
import com.srable.corporation.scrabble.player.PlayerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.srable.corporation.scrabble.ControllerUtils.makeLink;

/**
 * Player controller provides rest API endpoints.
 * Created by lubomir.viluda on 3/23/2019.
 */
@RestController
public class PlayerController {
    private PlayerManager playerManager;

    @Autowired
    public PlayerController(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @GetMapping(path = "/players")
    public ResponseEntity<List<PlayerDto>> get() {
        List<Player> players = playerManager.getPlayers();

        List<PlayerDto> playerDtos = players.stream().map(this::convertPlayerToDto).collect(Collectors.toList());

        return new ResponseEntity<>(playerDtos, HttpStatus.OK);
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable String id) {
        Optional<Player> player = playerManager.getPlayer(id);

        Player player1 = player.orElseThrow(() -> new PlayerNotFoundException("Coudn't find player with id: " + id));

        PlayerDto playerDto = convertPlayerToDto(player1);
        playerDto.setStatistics(playerManager.getPlayersStatistics(id));

        return new ResponseEntity<>(playerDto, HttpStatus.OK);
    }

    @GetMapping("/players/leaderboard")
    public ResponseEntity<List<PlayerDto>> getLeaderBoard() {
        List<Player> leadersBoard = playerManager.getLeadersBoard();
        ArrayList<PlayerDto> playerDtos = new ArrayList<>();
        for (Player player : leadersBoard) {
            PlayerDto playerDto = convertPlayerToDto(player);
            playerDtos.add(playerDto);
        }
        return new ResponseEntity<>(playerDtos, HttpStatus.OK);
    }

    private PlayerDto convertPlayerToDto(Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setFullName(player.getFirstName() + " " + player.getLastName());
        playerDto.setHref(makeLink("/players/%s", player.getId()));
        Map<String, Number> playerStatistics = player.getPlayerStatistics();
        if (playerStatistics != null) {
            playerDto.setStatistics(playerStatistics.isEmpty() ? null : playerStatistics);
        }
        return playerDto;
    }
}
