package com.mugane.MakMuGaNeTalk.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ListRequestDto {

    private Integer pageSize = 20;
    private Integer pageNumber = 0;
}
