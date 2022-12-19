package com.mugane.MakMuGaNeTalk.dto.response;

import com.mugane.MakMuGaNeTalk.entity.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagResponseDto {

    private Long id;
    private String content;

    @Builder
    public TagResponseDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public static TagResponseDto of(Tag tag) {
        return TagResponseDto.builder()
            .id(tag.getId())
            .content(tag.getContent())
            .build();
    }
}
