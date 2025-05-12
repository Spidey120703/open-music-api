package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.common.SFPage;
import com.spidey.openmusicapi.entity.SongDO;
import com.spidey.openmusicapi.service.ISongService;
import com.spidey.openmusicapi.utils.SFPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.spidey.openmusicapi.utils.ControllerUtils.*;

@RequiredArgsConstructor
@RequestMapping("/song")
@RestController
public class SongController {

    private final ISongService songService;

    @GetMapping("{songId}")
    public ApiResponse<SongDO> getSongById(@PathVariable Long songId) {
        return getSuccess(checkNull(songService.getByIdDeep(songId), "歌曲不存在"));
    }

    @GetMapping
    public ApiResponse<SFPage<SongDO>> getSongsByPage(@ModelAttribute SFModel model) {
        return getSuccess(
                SFPageUtils.pagingDeep(
                        songService,
                        model,
                        SongDO.Fields.title,
                        SongDO.Fields.genre,
                        SongDO.Fields.releaseDate));
    }

    @PreAuthorize("@perm.hasPerm('music:song:add')")
    @PostMapping
    public ApiResponse<Boolean> addSong(@RequestBody @Validated SongDO song) {
        return verifyCreateResult(songService.save(song));
    }

    @PreAuthorize("@perm.hasPerm('music:song:edit')")
    @PutMapping("{songId}")
    public ApiResponse<Boolean> updateSongById(@PathVariable Long songId, @RequestBody @Validated SongDO song) {
        song.setId(songId);
        return verifyUpdateResult(songService.updateById(song));
    }

    @PreAuthorize("@perm.hasPerm('music:song:delete')")
    @DeleteMapping("{songId}")
    public ApiResponse<Boolean> deleteSong(@PathVariable Long songId) {
        return verifyDeleteResult(songService.removeById(songId));
    }
}