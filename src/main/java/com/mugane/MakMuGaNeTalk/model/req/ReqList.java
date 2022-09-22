package com.mugane.MakMuGaNeTalk.model.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqList {

    // TODO validation check
    private Long prevLastSeq;

    private Integer pageSize = 20;
}
