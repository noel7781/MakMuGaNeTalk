package com.mugane.MakMuGaNeTalk.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListRequestDto {

    // TODO validation check
    private Long prevLastSeq;

    private Integer pageSize = 20;
}
