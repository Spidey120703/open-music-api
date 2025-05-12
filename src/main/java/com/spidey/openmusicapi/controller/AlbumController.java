package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.common.SFPage;
import com.spidey.openmusicapi.entity.AlbumDO;
import com.spidey.openmusicapi.service.IAlbumService;
import com.spidey.openmusicapi.utils.SFPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.spidey.openmusicapi.utils.ControllerUtils.*;

@RequiredArgsConstructor
@RequestMapping("/album")
@RestController
public class AlbumController {

    private final IAlbumService albumService;

    @GetMapping("{albumId}")
    public ApiResponse<AlbumDO> getAlbumById(@PathVariable Long albumId) {
        return getSuccess(checkNull(albumService.getByIdDeep(albumId), "专辑不存在"));
    }

    @GetMapping
    public ApiResponse<SFPage<AlbumDO>> getAlbumsByPage(@ModelAttribute SFModel model) {
        return getSuccess(
                SFPageUtils.pagingDeep(
                        albumService,
                        model,
                        AlbumDO.Fields.title,
                        AlbumDO.Fields.genre,
                        AlbumDO.Fields.releaseDate));
    }

    @PreAuthorize("@perm.hasPerm('music:album:add')")
    @PostMapping
    public ApiResponse<Boolean> addAlbum(@RequestBody @Validated AlbumDO album) {
        return verifyCreateResult(albumService.save(album));
    }

    @PreAuthorize("@perm.hasPerm('music:album:edit')")
    @PutMapping("{albumId}")
    public ApiResponse<Boolean> updateAlbumById(@PathVariable Long albumId, @RequestBody @Validated AlbumDO album) {
        album.setId(albumId);
        return verifyUpdateResult(albumService.updateById(album));
    }

    @PreAuthorize("@perm.hasPerm('music:album:delete')")
    @DeleteMapping("{albumId}")
    public ApiResponse<Boolean> deleteAlbum(@PathVariable Long albumId) {
        return verifyDeleteResult(albumService.removeById(albumId));
    }
}