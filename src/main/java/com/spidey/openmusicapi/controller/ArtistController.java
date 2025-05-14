package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.common.SFPage;
import com.spidey.openmusicapi.entity.ArtistDO;
import com.spidey.openmusicapi.service.IArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.spidey.openmusicapi.utils.ControllerUtils.*;

@RequiredArgsConstructor
@RequestMapping("/artist")
@RestController
public class ArtistController {

    private final IArtistService artistService;

    @GetMapping("{artistId}")
    public ApiResponse<ArtistDO> getArtistById(@PathVariable Long artistId) {
        return getSuccess(checkNull(artistService.getArtistById(artistId), "艺术家不存在"));
    }

    @GetMapping
    public ApiResponse<SFPage<ArtistDO>> getArtistsByPage(@ModelAttribute SFModel model) {
        return getSuccess(
                SFPage.of(
                        artistService.getArtistsByPage(model,
                            ArtistDO.Fields.name,
                            ArtistDO.Fields.nickname,
                            ArtistDO.Fields.bio,
                            ArtistDO.Fields.genre)
                ).extractFrom(model));
    }

    @PreAuthorize("@perm.hasPerm('music:artist:add')")
    @PostMapping
    public ApiResponse<Boolean> addArtist(@RequestBody @Validated ArtistDO artist) {
        artist.setId(null);
        return verifyCreateResult(artistService.save(artist));
    }

    @PreAuthorize("@perm.hasPerm('music:artist:edit')")
    @PutMapping("{artistId}")
    public ApiResponse<Boolean> updateArtistById(@PathVariable Long artistId, @RequestBody @Validated ArtistDO artist) {
        artist.setId(artistId);
        return verifyUpdateResult(artistService.updateById(artist));
    }

    @PreAuthorize("@perm.hasPerm('music:artist:delete')")
    @DeleteMapping("{artistId}")
    public ApiResponse<Boolean> deleteArtist(@PathVariable Long artistId) {
        return verifyDeleteResult(artistService.removeById(artistId));
    }
}