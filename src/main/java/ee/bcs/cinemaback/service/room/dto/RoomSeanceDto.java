package ee.bcs.cinemaback.service.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomSeanceDto {

    private String roomName;
    private Integer rows;
    private Integer cols;
    private List<SeatDto> seats;

}
