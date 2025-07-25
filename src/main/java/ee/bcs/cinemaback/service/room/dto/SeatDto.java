package ee.bcs.cinemaback.service.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {

    private Integer id;
    private Integer row;
    private Integer col;
    private Boolean available;
    private Boolean selected = false;

}
