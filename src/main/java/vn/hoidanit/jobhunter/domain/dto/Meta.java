package vn.hoidanit.jobhunter.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Meta {
    private int name;
    private  int pageSize;
    private int page;
    private long total;
}
