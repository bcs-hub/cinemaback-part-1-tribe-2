package ee.bcs.cinemaback.persistence.room.seat;

import ee.bcs.cinemaback.persistence.room.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @NotNull
    @Column(name = "col", nullable = false)
    private Integer col;

    @NotNull
    @Column(name = "\"row\"", nullable = false)
    private Integer row;

}
