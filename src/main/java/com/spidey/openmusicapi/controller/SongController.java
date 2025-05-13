package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.common.SFPage;
import com.spidey.openmusicapi.entity.AlbumDO;
import com.spidey.openmusicapi.entity.ArtistDO;
import com.spidey.openmusicapi.entity.SongDO;
import com.spidey.openmusicapi.service.IAlbumService;
import com.spidey.openmusicapi.service.IArtistService;
import com.spidey.openmusicapi.service.IArtistSongService;
import com.spidey.openmusicapi.service.ISongService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.spidey.openmusicapi.utils.ControllerUtils.*;

@RequiredArgsConstructor
@RequestMapping("/song")
@RestController
public class SongController {

    private final IArtistService artistService;
    private final IAlbumService albumService;
    private final ISongService songService;
    private final IArtistSongService artistSongService;

    @GetMapping("{songId}")
    public ApiResponse<SongDO> getSongById(@PathVariable Long songId) {
        return getSuccess(checkNull(songService.getByIdDeep(songId), "歌曲不存在"));
    }

    @GetMapping
    public ApiResponse<SFPage<SongDO>> getSongsByPage(@ModelAttribute SFModel model) {
        return getSuccess(
                SFPage.of(
                        songService.getSongsByPage(model,
                                SongDO.Fields.title,
                                SongDO.Fields.artistNames,
                                SongDO.Fields.albumName,
                                SongDO.Fields.genre,
                                SongDO.Fields.releaseDate,
                                SongDO.Fields.bio)
                ).extractFrom(model));
    }

    private boolean beforeSave(Long songId, @NonNull SongDO song) {
        if (song.getAlbumId() == null) {
            if (song.getAlbum() != null) {
                song.setAlbumId(song.getAlbum().getId());
                song.setAlbumName(song.getAlbum().getTitle());
            } else if (! song.getAlbumName().isEmpty()) {
                albumService
                        .lambdaQuery()
                        .like(AlbumDO::getTitle, song.getAlbumName())
                        .oneOpt()
                        .ifPresent(album -> {
                            song.setAlbumId(album.getId());
                            song.setAlbumName(album.getTitle());
                        });
            }
        }
        if (song.getArtistNames().isEmpty()) {
            if (song.getAlbum() != null) {
                song.setAlbumName(song.getAlbum().getTitle());
            }
        }

        List<Long> artistIds = Arrays.stream(song.getArtistNames().split(";"))
                .map(name -> artistService.lambdaQuery()
                        .eq(ArtistDO::getName, name)
                        .one())
                .filter(Objects::nonNull)
                .map(ArtistDO::getId)
                .toList();

        return artistSongService.saveArtistIdsBySongId(songId, artistIds);
    }

    @PreAuthorize("@perm.hasPerm('music:song:add')")
    @PostMapping
    public ApiResponse<Boolean> addSong(@RequestBody @Validated SongDO song) {
        song.setId(null);
        boolean saved = songService.save(song);
        if (! saved) return verifyCreateResult(false);

        return verifyCreateResult(beforeSave(song.getId(), song));
    }

    @PreAuthorize("@perm.hasPerm('music:song:edit')")
    @PutMapping("{songId}")
    public ApiResponse<Boolean> updateSongById(@PathVariable Long songId, @RequestBody @Validated SongDO song) {
        song.setId(songId);

        beforeSave(songId, song);

        return verifyUpdateResult(songService.updateById(song));
    }

    @PreAuthorize("@perm.hasPerm('music:song:delete')")
    @DeleteMapping("{songId}")
    public ApiResponse<Boolean> deleteSong(@PathVariable Long songId) {
        return verifyDeleteResult(songService.removeById(songId));
    }
}