package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.common.SFPage;
import com.spidey.openmusicapi.entity.AlbumDO;
import com.spidey.openmusicapi.entity.ArtistDO;
import com.spidey.openmusicapi.service.IAlbumService;
import com.spidey.openmusicapi.service.IArtistAlbumService;
import com.spidey.openmusicapi.service.IArtistService;
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
@RequestMapping("/album")
@RestController
public class AlbumController {

    private final IAlbumService albumService;
    private final IArtistService artistService;
    private final IArtistAlbumService artistAlbumService;

    @GetMapping("{albumId}")
    public ApiResponse<AlbumDO> getAlbumById(@PathVariable Long albumId) {
        return getSuccess(checkNull(albumService.getAlbumById(albumId), "专辑不存在"));
    }

    @GetMapping
    public ApiResponse<SFPage<AlbumDO>> getAlbumsByPage(@ModelAttribute SFModel model) {
        return getSuccess(
                SFPage.of(
                        albumService.getAlbumsByPage(model,
                                AlbumDO.Fields.title,
                                AlbumDO.Fields.artistNames,
                                AlbumDO.Fields.genre,
                                AlbumDO.Fields.releaseDate,
                                AlbumDO.Fields.bio,
                                AlbumDO.Fields.type)
                ).extractFrom(model));
    }

    private boolean beforeSave(Long albumId, @NonNull AlbumDO album) {
        List<Long> artistIds = Arrays.stream(album.getArtistNames().split(";"))
                .map(name -> artistService.lambdaQuery()
                        .eq(ArtistDO::getName, name)
                        .one())
                .filter(Objects::nonNull)
                .map(ArtistDO::getId)
                .toList();

        return artistAlbumService.saveArtistIdsByAlbumId(albumId, artistIds);
    }

    @PreAuthorize("@perm.hasPerm('music:album:add')")
    @PostMapping
    public ApiResponse<Boolean> addAlbum(@RequestBody @Validated AlbumDO album) {
        album.setId(null);
        boolean saved = albumService.save(album);
        if (! saved) return verifyCreateResult(false);

        return verifyCreateResult(beforeSave(album.getId(), album));
    }

    @PreAuthorize("@perm.hasPerm('music:album:edit')")
    @PutMapping("{albumId}")
    public ApiResponse<Boolean> updateAlbumById(@PathVariable Long albumId, @RequestBody @Validated AlbumDO album) {
        album.setId(albumId);

        beforeSave(albumId, album);

        return verifyUpdateResult(albumService.updateById(album));
    }

    @PreAuthorize("@perm.hasPerm('music:album:delete')")
    @DeleteMapping("{albumId}")
    public ApiResponse<Boolean> deleteAlbum(@PathVariable Long albumId) {
        return verifyDeleteResult(albumService.removeById(albumId));
    }
}